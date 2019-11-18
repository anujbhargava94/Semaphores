package com.java.thread.classExamples;

public class Concurrent_Update {

  public static void main(String[] args) {
	  
	 SharedState s = new SharedState();
	 
	 Worker t1 = new Worker(s);
	 Worker t2 = new Worker(s);
	 
	 t1.start();
	 t2.start();
}
}

class SharedState {
	private int v = 0;
	 public void incr() {
		v = f();
	}
	
	 synchronized int f() { 
		return v + 1;
	}
}

class Worker extends Thread {
	
	SharedState s;
	
	public Worker(SharedState s) {
		this.s = s;
	}
	
	public void run() {
		for (int i=0; i<5; i++) {
			s.incr();
		}
	}
}