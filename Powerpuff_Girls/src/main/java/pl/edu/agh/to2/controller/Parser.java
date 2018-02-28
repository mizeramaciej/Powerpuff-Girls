package pl.edu.agh.to2.controller;

public class Parser {

    boolean parse(String[] args) {
        for (String str : args) {
            if (!isWord(str) && !isNumeric(str) && !isBracketSymbol(str))
                return false;
        }
        return true;
    }

    private boolean isBracketSymbol(String arg) {
        if (arg.length() != 1) {
            return false;
        }
        if (arg.charAt(0) != '[' && arg.charAt(0) != ']') {
            return false;
        }

        return true;
    }

    private boolean isWord(String arg) {
        for (int i = 0; i < arg.length(); i++) {
            if (!Character.isLetter(arg.charAt(i)))
                return false;
        }
        return true;
    }

    private boolean isNumeric(String arg) {
        final Character MINUS = '-';
        for (int i = 0; i < arg.length(); i++) {
            if (i == 0 && arg.length() > 1 && arg.charAt(0) == MINUS) {
                continue;
            }
            if (!Character.isDigit(arg.charAt(i)))
                return false;
        }
        return true;
    }
}
