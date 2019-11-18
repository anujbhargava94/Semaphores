package com.java.thread.classExamples;
class DP {
	
 public static void main(String[] args)  {
   Fork f1 = new Fork ();
   Fork f2 = new Fork ();
   Fork f3 = new Fork ();   
   Fork f4 = new Fork ();
   Fork f5 = new Fork ();
   
   Philo p1 = new Philo("P1",f1,f2);
   Philo p2 = new Philo("P2",f2,f3);
   Philo p3 = new Philo("P3",f3,f4);   
   Philo p4 = new Philo("P4",f4,f5);
   Philo p5 = new Philo("P5",f1,f5);
   p1.start();
   p2.start();
   p3.start();
   p4.start();
   p5.start();
   }
}


class Fork {
    boolean taken = false;
    synchronized void pickup()  {
        while(taken)
            try { wait(); }
        	catch (Exception e) {}
        
        taken = true; 
    }
    synchronized void drop() {
        taken = false;
        notify();
    }
}  


class Philo extends Thread  {
	
	String name;
    Fork left, right;
    
    String state;
    
    Philo(String name,Fork left,Fork right) {   
    	this.name = name;
        this.left = left;
        this.right = right;
    }
    
    @Override
    public void run() {
          set_state("T");  
          for(int i=0; i<400; i++) {
	            left.pickup();
	            set_state("H");  
	            right.pickup();
	            set_state("E");  
	            
	            try{sleep((int)(300*Math.random()));}
	            catch (Exception e) {}
	            
	            set_state("T"); 
	            left.drop();
	            right.drop();
	      }  
    }
    
    protected void set_state(String s){
    	state = s;
    	try {
    	if (s.equals("T")) {
        		System.out.println ("Philo " + name + " is thinking");
                Thread.sleep((int)(175*Math.random()));
        }
    	if (s.equals("H")) {
    		System.out.println ("Philo " + name +" got left fork");
            Thread.sleep((int)(175*Math.random()));
    	}
    	if (s.equals("E")) {
            System.out.println ("Philo " + name +" got right fork and eating."); 
    		Thread.sleep((int)(175*Math.random())); // simulate eating
            System.out.println ("Philo " + name +" put down left and right fork");
    	}
    	}
    	catch (Exception e) {}
    }
}
 

