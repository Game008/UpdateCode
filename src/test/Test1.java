/**   
* Description: TODO(描述)
* @FileName: Test1.java
* @Package: test
* @author: ???ü
* @date: 2018年10月18日 上午9:24:20
*/

package test;

import java.io.IOException;

/**
 * Description: TODO(描述)
 * 
 * @ClassName: Test1
 * @author: ???ü
 * @date: 2018年10月18日 上午9:24:20
 */

public class Test1 {
	public void doA(int a) throws IOException {
		try {
		} catch (RuntimeException e) {
			throw e;
		}
		if (a == 0)
			throw new RuntimeException();
	}

}
