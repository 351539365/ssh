package com.ninemax;

import java.util.HashMap;
import java.util.Map;

import redis.clients.jedis.Jedis;

public class RedisTest {
	
	public void exec(){
		//连接本地的 Redis 服务
	      Jedis jedis = new Jedis("localhost");
	      //System.out.println("Connection to server sucessfully");
	      //设置 redis 字符串数据
	      jedis.set("w3ckey", "Redis tutorial");
	      
	      Map<String, String> hash=new HashMap<String, String>();
	      hash.put("hostcan", "1");
	    //  jedis.hmset("topic", hash);
	      jedis.hincrBy("topic", "hostcan", 1);
	     // jedis.hget("topic", "hostcan");
	     // jedis.hset("rules", "test", "name:test,prefix:PO,time:YYYYMMDD,seq:seq_po,seq_len:6");
	      System.out.println(jedis.hget("topic", "hostcan"));
	     // System.out.println(jedis.hvals("rules"));
	      //System.out.println("seq:"+jedis.incr("seq"));
	     // System.out.println("seq:"+jedis.incr("seq"));
	     //添加注释
	}

}
