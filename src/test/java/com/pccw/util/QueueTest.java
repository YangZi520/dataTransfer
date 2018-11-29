package com.pccw.util;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class QueueTest {
	public static void main(String[] args) {
		final BlockingQueue<Integer> queue = new ArrayBlockingQueue<Integer>(3);
		try {
			int i=0;
			queue.put(0);
			System.out.println(i++);
			queue.put(0);
			System.out.println(i++);
			queue.put(0);
			System.out.println(i++);
			queue.put(0);
			System.out.println(i++);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
