package spacemarket.main;

import lombok.extern.slf4j.Slf4j;
import spacemarket.Carrier;
import spacemarket.Service;

import java.util.Arrays;

@Slf4j
public class Consumer {
    public static void main(String[] args) {
        if (args.length < 2) {
            log.error("2 services' names are required");
            return;
        }
        Service[] services = Arrays.stream(args)
                .map(Service::fromString)
                .distinct()
                .toArray(Service[]::new);
        if (services.length != 2) {
            log.error("Duplicates or additional services detected");
            return;
        }
        Carrier carrier = new Carrier(services);
        carrier.start();
    }
}
