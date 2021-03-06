package com.woong.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.springframework.beans.factory.annotation.Autowired;

@Entity
public class Question {
	
	@Id
	@GeneratedValue
	private Long id;
	
	private String writer;
	
	private String title;
	
	private String contents;

	public Question() {}
	
	public Question(String writer, String title, String contents) {
		super();
		this.writer = writer;
		this.title = title;
		this.contents = contents;
	}
	
	
	
}
