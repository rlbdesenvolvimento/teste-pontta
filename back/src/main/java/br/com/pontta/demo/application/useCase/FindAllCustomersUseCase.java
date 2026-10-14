package br.com.pontta.demo.application.useCase;

import br.com.pontta.demo.application.dto.CustomerDTO;
import br.com.pontta.demo.application.mapper.CustomerMapper;
import br.com.pontta.demo.entity.Customer;
import br.com.pontta.demo.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@AllArgsConstructor
public class FindAllCustomersUseCase {

    private final CustomerRepository customerRepository;

    public List<CustomerDTO> execute(){
        List<Customer> customers = customerRepository.findAll();

        if(customers.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Customer not found");
        }

        return CustomerMapper.toCustomerDTO(customers);
    }
}
