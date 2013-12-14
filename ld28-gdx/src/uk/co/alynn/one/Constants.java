package uk.co.alynn.one;

import java.util.Map;

public final class Constants {
    private final Map<String, String> _keys;

    public Constants(Map<String, String> mapping) {
        _keys = mapping;
        for (String k : mapping.keySet()) {
            String v = mapping.get(k);
            System.out.println(k + " := " + v);
        }
    }

    private String rawGet(String key, String comment) {
        return _keys.get(key);
    }

    public String getString(String key, String dfl, String comment) {
        String value = rawGet(key, comment);
        return value != null ? value : dfl;
    }

    public double getDouble(String key, double dfl, String comment) {
        String value = rawGet(key, comment);
        return value != null ? Double.parseDouble(value) : dfl;
    }
}
