package com.jackson.puppy.mybatis.domain;

import java.util.List;

/**
 * @author Kevin
 * @since 4/14/2018
 */
public class Teacher {

	private Integer id;

	private String name;

	//	OneToMany
	private List<Class> classes;

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

	public List<Class> getClasses() {
		return classes;
	}

	public void setClasses(List<Class> classes) {
		this.classes = classes;
	}
}
