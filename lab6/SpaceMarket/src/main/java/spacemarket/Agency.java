package spacemarket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Agency extends SpaceMarketUser {
    private final String name;
    private int serviceRequestNumber = 0;

    public Agency(String name) {
        this.name = name;
    }

    private String nextServiceRequestId() {
        return String.format("%s%s%d", name, SpaceMarketUser.SERVICE_REQUEST_ID_SEPARATOR, serviceRequestNumber++);
    }

    private void requestService(Service service) {
        try {
            channel.basicPublish("", service.toString(), null, nextServiceRequestId().getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void start() {
        connect(Service.values());
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            try {
                String input = br.readLine();
                if (input.equals("q")) {
                    end();
                    break;
                }
                Service service = Service.fromString(input);
                if (service != null) {
                    requestService(service);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
