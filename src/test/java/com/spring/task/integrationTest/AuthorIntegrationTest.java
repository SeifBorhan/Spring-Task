package com.spring.task.integrationTest;


import com.spring.task.model.dto.AuthorDTO;
import com.spring.task.model.mapper.CourseMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthorIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CourseMapper courseMapper;

    private static HttpHeaders headers;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @Sql(statements = "INSERT INTO author(email, name) VALUES('test@example.com', 'John Doe')",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "DELETE FROM author WHERE email='test@example.com'",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testGetAuthorByEmail() {
        String url = "http://localhost:" + port + "/authors/test@example.com";

        ResponseEntity<AuthorDTO> response = restTemplate.getForEntity(url, AuthorDTO.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        AuthorDTO authorDTO = response.getBody();
        assert authorDTO != null;
        assertEquals("test@example.com", authorDTO.getEmail());
        assertEquals("John Doe", authorDTO.getName());
    }
}
