package smart.home.server;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Helper {
    private static final Random random = new Random();

    // Both inclusive
    public static int getRandomIntFromRange(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

    // Both inclusive
    public static double getRandomDoubleFromRange(double min, double max) {
        return min + random.nextDouble() * (max - min);
    }

    public static boolean checkIfValueInRange(double value, double min, double max) {
        return min <= value && value <= max;
    }
}
