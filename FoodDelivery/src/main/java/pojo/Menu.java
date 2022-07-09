package pojo;

import java.util.List;

/**
 *
 * The pojo class for menu
 *
 * @author jingjiejiang
 * @history Sep 18, 2021
 *
 */
public class Menu {

    private int id;
    private String code;
    private String name;
    private String description;
    private List<MenuCategory> menu_categories;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<MenuCategory> getMenu_categories() {
        return menu_categories;
    }

    public void setMenu_categories(List<MenuCategory> menu_categories) {
        this.menu_categories = menu_categories;
    }
}
