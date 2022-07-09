package model;

public class CommentText {
    private String value;
    private String matchLevel;
    private boolean fullyHighlighted;
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

    public boolean isFullyHighlighted() {
        return fullyHighlighted;
    }

    public void setFullyHighlighted(boolean fullyHighlighted) {
        this.fullyHighlighted = fullyHighlighted;
    }

    public String[] getMatchedWords() {
        return matchedWords;
    }

    public void setMatchedWords(String[] matchedWords) {
        this.matchedWords = matchedWords;
    }
}