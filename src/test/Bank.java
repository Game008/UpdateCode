/**   
* Description: TODO(描述)
* @FileName: MyThread.java
* @Package: test
* @author: ???ü
* @date: 2018年10月22日 下午2:36:34
*/

package test;

/**
 * Description: TODO(描述)
 * 
 * @ClassName: MyThread
 * @author: ???ü
 * @date: 2018年10月22日 下午2:36:34
 */

public class Bank {
	public static int account = 10;

	public static void main(String[] args) throws InterruptedException {
		Thread1 thread1 = new Thread1();
		thread1.start();// 开启线程
		thread1.join();// 检测线程是否执行完成，执行完成才继续往下
		Thread2 thread2 = new Thread2();
		thread2.start();
		thread2.join();
		Thread3 thread3 = new Thread3();
		thread3.start();
		thread3.join();
		Thread4 thread4 = new Thread4();
		thread4.start();
		thread4.join();
		System.out.println(Bank.account);
	}
}

class Thread1 extends Thread {
	@Override
	public void run() {
		Bank.account = Bank.account * 10;
	}
}

class Thread2 extends Thread {
	@Override
	public void run() {
		Bank.account = Bank.account * 20;
	}
}

class Thread3 extends Thread {
	@Override
	public void run() {
		Bank.account = Bank.account + 30;
	}
}

class Thread4 extends Thread {
	@Override
	public void run() {
		Bank.account = Bank.account + 40;
	}
}