import java.util.ArrayList;
import java.util.List;

/*
Author: Ziqi Tan
*/
public abstract class Account {
	
	private double balance;
	// Transaction history data structure:
	// String transaction time, String sender, String recipient, String Amount
	private List<List<String>> transHis = new ArrayList<List<String>>();
	
	/**
	 * getter()
	 * */
	public double getBalance() {
		return balance;
	}
	
	public List<List<String>> getTransHis() {
		return transHis;
	}
	
	/**
	 * setter()
	 * */
	public void setBalance(double offset) {		
		this.balance += offset;
	}
		
	public void addTransHis(List<String> trans) {
		transHis.add(trans);
	}
	
}


