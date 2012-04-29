package com.facetime.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.facetime.test.entity.Student;
import com.facetime.test.logic.StudentLogic;

public class StudentLogicTest {

	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:beans.xml");
		StudentLogic stuLogic = (StudentLogic) context.getBean("studentLogic");
		stuLogic.regist(new Student("laixiao", "nihao"));
		System.out.println("completed.");
	}
}
