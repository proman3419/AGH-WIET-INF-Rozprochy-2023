package spacemarket;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class Carrier extends SpaceMarketUser {
    private final static int SERVICE_TIME_MS = 1000;
    private final Service[] services;

    public Carrier(Service[] services) {
        this.services = services;
    }

    private Consumer createConsumer() {
        return new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
                                       byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                log.info("Received: '%s'", message);
                try {
                    Thread.sleep(SERVICE_TIME_MS);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        };
    }

    @Override
    public void start() {
        connect(services);
        Consumer consumer = createConsumer();
        try {
            channel.basicConsume(QUEUE_NAME, false, consumer);
            channel.basicQos(1);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
