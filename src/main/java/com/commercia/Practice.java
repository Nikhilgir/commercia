package com.commercia;

import java.util.concurrent.atomic.AtomicInteger;

public class Practice {
	AtomicInteger a = new AtomicInteger(0);
	static volatile int b = 0;

	public synchronized void changeValue() {
		for (int i = 0; i < 1000; i++) {
			a.getAndIncrement();
			b++;
		}
	}

	public void printValue() {
		System.out.println("a: " + a);
		System.out.println("b; " + b);
	}

	public static void main(String[] args) throws InterruptedException {
		Practice p = new Practice();

		Thread t1 = new Thread(() -> p.changeValue());
		Thread t2 = new Thread(() -> p.changeValue());
		t1.start();
		t2.start();

		// Ensure main thread waits for both threads to finish
		t1.join();
		t2.join();
		p.printValue();
	}
}
