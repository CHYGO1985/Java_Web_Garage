package model;


public class HighlightResult {
    private Author author;
    private CommentText comment_text;
    private StoryTitle story_title;

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public CommentText getComment_text() {
        return comment_text;
    }

    public void setComment_text(CommentText comment_text) {
        this.comment_text = comment_text;
    }

    public StoryTitle getStory_title() {
        return story_title;
    }

    public void setStory_title(StoryTitle story_title) {
        this.story_title = story_title;
    }
}