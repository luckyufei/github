package com.facetime.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.facetime.orm.dao.Dao;
import com.facetime.test.entity.Student;

public class HibernateTest {

	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
		Dao defaultDao = (Dao) context.getBean("defaultDao");
		System.out.println(defaultDao);
		defaultDao.delete(Student.class);
	}

}
