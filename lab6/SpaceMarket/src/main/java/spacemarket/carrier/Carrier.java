package spacemarket.carrier;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import spacemarket.common.Service;
import spacemarket.common.SpaceMarketUser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Carrier extends SpaceMarketUser {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final int SERVICE_TIME_MS = 0;
    private final Service[] services;

    public Carrier(Service[] services) {
        this.services = services;
    }

    @Override
    protected void connect(Service[] services) {
        super.connect(services);
        try {
            for (Service service : services) {
                String serviceName = service.toString();
                String serviceRequestQueueName = getServiceRequestQueueName(serviceName);
                channel.queueDeclare(serviceRequestQueueName, true, false, false, null);
                channel.queueBind(serviceRequestQueueName, SERVICE_REQUEST_EXCHANGE_NAME, serviceName);
                channel.basicConsume(serviceRequestQueueName, false, createConsumer());
                channel.basicQos(1);
            }
        } catch (IOException e) {
            LOGGER.error("Failed to connect, details: {}", e.getMessage());
        }
    }

    @Override
    protected Consumer createConsumer() {
        return new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
                                       byte[] body) throws IOException {
                String message = new String(body, CHARSET_NAME);
                LOGGER.info("Received request '{}', estimated processing time: {}ms", message, SERVICE_TIME_MS);
                try {
                    Thread.sleep(SERVICE_TIME_MS);
                } catch (InterruptedException ignored) {
                    // ignored
                }
                LOGGER.info("Processed request '{}', sending confirmation to the agency", message);
                String[] messageParts = message.split(MESSAGE_SEPARATOR);
                channel.basicPublish(SERVICE_RESPONSE_EXCHANGE_NAME, messageParts[0], null, message.getBytes());
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        };
    }

    @Override
    protected void enterInputLoop() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            try {
                String input = br.readLine();
                if (input.equals("quit")) {
                    end();
                    break;
                }
            } catch (IOException ignored) {
                // ignored
            }
        }
    }

    @Override
    public void start() {
        LOGGER.info("Starting carrier offering services: {}", Arrays.stream(services).map(Service::toString).toList());
        connect(services);
        enterInputLoop();
    }
}
