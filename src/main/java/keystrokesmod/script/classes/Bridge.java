package keystrokesmod.script.classes;

import java.util.HashMap;
import java.util.Map;

public class Bridge {
    private final Map<String, Object> map;

    public Bridge() {
        map = new HashMap<>();
    }

    public void add(String key, Object value) {
        map.put(key, value);
    }

    public void add(String key) {
        map.put(key, null);
    }

    public void remove(String key) {
        map.remove(key);
    }

    public boolean containsKey(String key) {
        return map.containsKey(key);
    }

    public Object get(String key) {
        return map.get(key);
    }

    public void clear() {
        map.clear();
    }
}
