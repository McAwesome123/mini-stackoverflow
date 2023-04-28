package com.example.questionservice;

import jakarta.persistence.*;

@Entity
@Table (name = "answers")
public class Answer
{
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Long id;

	@Column (nullable = false, length = 65535)
	private String description;

	@Column (nullable = false)
	private Boolean marked;

	@Column (nullable = false)
	private Long userId;

	@Column (nullable = false)
	private Long questionId;

	public Answer (String description, Boolean marked, Long userId, Long questionId)
	{
		this.description = description;
		this.marked = marked;
		this.userId = userId;
		this.questionId = questionId;
	}

	public Answer ()
	{ }

	public Long getId ()
	{
		return id;
	}
	public void setId (Long id)
	{
		this.id = id;
	}
	public String getDescription ()
	{
		return description;
	}
	public void setDescription (String description)
	{
		this.description = description;
	}
	public Boolean getMarked ()
	{
		return marked;
	}
	public void setMarked (Boolean marked)
	{
		this.marked = marked;
	}
	public Long getUserId ()
	{
		return userId;
	}
	public void setUserId (Long userId)
	{
		this.userId = userId;
	}
	public Long getQuestionId ()
	{
		return questionId;
	}
	public void setQuestionId (Long questionId)
	{
		this.questionId = questionId;
	}
}
