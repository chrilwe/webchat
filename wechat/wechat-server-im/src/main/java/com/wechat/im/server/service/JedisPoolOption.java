package com.wechat.im.server.service;

import java.util.List;
import java.util.Map;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import com.alibaba.fastjson.JSON;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * 操作redis业务
 * @author chrilwe
 *
 */
public class JedisPoolOption {
	
	private static JedisPool jedisPool = null;
	
	/**
	 * 初始化jedispool连接
	 */
	private JedisPoolOption() {
		//连接池配置对象,包含了很多默认配置
        GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
        //初始化Jedis连接池，通常来讲JedisPool是单例的
        jedisPool = new JedisPool(poolConfig, "127.0.0.1", 6379);
   
	}
	
	private static class Singleton{
		private static JedisPoolOption jpo = new JedisPoolOption();
		
		private static JedisPoolOption getInstance() {
			return jpo;
		}
	}
	
	public static Jedis getJedis() {
		return jedisPool.getResource();
	}
	
	/**
	 * hset操作
	 */
	public static void hset(String key1, String key2, String value) {
		Jedis jedis = getJedis();
		jedis.hset(key1, key2, value);
	}
	
	/**
	 * opsforhash get操作
	 */
	public static <T> T opsForHash_get(String key1, String key2, Class<T> classType) {
		Jedis jedis = getJedis();
		String hget = jedis.hget(key1, key2);
		return JSON.parseObject(hget, classType);
	}
	
	/**
	 * opsforhash getAll
	 * @param <T>
	 */
	public static Map<String, String> opsForHash_getAll(String key) {
		Jedis jedis = getJedis();
		return jedis.hgetAll(key);
	}
	
	/**
	 * del
	 */
	public static void del(String key) {
		Jedis jedis = getJedis();
		jedis.del(key);
	}
	
	public static void opsForHash_del(String key1, String key2) {
		Jedis jedis = getJedis();
		jedis.hdel(key1, key2);
	}
	
	public static JedisPoolOption getInstance() {
		return Singleton.getInstance();
	}
}
