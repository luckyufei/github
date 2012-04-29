package com.facetime.test.logic;

import org.springframework.stereotype.Service;

import com.facetime.orm.logic.LogicImpl;
import com.facetime.test.dao.StudentDao;
import com.facetime.test.entity.Student;

@Service("studentLogic")
public class StudentLogicImpl extends LogicImpl implements StudentLogic {

	@Override
	public void regist(Student stu) {
		this.locate(StudentDao.class).create(stu);
	}
}
