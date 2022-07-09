package pojo;

/**
 *
 * The pojo class for product_variations.
 *
 * @author jingjiejiang
 * @history Sep 18, 2021
 *
 */
public class ProductVariation {

    private int id;
    private String code;
    private float price;
    private boolean is_vegetarian;
    private String name;

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

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public boolean isIs_vegetarian() {
        return is_vegetarian;
    }

    public void setIs_vegetarian(boolean is_vegetarian) {
        this.is_vegetarian = is_vegetarian;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
