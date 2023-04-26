package com.example.questionservice;

import jakarta.persistence.*;

@Entity
@Table (name = "questions")
public class Question
{
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Long id;

	@Column (nullable = false, length = 120)
	private String title;

	@Column (nullable = false, length = 65535)
	private String description;

	@Column (nullable = false)
	private Boolean closed;

	@Column (nullable = false)
	private Long userId;

	public Question (String title, String description, Boolean closed, Long userId)
	{
		this.title = title;
		this.description = description;
		this.closed = closed;
		this.userId = userId;
	}

	public Question ()
	{ }

	public Long getId ()
	{
		return id;
	}
	public void setId (Long id)
	{
		this.id = id;
	}
	public String getTitle ()
	{
		return title;
	}
	public void setTitle (String title)
	{
		this.title = title;
	}
	public String getDescription ()
	{
		return description;
	}
	public void setDescription (String description)
	{
		this.description = description;
	}
	public Boolean getClosed ()
	{
		return closed;
	}
	public void setClosed (Boolean closed)
	{
		this.closed = closed;
	}
	public Long getUserId ()
	{
		return userId;
	}
	public void setUserId (Long userId)
	{
		this.userId = userId;
	}
}
