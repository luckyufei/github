package com.facetime.test.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import com.facetime.orm.common.BaseTestCase;
import com.facetime.orm.common.Limitable;
import com.facetime.orm.common.Limitable.Order;
import com.facetime.orm.common.Limitable.OrderBy;
import com.facetime.orm.common.Limitable.PageBy;
import com.facetime.orm.common.Page;
import com.facetime.orm.common.QueryFilter;
import com.facetime.orm.dao.DaoImpl;
import com.facetime.test.entity.Student;

public class DaoImplTest extends BaseTestCase {

	private static final String NEW_PWD = "2newpwd";
	private static final String NEW_USER = "2newuser";
	private static final String PASS_WORD = "1oldpwd";
	private static final int SIZE = 2;
	private static final String USER_NAME = "1olduser";

	@Test
	public void testCountClass() {
		this.createStudents(5);
		long count = getDefaultDao().count(Student.class);
		Assert.assertEquals(5, count);
	}

	@Test
	public void testCountClassQueryFilter() {
		this.createStudents(5);
		this.createStudent(NEW_PWD);

		long count = getDefaultDao().count(Student.class, QueryFilter.valueOf("password", PASS_WORD));
		Assert.assertEquals(5, count);
		long total = getDefaultDao().count(Student.class);
		Assert.assertEquals(6, total);
	}

	@Test
	public void testCountClassQueryFilterArray() {
		this.createStudents(5);
		this.createStudent(NEW_USER, NEW_PWD);

		long count = getDefaultDao().count(
				Student.class,
				new QueryFilter[] { QueryFilter.valueOf("username", USER_NAME),
						QueryFilter.valueOf("password", PASS_WORD) });
		Assert.assertEquals(5, count);
		long total = getDefaultDao().count(Student.class);
		Assert.assertEquals(6, total);
	}

	/**
	 * 	{@link DaoImpl#count(String, Object[])}
	 */
	@Test
	public void testCountStringObjectArray() {
		this.createStudents(5);
		this.createStudent(NEW_USER, NEW_PWD);

		long count = getDefaultDao().count("from Student stu where stu.username = ? ", new Object[] { USER_NAME });
		Assert.assertEquals(5, count);
		long total = getDefaultDao().count(Student.class);
		Assert.assertEquals(6, total);
	}

	@Test
	public void testCreateBusinessObject() {
		getDefaultDao().save(new Student(USER_NAME, PASS_WORD));
		List<Student> students = getDefaultDao().findList(
				Student.class,
				new QueryFilter[] { QueryFilter.valueOf("username", USER_NAME),
						QueryFilter.valueOf("password", PASS_WORD) });
		Assert.assertEquals(1, students.size());
	}

	@Test
	public void testCreateCollectionOfQextendsBusinessObject() {
		getDefaultDao().save(
				Arrays.asList(new Student[] { new Student(USER_NAME, PASS_WORD), new Student(USER_NAME, PASS_WORD) }));
		List<Student> students = getDefaultDao().findList(Student.class, QueryFilter.valueOf("username", USER_NAME));
		Assert.assertEquals(2, students.size());
	}

	@Test
	public void testDeleteBusinessObject() {
		Student stu = createStudent();
		Assert.assertNotNull(stu);
		getDefaultDao().delete(stu);
		List<Student> students = findStudentsByName(USER_NAME);
		Assert.assertEquals(0, students.size());
	}

	@Test
	public void testDeleteClassOfQextendsBusinessObjectQueryFilter() {
		Student stu = createStudent();
		Assert.assertNotNull(stu);

		getDefaultDao().delete(Student.class, QueryFilter.valueOf("username", USER_NAME));
		List<Student> students = findStudentsByName(USER_NAME);
		Assert.assertEquals(0, students.size());
	}

	@Test
	public void testDeleteClassOfQextendsBusinessObjectQueryFilterArray() {
		Student stu = this.createStudent();
		Assert.assertNotNull(stu);

		getDefaultDao().delete(
				Student.class,
				new QueryFilter[] { QueryFilter.valueOf("username", USER_NAME),
						QueryFilter.valueOf("password", PASS_WORD) });
		List<Student> students = findStudentsByName(USER_NAME);
		Assert.assertEquals(0, students.size());
	}

	@Test
	public void testDeleteClassOfQextendsBusinessObjectSerializable() {
		Student stu = createStudent();
		getDefaultDao().deleteById(Student.class, stu.getId());

		List<Student> students = findStudentsByName(USER_NAME);
		Assert.assertEquals(0, students.size());
	}

	@Test
	public void testDeleteClassOfQextendsBusinessObjectSerializableArray() {
		List<Student> students = createStudents();
		students = findStudentsByName(USER_NAME);
		Assert.assertEquals(2, students.size());

		getDefaultDao().deleteByIds(Student.class,
				new Serializable[] { students.get(0).getId(), students.get(1).getId() });
		students = findStudentsByName(USER_NAME);
		Assert.assertEquals(0, students.size());
	}

	@Test
	public void testDeleteCollectionOfQextendsBusinessObject() {
		List<Student> students = createStudents();
		Assert.assertEquals(2, students.size());

		getDefaultDao().delete(students);
		students = findStudentsByName(USER_NAME);
		Assert.assertEquals(0, students.size());
	}

	@Test
	public void testExecuteFindString() {
		this.createStudent();
		StringBuilder hql = new StringBuilder();
		hql.append("from ").append(Student.class.getName()).append(" as obj ");
		List<Student> students = getDefaultDao().findHQL(hql.toString());
		Assert.assertEquals(1, students.size());
	}

	@Test
	public void testExecuteFindStringObjectArray() {
		this.createStudent();
		this.createStudent(NEW_PWD);

		StringBuilder hql = new StringBuilder();
		List<Object> values = new ArrayList<Object>();
		hql.append("from ").append(Student.class.getName()).append(" as obj ");
		hql.append(" where obj.username = ? and obj.password = ? ");
		values.add(USER_NAME);
		values.add(PASS_WORD);
		List<Student> students = getDefaultDao().findHQL(hql.toString(), values.toArray());
		Assert.assertEquals(1, students.size());
	}

	@Test
	public void testFindAssignedPartClassOfQextendsBusinessObjectReturnProperty() {
		this.createStudents(10);
		List<Object[]> props = getDefaultDao().findPart(Student.class, new String[] { "username", "password" });
		Assert.assertEquals(10, props.size());
		for (Object[] prop : props) {
			Assert.assertEquals(USER_NAME, prop[0].toString());
			Assert.assertEquals(PASS_WORD, prop[1].toString());
		}
	}

	@Test
	public void testFindAssignedPartClassOfQextendsBusinessObjectReturnPropertyQueryFilter() {
		this.createStudents(5);
		List<Object[]> students = getDefaultDao().findPart(Student.class, QueryFilter.valueOf("username", USER_NAME),
				new String[] { "id", "username", "password" });
		Assert.assertEquals(5, students.size());
		for (Object[] props : students) {
			Assert.assertNotNull(props[0]);
			Assert.assertEquals(USER_NAME, props[1].toString());
			Assert.assertEquals(PASS_WORD, props[2].toString());
		}
	}

	@Test
	public void testFindAssignedPartClassOfQextendsBusinessObjectReturnPropertyQueryFilterArrayOrderByArrayPageBy() {
		this.createStudents(11);
		List<Object[]> students = getDefaultDao().findPart(
				Student.class,
				new QueryFilter[] { QueryFilter.valueOf("username", USER_NAME),
						QueryFilter.valueOf("password", PASS_WORD) }, new String[] { "id", "username", "password" },
				OrderBy.valueOf("username", Order.DESC), OrderBy.valueOf("password"), new PageBy(0, 10));
		Assert.assertEquals(10, students.size());
		for (Object[] props : students) {
			Assert.assertNotNull(props[0]);
			Assert.assertEquals(USER_NAME, props[1].toString());
			Assert.assertEquals(PASS_WORD, props[2].toString());
		}
	}

	@Test
	public void testFindAssignedPartClassOfQextendsBusinessObjectReturnPropertyQueryFilterOrderBy() {
		this.createStudents(5);
		List<String[]> students = getDefaultDao().findPart(Student.class, QueryFilter.valueOf("username", USER_NAME),
				new String[] { "username", "password" }, OrderBy.valueOf("username", Order.DESC));
		Assert.assertEquals(5, students.size());
		for (Object[] props : students) {
			Assert.assertNotNull(props[0]);
			Assert.assertEquals(USER_NAME, props[0].toString());
			Assert.assertEquals(PASS_WORD, props[1].toString());
		}
	}

	@Test
	public void testFindAssignedPartClassOfQextendsBusinessObjectReturnPropertyQueryFilterOrderByPageBy() {
		this.createStudents(11);
		List<Object[]> students = getDefaultDao().findPart(Student.class, QueryFilter.valueOf("username", USER_NAME),
				new String[] { "id", "username", "password" }, OrderBy.valueOf("username", Order.DESC),
				new PageBy(0, 10));
		Assert.assertEquals(10, students.size());
		for (Object[] props : students) {
			Assert.assertNotNull(props[0]);
			Assert.assertEquals(USER_NAME, props[1].toString());
			Assert.assertEquals(PASS_WORD, props[2].toString());
		}

	}

	@Test
	public void testFindClassOfT() {
		List<Student> stus = this.createStudents();
		Assert.assertEquals(2, stus.size());

		stus = getDefaultDao().findList(Student.class);
		Assert.assertEquals(2, stus.size());
	}

	@Test
	public void testFindClassOfTOrderByArrayPageBy() {
		this.createStudent(PASS_WORD);
		this.createStudent(NEW_PWD);

		List<Student> students = getDefaultDao().findList(Student.class, OrderBy.valueOf("password", Order.DESC),
				OrderBy.valueOf("username"), new PageBy(0, 2));
		Assert.assertEquals(2, students.size());
		Assert.assertEquals(NEW_PWD, students.get(0).getPassword());
	}

	@Test
	public void testFindClassOfTPageBy() {
		this.createStudents(11);
		List<Student> students = getDefaultDao().findList(Student.class, new PageBy(0, 5));
		Assert.assertEquals(5, students.size());
	}

	@Test
	public void testFindClassOfTQueryFilter() {
		this.createStudent();
		List<Student> stus = getDefaultDao().findList(Student.class, QueryFilter.valueOf("username", USER_NAME));
		Assert.assertEquals(1, stus.size());
	}

	@Test
	public void testFindClassOfTQueryFilterArray() {
		this.createStudent();
		List<Student> students = getDefaultDao().findList(
				Student.class,
				new QueryFilter[] { QueryFilter.valueOf("username", USER_NAME),
						QueryFilter.valueOf("password", PASS_WORD) });
		Assert.assertEquals(1, students.size());
	}

	@Test
	public void testFindClassOfTQueryFilterArrayOrderBy() {
		this.createStudent(PASS_WORD);
		this.createStudent(NEW_PWD);
		List<Student> students = getDefaultDao().findList(Student.class,
				new QueryFilter[] { QueryFilter.valueOf("username", USER_NAME) },
				OrderBy.valueOf("password", Order.DESC));
		Assert.assertEquals(2, students.size());
		Assert.assertEquals(NEW_PWD, students.get(0).getPassword());
	}

	@Test
	public void testFindClassOfTQueryFilterArrayOrderByArray() {
		this.createStudent(PASS_WORD);
		this.createStudent(NEW_PWD);
		List<Student> students = getDefaultDao().findList(Student.class,
				new QueryFilter[] { QueryFilter.valueOf("username", USER_NAME) },
				new OrderBy[] { OrderBy.valueOf("password", Order.DESC), OrderBy.valueOf("username") });
		Assert.assertEquals(2, students.size());
		Assert.assertEquals(NEW_PWD, students.get(0).getPassword());
	}

	@Test
	public void testFindClassOfTQueryFilterArrayOrderByPageBy() {
		this.createStudents(11);
		List<Student> students = getDefaultDao().findList(Student.class,
				new QueryFilter[] { QueryFilter.valueOf("username", USER_NAME) },
				OrderBy.valueOf("password", Order.DESC), new PageBy(0, 10));
		Assert.assertEquals(10, students.size());
	}

	@Test
	public void testFindClassOfTQueryFilterOrderBy() {
		this.createStudent(PASS_WORD);
		this.createStudent(NEW_PWD);

		List<Student> stus = getDefaultDao().findList(Student.class, QueryFilter.valueOf("username", USER_NAME),
				OrderBy.valueOf("password"));
		Assert.assertEquals(2, stus.size());
		Assert.assertEquals(PASS_WORD, stus.get(0).getPassword());

		stus = getDefaultDao().findList(Student.class, QueryFilter.valueOf("username", USER_NAME),
				OrderBy.valueOf("password", Order.DESC));
		Assert.assertEquals(2, stus.size());
		Assert.assertEquals(NEW_PWD, stus.get(0).getPassword());
	}

	@Test
	public void testFindClassOfTQueryFilterOrderByPageBy() {
		this.createStudents(11);
		List<Student> students = getDefaultDao().findList(Student.class, QueryFilter.valueOf("username", USER_NAME),
				OrderBy.valueOf("password"), new PageBy(0, 10));
		Assert.assertEquals(10, students.size());
	}

	@Test
	public void testFindClassOfTQueryFilterPageBy() {
		this.createStudents(11);
		List<Student> students = getDefaultDao().findList(Student.class, QueryFilter.valueOf("username", USER_NAME),
				new PageBy(0, 10));
		Assert.assertEquals(10, students.size());
	}

	@Test
	public void testFindClassOfTSerializable() {
		Student stu = this.createStudent();
		Assert.assertNotNull(stu);
		Assert.assertTrue(stu.getId().longValue() > 0);

		stu = getDefaultDao().findById(Student.class, stu.getId());
		Assert.assertNotNull(stu);
	}

	/**
	 * {@link DaoImpl#findIdArray(Class, QueryFilter[], Limitable...)}
	 */
	@Test
	public void testFindIdArrayClassQueryFilterArray() {
		this.createStudents(5);
		Object[] idArray = getDefaultDao().findIdArray(Student.class,
				new QueryFilter[] { QueryFilter.valueOf("username", USER_NAME) }, OrderBy.valueOf("username"));
		Assert.assertEquals(5, idArray.length);
	}

	@Test
	public void testFindPageClass() {
		this.createStudents(10);
		this.createStudent(NEW_USER, NEW_PWD);
		Page<Student> result = getDefaultDao().findPage(Student.class, PageBy.valueOf(1, 11));
		Assert.assertEquals(11, result.getQueryResult().size());
		Assert.assertEquals(11, result.getRecordCount());
	}

	@Test
	public void testFindPageClassQueryFilter() {
		this.createStudents(10);
		this.createStudent(NEW_USER, NEW_PWD);
		Page<Student> result = getDefaultDao().findPage(Student.class, QueryFilter.valueOf("username", USER_NAME),
				PageBy.valueOf(1, 8));
		Assert.assertEquals(8, result.getQueryResult().size());
		Assert.assertEquals(10, result.getRecordCount());
	}

	@Test
	public void testFindPageClassQueryFilterArray() {
		this.createStudents(10);
		this.createStudent(NEW_USER, NEW_PWD);
		Page<Student> result = getDefaultDao().findPage(Student.class, QueryFilter.valueOf("username", USER_NAME),
				new PageBy(0, 8));
		Assert.assertEquals(8, result.getQueryResult().size());
		Assert.assertEquals(10, result.getRecordCount());
	}

	@Test
	public void testFindPageClassQueryFilterArrayOrderBy() {
		this.createStudents(10);
		this.createStudent(NEW_USER, NEW_PWD);
		Page<Student> result = getDefaultDao().findPage(
				Student.class,
				new QueryFilter[] { QueryFilter.valueOf("username", USER_NAME),
						QueryFilter.valueOf("password", PASS_WORD) }, OrderBy.desc("id"), PageBy.valueOf(1, 10));
		Assert.assertEquals(10, result.getQueryResult().size());
		Assert.assertEquals(10, result.getRecordCount());
		orderByAssert(result.getQueryResult());
	}

	@Test
	public void testFindPageClassQueryFilterArrayOrderByArray() {
		this.createStudents(10);
		this.createStudent(NEW_USER, NEW_PWD);
		Page<Student> result = getDefaultDao().findPage(
				Student.class,
				new QueryFilter[] { QueryFilter.valueOf("username", USER_NAME),
						QueryFilter.valueOf("password", PASS_WORD) }, OrderBy.desc("id"), OrderBy.desc("username"),
				PageBy.valueOf(1, 8));
		Assert.assertEquals(8, result.getQueryResult().size());
		Assert.assertEquals(10, result.getRecordCount());
		orderByAssert(result.getQueryResult());
	}

	@Test
	public void testFindPageClassQueryFilterArrayOrderByArrayPageBy() {
		this.createStudents(10);
		this.createStudent(NEW_USER, NEW_PWD);
		Page<Student> result = getDefaultDao().findPage(
				Student.class,
				new QueryFilter[] { QueryFilter.valueOf("username", USER_NAME),
						QueryFilter.valueOf("password", PASS_WORD) }, OrderBy.valueOf("id", Order.DESC),
				OrderBy.valueOf("username", Order.DESC), new PageBy(0, 8));
		Assert.assertEquals(8, result.getQueryResult().size());
		Assert.assertEquals(10, result.getRecordCount());
		orderByAssert(result.getQueryResult());
	}

	@Test
	public void testFindPageClassQueryFilterArrayOrderByPageBy() {
		this.createStudents(10);
		this.createStudent(NEW_USER, NEW_PWD);
		Page<Student> result = getDefaultDao().findPage(
				Student.class,
				new QueryFilter[] { QueryFilter.valueOf("username", USER_NAME),
						QueryFilter.valueOf("password", PASS_WORD) }, OrderBy.valueOf("id", Order.DESC),
				new PageBy(0, 8));
		Assert.assertEquals(8, result.getQueryResult().size());
		Assert.assertEquals(10, result.getRecordCount());
		orderByAssert(result.getQueryResult());
	}

	@Test
	public void testFindPageClassQueryFilterArrayPageBy() {
		this.createStudents(10);
		this.createStudent(NEW_USER, NEW_PWD);
		Page<Student> result = getDefaultDao().findPage(
				Student.class,
				new QueryFilter[] { QueryFilter.valueOf("username", USER_NAME),
						QueryFilter.valueOf("password", PASS_WORD) }, new PageBy(0, 8));
		Assert.assertEquals(8, result.getQueryResult().size());
		Assert.assertEquals(10, result.getRecordCount());
	}

	@Test
	public void testFindPageClassQueryFilterOrderBy() {
		this.createStudents(10);
		this.createStudent(NEW_USER, NEW_PWD);
		Page<Student> result = getDefaultDao().findPage(Student.class, QueryFilter.valueOf("username", USER_NAME),
				OrderBy.desc("id"), PageBy.valueOf(1, 8));
		Assert.assertEquals(8, result.getQueryResult().size());
		Assert.assertEquals(10, result.getRecordCount());
		List<Student> students = result.getQueryResult();
		orderByAssert(students);
	}

	@Test
	public void testFindPageClassQueryFilterOrderByArray() {
		this.createStudents(10);
		this.createStudent(NEW_USER, NEW_PWD);
		Page<Student> result = getDefaultDao().findPage(Student.class, QueryFilter.valueOf("username", USER_NAME),
				OrderBy.desc("id"), OrderBy.desc("username"), PageBy.valueOf(1, 8));
		Assert.assertEquals(8, result.getQueryResult().size());
		Assert.assertEquals(10, result.getRecordCount());
		List<Student> students = result.getQueryResult();
		orderByAssert(students);
	}

	@Test
	public void testFindPageClassQueryFilterOrderByArrayPageBy() {
		this.createStudents(10);
		this.createStudent(NEW_USER, NEW_PWD);
		Page<Student> result = getDefaultDao().findPage(
				Student.class,
				QueryFilter.valueOf("username", USER_NAME),
				new Limitable[] { OrderBy.valueOf("id", Order.DESC), OrderBy.valueOf("username", Order.DESC),
						new PageBy(0, 8) });
		Assert.assertEquals(8, result.getQueryResult().size());
		Assert.assertEquals(10, result.getRecordCount());
		List<Student> students = result.getQueryResult();
		orderByAssert(students);
	}

	@Test
	public void testFindPageClassQueryFilterOrderByPageBy() {
		this.createStudents(10);
		this.createStudent(NEW_USER, NEW_PWD);
		this.createStudent(NEW_USER, NEW_PWD);
		Page<Student> result = getDefaultDao().findPage(Student.class, QueryFilter.valueOf("username", USER_NAME),
				OrderBy.valueOf("id", Order.DESC), new PageBy(0, 8));
		Assert.assertEquals(8, result.getQueryResult().size());
		Assert.assertEquals(10, result.getRecordCount());
		List<Student> students = result.getQueryResult();
		orderByAssert(students);
	}

	@Test
	public void testFindPageClassQueryFilterPageBy() {
		this.createStudents(10);
		this.createStudent(NEW_USER, NEW_PWD);
		Page<Student> result = getDefaultDao().findPage(Student.class, QueryFilter.valueOf("username", USER_NAME),
				new PageBy(0, 8));
		Assert.assertEquals(8, result.getQueryResult().size());
		Assert.assertEquals(10, result.getRecordCount());
	}

	@Test
	public void testFindString() {
		this.createStudent();
		List<Student> students = getDefaultDao().findHQL(" from Student ");
		Assert.assertEquals(1, students.size());
	}

	@Test
	public void testFindStringObjectArray() {
		this.createStudent();
		List<Student> students = getDefaultDao().findHQL("from Student where username=? and password=?",
				new Object[] { USER_NAME, PASS_WORD });
		Assert.assertEquals(1, students.size());
	}

	@Test
	public void testFindStringObjectArrayPageBy() {
		this.createStudents(11);
		List<Student> students = getDefaultDao().findHQL("from Student where username=? and password=?",
				new Object[] { USER_NAME, PASS_WORD }, new PageBy(0, 10));
		Assert.assertEquals(10, students.size());
	}

	@Test
	public void testGetIdentifierName() {
		String idName = getDefaultDao().getIdentifierName(Student.class);
		Assert.assertNotNull(idName);
		Assert.assertEquals("id", idName);
	}

	@Test
	public void testUpdateBusinessObject() {
		Student stu = createStudent();
		Assert.assertNotNull(stu);

		stu.setPassword(NEW_PWD);
		getDefaultDao().update(stu);
		List<Student> students = findStudentsByName(USER_NAME);
		Assert.assertEquals(1, students.size());
		Assert.assertEquals(NEW_PWD, students.get(0).getPassword());
	}

	@Test
	public void testUpdateBusinessObjectStringArray() {
		Student stu = this.createStudent();
		Assert.assertNotNull(stu);

		List<Student> students = findStudentsByName(USER_NAME);
		Assert.assertEquals(1, students.size());
		stu = students.get(0);
		// stu.setUsername("xxxxx"); 此时会改变Session中的username
		stu.setPassword(NEW_PWD);
		getDefaultDao().update(stu, new String[] { "password" });
		students = findStudentsByName(USER_NAME);
		Assert.assertEquals(1, students.size());
		Assert.assertEquals(NEW_PWD, students.get(0).getPassword());
		Assert.assertEquals(USER_NAME, students.get(0).getUsername());
	}

	@Test
	public void testUpdateCollectionOfQextendsBusinessObject() {
		List<Student> students = createStudents();
		Assert.assertEquals(2, students.size());

		for (Student stu : students)
			stu.setPassword(NEW_PWD);
		getDefaultDao().update(students);
		students = findStudentsByName(USER_NAME);
		Assert.assertEquals(2, students.size());
		for (Student stu : students)
			Assert.assertEquals(NEW_PWD, stu.getPassword());
	}

	/**
	 * @see  DaoImpl#update(Class, QueryFilter[], String[], Object[])
	 */
	@Deprecated
	public void untestUpdateClassOfQextendsBusinessObjectStringArrayObjectArrayQueryFilterArray() {
		Student stu = this.createStudent();
		getDefaultDao().update(Student.class, new QueryFilter[] { QueryFilter.valueOf("username", USER_NAME) },
				new String[] { "username", "password" }, new Object[] { NEW_USER, NEW_PWD });
		List<Student> students = findStudentsByName(USER_NAME);
		Assert.assertEquals(0, students.size());
		students = findStudentsByName(NEW_USER);
		Assert.assertEquals(1, students.size());
		stu = students.get(0);
		Assert.assertEquals(NEW_PWD, stu.getPassword());
	}

	private Student createStudent() {
		Student stu = new Student(USER_NAME, PASS_WORD);
		getDefaultDao().save(stu);
		Assert.assertNotNull(stu);
		return stu;
	}

	private Student createStudent(String password) {
		Student stu = new Student(USER_NAME, password);
		getDefaultDao().save(stu);
		Assert.assertNotNull(stu);
		return stu;
	}

	private Student createStudent(String username, String password) {
		Student stu = new Student(username, password);
		getDefaultDao().save(stu);
		Assert.assertNotNull(stu);
		return stu;
	}

	private List<Student> createStudents() {
		return createStudents(SIZE);
	}

	private List<Student> createStudents(int size) {
		Student[] students = new Student[size];
		for (int i = 0; i < size; i++)
			students[i] = new Student(USER_NAME, PASS_WORD);
		getDefaultDao().save(Arrays.asList(students));
		return Arrays.asList(students);
	}

	private List<Student> findStudentsByName(String name) {
		List<Student> students = getDefaultDao().findList(Student.class, QueryFilter.valueOf("username", name));
		return students;
	}

	private void orderByAssert(List<Student> students) {
		for (int i = 0; i < students.size(); i++) {
			if (i == 0)
				continue;
			Assert.assertEquals(true, students.get(i).getId() < students.get(i - 1).getId());
		}
	}
}
