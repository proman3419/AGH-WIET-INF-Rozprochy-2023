package dynamiccalls.action;

import java.util.List;

public class Calculator {
    public double add(List<Double> values) {
        return values.stream()
                .reduce(0.0, Double::sum);
    }

    public double multiply(List<Double> values) {
        return values.stream()
                .reduce(1.0, (a, b) -> a * b);
    }

    public double subtract(List<Double> values) {
        double result = values.get(0);
        for (int i = 1; i < values.size(); i++) {
            result -= values.get(i);
        }
        return result;
    }

    public double divide(List<Double> values) throws ArithmeticException {
        double result = values.get(0);
        for (int i = 1; i < values.size(); i++) {
            if (0 == values.get(i)) {
                throw new ArithmeticException("Division by zero");
            }
            result /= values.get(i);
        }
        return result;
    }
}
