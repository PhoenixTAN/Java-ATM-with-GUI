import java.util.List;

/*
Author: Ziqi Tan
*/
public interface CustomerDAO {
	public List<Customer> getAllCustomers();
	public void addCustomer(Customer customer);
	public Customer getCustomer(String name);
	public void updateCustomer(Customer customer);
	public void deleteCustomer(Customer customer);
	public Manager getManager();
}
