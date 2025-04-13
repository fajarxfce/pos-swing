package event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ApplicationEventBus {
    private static final Map<String, List<EventListener>> listeners = new HashMap<>();

    public static void subscribe(String eventType, EventListener listener) {
        listeners.computeIfAbsent(eventType, k -> new ArrayList<>()).add(listener);
    }

    public static void publish(String eventType, Object data) {
        if (listeners.containsKey(eventType)) {
            for (EventListener listener : listeners.get(eventType)) {
                listener.onEvent(data);
            }
        }
    }

    public interface EventListener {
        void onEvent(Object data);
    }
}