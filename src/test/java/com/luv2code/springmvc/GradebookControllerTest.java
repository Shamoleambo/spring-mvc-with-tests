package com.luv2code.springmvc;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

@TestPropertySource("/application.properties")
@AutoConfigureMockMvc
@SpringBootTest
public class GradebookControllerTest {

	@Autowired
	private JdbcTemplate jdbc;

	@Autowired
	private MockMvc mockMvc;

	@Mock
	private StudentAndGradeServiceTest studentAndGradeService;

	@BeforeEach
	void beforeEach() {
		this.jdbc.execute("insert into student(id, firstname, lastname, email_address) "
				+ "values (1, 'Mano', 'Dahora', 'mano@mail.com')");
	}
	
	@AfterEach
	void afterEach() {
		this.jdbc.execute("delete from student");
	}
}
