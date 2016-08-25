package com.ninemax;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class ConcurrentTest {
	private static int thread_num = 60;// 200;
	private static int client_num = 100;// 460;

	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		ExecutorService exec = Executors.newCachedThreadPool();
		// thread_num个线程可以同时访问
		final Semaphore semp = new Semaphore(thread_num);
		// 模拟2000个客户端访问
		for (int index = 0; index < client_num; index++) {
			final int NO = index;
			exec.execute(new TaskThread(semp, NO));
		}
		
		long timeSpend = (System.currentTimeMillis() - start) / 1000;
		System.out.println("花费1: " + timeSpend + "秒");
		// 退出线程池
		exec.shutdown();
		timeSpend = System.currentTimeMillis() - start;
		System.out.println("花费2: " + timeSpend + "秒");
		// long end = (System.currentTimeMillis()-start)/1000;//当前时间离当天0点的毫秒数

	}

	static class TaskThread implements Runnable {
		Semaphore semp;
		int NO;

		public TaskThread(Semaphore semp, int NO) {
			this.semp = semp;
			this.NO = NO;
		}

		public void run() {
			try {
				// 获取许可
				semp.acquire();
				System.out.println("Thread:" + NO);
				TestRedisLua test=new TestRedisLua();
				test.exce();
				// System.out.println(result);
				 Thread.sleep((long) (Math.random()) * 1000);
				// 释放
				//System.out.println("第：" + NO + " 个");
				// System.out.println(result);
				semp.release();
			} catch (Exception e) {
				e.printStackTrace();
			}
		};

	}

}
