package dynamiccalls.server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dynamiccalls.Executors.ExecutionRequest;
import dynamiccalls.Executors.ExecutionResponse;
import dynamiccalls.Executors.ExecutionServiceGrpc;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nullable;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

public class ExecutionServiceImpl extends ExecutionServiceGrpc.ExecutionServiceImplBase {
    private static final Logger LOGGER = LogManager.getLogger(ExecutionServiceImpl.class);
    private static final Gson GSON = new GsonBuilder()
            .setDateFormat("dd-MM-yyyy HH:mm")
            .setLenient() // Makes the parser very tolerant, even to allow malformed JSON data
            .create();

    private void onError(String message, ExecutionResponse.Builder responseBuilder) {
        LOGGER.error(message);
        responseBuilder.setError(message);
    }

    private String getErrorMessageWithName(Exception e) {
        return String.format("{} - {}", e.getClass(), e.getMessage());
    }

    @Nullable
    private URL getJarUrl(String jarLocation, ExecutionResponse.Builder responseBuilder) {
        jarLocation = "file://" + jarLocation;
        URL jarUrl = null;
        try {
            jarUrl = new URL(jarLocation);
            LOGGER.info("Successfully retrieved the JAR's URL");
        } catch (MalformedURLException e) {
            onError(getErrorMessageWithName(e), responseBuilder);
        }
        return jarUrl;
    }

    @Nullable
    private Class getClass(URL jarUrl, String className, ExecutionResponse.Builder responseBuilder) {
        ClassLoader classLoader = ExecutionServiceImpl.class.getClassLoader();
        URLClassLoader urlClassLoader = new URLClassLoader(new URL[]{jarUrl}, classLoader);
        Class clazz = null;
        try {
            clazz = urlClassLoader.loadClass(className);
            LOGGER.info("Found the class");
        } catch (ClassNotFoundException e) {
            onError("Couldn't find the class", responseBuilder);
        }
        return clazz;
    }

    @Nullable
    private Method getMethod(Class clazz, String methodName, ExecutionResponse.Builder responseBuilder) {
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            LOGGER.debug("Method's name: {}", method.getName());
            if (method.getName().equals(methodName)) {
                Class[] parameterTypes = method.getParameterTypes();
                LOGGER.info("Found the method, parameter types: {}", parameterTypes);
                try {
                    if (1 == parameterTypes.length) {
                        return clazz.getMethod(methodName, new Class[]{parameterTypes[0]});
                    } else if (0 == parameterTypes.length) {
                        return clazz.getMethod(methodName);
                    }
                } catch (NoSuchMethodException e) {
                    break;
                }
            }
        }
        onError("Couldn't find the method", responseBuilder);
        return null;
    }

    @Nullable
    private Object getExecutionResult(Class clazz, Method method, String data, ExecutionResponse.Builder responseBuilder) throws Exception {
        Object result = null;
        Object object = clazz.getDeclaredConstructor().newInstance();
        Class[] parameterTypes = method.getParameterTypes();
        if (1 == parameterTypes.length) {
            result = method.invoke(object, GSON.fromJson(data, parameterTypes[0]));
        } else if (0 == parameterTypes.length) {
            result = method.invoke(object);
        }
        return result;
    }

    @Override
    public void execute(ExecutionRequest request,
                        io.grpc.stub.StreamObserver<ExecutionResponse> responseObserver) {
        LOGGER.info("Handling request: '{}'", request);
        ExecutionResponse.Builder responseBuilder = ExecutionResponse.newBuilder();

        URL jarUrl = getJarUrl(request.getJarLocation(), responseBuilder);
        if (null != jarUrl) {
            Class clazz = getClass(jarUrl, request.getClassName(), responseBuilder);
            if (null != clazz) {
                Method method = getMethod(clazz, request.getMethodName(), responseBuilder);
                if (null != method) {
                    try {
                        Object executionResult = getExecutionResult(clazz, method, request.getData(), responseBuilder);
                        responseBuilder.setData(GSON.toJson(executionResult));
                        LOGGER.info("Result: '{}'", responseBuilder.getData());
                    } catch (Exception e) {
                        onError(getErrorMessageWithName(e), responseBuilder);
                    }
                }
            }
        }

        responseObserver.onNext(responseBuilder.build());
        responseObserver.onCompleted();
    }
}
