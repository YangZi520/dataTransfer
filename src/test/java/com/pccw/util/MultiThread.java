package com.pccw.util;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MultiThread {
	
	public void readAndWrite() throws InterruptedException{
		List<Integer> list = new ArrayList<>();
		final BlockingQueue<List<Integer>> queue = new ArrayBlockingQueue<>(3);
		ExecutorService service = Executors.newFixedThreadPool(3);
		for(int i=0;i<10000;i++){
			list.add(i);
			if(list.size()==1000){
				queue.put(list);
				System.out.println("读取了"+(i+1)+"行");
				service.submit(new Task(i+1, queue));
				list = new ArrayList<>();
			}
		}
		service.shutdown();
	}
	
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		MultiThread m = new MultiThread();
		m.readAndWrite();
	}
}
