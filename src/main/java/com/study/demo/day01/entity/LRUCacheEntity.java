package com.study.demo.day01.entity;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author wxf
 * @createTime 20191207 16:53
 * @description LRU缓存淘汰策略entity
 */
public class LRUCacheEntity<K,V> {
    private static final float hashLoadFactory = 0.75f;//扩容因子？
    private LinkedHashMap<K,V> map;
    private int cacheSize;//缓存数据列表的长度
    public LRUCacheEntity(int cacheSize) {
        this.cacheSize = cacheSize;
        int capacity = (int)Math.ceil(cacheSize / hashLoadFactory) + 1;
        map = new LinkedHashMap<K,V>(capacity, hashLoadFactory, true){

            /**
             * Constructs an empty <tt>LinkedHashMap</tt> instance with the
             * specified initial capacity, load factor and ordering mode.
             *
             * @param  initialCapacity the initial capacity
             * @param  loadFactor      the load factor
             * @param  accessOrder     the ordering mode - <tt>true</tt> for
             *         access-order, <tt>false</tt> for insertion-order
             * @throws IllegalArgumentException if the initial capacity is negative
             *         or the load factor is nonpositive
             */
            private static final long serialVersionUID = 1;

            @Override
            protected boolean removeEldestEntry(Map.Entry eldest) {
                return size() > LRUCacheEntity.this.cacheSize;
            }
        };
    }

} 