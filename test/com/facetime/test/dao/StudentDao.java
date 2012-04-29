package com.facetime.test.dao;

import com.facetime.orm.dao.Dao;
import com.facetime.test.entity.Student;

public interface StudentDao extends Dao{

	public void create(Student stu);
}
