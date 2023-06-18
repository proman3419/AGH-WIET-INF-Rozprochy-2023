package zookeeperwatches;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class Main {
    private static final Logger LOGGER = LogManager.getLogger();

    public static void main(String[] args) {
        if (args.length < 3) {
            LOGGER.error("Required arguments: <host>:<port> <znodeName> <execPath> <execArgs>*");
            System.exit(1);
        }
        String hostPort = args[0];
        String znodeName = args[1];
        String exec[] = new String[args.length - 2];
        System.arraycopy(args, 2, exec, 0, exec.length);

        CustomWatcher customWatcher = null;
        try {
            customWatcher = new CustomWatcher(hostPort, znodeName, exec);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(2);
        }

        customWatcher.start();
    }
}
