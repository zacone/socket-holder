package com.isthisone.socketholder.channel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class AbstractHolderChannel implements HolderChannel {

    private volatile Map<String, Object> map;

    @Override
    public Object attr(String key) {
        if (map == null) {
            return null;
        }
        return map.get(key);
    }

    @Override
    public void attr(String key, Object val) {
        if (map == null) {
            synchronized (this) {
                if (map == null) {
                    map = new ConcurrentHashMap<>(4);
                }
            }
        }
        map.put(key, val);
    }
}
