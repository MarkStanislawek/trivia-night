package com.mjs.trivianight;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Set;

/**
 * Program command line invocation.
 * 
 * @author mjs
 *
 */
public final class TriviaPresentation {

	/**
	 * @param args
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public static void main(String[] args) throws FileNotFoundException,
			IOException {
		if (args.length < 2) {
			System.out.println("java " + TriviaPresentation.class.getName()
					+ " <trivia file> <output ppt file>");
			return;
		}

		System.out.println("Reading " + args[0]);
		Parser parser = new Parser(new FileReader(args[0]));
		Set<Round> rounds = parser.getRounds();

		System.out.println("Creating " + args[1]);
		PPTXBuilder builder = new PPTXBuilder(args[1]);
		for (Round round : rounds) {
			builder.addRound(round);
		}
		builder.build();
		System.out.println("Complete.");
	}
}
