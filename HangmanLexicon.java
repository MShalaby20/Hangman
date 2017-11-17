
/*
 * File: HangmanLexicon.java
 * -------------------------
 * This file contains a stub implementation of the HangmanLexicon
 * class that you will reimplement for Part III of the assignment.
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import acm.util.RandomGenerator;

public class HangmanLexicon {

	private RandomGenerator rgen;
	private ArrayList<String> lines;

	private static HangmanLexicon instance = new HangmanLexicon();

	public static HangmanLexicon getInstance() {
		return instance;
	}

	/** Constructor */
	private HangmanLexicon() {
		rgen = RandomGenerator.getInstance();
		lines = new ArrayList<String>();
	}

	/** Returns the number of words in the lexicon. */
	public int getWordCount() {
		return lines.size();
	}

	/**
	 * Returns a random word.
	 * 
	 * @throws IOException
	 */
	public String getWord(Level l) throws IOException {

		BufferedReader easy = new BufferedReader(new FileReader("EasyLexicon.txt"));
		BufferedReader medium = new BufferedReader(new FileReader("MediumLexicon.txt"));
		BufferedReader hard = new BufferedReader(new FileReader("HardLexicon.txt"));

		String line;

		switch (l) {

		case EASY:
			line = easy.readLine();
			while (line != null) {
				lines.add(line);
				line = easy.readLine();
			}
			break;

		case MEDIUM:
			line = medium.readLine();
			while (line != null) {
				lines.add(line);
				line = medium.readLine();
			}
			break;

		case HARD:
			line = hard.readLine();
			while (line != null) {
				lines.add(line);
				line = hard.readLine();
			}
			break;

		}

		return lines.get(rgen.nextInt(lines.size()));

	}
}