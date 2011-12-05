package com.mjs.trivianight;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.regex.Pattern;

import org.apache.commons.collections.Closure;
import org.apache.commons.collections.ClosureUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.collections.PredicateUtils;

/**
 * Reads questions and answers separated by rounds into local data structures.
 * 
 * @author mjs
 * 
 */
public final class Parser {
	private BufferedReader reader;
	private int roundNumber = 0;
	private int questionNumber = 0;
	private String question;
	private String answer;
	private Set<Question> questions;
	private final Set<Round> rounds = new LinkedHashSet<Round>();

	public Parser(Reader triviaSource) throws IOException {
		reader = new BufferedReader(triviaSource);
		read();
		close();
	}

	private void read() throws IOException {
		String line;
		while ((line = reader.readLine()) != null) {
			processTrivia.execute(line);
		}
	}

	private void close() throws IOException {
		reader.close();
	}

	public Set<Round> getRounds() {
		return rounds;
	}

	private Predicate isRound = new Predicate() {

		public boolean evaluate(Object str) {
			String line = (String) str;
			return line.trim().toUpperCase().startsWith("ROUND");
		}

	};

	private Closure addRound = new Closure() {

		public void execute(Object arg0) {
			roundNumber++;
			questionNumber = 0;
			questions = new LinkedHashSet<Question>();
			rounds.add(new Round(roundNumber, questions));
			assert rounds.size() == roundNumber;
		}

	};

	private final Pattern numberedQuestion = Pattern.compile("^\\d+\\..*");

	private Predicate isQuestion = new Predicate() {

		public boolean evaluate(Object str) {
			String line = (String) str;
			return numberedQuestion.matcher(line).matches();
		}

	};

	private final Pattern numberingPattern = Pattern.compile("^\\d+\\.");

	private Closure addQuestion = new Closure() {

		public void execute(Object arg0) {
			assert answer == null;
			question = arg0.toString().trim();
			questionNumber++;
			question = numberingPattern.matcher(question).replaceFirst("")
					.trim();
		}

	};

	private Predicate isBlank = new Predicate() {

		public boolean evaluate(Object arg0) {
			return arg0.toString().trim().length() == 0;
		}
	};

	private Predicate isAnswer = PredicateUtils.nonePredicate(new Predicate[] {
			isRound, isQuestion, isBlank });
	
	private final Pattern answerPattern = Pattern.compile("^A\\.");

	private Closure addAnswer = new Closure() {

		public void execute(Object arg0) {
			assert question != null;
			answer = arg0.toString().trim();
			answer = answerPattern.matcher(answer).replaceFirst("");
			questions.add(new Question(questionNumber, question, answer));
			question = null;
			answer = null;
		}

	};

	private final Predicate[] predicates = { isRound, isQuestion, isAnswer,
			isBlank };
	private final Closure[] closures = { addRound, addQuestion, addAnswer,
			ClosureUtils.nopClosure() };
	private final Closure processTrivia = ClosureUtils.switchClosure(
			predicates, closures);

}