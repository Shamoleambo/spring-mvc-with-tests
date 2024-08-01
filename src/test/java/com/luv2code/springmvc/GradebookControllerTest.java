package com.luv2code.springmvc;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.ModelAndViewAssert;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.servlet.ModelAndView;

import com.luv2code.springmvc.models.CollegeStudent;
import com.luv2code.springmvc.models.GradebookCollegeStudent;
import com.luv2code.springmvc.service.StudentAndGradeService;

@TestPropertySource("/application.properties")
@AutoConfigureMockMvc
@SpringBootTest
public class GradebookControllerTest {

	@Autowired
	private JdbcTemplate jdbc;

	@Autowired
	private MockMvc mockMvc;

	@Mock
	private StudentAndGradeService studentAndGradeServiceMock;

	@BeforeEach
	void beforeEach() {
		this.jdbc.execute("insert into student(id, firstname, lastname, email_address) "
				+ "values (1, 'Mano', 'Dahora', 'mano@mail.com')");
	}

	@AfterEach
	void afterEach() {
		this.jdbc.execute("delete from student");
	}

	@Test
	void getStudentHttpRequest() throws Exception {
		CollegeStudent studentOne = new GradebookCollegeStudent("Truta", "Maneiro", "truta@mail.com");
		CollegeStudent studentTwo = new GradebookCollegeStudent("Tiu", "Deboa", "tiu@mail.com");

		List<CollegeStudent> students = new ArrayList<>(Arrays.asList(studentOne, studentTwo));
		when(this.studentAndGradeServiceMock.getGradebook()).thenReturn(students);

		assertIterableEquals(students, this.studentAndGradeServiceMock.getGradebook());

		MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get("/")).andExpect(status().isOk())
				.andReturn();
		ModelAndView mav = mvcResult.getModelAndView();

		ModelAndViewAssert.assertViewName(mav, "index");
	}

}
