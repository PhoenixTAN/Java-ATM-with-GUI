import java.util.List;

/*
Author: Ziqi Tan
*/
public interface Transaction {
	
	public void withdraw(Customer customer, double offset);
	public void deposit(Customer customer, double offset, String choice);
	public void transferBetweenYourAccount(Customer customer, double amount, String choice);
	public void transfer(Customer sender, Customer recipient, double amount);
	public List<List<String>> inquiry(Customer customer, String whichAccount);
	public void purchaseEUR(Customer customer, double amount);
	public void purchaseCNY(Customer customer, double amount);
	public void withdrawEUR(Customer customer, double amount);
	public void withdrawCNY(Customer customer, double amount);
	
}
