package com.java.thread.classExamples;
import java.util.Random;

public class ProducerConsumerBasi {
	public static void main(String[] args) {
		DropBox db = new DropBox();
		Producer p = new Producer(db);
		Consumer c = new Consumer(db);
		p.start();
		c.start();
	}
}

class Producer extends Thread {
	private DropBox db;

	public Producer(DropBox db) {
		this.db = db;
	}

	public void run() {
		for (int i = 0; i < 5; i++) {
			db.put(i);
			try {
				Thread.sleep(new Random().nextInt(200));
			} catch (Exception e) {}
		}
	}
	
	void delay(int n) {
		
	}
}

class Consumer extends Thread {
	private DropBox db;
	int value;

	public Consumer(DropBox db) {
		this.db = db;
	}

	public void run() {
		while (true) {
			value = db.get();
			try {
				Thread.sleep(new Random().nextInt(100));
			} catch (Exception e) { }
		}
	}
}

class DropBox {
	private int data;
	private boolean empty = true;

	public synchronized int get() {
		try {
			while (empty)
				wait();
			empty = true;
			System.out.println(" Got " + data);
			notify();
		} catch (Exception e) {}
		return data;
	}

	public synchronized void put(int v) {
		try {
			while (!empty)
				wait();
			data = v;
			System.out.println(" Put " + data);
			empty = false;
			notify();
		} catch (Exception e) {}
	}
}

