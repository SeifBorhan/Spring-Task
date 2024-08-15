package com.spring.task.integrationTest;

import com.spring.task.model.dto.CourseDTO;
import com.spring.task.model.entity.Course;
import com.spring.task.model.mapper.CourseMapper;
import com.spring.task.repository.CourseRepository;
import com.spring.task.service.CourseService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.http.*;
import org.testcontainers.shaded.com.fasterxml.jackson.core.JsonProcessingException;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.JsonNode;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CourseIntegrationTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private CourseService courseService;

    private static HttpHeaders headers;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeAll
    public static void init() {
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
    }
    private String createURLWithPort() {
        return "http://localhost:" + port + "/courses";
    }

    @Test
    @Sql(statements = "INSERT INTO course(id, name, description, credit) VALUES(2,'Python','Python Course',6)"
            ,executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "DELETE FROM course WHERE id=2",executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testViewCourse(){
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<CourseDTO> response = restTemplate.exchange(
                createURLWithPort() + "/view/2", HttpMethod.GET, entity, CourseDTO.class);
        CourseDTO courseDTO = response.getBody();
        assert courseDTO != null;
        assertEquals(response.getStatusCodeValue(), 200);
        assertEquals(courseDTO.getId(), 2L);
        assertEquals(courseDTO.getName(), "Python");
        assertEquals(courseDTO.getDescription(), "Python Course");
        assertEquals(courseDTO.getCredit(), 6);
    }

    @Test
    @Sql(statements = "DELETE FROM course WHERE id=2",executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testCreateCourse() throws JsonProcessingException {
        CourseDTO courseDTO = CourseDTO
                .builder()
                .id(2L)
                .name("Python")
                .description("Python Course")
                .credit(6)
                .build();
        Course course = courseMapper.toEntity(courseDTO);
        HttpEntity<String> entity = new HttpEntity<>(objectMapper.writeValueAsString(course), headers);
        ResponseEntity<CourseDTO> response = restTemplate.exchange(
                createURLWithPort() +"/add", HttpMethod.POST, entity, CourseDTO.class);
        assertEquals(response.getStatusCodeValue(), 200);
        assertEquals(response.getBody().getId(), courseDTO.getId());
        assertEquals(response.getBody().getName(), courseDTO.getName());
        assertEquals(response.getBody().getDescription(), courseDTO.getDescription());
        assertEquals(response.getBody().getCredit(), courseDTO.getCredit());
    }

    @Test
    @Sql(statements = "INSERT INTO course (id, name, description, credit) VALUES (2, 'Java', 'Java Course', 5)", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "DELETE FROM course WHERE id=2", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testUpdateCourse() throws JsonProcessingException {
        String updatedName = "Advanced Java";
        HttpEntity<String> entity = new HttpEntity<>(updatedName, headers);
        ResponseEntity<CourseDTO> response = restTemplate.exchange(
                createURLWithPort() + "/update/2", HttpMethod.PUT, entity, CourseDTO.class);

        assertEquals(response.getStatusCodeValue(), 200);
        CourseDTO updatedCourse = response.getBody();
        assertNotNull(updatedCourse);
        assertEquals(updatedCourse.getId(), 2L);
        assertEquals(updatedCourse.getName(), "Advanced Java");
    }

    @Test
    @Sql(statements = "INSERT INTO course (id, name, description, credit) VALUES (2, 'JavaScript', 'JavaScript Course', 4)", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "DELETE FROM course WHERE id=2", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testDeleteCourse() {
        HttpEntity<String> entity = new HttpEntity<>(headers);
        restTemplate.exchange(createURLWithPort() + "/delete/2", HttpMethod.DELETE, entity, Void.class);

        ResponseEntity<List<CourseDTO>> response = restTemplate.exchange(
                createURLWithPort() + "/discover", HttpMethod.GET, entity, new ParameterizedTypeReference<List<CourseDTO>>() {});

        List<CourseDTO> courses = response.getBody();
        assertNotNull(courses);
        assertTrue(courses.stream().noneMatch(course -> course.getId() == 2L));
    }

    @Test
    @Sql(statements = "INSERT INTO course (id, name, description, credit) VALUES (2, 'Data Science', 'Data Science Course', 7)", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "DELETE FROM course WHERE id=2", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testDiscoverCourses() {
        CourseDTO courseDTO = CourseDTO
                .builder()
                .id(2L)
                .name("Python")
                .description("Python Course")
                .credit(6)
                .build();
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<List<CourseDTO>> response = restTemplate.exchange(
                createURLWithPort() + "/discover", HttpMethod.GET, entity, new ParameterizedTypeReference<List<CourseDTO>>() {});

        List<CourseDTO> courses = response.getBody();
        assert courses != null;
        courses.add(courseDTO);
        assertNotNull(courses);
        assertFalse(courses.isEmpty());
        assertTrue(courses.stream().anyMatch(course -> course.getId() == 2L));
    }

    @Test
    @Sql(statements = "INSERT INTO course (id, name, description, credit) VALUES (2, 'English', 'English Course', 3), (3, 'Math', 'Math Course', 4)", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "DELETE FROM course WHERE id IN (2, 3)", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testGetAllCourses() throws IOException {
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort() + "/allCourses?page=0&size=10", HttpMethod.GET, entity, String.class);

        String responseBody = response.getBody();
        assertNotNull(responseBody);
        assertEquals(200, response.getStatusCodeValue());

        JsonNode rootNode = objectMapper.readTree(responseBody);

        int totalElements = rootNode.path("totalElements").asInt();
        int totalPages = rootNode.path("totalPages").asInt();
        JsonNode contentNode = rootNode.path("content");
        JsonNode firstCourse = contentNode.get(0);
        JsonNode secondCourse = contentNode.get(1);

        assertAll("Response Assertions",
                () -> assertEquals(2, totalElements, "Total elements should match the number of courses inserted"),
                () -> assertEquals(1, totalPages, "Total pages should be 1"),
                () -> assertTrue(contentNode.isArray(), "Content should be an array"),
                () -> assertEquals(2, contentNode.size(), "Number of courses in content should match the number of inserted courses")
        );

        assertAll("First Course Assertions",
                () -> assertEquals(2, firstCourse.path("id").asInt(), "ID of the first course should be 2"),
                () -> assertEquals("English", firstCourse.path("name").asText(), "Name of the first course should be 'English'"),
                () -> assertEquals("English Course", firstCourse.path("description").asText(), "Description of the first course should be 'English Course'"),
                () -> assertEquals(3, firstCourse.path("credit").asInt(), "Credit of the first course should be 3")
        );

        assertAll("Second Course Assertions",
                () -> assertEquals(3, secondCourse.path("id").asInt(), "ID of the second course should be 3"),
                () -> assertEquals("Math", secondCourse.path("name").asText(), "Name of the second course should be 'Math'"),
                () -> assertEquals("Math Course", secondCourse.path("description").asText(), "Description of the second course should be 'Math Course'"),
                () -> assertEquals(4, secondCourse.path("credit").asInt(), "Credit of the second course should be 4")
        );
    }



}
