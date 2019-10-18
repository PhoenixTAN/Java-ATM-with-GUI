/*
Author: Ziqi Tan
*/
public class Individual {
	
	private String name;
	private String ssn;
	private String phone;
	private String address;
	private String password;
	
	public Individual(String n, String s, String ph, String a, String pa) {
		this.name = n;
		this.ssn = s;
		this.phone = ph;
		this.address = a;
		this.password = pa;
	}
	
	/**
	 * getter()
	 * */
	public String getName() {
		return name;
	}
	
	public String getSSN() {
		return ssn;
	}
	
	public String getPhone() {
		return phone;
	}
	
	public String getAddress() {
		return address;
	}
	
	public String getPassword() {
		return password;
	}
	
	/**
	 * setter()
	 * */
	public void setSSN(String s) {
		this.ssn = s;
	}
	
	public void setPhone(String p) {
		this.phone = p;
	}
	
	public void setAddress(String a) {
		this.address = a;
	}
	
	public void setPassword(String p) {
		this.password = p;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
