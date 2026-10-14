package br.com.pontta.demo.application.useCase;

import br.com.pontta.demo.application.dto.CustomerDTO;
import br.com.pontta.demo.application.mapper.CustomerMapper;
import br.com.pontta.demo.entity.Customer;
import br.com.pontta.demo.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateCustomerUseCaseTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CreateCustomerUseCase createCustomerUseCase;

    @Test
    void execute() {
        CustomerDTO dto = CustomerDTO.builder()
                .firstName("John")
                .lastName("Doe")
                .email("teste@teste.com.br")
                .build();

        Customer customer = CustomerMapper.toCustomer(dto);

        when(customerRepository.findByEmail(dto.getEmail())).thenReturn(Optional.empty());

        when(customerRepository.save(customer)).thenReturn(customer);

        CustomerDTO result = createCustomerUseCase.execute(dto);


        assertEquals(result.getEmail(), customer.getEmail());
        assertEquals(result.getFirstName(), customer.getFirstName());
        assertEquals(result.getLastName(), customer.getLastName());
    }
}