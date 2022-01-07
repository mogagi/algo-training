package io.mogagi.quant.supports;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Local cache
 *
 * @author mogagi
 * @since 2021/10/23 22:41
 */
@Component
public class Cache {

    private final ConcurrentHashMap<String, Ventry> map = new ConcurrentHashMap<>();

    public Cache() {
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> map.entrySet().removeIf(entry -> System.currentTimeMillis() > entry.getValue().expiredAt), 0, 1, TimeUnit.HOURS);
    }

    public <T> T put(String key, T value) {
        Ventry<T> ventry = map.get(key);
        return ventry == null ? put(key, value, Long.MAX_VALUE) : put(key, value, ventry.expiredAt);
    }

    public <T> T put(String key, T value, LocalDateTime until) {
        return put(key, value, until.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
    }

    public <T> T put(String key, T value, Long expiredAt) {
        Ventry<T> ventry = map.put(key, new Ventry(value, expiredAt));
        return ventry == null ? null : ventry.val;
    }

    public <T> T get(String key) {
        return get(key, null);
    }

    public <T> T get(String key, T defaultValue) {
        Ventry<T> ventry = map.get(key);
        if (ventry == null || System.currentTimeMillis() > ventry.expiredAt) {
            map.remove(key);
            return defaultValue;
        }
        return ventry.val;
    }

    public <T> T remove(String key) {
        Ventry<T> ventry = map.remove(key);
        return ventry == null ? null : ventry.val;
    }

    @AllArgsConstructor
    static class Ventry<T> {
        private T val;
        private Long expiredAt;
    }
}