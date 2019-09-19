/**   
* Description: TODO(描述)
* @FileName: Parent.java
* @Package: test
* @author: ???ü
* @date: 2018年10月22日 上午10:44:45
*/

package test;

/**
 * Description: TODO(描述)
 * 
 * @ClassName: Parent
 * @date: 2018年10月22日 上午10:44:45
 */

public class Parent {
	public final void TestFinal() {
		System.out.println("父类--这是一个final方法");
	}
	public final void TestFinal(String a) {
		System.out.println("父类--这是一个final方法");
	}
}

class SubClass extends Parent {
	// 子类无法重写（override父类的final方法，否则编译时会报错
	/*
	 * public void TestFinal(){ System.out.println("子类--重写final方法"); }
	 */
	public static void main(String[] args) {
		SubClass sc = new SubClass();
		sc.TestFinal();
	}
}