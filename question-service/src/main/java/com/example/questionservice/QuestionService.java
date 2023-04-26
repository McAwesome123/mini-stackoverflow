package com.example.questionservice;

import jakarta.persistence.Access;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.PermissionDeniedDataAccessException;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;

@Service
public class QuestionService
{
	private final QuestionRepository questionRepository;

	@Autowired
	public QuestionService (QuestionRepository questionRepository)
	{
		this.questionRepository = questionRepository;
	}

	public Question CreateQuestion (Question question)
	{
		return questionRepository.save(question);
	}

	public Question EditQuestion (Question currentQuestion, Question wantedQuestion)
	{
		currentQuestion.setTitle(wantedQuestion.getTitle());
		currentQuestion.setDescription(wantedQuestion.getDescription());
		return questionRepository.save(currentQuestion);
	}

	public Question CloseQuestion (Question question)
	{
		question.setClosed(!question.getClosed());
		return questionRepository.save(question);
	}

	public Question GetQuestion(Long id)
	{
		return questionRepository.findById(id).orElse(null);
	}
}
