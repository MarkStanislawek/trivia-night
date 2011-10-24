package com.mjs.trivianight;

import java.awt.Color;
import java.awt.Rectangle;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.xslf.usermodel.TextAlign;
import org.apache.poi.xslf.usermodel.VerticalAlignment;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFSlide;
import org.apache.poi.xslf.usermodel.XSLFTextBox;
import org.apache.poi.xslf.usermodel.XSLFTextParagraph;
import org.apache.poi.xslf.usermodel.XSLFTextRun;

/**
 * Builds a set trivia pptx slides. Each round starts with a slide introduction.
 * Followed by a slide per question with its question number (participants write
 * answers on round/question numbered sheet). The end of a trivia round is
 * introduced on a slide (to allow participants to hand-in answer sheets).
 * Finally, each question and its answer is presented on a slide.
 * 
 * @author mjs
 */
public final class PPTXBuilder {

	private final XMLSlideShow ppt = new XMLSlideShow();
	private final String saveLocation;

	private static final int DPI = 72;
	private static final String FONT_FAMILY = "Trebuchet MS (Headings)";

	public PPTXBuilder(String saveLocation) {
		this.saveLocation = saveLocation;
	}

	private void createRound(Round round) {
		XSLFSlide slide = ppt.createSlide();
		XSLFTextBox textBox = slide.createTextBox();
		textBox.setVerticalAlignment(VerticalAlignment.MIDDLE);
		XSLFTextParagraph paragraph = textBox.addNewTextParagraph();
		paragraph.setTextAlign(TextAlign.CENTER);
		XSLFTextRun textRun = paragraph.addNewTextRun();
		textRun.setFontColor(Color.black);
		textRun.setFontFamily(FONT_FAMILY);
		textRun.setFontSize(60);
		textRun.setText("Round " + String.valueOf(round.getRoundNumber()));
		int fraction = (int) (DPI * .83);
		textBox.setAnchor(new Rectangle(DPI / 4, DPI * 1 + fraction, 9 * DPI,
				1 * DPI));
	}

	private void createQuestion(Question question) {
		XSLFSlide slide = ppt.createSlide();
		XSLFTextBox questionNbrTextBox = slide.createTextBox();
		questionNbrTextBox.setVerticalAlignment(VerticalAlignment.MIDDLE);
		XSLFTextRun questionNbrTextRun = questionNbrTextBox
				.addNewTextParagraph().addNewTextRun();
		questionNbrTextRun.setFontColor(Color.black);
		questionNbrTextRun.setFontFamily(FONT_FAMILY);
		questionNbrTextRun.setFontSize(48);
		questionNbrTextRun.setText("Question "
				+ String.valueOf(question.getQuestionNumber()));
		questionNbrTextRun.setItalic(true);
		questionNbrTextBox
				.setAnchor(new Rectangle(DPI / 4, 0, 9 * DPI, 1 * DPI));

		XSLFTextBox questionTextBox = slide.createTextBox();
		questionTextBox.setVerticalAlignment(VerticalAlignment.TOP);
		XSLFTextParagraph paragraph = questionTextBox.addNewTextParagraph();
		paragraph.setTextAlign(TextAlign.CENTER);
		XSLFTextRun questionTextRun = paragraph.addNewTextRun();
		questionTextRun.setFontColor(Color.black);
		questionTextRun.setFontFamily(FONT_FAMILY);
		questionTextRun.setFontSize(60);
		questionTextRun.setText(question.getQuestion());
		questionTextBox.setWordWrap(true);
		int fraction = DPI / 4;
		questionTextBox.setAnchor(new Rectangle(DPI / 4, 1 * DPI, 9 * DPI, 6
				* DPI + fraction));
	}

	private void createEndOfRound(Round round) {
		XSLFSlide slide = ppt.createSlide();
		XSLFTextBox endOfRdTextBox = slide.createTextBox();
		endOfRdTextBox.setVerticalAlignment(VerticalAlignment.MIDDLE);
		XSLFTextRun endOfRdTextRun = endOfRdTextBox.addNewTextParagraph()
				.addNewTextRun();
		endOfRdTextRun.setFontColor(Color.black);
		endOfRdTextRun.setFontFamily(FONT_FAMILY);
		endOfRdTextRun.setFontSize(48);
		endOfRdTextRun.setText("End of Round "
				+ String.valueOf(round.getRoundNumber()));
		endOfRdTextRun.setBold(true);
		endOfRdTextBox.setAnchor(new Rectangle(DPI / 4, 0, 9 * DPI, 1 * DPI));

		XSLFTextBox textBox = slide.createTextBox();
		textBox.setVerticalAlignment(VerticalAlignment.TOP);
		XSLFTextRun textRun = textBox.addNewTextParagraph().addNewTextRun();
		textRun.setFontColor(Color.black);
		textRun.setFontFamily(FONT_FAMILY);
		textRun.setFontSize(60);
		textRun.setText("Please hand-in your table’s answer sheet.");
		textBox.setWordWrap(true);
		int fraction = DPI / 4;
		textBox.setAnchor(new Rectangle(DPI / 4, 1 * DPI, 9 * DPI, 6 * DPI
				+ fraction));
	}

	private void createQuestionAndAnswer(Question question) {
		int fraction = (int) (DPI * (3 / 4));
		XSLFSlide slide = ppt.createSlide();
		XSLFTextBox questionTextBox = slide.createTextBox();
		questionTextBox.setVerticalAlignment(VerticalAlignment.TOP);
		XSLFTextRun questionTextRun = questionTextBox.addNewTextParagraph()
				.addNewTextRun();
		questionTextRun.setFontColor(Color.black);
		questionTextRun.setFontFamily(FONT_FAMILY);
		questionTextRun.setFontSize(48);
		questionTextRun.setText(question.getQuestion());
		questionTextRun.setItalic(true);
		questionTextBox.setAnchor(new Rectangle(DPI / 4, 0, 9 * DPI, 3 * DPI
				+ fraction));

		XSLFTextBox answerTextBox = slide.createTextBox();
		answerTextBox.setVerticalAlignment(VerticalAlignment.TOP);
		XSLFTextParagraph paragraph = answerTextBox.addNewTextParagraph();
		paragraph.setTextAlign(TextAlign.CENTER);
		XSLFTextRun answerTextRun = paragraph.addNewTextRun();
		answerTextRun.setFontColor(Color.black);
		answerTextRun.setFontFamily("Trebuchet MS (Headings)");
		answerTextRun.setFontSize(60);
		answerTextRun.setText(question.getAnswer());
		answerTextBox.setWordWrap(true);
		answerTextBox.setAnchor(new Rectangle(0, 3 * DPI + fraction, 10 * DPI,
				3 * DPI + fraction));
	}

	public void addRound(Round round) {
		createRound(round);
		for (Question question : round.getQuestions()) {
			createQuestion(question);
		}
		createEndOfRound(round);
		for (Question question : round.getQuestions()) {
			createQuestionAndAnswer(question);
		}
	}

	public void build() throws IOException {
		FileOutputStream out = new FileOutputStream(saveLocation);
		ppt.write(out);
		out.flush();
		out.close();
	}

}
