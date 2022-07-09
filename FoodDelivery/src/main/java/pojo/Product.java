package pojo;

import java.util.List;

/**
 *
 * The pojo class for products.
 *
 * @author jingjiejiang
 * @history Sep 18, 2021
 *
 */
public class Product {

    private int id;
    private String code;
    private String name;
    private String description;
    private List<ProductVariation> product_variations;

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

    public List<ProductVariation> getProduct_variations() {
        return product_variations;
    }

    public void setProduct_variations(List<ProductVariation> product_variations) {
        this.product_variations = product_variations;
    }
}
