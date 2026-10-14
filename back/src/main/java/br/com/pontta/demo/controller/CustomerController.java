package br.com.pontta.demo.controller;

import br.com.pontta.demo.application.dto.CustomerDTO;
import br.com.pontta.demo.application.useCase.*;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
@AllArgsConstructor
public class CustomerController {

    private CreateCustomerUseCase createCustomerUseCase;
    private UpdateCustomerUseCase updateCustomerUseCase;
    private DeleteCustomerUseCase deleteCustomerUseCase;
    private FindAllCustomersUseCase findAllCustomersUseCase;
    private FindCustomerByIdUseCase findCustomerByIdUseCase;

    @GetMapping
    public List<CustomerDTO> findAll() {
        return findAllCustomersUseCase.execute();
    }

    @GetMapping("/{id}")
    public CustomerDTO findById(@PathVariable Long id) {
        return findCustomerByIdUseCase.execute(id);
    }

    @PostMapping
    public CustomerDTO createCustomer(@RequestBody CustomerDTO customerDTO) {
        return createCustomerUseCase.execute(customerDTO);
    }

    @PutMapping("/{id}")
    public CustomerDTO updateCustomer(@RequestBody CustomerDTO customerDTO, @PathVariable Long id) {
        return updateCustomerUseCase.updateCustomer(customerDTO, id);
    }

    @DeleteMapping("/{id}")
    public void deleteCustomer(@PathVariable Long id) {
        deleteCustomerUseCase.execute(id);
    }

}
