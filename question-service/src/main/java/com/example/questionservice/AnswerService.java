package com.example.questionservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
public class AnswerService
{
	private final AnswerRepository answerRepository;

	@Autowired
	public AnswerService (AnswerRepository answerRepository)
	{
		this.answerRepository = answerRepository;
	}

	public Answer CreateAnswer (Long questionId, Long userId, Answer answer)
	{
		answer.setQuestionId(questionId);
		answer.setUserId(userId);
		return answerRepository.save(answer);
	}

	public Answer EditAnswer (Answer currentAnswer, Answer intendedAnswer)
	{
		currentAnswer.setDescription(intendedAnswer.getDescription());
		currentAnswer.setMarked(false);
		return answerRepository.save(currentAnswer);
	}

	public Answer MarkAnswer(Answer answer)
	{
		answer.setMarked(!answer.getMarked());
		return answerRepository.save(answer);
	}

	public Answer GetAnswer(Long id)
	{
		return answerRepository.findById(id).orElse(null);
	}
}
