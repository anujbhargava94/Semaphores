package com.java.thread.classExamples;

	//------------- Unsychronized Printing -------------

	class Test_Simple_Printer {
		
		public static void main(String[] args) {
		    Printer thread1 = new Printer("A", 4);
		    Printer thread2 = new Printer("Z", 4);
		    thread1.start();
		    thread2.start();
		}
	}

	class Printer extends Thread {
		String s;
		int n;
		
		public Printer(String s, int n) {
			this.s = s;
			this.n = n;
		}
		public void print() { 
				System.out.print(s);
		}
		public void run() {
			for (int i=0; i<n; i++) 
				print();
		}
	}



	//------------- Sychronized Printing -------------


	class Test_SharedPrinter {
		public static void main(String[] args) {
		   
		    SharedPrinter p = new SharedPrinter();
		    
		    PrintThread p1 = new PrintThread(p,"A",5);
		    PrintThread p2 = new PrintThread(p,"B",5);
		    PrintThread p3 = new PrintThread(p,"C",5);
		    PrintThread p4 = new PrintThread(p,"D",5);

		    p1.start();
		    p2.start();
		    p3.start();
		    p4.start();
		}
	}

	class PrintThread extends Thread {
		SharedPrinter p;
		String s;
		int n;
		
		public PrintThread(SharedPrinter p, String s, int n) {
			this.p = p;
			this.s = s;
			this.n = n;
		}
		public void run() {
				p.print(s,n);
		}
	}

	class SharedPrinter {
	    public void print(String s, int n) {
			 for (int i=0; i<n; i++) { 
				 System.out.print(s); 
			 }
		}
	}




	//try { sleep((int) (100*Math.random())); }
	//catch (Exception e) {}