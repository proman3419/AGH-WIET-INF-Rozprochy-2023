package dynamic.calls.Executors;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.15.0)",
    comments = "Source: ExecutionService.proto")
public final class ExecutionServiceGrpc {

  private ExecutionServiceGrpc() {}

  public static final String SERVICE_NAME = "dynamic.calls.Executors.ExecutionService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<dynamic.calls.Executors.ExecutionRequest,
      dynamic.calls.Executors.ExecutionResponse> getExecuteMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "execute",
      requestType = dynamic.calls.Executors.ExecutionRequest.class,
      responseType = dynamic.calls.Executors.ExecutionResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<dynamic.calls.Executors.ExecutionRequest,
      dynamic.calls.Executors.ExecutionResponse> getExecuteMethod() {
    io.grpc.MethodDescriptor<dynamic.calls.Executors.ExecutionRequest, dynamic.calls.Executors.ExecutionResponse> getExecuteMethod;
    if ((getExecuteMethod = ExecutionServiceGrpc.getExecuteMethod) == null) {
      synchronized (ExecutionServiceGrpc.class) {
        if ((getExecuteMethod = ExecutionServiceGrpc.getExecuteMethod) == null) {
          ExecutionServiceGrpc.getExecuteMethod = getExecuteMethod = 
              io.grpc.MethodDescriptor.<dynamic.calls.Executors.ExecutionRequest, dynamic.calls.Executors.ExecutionResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "dynamic.calls.Executors.ExecutionService", "execute"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  dynamic.calls.Executors.ExecutionRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  dynamic.calls.Executors.ExecutionResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new ExecutionServiceMethodDescriptorSupplier("execute"))
                  .build();
          }
        }
     }
     return getExecuteMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static ExecutionServiceStub newStub(io.grpc.Channel channel) {
    return new ExecutionServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static ExecutionServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new ExecutionServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static ExecutionServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new ExecutionServiceFutureStub(channel);
  }

  /**
   */
  public static abstract class ExecutionServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void execute(dynamic.calls.Executors.ExecutionRequest request,
        io.grpc.stub.StreamObserver<dynamic.calls.Executors.ExecutionResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getExecuteMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getExecuteMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                dynamic.calls.Executors.ExecutionRequest,
                dynamic.calls.Executors.ExecutionResponse>(
                  this, METHODID_EXECUTE)))
          .build();
    }
  }

  /**
   */
  public static final class ExecutionServiceStub extends io.grpc.stub.AbstractStub<ExecutionServiceStub> {
    private ExecutionServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private ExecutionServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ExecutionServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new ExecutionServiceStub(channel, callOptions);
    }

    /**
     */
    public void execute(dynamic.calls.Executors.ExecutionRequest request,
        io.grpc.stub.StreamObserver<dynamic.calls.Executors.ExecutionResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getExecuteMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class ExecutionServiceBlockingStub extends io.grpc.stub.AbstractStub<ExecutionServiceBlockingStub> {
    private ExecutionServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private ExecutionServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ExecutionServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new ExecutionServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public dynamic.calls.Executors.ExecutionResponse execute(dynamic.calls.Executors.ExecutionRequest request) {
      return blockingUnaryCall(
          getChannel(), getExecuteMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class ExecutionServiceFutureStub extends io.grpc.stub.AbstractStub<ExecutionServiceFutureStub> {
    private ExecutionServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private ExecutionServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ExecutionServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new ExecutionServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<dynamic.calls.Executors.ExecutionResponse> execute(
        dynamic.calls.Executors.ExecutionRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getExecuteMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_EXECUTE = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final ExecutionServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(ExecutionServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_EXECUTE:
          serviceImpl.execute((dynamic.calls.Executors.ExecutionRequest) request,
              (io.grpc.stub.StreamObserver<dynamic.calls.Executors.ExecutionResponse>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class ExecutionServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    ExecutionServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return dynamic.calls.Executors.ExecutionServiceOuterClass.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("ExecutionService");
    }
  }

  private static final class ExecutionServiceFileDescriptorSupplier
      extends ExecutionServiceBaseDescriptorSupplier {
    ExecutionServiceFileDescriptorSupplier() {}
  }

  private static final class ExecutionServiceMethodDescriptorSupplier
      extends ExecutionServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    ExecutionServiceMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (ExecutionServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new ExecutionServiceFileDescriptorSupplier())
              .addMethod(getExecuteMethod())
              .build();
        }
      }
    }
    return result;
  }
}
