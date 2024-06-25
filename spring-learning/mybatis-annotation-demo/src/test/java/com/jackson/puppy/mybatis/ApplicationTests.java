package com.jackson.puppy.mybatis;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.jackson.puppy.mybatis.dao.ClassDao;
import com.jackson.puppy.mybatis.dao.ExtendDao;
import com.jackson.puppy.mybatis.dao.StudentDao;
import com.jackson.puppy.mybatis.dao.TeacherDao;
import com.jackson.puppy.mybatis.domain.Class;
import com.jackson.puppy.mybatis.domain.Student;
import com.jackson.puppy.mybatis.domain.Teacher;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ClassDao classDao;

	@Autowired
	private StudentDao studentDao;

	@Autowired
	private TeacherDao teacherDao;

	@Autowired
	private ExtendDao extendDao;

	@Test
	public void findAllClass() {
		final List<Class> all = classDao.findAll();
		for (Class aClass : all) {
			logger.info("classId={}, className={}", aClass.getId(), aClass.getName());
			logger.info("before get teacher.");
			final Teacher teacher = aClass.getTeacher();
			logger.info("teacherId={}, teacherName={}", teacher.getId(), teacher.getName());
			logger.info("before get student.");
			final List<Student> students = aClass.getStudents();
			for (Student student : students) {
				logger.info("studentId={}, studentName={}", student.getId(), student.getName());
			}
			logger.info("==================================");
		}
	}

	@Test
	public void findAllStudent() {
		final List<Student> students = studentDao.findAll();
		for (Student student : students) {
			logger.info("studentId={}, studentName={}, aclass={}", student.getId(), student.getName(),student.getaClass());
		}
	}

	@Test
	public void findAllTeacher() {
		final List<Teacher> teachers = teacherDao.findAll();
		for (Teacher teacher : teachers) {
			logger.info("teacherId={}, teacherName={}, classes={} ", teacher.getId(), teacher.getName(), teacher.getClasses());
		}
	}

	@Test
	public void findStudentByNames() {
		final List<String> nameList = new ArrayList<>();
		nameList.add("Student B");
		nameList.add("Student C");
		nameList.add("Student H");
		final List<Student> students = studentDao.findByNames(nameList);
		for (Student student : students) {
			logger.info("studentId={}, studentName={}", student.getId(), student.getName());
		}
	}

	@Test
	public void findStudentByIds() {
		final List<Integer> idList = new ArrayList<>();
		idList.add(5);
		idList.add(6);
		idList.add(10);
		final List<Student> students = studentDao.findByIds(idList);
		for (Student student : students) {
			logger.info("studentId={}, studentName={}, studentClass={}", student.getId(), student.getName(),student.getaClass());
		}
	}

	@Test
	@Transactional
	public void insertClass() {
		String name = "For Test";
		Integer teacherId = 4;
		final Class aClass = new Class();
		final Teacher teacher = new Teacher();
		teacher.setId(teacherId);
		aClass.setName(name);
//		aClass.setTeacher(teacher);
		aClass.setTeacherId(teacherId);
		classDao.insert(aClass);
		final Class newClass = classDao.getById(aClass.getId());
		Assert.assertEquals(name, newClass.getName());
		Assert.assertEquals(teacherId, newClass.getTeacherId());
	}

	@Test
	@Transactional
	public void updateClass() {
		String name = "For Test";
		Integer teacherId = 4;
		Integer classId = 6;
		final Class aClass = new Class();
		final Teacher teacher = new Teacher();
		teacher.setId(teacherId);
		aClass.setId(classId);
		aClass.setName(name);
//		aClass.setTeacher(teacher);
		aClass.setTeacherId(teacherId);
		classDao.updateById(aClass);
		final Class newClass = classDao.getById(classId);
		Assert.assertEquals(newClass.getName(), name);
		Assert.assertEquals(newClass.getTeacherId(), teacherId);
	}

	@Test
	public void deleteClass() {
		String name = "For Test";
		Integer teacherId = 4;
		final Class aClass = new Class();
		final Teacher teacher = new Teacher();
		teacher.setId(teacherId);
		aClass.setName(name);
//		aClass.setTeacher(teacher);
		aClass.setTeacherId(teacherId);
		classDao.insert(aClass);
		final Integer classId = aClass.getId();
		classDao.deleteById(classId);
		final Class deletedClass = classDao.getById(classId);
		Assert.assertNull(deletedClass);
	}

	@Test
	@Transactional
	public void insertStudent() {
		String name = "For Test";
		Integer classId = 4;
		final Student student = new Student();
		final Class aClass = new Class();
		student.setName(name);
		aClass.setId(classId);
		student.setaClass(aClass);
//		student.setClassId(classId);
		studentDao.insert(student);
		final Student newStudent = studentDao.getById(student.getId());
		Assert.assertEquals(name, newStudent.getName());
		Assert.assertEquals(classId, newStudent.getClassId());
	}

	@Test
	@Transactional
	public void updateStudent() {
		String name = "For Test";
		Integer classId = 4;
		Integer studentId = 4;
		final Student student = new Student();
		final Class aClass = new Class();
		student.setName(name);
		student.setId(studentId);
		aClass.setId(classId);
		student.setaClass(aClass);
//		student.setClassId(classId)
		studentDao.updateById(student);
		final Student newStudent = studentDao.getById(student.getId());
		Assert.assertEquals(name, newStudent.getName());
		Assert.assertEquals(classId, newStudent.getClassId());
	}

	@Test
	@Transactional
	public void deleteStudent() {
		String name = "For Test";
		Integer classId = 4;
		final Student student = new Student();
		final Class aClass = new Class();
		student.setName(name);
		aClass.setId(classId);
		student.setaClass(aClass);
//		student.setClassId(classId);
		studentDao.insert(student);
		final Integer studentId = student.getId();
		studentDao.deleteById(studentId);
		final Student deletedStudent = studentDao.getById(studentId);
		Assert.assertNull(deletedStudent);
	}

	@Test
	@Transactional
	public void insertTeacher() {
		String name = "For Test";
		final Teacher teacher = new Teacher();
		teacher.setName(name);
		teacherDao.insert(teacher);
		final Teacher newTeacher = teacherDao.getById(teacher.getId());
		Assert.assertEquals(name, newTeacher.getName());
	}

	@Test
	@Transactional
	public void updateTeacher() {
		String name = "For Test";
		Integer teacherId = 4;
		final Teacher teacher = new Teacher();
		teacher.setId(teacherId);
		teacher.setName(name);
		teacherDao.updateById(teacher);
		final Teacher newTeacher = teacherDao.getById(teacher.getId());
		Assert.assertEquals(name, newTeacher.getName());
	}

	@Test
	@Transactional
	public void deleteTeacher() {
		String name = "For Test";
		final Teacher teacher = new Teacher();
		teacher.setName(name);
		teacherDao.insert(teacher);
		final Integer teacherId = teacher.getId();
		teacherDao.deleteById(teacherId);
		final Teacher deletedTeacher = teacherDao.getById(teacherId);
		Assert.assertNull(deletedTeacher);
	}

	@Test
	public void joinAll() {
		final List<Map<String, Object>> maps = extendDao.joinAll();
		for (Map<String, Object> map : maps) {
			final Integer teacherId = (Integer) map.get("teacherId");
			final String teacherName = (String) map.get("teacherName");
			final Integer classId = (Integer) map.get("classId");
			final String className = (String) map.get("className");
			final Integer studentId = (Integer) map.get("studentId");
			final String studentName = (String) map.get("studentName");
			logger.info("teacherId = {}, teacherName = {}, classId = {}, className = {}, studentId = {}, studentName = {}",
					teacherId, teacherName, classId, className, studentId, studentName);
		}
	}

	@Test
	public void joinAllPage() {
		final Page<Map<String, Object>> page = PageHelper.startPage(2, 5);
		joinAll();
		logger.info("total size = {}", page.getTotal());
	}

	@Test
	public void joinAllByTeacherIdAndClassIdAndStudentId() {
		final Map<String, Integer> queryMap = new HashMap<>();
		queryMap.put("teacherId", 2);
		queryMap.put("classId", 3);
		queryMap.put("studentId", 6);
		final List<Map<String, Object>> maps = extendDao.joinAllByTeacherIdAndClassIdAndStudentId(queryMap);
		for (Map<String, Object> map : maps) {
			final Integer teacherId = (Integer) map.get("teacherId");
			final String teacherName = (String) map.get("teacherName");
			final Integer classId = (Integer) map.get("classId");
			final String className = (String) map.get("className");
			final Integer studentId = (Integer) map.get("studentId");
			final String studentName = (String) map.get("studentName");
			logger.info("teacherId = {}, teacherName = {}, classId = {}, className = {}, studentId = {}, studentName = {}",
					teacherId, teacherName, classId, className, studentId, studentName);
		}
	}
}
