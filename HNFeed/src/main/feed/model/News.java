package model;

import java.util.Date;

public class News {
    private Date created_at;
    private String title;
    private String url;
    private String author;
    private String points;
    private String story_text;
    private String comment_text;
    private int num_comments;
    private int story_id;
    private String story_title;
    private String story_url;
    private int parent_id;
    private int created_at_i;
    private String[] _tags;
    private String objectID;
    private HighlightResult _highlightResult;

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }

    public String getStory_text() {
        return story_text;
    }

    public void setStory_text(String story_text) {
        this.story_text = story_text;
    }

    public String getComment_text() {
        return comment_text;
    }

    public void setComment_text(String comment_text) {
        this.comment_text = comment_text;
    }

    public int getNum_comments() {
        return num_comments;
    }

    public void setNum_comments(int num_comments) {
        this.num_comments = num_comments;
    }

    public int getStory_id() {
        return story_id;
    }

    public void setStory_id(int story_id) {
        this.story_id = story_id;
    }

    public String getStory_title() {
        return story_title;
    }

    public void setStory_title(String story_title) {
        this.story_title = story_title;
    }

    public String getStory_url() {
        return story_url;
    }

    public void setStory_url(String story_url) {
        this.story_url = story_url;
    }

    public int getParent_id() {
        return parent_id;
    }

    public void setParent_id(int parent_id) {
        this.parent_id = parent_id;
    }

    public int getCreated_at_i() {
        return created_at_i;
    }

    public void setCreated_at_i(int created_at_i) {
        this.created_at_i = created_at_i;
    }

    public String[] get_tags() {
        return _tags;
    }

    public void set_tags(String[] _tags) {
        this._tags = _tags;
    }

    public String getObjectID() {
        return objectID;
    }

    public void setObjectID(String objectID) {
        this.objectID = objectID;
    }

    public HighlightResult get_highlightResult() {
        return _highlightResult;
    }

    public void set_highlightResult(HighlightResult _highlightResult) {
        this._highlightResult = _highlightResult;
    }
}