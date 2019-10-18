/*
Author: Ziqi Tan
*/
public class Customer extends Individual {
	
	private CheckingAccount checking;
	private SavingAccount saving;
	private EuroAccount euroAccount;
	private CNYAccount cnyAccount;
	
	public Customer(String n, String s, String ph, String a, String pa, int money) {
		super(n, s, ph, a, pa);
		this.checking = new CheckingAccount(money);
		this.saving = new SavingAccount(money);
		this.euroAccount = new EuroAccount();
		this.cnyAccount = new CNYAccount();
	}
	
	/**
	 * Getter()
	 * */
	public CheckingAccount getCheckingAccount() {
		return checking;
	}
	
	public SavingAccount getSavingAccount() {
		return saving;
	}
	
	public EuroAccount getEuroAccount() {
		return euroAccount;
	}
	
	public CNYAccount getCNYAccount() {
		return cnyAccount;
	}
	
}
