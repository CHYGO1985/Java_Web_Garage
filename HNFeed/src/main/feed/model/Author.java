package model;

public class Author {
    private String value;
    private String matchLevel;
    private String[] matchedWords;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getMatchLevel() {
        return matchLevel;
    }

    public void setMatchLevel(String matchLevel) {
        this.matchLevel = matchLevel;
    }

    public String[] getMatchedWords() {
        return matchedWords;
    }

    public void setMatchedWords(String[] matchedWords) {
        this.matchedWords = matchedWords;
    }
}