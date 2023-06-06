package spacemarket.main;

import lombok.extern.slf4j.Slf4j;
import spacemarket.Agency;

@Slf4j
public class Producer {
    public static void main(String[] args) {
        if (args.length < 1) {
            log.error("Agency name is required");
            return;
        }
        Agency agency = new Agency(args[0]);
        agency.start();
    }
}
