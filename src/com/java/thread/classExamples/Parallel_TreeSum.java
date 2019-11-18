package com.java.thread.classExamples;
import java.util.Random;

/******** Concurrent Summation of Tree Values *********/

// ===========================================================================

public class Parallel_TreeSum {

	public static void main(String[] args) {
		Tree tr;
		tr = new Tree(1000);

		for (int i = 0; i < 30; i++)
			tr.insert(new Random().nextInt(1000));

		for (int i = 0; i < 30; i++)
			tr.insert(new Random().nextInt(1000) + 1000);

		System.out.println(tr.sum_values());
		System.out.println(tr.par_sum_values());
	}
}

class Test_TreeParSum {
	static int sum;

	public static void main(String[] args) {
		Tree tr;
		int ni;

		tr = new Tree(6000);

		for (int i = 1; i < 6; i++) {
			ni = (10000 % (80 + i)) + 5000;
			tr.insert(ni);
		}

		for (int i = 1; i < 6; i++) {
			ni = (10000 % (80 + i)) + 10000;
			tr.insert(ni);
		}

		sum = tr.sum_values();

		TreeSum thread1 = new TreeSum(tr.left);
		TreeSum thread2 = new TreeSum(tr.right);

		thread1.start();
		thread2.start();

		try {
			thread1.join();
			thread2.join();
		} catch (InterruptedException e) {
		}

		int result = tr.value + thread1.sum + thread2.sum;

		System.out.println(sum);
		// System.out.println(tr.par_sum_values());
	}
}

class TreeSum extends Thread {
	Tree tr;
	int sum;

	public TreeSum(Tree tr) {
		this.tr = tr;
	}

	public void run() {
		if (tr == null)
			sum = 0;
		else
			sum = tr.sum_values();
	}
}

//========================================================================

class Tree { // Defines one node of a binary search tree

	public Tree(int n) {
		value = n;
		left = null;
		right = null;
		depth = 0;
	}

	public void insert(int n) {
		int ld = 0;
		int rd = 0;
		if (left != null)
			ld = left.depth;
		if (right != null)
			rd = right.depth;

		if (value == n)
			return;
		if (value < n) {
			if (right == null)
				right = new Tree(n);
			else
				right.insert(n);
			rd = right.depth;
		} else {
			if (left == null)
				left = new Tree(n);
			else
				left.insert(n);
			ld = left.depth;
		}

		if (ld > rd)
			depth = 1 + ld;
		else
			depth = 1 + rd;
	}

	int sum_values() {
		int s1 = 0;
		int s2 = 0;
		if (left != null)
			s1 = left.sum_values();
		if (right != null)
			s2 = right.sum_values();
		int sum = value + s1 + s2;
		return sum;
	}

	int par_sum_values() {

		if (depth < 3)
			return sum_values();

		TreeSum thread1 = new TreeSum(left);
		TreeSum thread2 = new TreeSum(right);

		thread1.start();
		thread2.start();

		try {
			thread1.join();
			thread2.join();
		} catch (InterruptedException e) {
		}

		int result = value + thread1.sum + thread2.sum;

		// thread1 = null;
		// thread2 = null;

		return result;
	}

	protected int depth;
	protected int value;
	protected Tree left;
	protected Tree right;
}
