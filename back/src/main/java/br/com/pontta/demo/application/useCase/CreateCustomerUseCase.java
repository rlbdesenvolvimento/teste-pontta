package br.com.pontta.demo.application.useCase;

import br.com.pontta.demo.application.dto.CustomerDTO;
import br.com.pontta.demo.application.mapper.CustomerMapper;
import br.com.pontta.demo.entity.Customer;
import br.com.pontta.demo.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@AllArgsConstructor
public class CreateCustomerUseCase {

    private CustomerRepository customerRepository;

    public CustomerDTO execute(CustomerDTO customerDTO){
        if (customerRepository.findByEmail(customerDTO.getEmail()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already exists");
        }

        Customer entity = CustomerMapper.toCustomer(customerDTO);

        entity = customerRepository.save(entity);
        return CustomerMapper.toCustomerDTO(entity);
    }
}
