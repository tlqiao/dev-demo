package com.jackson.puppy.mybatis.domain;

/**
 * @author Kevin
 * @since 4/14/2018
 */
public class Student {

	private Integer id;

	private String name;

	private Integer classId;
	//	ManyToOne
	private Class aClass;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getClassId() {
		return classId;
	}

	public void setClassId(Integer classId) {
		this.classId = classId;
	}

	public Class getaClass() {
		return aClass;
	}

	public void setaClass(Class aClass) {
		this.aClass = aClass;
	}
}
