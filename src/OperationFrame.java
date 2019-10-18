import javax.swing.JFrame;
/*
Author: Ziqi Tan
*/
public class OperationFrame extends JFrame {
	
	private static ATM atm;
	private static LoginPanel loginPanel;
	private static CustomerMenu customerMenu;
	private static RegisterPanel registerPanel;
	private static TransferPanel transferPanel;
	private static TransferWindow transferWindow;
	private static ForeignCurrencyPanel foreignCurrencyPanel;
	private static ManagerPanel managerPanel;
	private static Customer user;
	private static Manager manager;
	
	public OperationFrame(ATM _atm) {
				
		atm = _atm;
		user = null;
		manager = null;
		
		setTitle("Welcome to Bank of BBUU!");
		setSize(500,500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);         		
		setLocationRelativeTo(null);  // this will center your app
		System.out.println("ATM opeartion frame has been created.");
		
		loginPanel = new LoginPanel(this, atm);
		setLoginPanel();		
		customerMenu = new CustomerMenu(this, atm);		
		registerPanel = new RegisterPanel(this, atm);
		transferPanel = new TransferPanel(this, atm);
		transferWindow = new TransferWindow(this, atm);
		managerPanel = new ManagerPanel(this, atm);
		foreignCurrencyPanel = new ForeignCurrencyPanel(this, atm);
		
		setVisible(true);
	}
	
	public CustomerMenu getCustomerMenu() {
		return customerMenu;
	}
	
	public Customer getCustomer() {
		return user;
	}
	
	/**
	 * setter() for showing which JPanel on the JFrame.
	 * */
	public void setLoginPanel() {
		add(loginPanel);
		loginPanel.setEnabled(true);
		loginPanel.setVisible(true);
	}
	
	public void setCustomerMenu() {
		add(customerMenu);
		customerMenu.updateInfo();
		customerMenu.setEnabled(true);
		customerMenu.setVisible(true);
	}
	
	public void setRegisterPanel() {
		add(registerPanel);
		registerPanel.setEnabled(true);
		registerPanel.setVisible(true);
	}
	
	public void setTransferPanel() {
		add(transferPanel);
		transferPanel.setEnabled(true);
		transferPanel.setVisible(true);
	}
	
	public void setTransferWindow() {
		add(transferWindow);
		transferWindow.setEnabled(true);
		transferWindow.setVisible(true);
	}
	
	public void setManagerPanel() {
		add(managerPanel);
		managerPanel.setEnabled(true);
		managerPanel.setVisible(true);
	}
	
	public void setForeignCurrencyPanel() {
		add(foreignCurrencyPanel);
		foreignCurrencyPanel.setEnabled(true);
		foreignCurrencyPanel.setVisible(true);
	}
	
	/*
	 * Method: setCustomer
	 * Function: set current customer
	 * */
	public void setCustomer(Customer cus) {
		user = cus;
	}


}
