import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/*
Author: Ziqi Tan
*/
public class CheckingAccount extends Account {
	
	/**
	 * Constructor
	 * Need to deposit some money to activate your account.
	 * */
	public CheckingAccount( int balance ) {
		this.setBalance(balance);
		List<String> trans = new ArrayList<String>();
		Calendar calendar = Calendar.getInstance();
		Date time = calendar.getTime();
		String timestamp = time.toString();
		
		trans.add(timestamp);
		trans.add("----");
		trans.add("This account");
		trans.add(Integer.toString(balance));
		
		this.addTransHis(trans);
		
		System.out.println(timestamp);	
		System.out.println("Your checking account has been activated with " + balance + " dollars!\n");
		
	}
	
	
}
