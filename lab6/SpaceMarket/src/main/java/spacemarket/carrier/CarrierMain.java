package spacemarket.carrier;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import spacemarket.common.Service;

import java.util.Arrays;

public class CarrierMain {
    private static final Logger LOGGER = LogManager.getLogger();

    public static void main(String[] args) {
        if (args.length < 2) {
            LOGGER.error("2 services' names are required");
            return;
        }
        Service[] services = Arrays.stream(args)
                .map(Service::fromString)
                .distinct()
                .toArray(Service[]::new);
        if (services.length != 2) {
            LOGGER.error("Duplicates or additional services detected");
            return;
        }
        Carrier carrier = new Carrier(services);
        carrier.start();
    }
}
