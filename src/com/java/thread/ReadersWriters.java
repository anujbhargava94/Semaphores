package com.java.thread;

import java.util.Random;

public class ReadersWriters {

	public static void main(String[] args) {

		Database d = new Database();

		Writer w1 = new Writer(d, 1);
		Writer w2 = new Writer(d, 10);
		Writer w3 = new Writer(d, 100);
		Writer w4 = new Writer(d, 1000);
		Writer w5 = new Writer(d, 10000);
		Reader r1 = new Reader(d);
		Reader r2 = new Reader(d);
		Reader r3 = new Reader(d);
		Reader r4 = new Reader(d);
		Reader r5 = new Reader(d);
		Reader r6 = new Reader(d);
		Reader r7 = new Reader(d);
		Reader r8 = new Reader(d);
		Reader r9 = new Reader(d);
		Reader r10 = new Reader(d);

		w1.start();
		w2.start();
		w3.start();
		r1.start();
		r2.start();
		r3.start();
		r4.start();
		r5.start();
		w4.start();
		w5.start();
		r6.start();
		r7.start();
		r8.start();
		r9.start();
		r10.start();
	}
}

class Reader extends Thread {
	Database d;

	public Reader(Database d) {
		this.d = d;
	}

	public void run() {
		for (int i = 0; i < 5; i++) {
			d.request_read();
			System.out.println(d.read());
			d.done_read();

		}
	}
}

class Writer extends Thread {
	Database d;
	int x;

	public Writer(Database d, int x) {
		this.d = d;
		this.x = x;
	}

	public void run() {
		for (int i = 0; i < 5; i++) {
			try {
				Thread.sleep(new Random().nextInt(200));
			} catch (InterruptedException e) {
			}
			d.request_write();
			d.write(x);
			d.done_write();
		}
	}
}

class Database {
	int data = 0;
	int r = 0; // # active readers
	int w = 0; // # active writers (0 or 1)
	int ww = 0; // # waiting writers
	int wr = 0; // # waiting readers

	public synchronized void request_read() {
		while (w == 1 || ww > 0) // writers priority condition
			try {
				wr++;
				wait();
				wr--;
			} catch (Exception e) {
			}
		r++;
	}

	public synchronized void done_read() {
		r--;
		notifyAll();
	}

	public synchronized void request_write() {
		while (r > 0 || w == 1) {
			try {
				ww++;
				wait();
				ww--;
			} catch (Exception e) {
			}
		}
		w = 1;
	}

	public synchronized void done_write() {
		w = 0;
		notifyAll();
	}

	int read() {
		return data;
	}

	void write(int x) {
		data = data + x;
	}
}
