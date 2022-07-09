package pojo;

/**
 *
 * The pojo class for storing posting menu item response.
 *
 * @author jingjiejiang
 * @history Sep 18, 2021
 *
 */
public class PostResonse {

    private boolean created;
    private String error;
    private MenuItem item;

    public boolean isCreated() {
        return created;
    }

    public void setCreated(boolean created) {
        this.created = created;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public MenuItem getItem() {
        return item;
    }

    public void setItem(MenuItem item) {
        this.item = item;
    }
}
