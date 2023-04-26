package com.example.questionservice;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping ("/questions")
public class AnswerController
{
	private final AnswerService answerService;
	private final QuestionService questionService;

	@Autowired
	public AnswerController(AnswerService answerService, QuestionService questionService)
	{
		this.answerService = answerService;
		this.questionService = questionService;
	}

	@PostMapping ("/{questionId}/answer")
	public ResponseEntity<Answer> CreateAnswer(@PathVariable Long questionId, @RequestBody Answer answer, HttpSession session)
	{
		if (session.getAttribute("userId") == null)
		{
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		Question question = questionService.GetQuestion(questionId);
		if (question == null)
		{
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		if (question.getClosed())
		{
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

		Answer createdAnswer;
		try
		{
			createdAnswer = answerService.CreateAnswer(questionId, (Long)session.getAttribute("userId"), answer);
		}
		catch (ClassCastException e)
		{
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(createdAnswer, HttpStatus.CREATED);
	}

	@GetMapping("/{questionId}/{answerId}")
	public Answer GetAnswer(@PathVariable Long questionId, @PathVariable Long answerId)
	{
		if (questionService.GetQuestion(questionId) == null)
		{
			return null;
		}
		return answerService.GetAnswer(answerId);
	}

	@PutMapping ("/{questionId}/{answerId}/edit")
	public ResponseEntity<Answer> EditAnswer(@PathVariable Long questionId, @PathVariable Long answerId, @RequestBody Answer intendedAnswer, HttpSession session)
	{
		Answer editedAnswer = answerService.GetAnswer(answerId);
		Question question = questionService.GetQuestion(questionId);
		ResponseEntity<Answer> invalidAnswer = ValidateAnswer(editedAnswer, question, session, editedAnswer.getUserId());
		if (invalidAnswer != null)
		{
			return invalidAnswer;
		}

		answerService.EditAnswer(editedAnswer, intendedAnswer);
		return new ResponseEntity<>(editedAnswer, HttpStatus.OK);
	}

	@PutMapping ("/{questionId}/{answerId}/mark")
	public ResponseEntity<Answer> MarkAnswer(@PathVariable Long questionId, @PathVariable Long answerId, HttpSession session)
	{
		Answer markedAnswer = answerService.GetAnswer(answerId);
		Question question = questionService.GetQuestion(questionId);
		ResponseEntity<Answer> invalidAnswer = ValidateAnswer(markedAnswer, question, session, question.getUserId());
		if (invalidAnswer != null)
		{
			return invalidAnswer;
		}

		answerService.MarkAnswer(markedAnswer);
		return new ResponseEntity<>(markedAnswer, HttpStatus.OK);
	}

	public ResponseEntity<Answer> ValidateAnswer(Answer answer, Question question, HttpSession session, Long userId)
	{
		if (answer == null)
		{
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		if (question == null)
		{
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		if (question.getClosed())
		{
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		if (!session.getAttribute("userId").equals(userId))
		{
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		return null;
	}
}
