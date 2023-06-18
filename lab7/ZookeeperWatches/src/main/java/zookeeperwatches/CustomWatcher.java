package zookeeperwatches;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.zookeeper.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CustomWatcher implements Watcher {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final int SESSION_TIMEOUT_MS = 3000;
    private final String znodeName;
    private final String[] exec;
    private final ZooKeeper zk;
    private final Util util;
    private Process execProcess;

    public CustomWatcher(String hostPort, String znodeName, String exec[]) throws IOException {
        this.znodeName = znodeName;
        this.exec = exec;
        zk = new ZooKeeper(hostPort, SESSION_TIMEOUT_MS, this);
        util = new Util(zk);
    }

    public void start() {
        try {
            zk.addWatch(znodeName, AddWatchMode.PERSISTENT_RECURSIVE);
        } catch (KeeperException | InterruptedException e) {
            e.printStackTrace();
        }
        enterInputLoop();
    }

    private void enterInputLoop() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            try {
                String input = br.readLine();
                if (input.equals("quit")) {
                    break;
                } else if (input.equals("tree")) {
                    util.printTree(znodeName);
                }
            } catch (IOException ignored) {
                // ignored
            }
        }
    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        String watchedEventPath = watchedEvent.getPath();
        if (watchedEvent.getType() == Event.EventType.NodeCreated) {
            handleNodeCreatedEvent(watchedEventPath);
        } else if (watchedEvent.getType() == Event.EventType.NodeDeleted) {
            handleNodeDeletedEvent(watchedEventPath);
        }
    }

    private void handleNodeCreatedEvent(String watchedEventPath) {
        LOGGER.info("Created zNode `{}`", watchedEventPath);
        if (watchedEventPath.startsWith(znodeName)) {
            if (watchedEventPath.length() == znodeName.length()) {
                LOGGER.info("Running exec `{}`", exec);
                try {
                    execProcess = Runtime.getRuntime().exec(exec);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    LOGGER.info("zNode `{}` has {} children", znodeName, zk.getAllChildrenNumber(znodeName));
                } catch (KeeperException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void handleNodeDeletedEvent(String watchedEventPath) {
        LOGGER.info("Deleted zNode `{}`", watchedEventPath);
        if (znodeName.equals(watchedEventPath)) {
            if (execProcess == null || !execProcess.isAlive()) {
                LOGGER.info("Exec already dead");
            } else {
                LOGGER.info("Killing exec");
                execProcess.destroy();
            }
        }
    }
}
