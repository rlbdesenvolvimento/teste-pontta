package br.com.pontta.demo.application.useCase;

import br.com.pontta.demo.entity.Customer;
import br.com.pontta.demo.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@AllArgsConstructor
public class DeleteCustomerUseCase {

    private CustomerRepository customerRepository;

    public void execute(Long id) {
        Optional<Customer> byId = customerRepository.findById(id);

        if (byId.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found");

        //TODO Validação extras para ver se há pedido para esse cliente antes de excluir.

        customerRepository.deleteById(id);
    }
}
