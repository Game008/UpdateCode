package test;

public class Teacher {
	private String name;
	private String code;
	private double baseWage;
	private double postWage;
	private double meritWage;

	public static void main(String[] args) {
		Teacher t = new Teacher();
		t.setName("张老师");
		t.setCode("T001");
		t.setBaseWage(1000.0);
		t.setPostWage(500.0);
		t.setMeritWage(5000.0);
		System.out.println(t.toString());
	}

	/**
	 * Description: 信息输出
	 * 
	 * @MethodName: toString
	 * @return
	 * @date: 2018年10月22日 上午11:34:06
	 */
	@Override
	public String toString() {
		String lineFeed = System.getProperty("line.separator");
		StringBuffer sbu = new StringBuffer();
		sbu.append("姓名：" + name + lineFeed);
		sbu.append("教工号：" + code + lineFeed);
		sbu.append("基本工资：" + baseWage + lineFeed);
		sbu.append("岗位工资：" + postWage + lineFeed);
		sbu.append("绩效工资：" + meritWage + lineFeed);
		sbu.append("总工资：" + getWageCount() + lineFeed);
		sbu.append("税后工资：" + getWageAfterTax());
		return sbu.toString();
	}

	/**
	 * Description: 计算总工资
	 * 
	 * @MethodName: getWageCount
	 * @return
	 * @date: 2018年10月22日 上午11:33:40
	 */
	public double getWageCount() {
		return baseWage + postWage + meritWage;
	}

	/**
	 * Description: 税后工资
	 * 
	 * @MethodName: getWageAfterTax
	 * @return
	 * @date: 2018年10月22日 上午11:33:40
	 */
	public double getWageAfterTax() {
		double countWage = getWageCount();
		if (countWage - 3000 > 0 && countWage - 5000 <= 0) {
			return countWage - (countWage - 3000) * 0.1;
		} else if (countWage - 5000 > 0) {
			return countWage - 2000 * 0.1 - (countWage - 5000) * 0.15;
		} else {
			return countWage;
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public double getBaseWage() {
		return baseWage;
	}

	public void setBaseWage(double baseWage) {
		this.baseWage = baseWage;
	}

	public double getPostWage() {
		return postWage;
	}

	public void setPostWage(double postWage) {
		this.postWage = postWage;
	}

	public double getMeritWage() {
		return meritWage;
	}

	public void setMeritWage(double meritWage) {
		this.meritWage = meritWage;
	}
}
