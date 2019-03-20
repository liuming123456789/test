package servlet;

import java.util.Date;

public class test {
	public static void main(String args[]) {
		MyThread3 t1 = new MyThread3("t1");
		/* 同时开辟了两条子线程t1和t2，t1和t2执行的都是run()方法 */
		/* 这个程序的执行过程中总共有3个线程在并行执行，分别为子线程t1和t2以及主线程 */
		MyThread3 t2 = new MyThread3("t2");
		t1.start();// 启动子线程t1
		t2.start();// 启动子线程t2
		for (int i = 0; i <= 5; i++) {
			System.out.println("I am main Thread");
		}
	}
}

class MyThread3 extends Thread {
	MyThread3(String s) {
		super(s);
	}

	public void run() {
		for (int i = 1; i <= 5; i++) {
			System.out.println(getName() + "：" + i);
			if (i % 2 == 0) {
				yield();// 当执行到i能被2整除时当前执行的线程就让出来让另一个在执行run()方法的线程来优先执行
				/*
				 * 在程序的运行的过程中可以看到， 线程t1执行到(i%2==0)次时就会让出线程让t2线程来优先执行
				 * 而线程t2执行到(i%2==0)次时也会让出线程给t1线程优先执行
				 */
			}
		}
	}
}