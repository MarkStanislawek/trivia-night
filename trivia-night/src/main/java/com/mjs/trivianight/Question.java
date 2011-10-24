/**
 * 
 */
package com.mjs.trivianight;

/**
 * A question and its answer with sequence number.
 * 
 * @author mjs
 * 
 */
public final class Question {

	private final int questionNumber;
	private final String question;
	private final String answer;

	public Question(int questionNumber, String question, String answer) {
		super();
		this.questionNumber = questionNumber;
		this.question = question;
		this.answer = answer;
	}

	public int getQuestionNumber() {
		return questionNumber;
	}

	public String getQuestion() {
		return question;
	}

	public String getAnswer() {
		return answer;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((answer == null) ? 0 : answer.hashCode());
		result = prime * result
				+ ((question == null) ? 0 : question.hashCode());
		result = prime * result + questionNumber;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Question other = (Question) obj;
		if (answer == null) {
			if (other.answer != null)
				return false;
		} else if (!answer.equals(other.answer))
			return false;
		if (question == null) {
			if (other.question != null)
				return false;
		} else if (!question.equals(other.question))
			return false;
		if (questionNumber != other.questionNumber)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TriviaQuestion [questionNumber=" + questionNumber
				+ ", question=" + question + ", answer=" + answer + "]";
	}

}
