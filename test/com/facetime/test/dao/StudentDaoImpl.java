package com.facetime.test.dao;

import org.springframework.stereotype.Repository;

import com.facetime.orm.dao.DaoImpl;
import com.facetime.test.entity.Student;

@Repository("studentDao")
public class StudentDaoImpl extends DaoImpl implements StudentDao {

	@Override
	public void create(Student stu) {
		super.save(stu);
	}
}
