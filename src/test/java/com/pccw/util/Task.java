package com.pccw.util;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class Task  implements Runnable{
	private BlockingQueue<List<Integer>> queue = null;;
	private int endRow;
	public Task(int endRow, BlockingQueue<List<Integer>> queue) {
		this.endRow = endRow;
		this.queue = queue;
	}

	@Override
	public void run() {
		try {
			TimeUnit.SECONDS.sleep(5);
			queue.take();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("执行了"+endRow+"行");
	}
}
