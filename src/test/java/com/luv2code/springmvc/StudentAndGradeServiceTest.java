package com.luv2code.springmvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import com.luv2code.springmvc.models.CollegeStudent;
import com.luv2code.springmvc.repository.StudentDao;
import com.luv2code.springmvc.service.StudentAndGradeService;

@TestPropertySource("/application.properties")
@SpringBootTest
public class StudentAndGradeServiceTest {

	@Autowired
	private JdbcTemplate jdbc;

	@Autowired
	private StudentAndGradeService studentService;

	@Autowired
	private StudentDao studentDao;

	@BeforeEach
	void setupDatabase() {
		this.jdbc.execute("insert into student(id, firstname, lastname, email_address) "
				+ "values (1, 'Truta', 'Maneiro', 'truta@mail.com')");
	}

	@AfterEach
	void setupAfterTransaction() {
		this.jdbc.execute("delete from student");
	}

	@Test
	void createStudentService() {
		this.studentService.createStudent("Mano", "Dahora", "mano@mail.com");

		CollegeStudent student = this.studentDao.findByEmailAddress("mano@mail.com");

		assertEquals("mano@mail.com", student.getEmailAddress(), "find by email");
	}

	@Test
	void isStudentNullCheck() {
		assertTrue(this.studentService.checkIfStudentIsNull(1));
		assertFalse(this.studentService.checkIfStudentIsNull(0));
	}

	@Test
	void deleteStudentService() {
		Optional<CollegeStudent> student = this.studentDao.findById(1);
		assertTrue(student.isPresent(), "Return true");

		this.studentService.deleteStudent(1);
		student = this.studentDao.findById(1);
		assertFalse(student.isPresent());
	}

	@Sql("/insertData.sql")
	@Test
	void getGradebookService() {
		Iterable<CollegeStudent> iterableCollegeStudents = this.studentService.getGradebook();
		List<CollegeStudent> collegeStudents = new ArrayList<>();
		for (CollegeStudent student : iterableCollegeStudents) {
			collegeStudents.add(student);
		}

		assertEquals(5, collegeStudents.size());
	}
}
