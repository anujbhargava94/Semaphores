package com.java.thread.classExamples;
// This program has the shortcoming that
// all read operations execute sequentially

import java.util.Random;

public class ReadWrite1 {
	public static void main(String[] args) {

		Database d = new Database();
		
		Writer w1 = new Writer(d,1);
		Writer w2 = new Writer(d,10);
		Writer w3 = new Writer(d,100);
		Writer w4 = new Writer(d,1000);
		Writer w5 = new Writer(d,10000);
		Reader r1 = new Reader(d);
		Reader r2 = new Reader(d);
		Reader r3 = new Reader(d);
		Reader r4 = new Reader(d);
		Reader r5 = new Reader(d);

		w1.start();
		r1.start();
		r2.start();
		r3.start();
		w2.start();
		w3.start();
		w4.start();
		w5.start();
		r4.start();
		r5.start();
	}
}

class Database {
     int data = 0;
     int r = 0;
     int w = 0;
	
     public synchronized int read() {
			 while (w == 1) {
			       try {wait();} 
			       catch (Exception e) {} 		 
			 }
			 r++;
			 try {Thread.sleep(50);} catch (Exception e) {}
			 r--;
			 notifyAll();
			 return data; 
	}
	
     public synchronized void write(int x) { 
			  while (r > 0 || w == 1) {
				  try {wait();} 
		          catch (Exception e) {} 	
			  }
			  w = 1;
			  try {Thread.sleep(200);}
			  catch (InterruptedException e) {}
			  data = data + x; 
			  w = 0;
			  notifyAll();
	 }
}

class Reader extends Thread {
	Database d;

	public Reader(Database d) {
		this.d = d;
	}

	public void run() {

		for (int i = 0; i < 5; i++){
			try {Thread.sleep(new Random().nextInt(750));}
			catch (InterruptedException e) {}
			
			System.out.println(d.read());
			
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
			try {Thread.sleep(new Random().nextInt(200));}
			catch (InterruptedException e) {}
			d.write(x);
		}
	}
}
 


