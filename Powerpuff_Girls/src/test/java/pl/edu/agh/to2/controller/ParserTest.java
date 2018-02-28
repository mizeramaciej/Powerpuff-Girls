package pl.edu.agh.to2.controller;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ParserTest {
    private static final String SPACE = " ";
    private Parser parser = new Parser();
    private String command;
    private String word = "aAzZ";
    private String notWord = "aAzZ100";
    private String punctuation = ".;-_-')";
    private String numeric = "1234567890";
    private String negativeNumeric = "-1234567890";
    private String notNumeric = "aAzZ;').-_";

    @Test
    public void parsePositive() {
        command = word;
        assertTrue(parser.parse(command.split(SPACE)));
        command = numeric;
        assertTrue(parser.parse(command.split(SPACE)));
        command = word + " " + negativeNumeric;
        assertTrue(parser.parse(command.split(SPACE)));
        command = word + " [ " + word + " ] " + word;
        assertTrue(parser.parse(command.split(SPACE)));
    }

    @Test
    public void parseNegative() {
        command = notWord;
        assertFalse(parser.parse(command.split(SPACE)));
        command = notNumeric;
        assertFalse(parser.parse(command.split(SPACE)));
        command = word + " " + negativeNumeric + " " + punctuation;
        assertFalse(parser.parse(command.split(SPACE)));
        command = word + " [ " + word + "] " + word;
        assertFalse(parser.parse(command.split(SPACE)));
    }

}