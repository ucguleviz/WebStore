package shop.customers.dto;

import shop.customers.domain.Customer;

public class CustomerAdapter {

	public static Customer getCustomer (CustomerDTO customerDTO) {
		Customer customer = new Customer(
				customerDTO.getCustomerNumber(),
				customerDTO.getFirstname(),
				customerDTO.getLastname(),
				customerDTO.getEmail(),
				customerDTO.getPhone()				
				);
		if (customerDTO.getAddress() != null) {
			customer.setAddress(AddressAdapter.getAddress(customerDTO.getAddress()) );
		}

		return customer;
	}
	
	public static CustomerDTO getCustomerDTO (Customer customer) {
		CustomerDTO customerDTO = new CustomerDTO(
				customer.getCustomerNumber(),
				customer.getFirstname(),
				customer.getLastname(),
				customer.getEmail(),
				customer.getPhone()				
				);
		if (customer.getAddress() != null) {
			customerDTO.setAddress(AddressAdapter.getAddressDTO(customer.getAddress()) );
		}

		return customerDTO;
	}
	
	
}
