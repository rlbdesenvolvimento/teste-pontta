package br.com.pontta.demo.controller;

import br.com.pontta.demo.entity.Customer;
import br.com.pontta.demo.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.orm.jpa.persistenceunit.PersistenceManagedTypes;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.postgresql.PostgreSQLContainer;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("teste")
@Testcontainers
class CustomerControllerTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer postgres = new PostgreSQLContainer("postgres:17-alpine");

    // O DeleteCustomerUseCase consulta pedidos, então o JPA precisa conhecer todas as entidades.
    @TestConfiguration
    static class CustomerOnlyPersistence {

        @Bean
        PersistenceManagedTypes persistenceManagedTypes() {
            return PersistenceManagedTypes.of(
                    Customer.class.getName());
        }
    }

    @Autowired
    private static CustomerRepository customerRepository;

    @Autowired
    private MockMvc mockMvc;


    @Test
    void findAll() throws Exception {
        mockMvc
                .perform(get("/customers"))
                .andExpect(status().isOk());

    }

    @Test
    void findById() throws Exception {
        mockMvc
                .perform(get("/customers/1"))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    assertTrue(result.getResponse().getContentAsString().contains("joao.silva@email.com"));
                });
    }

    @Test
    void createCustomer() throws Exception {
        String json = """
                {
                    "email": "teste@teste.com.br",
                    "firstName": "Teste",
                    "lastName": "Silva"
                }
                """;
        mockMvc
                .perform(post("/customers").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk());


    }

    @Test
    void updateCustomer() {
    }

    @Test
    void deleteCustomer() {
    }
}