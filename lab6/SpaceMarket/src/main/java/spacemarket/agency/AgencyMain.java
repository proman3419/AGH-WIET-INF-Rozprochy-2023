package spacemarket.agency;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AgencyMain {
    private static final Logger LOGGER = LogManager.getLogger();

    public static void main(String[] args) {
        if (args.length < 1) {
            LOGGER.error("Agency name is required");
            return;
        }
        Agency agency = new Agency(args[0]);
        agency.start();
    }
}
