
/*
 * File: Hangman.java
 * ------------------
 * This program will eventually play the Hangman game from
 * Assignment #4.
 * 
 * 
 * Author: Michael Shalaby
 * 
 * Help: Used help from the Internet (Yahoo Answers) for reading a file, and for checking if guess is in the alphabet
 * 
 */

import java.io.IOException;

import acm.graphics.GObject;
import acm.program.ConsoleProgram;

public class Hangman extends ConsoleProgram {

	/** Canvas */
	private static HangmanCanvas canvas = new HangmanCanvas();;
	private HangmanLexicon hl = HangmanLexicon.getInstance();

	/** Strings */
	public String wordToGuess = "", c = "";
	public static String underlinedWord = "", guessedChars = "";

	/** Repeat method boolean */
	private boolean run;

	/** Levels */
	private LevelManager levelm = LevelManager.getInstance();

	/** Variables */
	private static final int WIDTH = 900, HEIGHT = 500, UNDERLINED_WORD = 50, GUESSED_CHARS = UNDERLINED_WORD * 2;

	/** Main run method */
	public void run() {
		setSize(WIDTH / 2, HEIGHT);
		initSetup();
		playGame();
	}

	/** The initial setup */
	private void initSetup() {
		resetAllVariables();

		println("\t\tWelcome to Hangman!\n\n");
		readLine("");

		instructionScreen();

		String level;

		level = readLine("\n\tEnter 'Easy', 'Medium', or 'Hard'\n\tto select your level\n\n").toUpperCase();

		while (!level.equals("EASY") && !level.equals("MEDIUM") && !level.equals("HARD") && !level.equals("E")
				&& !level.equals("MED") && !level.equals("H"))
			level = readLine("\n\tEnter 'Easy', 'Medium', or 'Hard'\n\tto select your level\n\n").toUpperCase();

		switch (level) {

		case "EASY":
			levelm.setLevel(Level.EASY);
			break;

		case "E":
			levelm.setLevel(Level.EASY);
			break;

		case "MEDIUM":
			levelm.setLevel(Level.MEDIUM);
			break;

		case "MED":
			levelm.setLevel(Level.MEDIUM);
			break;

		case "HARD":
			levelm.setLevel(Level.HARD);
			break;

		case "H":
			levelm.setLevel(Level.HARD);
			break;

		}

		try {
			wordToGuess = hl.getWord(levelm.getLevel()).toUpperCase();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/** Sets up the instruction screen */
	private void instructionScreen() {
		getConsole().clear();
		println("\t\tINSTRUCTIONS\n\n\t1. There is a random word selected \n\tyour goal is to figure it out\n\n\t2. If you run out of guesses, you lose!\n\n\tPlease enter any key to start the game!");
		readLine("");
		getConsole().clear();
		return;

	}

	/** Gameplay Method */
	private void playGame() {

		setSize(WIDTH, HEIGHT);

		add(canvas);
		wordToUnderlined(wordToGuess);
		canvas.reset();
		getConsole().clear();

		while (canvas.getScaffold().size() < 15) {

			if (!(underlinedWord.equals(wordToGuess))) {

				updateWordsOnCanvas();

				println("\nYour word: " + underlinedWord + "\nGuessed Letters: " + guessedChars);
				String g = readLine("\nYour guess: ").toUpperCase();

				while (g.length() > 1 || !g.matches("[a-zA-Z]+") || guessedChars.indexOf(g) > -1) {

					println("\nYour word: " + underlinedWord + "\nGuessed Letters: " + guessedChars);
					g = readLine("\nYour guess: ").toUpperCase();

				}

				checkGuessedLetter(g.charAt(0), wordToGuess);
			} else {
				setupWinningScreen();
				break;
			}
		}

		pause(500);
		canvas.displayWord("", 20, 50);
		canvas.displayWord("", 20, 100);

		for (GObject g : canvas.getScaffold()) {
			pause(100);
			canvas.remove(g);
		}

		setupLosingScreen();

	}

	/** Losing Screen */
	private void setupLosingScreen() {

		getConsole().clear();
		remove(canvas);
		setSize(WIDTH / 2, HEIGHT);
		run = true;
		println("\nYou have lost! The word was: " + wordToGuess + "\nYour guesses: " + guessedChars);
		askRepeat();

	}

	/** Winning screen */
	private void setupWinningScreen() {

		getConsole().clear();
		remove(canvas);
		setSize(WIDTH / 2, HEIGHT);
		run = true;

		println("\nYou have won! The word was: " + wordToGuess + ". Congratulations!");
		askRepeat();

	}

	/**
	 * 
	 * Resets all of the primitive types inside of the class (Mostly that I want)
	 */
	private void resetAllVariables() {

		underlinedWord = "";
		c = "";
		guessedChars = "";
		run = false;
	}

	/**
	 * 
	 * Takes a input and turns the underlinedWord variable into another string of
	 * underlines
	 */
	private void wordToUnderlined(String s) {
		for (int i = 0; i < s.length(); i++)
			underlinedWord = underlinedWord + "-";
	}

	/** Updates the words on the screen */
	private static void updateWordsOnCanvas() {
		canvas.displayWord(underlinedWord, 24, 50);
		canvas.displayWord(guessedChars, 20, 100);
	}

	/** Checks a char against a string to see if it is inside the string */
	private void checkGuessedLetter(Character c, String s) {
		if (s.indexOf(c) == -1) {
			println("\n\nThere are no " + c + "'s in the word");
			guessedChars = guessedChars + c.toString().toUpperCase() + ",";
			canvas.noteIncorrectGuess();
		} else {

			int appearsInWord = 0;
			for (int i = 0; i < s.length(); i++) {
				if (c == s.charAt(i)) {
					underlinedWord = (underlinedWord.substring(0, i) + c + underlinedWord.substring(i + 1))
							.toUpperCase();
					appearsInWord++;
				}
			}

			println("\nYour guess is correct! There were " + appearsInWord + " " + c + "'s in the word!");
			guessedChars = guessedChars + c.toString().toUpperCase() + ",";
			return;
		}

	}

	/** Repeat question Method */
	private void askRepeat() {

		// A forever loop that is started after you get your total
		while (run == true) {
			while (!(c.equalsIgnoreCase("Y") || c.equalsIgnoreCase("Yes") || c.equalsIgnoreCase("No")
					|| c.equalsIgnoreCase("n"))) {

				println("\nWould you like to do play again? If no, the program will quit.");
				c = this.readLine("Yes / No: ");

				if ((c.equalsIgnoreCase("Y") || c.equalsIgnoreCase("Yes") || c.equalsIgnoreCase("No")
						|| c.equalsIgnoreCase("n")))
					break;

			}
			// Creates a forever loop that will run after a user types yes r
			while (true) {

				if (c.equalsIgnoreCase("Yes") || c.equalsIgnoreCase("y")) {

					resetAllVariables();
					getConsole().clear();

					init();
					run();

				} else if (c.equalsIgnoreCase("No") || c.equalsIgnoreCase("n")) {
					getConsole().clear();
					println("Shutting down in 5 seconds");
					pause(5000);
					System.exit(0);

				}
			}
		}
	}

}
