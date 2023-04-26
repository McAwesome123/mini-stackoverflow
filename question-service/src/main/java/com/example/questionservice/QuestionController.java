package com.example.questionservice;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping ("/questions")
public class QuestionController
{
	private final QuestionService questionService;

	@Autowired
	public QuestionController(QuestionService questionService)
	{
		this.questionService = questionService;
	}

	@PostMapping("/create")
	public ResponseEntity<Question> CreateQuestion(@RequestBody Question question, HttpSession session)
	{
		if (session.getAttribute("userId") == null)
		{
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

		Question createdQuestion = questionService.CreateQuestion(question);

		return new ResponseEntity<>(createdQuestion, HttpStatus.CREATED);
	}

	@GetMapping("/{id}")
	public Question GetQuestion(@PathVariable Long id)
	{
		return questionService.GetQuestion(id);
	}

	@PutMapping("/{id}/edit")
	public ResponseEntity<Question> Edit(@PathVariable Long id, @RequestBody Question intendedQuestion, HttpSession session)
	{

		Question editedQuestion = questionService.GetQuestion(id);
		ResponseEntity<Question> validQuestion = ValidateQuestion(editedQuestion, session);
		if (validQuestion != null)
		{
			return validQuestion;
		}
		if (editedQuestion.getClosed())
		{
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

		questionService.EditQuestion(editedQuestion, intendedQuestion);
		return new ResponseEntity<>(editedQuestion, HttpStatus.OK);
	}

	@PutMapping("/{id}/close")
	public ResponseEntity<Question> CloseQuestion(@PathVariable Long id, HttpSession session)
	{
		Question closedQuestion = questionService.GetQuestion(id);
		ResponseEntity<Question> validQuestion = ValidateQuestion(closedQuestion, session);
		if (validQuestion != null)
		{
			return validQuestion;
		}

		questionService.CloseQuestion(closedQuestion);
		return new ResponseEntity<>(closedQuestion, HttpStatus.OK);
	}

	private ResponseEntity<Question> ValidateQuestion (Question question, HttpSession session)
	{
		if (question == null)
		{
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		if (!session.getAttribute("userId").equals(question.getUserId()))
		{
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		return null;
	}
}
