package com.thoughtworks.prob1.util;

import com.thoughtworks.prob1.base.ParserResultVO;

import java.util.concurrent.ConcurrentHashMap;

/**
 * A simple generic structure of a cache to store parser related data etc.
 *
 * Created by Kunal Chowdhury on 6/18/14.
 */
public class DataWarmer<K ,V> {
    public final static DataWarmer<String, ParserResultVO> INSTANCE = new DataWarmer<String, ParserResultVO>();

    private final ConcurrentHashMap<K ,V> cache = new ConcurrentHashMap<K ,V>();

    private DataWarmer(){}

    public void store(K key, V value){
         cache.putIfAbsent(key, value);
    }

    public V get(K key){
          return cache.get(key);
    }


}
