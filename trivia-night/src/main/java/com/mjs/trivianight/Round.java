/**
 * 
 */
package com.mjs.trivianight;

import java.util.Set;

/**
 * Trivia Round. Composed of a round number and a set of question/answer.
 * 
 * @author mjs
 * 
 */
public final class Round {

	private final int roundNumber;
	private final Set<Question> questions;

	public Round(int roundNumber, Set<Question> questions) {
		this.roundNumber = roundNumber;
		this.questions = questions;
	}

	public int getRoundNumber() {
		return roundNumber;
	}

	public Set<Question> getQuestions() {
		return questions;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((questions == null) ? 0 : questions.hashCode());
		result = prime * result + roundNumber;
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
		Round other = (Round) obj;
		if (questions == null) {
			if (other.questions != null)
				return false;
		} else if (!questions.equals(other.questions))
			return false;
		if (roundNumber != other.roundNumber)
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuffer buf = new StringBuffer();
		for (Question question : questions) {
			buf.append("{").append(question.toString()).append("},");
		}

		return "TriviaRound [roundNumber=" + roundNumber + ", questions="
				+ buf.toString() + "]";
	}
}
