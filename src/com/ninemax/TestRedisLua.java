package com.ninemax;

import java.util.HashMap;
import java.util.Map;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class TestRedisLua {

	public static final String REDIS_LUA = "redis.call('select',1);local key = KEYS[1];local expire = tonumber(KEYS[2]);"
			+ "local number = tonumber(KEYS[3]);local count = tonumber(redis.call('GET',key));"
			+ "if count == nil then redis.call('SETEX',KEYS[1],expire,'100');return 0;"
			+ "else if count +1 >= number then redis.call('SETEX',KEYS[1],expire,'1');return 0;"
			+ "else redis.call('INCR',KEYS[1]);return 1;end;end;";

	public static final String REDIS_LUA2 = "local key = KEYS[1];return redis.call('INCR',KEYS[1]);";

	private static String redisScript = null;

	private static JedisPool jedisPool = null;

	static {
		/*
		 * try { Conf.load(); } catch (Exception e) { e.printStackTrace(); }
		 */
		System.out.println("====");
		jedisPool = getPool();
		redisScript = loadLuaScript();
	}

	/**
	 * 构建redis连接池
	 * 
	 * @param ip
	 * @param port
	 * @return JedisPool
	 */
	public static JedisPool getPool() {
		if (jedisPool == null) {
			JedisPoolConfig config = new JedisPoolConfig();
			// 控制一个pool可分配多少个jedis实例，通过pool.getResource()来获取；
			// 如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。
			config.setMaxActive(500);
			// 控制一个pool最多有多少个状态为idle(空闲的)的jedis实例。
			config.setMaxIdle(5);
			// 表示当borrow(引入)一个jedis实例时，最大的等待时间，如果超过等待时间，则直接抛出JedisConnectionException；
			config.setMaxWait(1000 * 100);
			// 在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
			config.setTestOnBorrow(true);
			jedisPool = new JedisPool(config, "127.0.0.1");
		}
		return jedisPool;
	}

	private static String loadLuaScript() {
		Jedis jedis = null;
		String redisScript = null;
		try {
			jedis = jedisPool.getResource();
			redisScript = jedis.scriptLoad(REDIS_LUA2);
		} catch (Exception e) {
			e.printStackTrace();
			if (jedis != null) {
				jedisPool.returnResource(jedis);
			}
		} finally {
			if (jedis != null) {
				jedisPool.returnResource(jedis);
			}
		}
		return redisScript;
	}

	public void exce() {
		Jedis jedis = jedisPool.getResource();
		jedis.select(1);
		Map<String, String> hash = new HashMap<String, String>();
		hash.put("hostcan", "1");
		// jedis.hmset("topic", hash);
		jedis.hincrBy("topic", "topic110:192", 1);
		//String ip = "192";
		// Object result = jedis.evalsha(redisScript, 1, "topic110:192", "100",
		// "3");
		// jedis.hset("topic", "topic110:192", result.toString());
		System.out.println(jedis.hget("topic", "topic110:192"));

		if (jedis != null) {
			jedisPool.returnResource(jedis);
		}
	}

	public static void main(String[] args) {
		// 连接本地的 Redis 服务
		/*
		 * Jedis jedis = jedisPool.getResource(); Object result =
		 * jedis.evalsha(redisScript, 3 , "zhangsan", "100", "3");
		 */
		// System.out.println(result);
		
		/**counter:user
        ->  ip_1001: 21
        ->  ip_1002: 10
        ->  ip_1003: 32
        ->  ip_1004: 203
            .......
        ->  ip_9999: 130
        
        **/
		
		/**counter:topic
        ->  ID_1001: 21
        ->  ID_1002: 10
        ->  ID_1003: 32
        ->  ID_1004: 203
            .......
        ->  ID_9999: 130
        
        jedis.hincrBy("topic", "ID_1001", 1);
        **/
		

		TestRedisLua test = new TestRedisLua();
		test.exce();
	}

}
