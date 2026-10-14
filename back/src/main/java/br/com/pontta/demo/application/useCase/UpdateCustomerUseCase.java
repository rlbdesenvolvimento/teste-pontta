package br.com.pontta.demo.application.useCase;

import br.com.pontta.demo.application.dto.CustomerDTO;
import br.com.pontta.demo.application.mapper.CustomerMapper;
import br.com.pontta.demo.entity.Customer;
import br.com.pontta.demo.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UpdateCustomerUseCase {

    private CustomerRepository customerRepository;

    public CustomerDTO updateCustomer(CustomerDTO customerDTO, Long id) {
        Optional<Customer> byId = customerRepository.findById(id);

        if (byId.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found");
        }

        Customer entity = byId.get();
        entity.setFirstName(customerDTO.getFirstName());
        entity.setLastName(customerDTO.getLastName());
        entity.setEmail(customerDTO.getEmail());

        Customer save = customerRepository.save(entity);

        return CustomerMapper.toCustomerDTO(save);
    }

}
