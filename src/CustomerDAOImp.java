import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;

/*
Author: Ziqi Tan
Class: CustomerDAPImp
Function: Data access object pattern and serves as a connection to a database.
Interface 1: Transaction (withdraw, deposit, transfer, inquiry)
Interface 2: CustomerDAO (method to access Customer)
*/
public class CustomerDAOImp implements CustomerDAO, Transaction {
	
	/**
	 * Private block
	 * */
	// List as a database
	private List<Customer> customers;
	private Manager manager;
	
	// Manager login password
	private static final String rootPassword = "123456789";
	
	// Foreign currency exchange rate
	private static final double USD_Euro = 0.9;  // 1 USD = 0.9 Euro
	private static final double USD_CNY = 7.08;  // 1 USD = 7.08 CNY
	
	// time stamp
	private Calendar calendar = Calendar.getInstance();
	
	// Currency format
	private DecimalFormat df = new DecimalFormat(".00");
	
	/*
	 * Constructor
	 * Add customers to database.
	 * */
	public CustomerDAOImp() {
		customers = new ArrayList<Customer>();
		// String name, String ssn, String phone, String address, String password
		Customer customer1 = new Customer("ziqi", "1234567890", "617.888.888", "Boston University", "123", 300);
		Customer customer2 = new Customer("tf", "1233211234567", "617.666.666", "CS 591 OOD in Java", "123456", 500);
		customers.add(customer1);
		customers.add(customer2);
		this.manager = new Manager("root", "45412452", "617.999.999", "Super Bank", rootPassword);
	}
	
	/**
	 * Implements CustomerDAO method
	 * */
	@Override
	public List<Customer> getAllCustomers() {
		return customers;
	}

	@Override
	public void addCustomer(Customer customer) {
		// TODO Auto-generated method stub
		customers.add(customer);
	}

	@Override
	public Customer getCustomer(String name) {
		// TODO Auto-generated method stub
		ListIterator iter = this.customers.listIterator();
		while( iter.hasNext() ) {
			Customer customer =  (Customer) iter.next();
			if( name.equals(customer.getName()) ) {
				return customer;
			}
		}
		return null;
	}

	@Override
	public void updateCustomer(Customer customer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteCustomer(Customer customer) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * implements Transaction method
	 * */
	/*
	 * Method: withdraw
	 * Function: withdraw from checking account
	 * */
	@Override
	public void withdraw(Customer customer, double offset) {
		customer.getCheckingAccount().setBalance(offset);	
		
		// Create transaction history
		List<String> trans = newTransHis(customer, "----", "----", offset);
		customer.getCheckingAccount().addTransHis(trans);
	}
	
	/*
	 * Method: deposit
	 * Function:
	 * 		1. Deposit money in checking account.
	 * 		2. Deposit money in saving account.
	 * */
	@Override
	public void deposit(Customer customer, double offset, String choice) {
		if( choice.equals("CHECKING") ) {
			customer.getCheckingAccount().setBalance(offset);
			
			// Create transaction history
			List<String> trans = newTransHis(customer, "----", "This account", offset);
			customer.getCheckingAccount().addTransHis(trans);
		}
		
		if( choice.equals("SAVING") ) {
			customer.getSavingAccount().setBalance(offset);		
			
			// Create transaction history
			List<String> trans = newTransHis(customer, "----", "This account", offset);			
			customer.getSavingAccount().addTransHis(trans);
		}		
	}
	
	/*
	 * Method: transferBetweenYourAccount
	 * Function:
	 * 		1. Transfer from checking account to saving account
	 * 		2. Transfer from saving account to checking account
	 * */
	@Override
	public void transferBetweenYourAccount(Customer customer, double amount, String choice) {
		
		// Saving account to checking account
		if( choice.equals("CHECKING") ) {
			customer.getCheckingAccount().setBalance(-amount);
			customer.getSavingAccount().setBalance(amount);
			
			// Create transaction history
			List<String> trans = newTransHis(customer, "checking account", "saving account", -amount);
			customer.getCheckingAccount().addTransHis(trans);
			trans = newTransHis(customer, "checking account", "saving account", amount);
			customer.getSavingAccount().addTransHis(trans);			
		}
		
		// Saving account to checking account
		if( choice.equals("SAVING") ) {
			customer.getCheckingAccount().setBalance(+amount);
			customer.getSavingAccount().setBalance(-amount);
			
			// Create transaction history
			List<String> trans = newTransHis(customer, "saving account", "checking account", amount);
			customer.getCheckingAccount().addTransHis(trans);	
			trans = newTransHis(customer, "saving account", "checking account", -amount);
			customer.getSavingAccount().addTransHis(trans);
		}
		
	}
	
	/*
	 * Method: transfer
	 * Function: transfer to others from checking account
	 * */
	@Override
	public void transfer(Customer sender, Customer recipient, double amount) {
		
		sender.getCheckingAccount().setBalance(-amount);
		recipient.getCheckingAccount().setBalance(amount);
		
		// Create transaction history
		List<String> trans = newTransHis(sender, "checking account", recipient.getName(), -amount);
		sender.getCheckingAccount().addTransHis(trans);
		trans = newTransHis(sender, sender.getName(), "checking account", amount);
		recipient.getCheckingAccount().addTransHis(trans);
	}
	
	
	/*
	 * Method: inquiry
	 * Function: 
	 * 		Inquire transaction history on 
	 * 		checking account/saving account/Euro account/CNY account.
	 * Return: List of transaction history
	 */
	@Override
	public List<List<String>> inquiry(Customer customer, String whichAccount) {
				
		if( whichAccount.equals("Checking") ) {
			return customer.getCheckingAccount().getTransHis();
		}
		if( whichAccount.equals("Saving") ) {
			return customer.getSavingAccount().getTransHis();
		}
		if( whichAccount.equals("Euro") ) {
			return customer.getEuroAccount().getTransHis();
		}
		if( whichAccount.equals("CNY") ) {
			return customer.getCNYAccount().getTransHis();
		}
		
		return null;
	}

	@Override
	public Manager getManager() {
		return this.manager;
	}
	
	/*
	 * Method: purchaseEUR
	 * Function: Use the balance in checking account to purchase EUR.
	 * */
	@Override
	public void purchaseEUR(Customer customer, double amount) {
		customer.getEuroAccount().setBalance(amount);
		customer.getCheckingAccount().setBalance(-amount/USD_Euro);
		
		// Transaction history for checking account and euro account
		List<String> trans = newTransHis(customer, "This account", "Euro account", -amount/USD_Euro);	
		customer.getCheckingAccount().addTransHis(trans);
		trans = newTransHis(customer, "Checking account", "This account", amount);	
		customer.getEuroAccount().addTransHis(trans);
	}

	/*
	 * Method: purchaseCNY
	 * Function: Use the balance in checking account to purchase CNY.
	 * */
	@Override
	public void purchaseCNY(Customer customer, double amount) {
		customer.getCNYAccount().setBalance(amount);
		customer.getCheckingAccount().setBalance(-amount/USD_CNY);
		
		// Create transaction history
		List<String> trans = newTransHis(customer, "This account", "CNY account", -amount/USD_CNY);
		customer.getCheckingAccount().addTransHis(trans);	
		trans = newTransHis(customer, "Checking account", "This account", amount);
		customer.getCNYAccount().addTransHis(trans);
	}

	/*
	 * Method: withdrawEUR
	 * Function: Withdraw EUR from EUR account.
	 * */
	@Override
	public void withdrawEUR(Customer customer, double amount) {
		customer.getEuroAccount().setBalance(-amount);
		// Create transaction history
		List<String> trans = newTransHis(customer, "----", "----", -amount);		
		customer.getEuroAccount().addTransHis(trans);
	}
	
	/*
	 * Method: withdrawCNY
	 * Function: Withdraw CNY from CNY account.
	 * */
	@Override
	public void withdrawCNY(Customer customer, double amount) {
		customer.getCNYAccount().setBalance(-amount);
		// Create transaction history
		List<String> trans = newTransHis(customer, "----", "----", -amount);	
		customer.getCNYAccount().addTransHis(trans);
	}
	
	/**
	 * getter()
	 * */
	public double getEURRate() {
		return USD_Euro;
	}
	
	public double getCNYRate() {
		return USD_CNY;
	}
	
	/*
	 * Method: newTransHis
	 * Function: Create transaction record.
	 * */
	private List<String> newTransHis(Customer customer, String sender, String recipient, double amount) {
		
		List<String> trans = new ArrayList<String>();
		Date time = calendar.getTime();
		String timestamp = time.toString();		
		trans.add(timestamp);
		trans.add(sender);
		trans.add(recipient);
		trans.add(df.format(amount));	
		
		return trans;
	}
	
}
