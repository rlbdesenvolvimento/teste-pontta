package br.com.pontta.demo.application.mapper;


import br.com.pontta.demo.application.dto.CustomerDTO;
import br.com.pontta.demo.entity.Customer;

import java.util.ArrayList;
import java.util.List;

public class CustomerMapper {

    public static CustomerDTO toCustomerDTO(Customer customer) {
        return CustomerDTO.builder()
                .id(customer.getId())
                .email(customer.getEmail())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .build();
    }

    public static Customer toCustomer(CustomerDTO customerDTO) {
        return Customer.builder()
                .email(customerDTO.getEmail())
                .firstName(customerDTO.getFirstName())
                .lastName(customerDTO.getLastName())
                .build();
    }

    public static List<CustomerDTO> toCustomerDTO(List<Customer> customerList) {
        List<CustomerDTO> customerDTOList = new ArrayList<>();
        for (Customer customer : customerList) {
            customerDTOList.add(toCustomerDTO(customer));
        }
        return customerDTOList;
    }

    public static List<Customer> toCustomer(List<CustomerDTO> customerDTOList) {
        List<Customer> customerList = new ArrayList<>();
        for (CustomerDTO customerDTO : customerDTOList) {
            customerList.add(toCustomer(customerDTO));
        }
        return customerList;
    }
}
