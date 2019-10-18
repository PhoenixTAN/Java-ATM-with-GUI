/*
Author: Ziqi Tan
*/
public class ATM {
	
	private static OperationFrame operationFrame;
	private static CustomerDAOImp customerDAOImp;
	
	public ATM() {
		operationFrame = new OperationFrame(this);
		customerDAOImp = new CustomerDAOImp();
	}
	
	public CustomerDAOImp getCustomerDAOImp() {
		return customerDAOImp;
	}
	
}
