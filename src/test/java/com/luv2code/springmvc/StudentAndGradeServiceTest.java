package com.luv2code.springmvc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import com.luv2code.springmvc.models.CollegeStudent;
import com.luv2code.springmvc.repository.StudentDao;
import com.luv2code.springmvc.service.StudentAndGradeService;

@TestPropertySource("/application.properties")
@SpringBootTest
public class StudentAndGradeServiceTest {

	@Autowired
	private StudentAndGradeService studentService;
	
	@Autowired
	private StudentDao studentDao;

	@Test
	void createStudentService() {
		this.studentService.createStudent("Mano", "Dahora", "mano@mail.com");
		
		CollegeStudent student = this.studentDao.findByEmailAddress("mano@mail.com");
	}
}
