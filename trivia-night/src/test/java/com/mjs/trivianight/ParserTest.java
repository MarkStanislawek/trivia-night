package com.mjs.trivianight;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ParserTest {

	private StringReader reader;

	@Before
	public void setup() throws IOException {
		StringWriter strWriter = new StringWriter();
		BufferedWriter writer = new BufferedWriter(strWriter);
		writer.write("round one");
		writer.newLine();
		writer.newLine();
		writer.write("1. Who was Gerald Ford’s vice president?");
		writer.newLine();
		writer.write("Nelson Rockefeller");
		writer.newLine();
		writer.write("2. What charter did King John of England sign in 1215?");
		writer.newLine();
		writer.write("The Magna Carta");
		writer.newLine();
		writer.write("3. To what country does Greenland belong?");
		writer.newLine();
		writer.write("Denmark");
		writer.newLine();
		writer.write("round two");
		writer.newLine();
		writer.newLine();
		writer.write("1. On what religious holiday was Abraham Lincoln shot?");
		writer.newLine();
		writer.write("Good Friday");
		writer.newLine();
		writer.write("2. What ship was torpedoed and sank off the coast of Ireland in 1915?");
		writer.newLine();
		writer.write("Lusitania");
		writer.newLine();
		writer.write("Which two continents do not border on the Atlantic Ocean?"); // no
																					// numbering.
		writer.newLine();
		writer.write("Australia and Asia");
		writer.flush();
		reader = new StringReader(strWriter.toString());

	}

	@Test
	public void testParse() {
		Parser parser;
		try {
			parser = new Parser(reader);
			Set<Round> rounds = parser.getRounds();
			displayTestData(rounds);
			assertTrue(rounds.size() == 2);
			for (Round round : rounds) {
				assertTrue(round.getQuestions().size() == 3);
			}

		} catch (IOException e) {
			fail(e.getMessage());
		}
	}

	private void displayTestData(Set<Round> rounds) {
		for (Round round : rounds) {
			System.out.println(round.toString());
		}

	}

}
