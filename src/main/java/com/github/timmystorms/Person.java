package com.github.timmystorms;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.springframework.data.annotation.CreatedDate;

@Entity
public class Person {

	@GeneratedValue
	@Id
	private Long id;
	
	@Column
	private String name;
	
	@Column
	@CreatedDate
	private Date creation;
	
	public Person() {}
	
	public Person(final String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return id + " " + name + " " + creation;
	}
	
}
