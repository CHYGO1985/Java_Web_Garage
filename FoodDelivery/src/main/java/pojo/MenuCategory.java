package pojo;

import java.util.List;

/**
 *
 * The pojo class for menu_categories.
 *
 * @author jingjiejiang
 * @history Sep 18, 2021
 *
 */
public class MenuCategory {

    private int id;
    private String code;
    private String name;
    private String description;
    private List<Product> products;

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

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
