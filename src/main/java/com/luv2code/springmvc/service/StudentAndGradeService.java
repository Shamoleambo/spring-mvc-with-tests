package com.luv2code.springmvc.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luv2code.springmvc.models.CollegeStudent;
import com.luv2code.springmvc.repository.StudentDao;

@Service
@Transactional
public class StudentAndGradeService {

	@Autowired
	private StudentDao studentDao;

	public void createStudent(String firstName, String lastName, String emailAddress) {
		CollegeStudent student = new CollegeStudent(firstName, lastName, emailAddress);
		student.setId(0);
		this.studentDao.save(student);
	}

	public boolean checkIfStudentIsNull(int id) {
		Optional<CollegeStudent> student = this.studentDao.findById(id);
		if (student.isPresent()) {
			return true;
		}
		return false;
	}

	public void deleteStudent(int id) {
		if (this.checkIfStudentIsNull(id)) {
			this.studentDao.deleteById(id);
		}
	}

	public Iterable<CollegeStudent> getGradebook() {
		Iterable<CollegeStudent> collegeStudents = this.studentDao.findAll();
		return collegeStudents;
	}
}
