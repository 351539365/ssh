package com.ninemax;

import java.util.HashMap;
import java.util.Map;

import redis.clients.jedis.Jedis;

public class Test {
	


	/**
	 * LP
	 * 2016-8-17
	 * @param args
	 * Version @1.0
	 */
	public static void main(String[] args) {
		//���ӱ��ص� Redis ����
	      Jedis jedis = new Jedis("localhost");
	      System.out.println("Connection to server sucessfully");
	      //���� redis �ַ�������
	      jedis.set("w3ckey", "Redis tutorial");
	      
	      Map<String, String> hash=new HashMap<String, String>();
	      hash.put("hostcan", "1");
	    //  jedis.hmset("topic", hash);
	      jedis.hincrBy("topic", "hostcan", 1);
	      jedis.hget("topic", "hostcan");
	      jedis.hset("rules", "test", "name:test,prefix:PO,time:YYYYMMDD,seq:seq_po,seq_len:6");
	      System.out.println(jedis.hget("topic", "hostcan"));
	      System.out.println(jedis.hvals("rules"));
	      System.out.println("seq:"+jedis.incr("seq"));
	      System.out.println("seq:"+jedis.incr("seq"));
	      
	     // ��ȡ�洢�����ݲ����
	    // System.out.println("Stored string in redis:: "+ jedis.get("w3ckey"));
	   // ��ȡ�洢�����ݲ����
		   // ��ȡ�洢�����ݲ����	   // ��ȡ�洢�����ݲ����	   // ��ȡ�洢�����ݲ����
		   // ��ȡ�洢�����ݲ����
	      /**
	       * ע�Ͳ��� 123
	       */

	}

}
