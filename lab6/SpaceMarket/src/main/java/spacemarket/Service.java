package spacemarket;

import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

import static java.util.Map.entry;

public enum Service {
    TRANSPORT_PASSENGERS,
    TRANSPORT_CARGO,
    INSTALL_SATELLITE;

    private static final Map<Service, String> toStringMap = Map.ofEntries(
            entry(TRANSPORT_PASSENGERS, "passengers"),
            entry(TRANSPORT_CARGO, "cargo"),
            entry(INSTALL_SATELLITE, "satellite")
    );
    private static final Map<String, Service> fromStringMap = Map.ofEntries(
            toStringMap.entrySet().stream()
            .map(e -> entry(e.getValue(), e.getKey()))
            .toArray(Map.Entry[]::new)
    );

    @Nullable
    @Override
    public String toString() {
        return toStringMap.get(this);
    }

    @Nullable
    public static Service fromString(String s) {
        return fromStringMap.get(s);
    }
}
