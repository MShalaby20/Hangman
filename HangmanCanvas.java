
/*
 * File: HangmanCanvas.java
 * ------------------------
 * This file keeps track of the Hangman display.
 */

import java.awt.Color;
import java.util.ArrayList;

import acm.graphics.GCanvas;
import acm.graphics.GLabel;
import acm.graphics.GLine;
import acm.graphics.GObject;
import acm.graphics.GOval;

public class HangmanCanvas extends GCanvas {

	/* Constants for the simple version of the picture (in pixels) */
	private static final int SCAFFOLD_HEIGHT = 360;
	private static final int BEAM_LENGTH = 144;
	private static final int ROPE_LENGTH = 18;
	private static final int HEAD_RADIUS = 36;
	private static final int HEAD_DIAMETER = HEAD_RADIUS * 2;
	private static final int BODY_LENGTH = 144;
	private static final int ARM_OFFSET_FROM_HEAD = 28;
	private static final int UPPER_ARM_LENGTH = 72;
	private static final int LOWER_ARM_LENGTH = 44;
	private static final int HIP_WIDTH = 36;
	private static final int LEG_LENGTH = 108;
	private static final int FOOT_LENGTH = 28;

	private static final int START_X = 75;
	private static final int START_Y = START_X;

	private GLine scaffoldBase, beam, rope;

	private int bodyPart = 0;

	private ArrayList<GObject> scaffold = new ArrayList<>();

	/** Resets the display so that only the scaffold appears */
	public void reset() {
		removeAll();
		createScaffold();
	}

	/** Updates the scaffold for all of the body parts */
	private void updateScaffold() {
		for (GObject g : scaffold)
			add(g);
	}

	/** Creates the scaffold */
	public void createScaffold() {

		scaffold.clear();
		bodyPart = 0;

		scaffoldBase = new GLine(START_X, START_Y, START_X, getY() + SCAFFOLD_HEIGHT);
		beam = new GLine(START_X, START_Y, START_X + BEAM_LENGTH, START_Y);
		rope = new GLine(START_X + BEAM_LENGTH, START_Y, beam.getEndPoint().getX(), START_Y + ROPE_LENGTH);

		add(scaffoldBase);
		add(beam);
		add(rope);

		scaffold.add(scaffoldBase);
		scaffold.add(beam);
		scaffold.add(rope);
	}

	/**
	 * Updates the word on the screen to correspond to the current state of the
	 * game. The argument string shows what letters have been guessed so far;
	 * unguessed letters are indicated by hyphens.
	 * 
	 * Size variable indicates how large the text should be, and
	 * howfardownfromscaffold is how many pixels it should be. Which I have declared
	 * to be 50 and 100 inside the HangMan class
	 */
	public void displayWord(String word, int size, int howfardownfromscaffold) {

		GLabel underlinedWord = new GLabel(word);
		underlinedWord.setColor(Color.BLACK);

		if (getElementAt(START_X, scaffoldBase.getEndPoint().getY() + howfardownfromscaffold) == null) {
			underlinedWord.setLocation(START_X, scaffoldBase.getEndPoint().getY() + howfardownfromscaffold);
			underlinedWord.setFont(underlinedWord.getFont() + "-bold-" + size);
		} else {
			remove(getElementAt(START_X, scaffoldBase.getEndPoint().getY() + howfardownfromscaffold));
			underlinedWord.setLocation(START_X, scaffoldBase.getEndPoint().getY() + howfardownfromscaffold);
			underlinedWord.setFont(underlinedWord.getFont() + "-bold-" + size);
		}

		add(underlinedWord);

	}

	/**
	 * Updates the display to correspond to an incorrect guess by the user. Calling
	 * this method causes the next body part to appear on the scaffold and adds the
	 * letter to the list of incorrect guesses that appears at the bottom of the
	 * window.
	 */
	public void noteIncorrectGuess() {

		switch (bodyPart) {

		case 0:
			GOval head = new GOval(rope.getEndPoint().getX() - HEAD_DIAMETER / 2, rope.getEndPoint().getY(),
					HEAD_DIAMETER, HEAD_DIAMETER);
			add(head);
			scaffold.add(head);
			break;

		case 1:
			updateScaffold();
			GLine body = new GLine(rope.getEndPoint().getX(), rope.getEndPoint().getY() + HEAD_DIAMETER,
					rope.getEndPoint().getX(), rope.getEndPoint().getY() + HEAD_RADIUS + BODY_LENGTH);

			add(body);
			scaffold.add(body);
			break;

		case 2:
			updateScaffold();
			GLine lHip = new GLine(((GLine) scaffold.get(4)).getEndPoint().getX(),
					((GLine) scaffold.get(4)).getEndPoint().getY(),
					((GLine) scaffold.get(4)).getEndPoint().getX() - HIP_WIDTH,
					((GLine) scaffold.get(4)).getEndPoint().getY());
			add(lHip);
			scaffold.add(lHip);
			break;

		case 3:
			updateScaffold();
			GLine rHip = new GLine(((GLine) scaffold.get(4)).getEndPoint().getX(),
					((GLine) scaffold.get(4)).getEndPoint().getY(),
					((GLine) scaffold.get(4)).getEndPoint().getX() + HIP_WIDTH,
					((GLine) scaffold.get(4)).getEndPoint().getY());
			add(rHip);
			scaffold.add(rHip);
			break;

		case 4:
			updateScaffold();
			GLine lLeg = new GLine(((GLine) scaffold.get(5)).getEndPoint().getX(),
					((GLine) scaffold.get(5)).getEndPoint().getY(), ((GLine) scaffold.get(5)).getEndPoint().getX(),
					((GLine) scaffold.get(5)).getEndPoint().getY() + LEG_LENGTH);
			add(lLeg);
			scaffold.add(lLeg);
			break;
		case 5:
			updateScaffold();
			GLine rLeg = new GLine(((GLine) scaffold.get(6)).getEndPoint().getX(),
					((GLine) scaffold.get(6)).getEndPoint().getY(), ((GLine) scaffold.get(6)).getEndPoint().getX(),
					((GLine) scaffold.get(6)).getEndPoint().getY() + LEG_LENGTH);
			add(rLeg);
			scaffold.add(rLeg);
			break;

		case 6:
			updateScaffold();
			GLine lShoulder = new GLine(((GLine) scaffold.get(4)).getStartPoint().getX(),
					((GLine) scaffold.get(4)).getStartPoint().getY() + ARM_OFFSET_FROM_HEAD,
					((GLine) scaffold.get(4)).getStartPoint().getX() - UPPER_ARM_LENGTH,
					((GLine) scaffold.get(4)).getStartPoint().getY() + ARM_OFFSET_FROM_HEAD);
			add(lShoulder);
			scaffold.add(lShoulder);
			break;

		case 7:
			updateScaffold();
			GLine rShoulder = new GLine(((GLine) scaffold.get(4)).getStartPoint().getX(),
					((GLine) scaffold.get(4)).getStartPoint().getY() + ARM_OFFSET_FROM_HEAD,
					((GLine) scaffold.get(4)).getStartPoint().getX() + UPPER_ARM_LENGTH,
					((GLine) scaffold.get(4)).getStartPoint().getY() + ARM_OFFSET_FROM_HEAD);
			add(rShoulder);
			scaffold.add(rShoulder);
			break;

		case 8:
			updateScaffold();
			GLine lArm = new GLine(((GLine) scaffold.get(9)).getEndPoint().getX(),
					((GLine) scaffold.get(9)).getEndPoint().getY(), ((GLine) scaffold.get(9)).getEndPoint().getX(),
					((GLine) scaffold.get(9)).getEndPoint().getY() + LOWER_ARM_LENGTH);

			add(lArm);
			scaffold.add(lArm);
			break;

		case 9:
			updateScaffold();
			GLine rArm = new GLine(((GLine) scaffold.get(10)).getEndPoint().getX(),
					((GLine) scaffold.get(10)).getEndPoint().getY(), ((GLine) scaffold.get(10)).getEndPoint().getX(),
					((GLine) scaffold.get(10)).getEndPoint().getY() + LOWER_ARM_LENGTH);
			add(rArm);
			scaffold.add(rArm);
			break;

		case 10:
			updateScaffold();
			GLine lFoot = new GLine(((GLine) scaffold.get(7)).getEndPoint().getX(),
					((GLine) scaffold.get(7)).getEndPoint().getY(),
					((GLine) scaffold.get(7)).getEndPoint().getX() - FOOT_LENGTH,
					((GLine) scaffold.get(7)).getEndPoint().getY());
			add(lFoot);
			scaffold.add(lFoot);
			break;

		case 11:
			updateScaffold();
			GLine rFoot = new GLine(((GLine) scaffold.get(8)).getEndPoint().getX(),
					((GLine) scaffold.get(8)).getEndPoint().getY(),
					((GLine) scaffold.get(8)).getEndPoint().getX() + FOOT_LENGTH,
					((GLine) scaffold.get(8)).getEndPoint().getY());
			add(rFoot);
			scaffold.add(rFoot);
			break;

		}

		bodyPart++;

	}

	/** Gets the scaffold */
	public ArrayList<GObject> getScaffold() {
		return scaffold;
	}

}
