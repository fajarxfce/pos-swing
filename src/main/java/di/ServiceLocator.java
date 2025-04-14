package di;

import java.util.HashMap;
import java.util.Map;

public class ServiceLocator {
    private static final Map<Class<?>, Object> services = new HashMap<>();

    public static <T> void register(Class<T> type, T implementation) {
        services.put(type, implementation);
    }

    @SuppressWarnings("unchecked")
    public static <T> T get(Class<T> type) {
        if (services.containsKey(type)) {
            return (T) services.get(type);
        } else {
            throw new IllegalArgumentException("Service not found: " + type.getName());
        }
    }
}