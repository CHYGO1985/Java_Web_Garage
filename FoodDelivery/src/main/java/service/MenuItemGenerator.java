package service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import pojo.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * The class is for generating menu items from returned data from specified Restful APIs.
 *
 * @author jingjiejiang
 * @history Sep 18, 2021
 *
 */
public class MenuItemGenerator {

    private List<Restaurant> restaurantList;

    public List<Restaurant> generateRestaurantList(String restAPI) {

        try {

            // "https://menu-processor-api.fly.dev/restaurants"
            URL url = new URL(restAPI);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            String output;
            while ((output = br.readLine()) != null) {

                restaurantList = new ArrayList<Restaurant>(
                        JSONArray.parseArray(output, Restaurant.class));
            }

            conn.disconnect();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return restaurantList;
    }

    public void generateAllMenuItem(List<Restaurant> restaurantList) {

        for (Restaurant restaurant : restaurantList) {
            String code = restaurant.getCode();
            String url = "https://menu-processor-api.fly.dev/restaurants/" + code;
            List<MenuItem> menuItemList = getMenuItemFromRestaurant(url);
        }
    }

    public List<MenuItem> getMenuItemFromRestaurant(String restAPI) {

        List<MenuItem> menuItemList = new LinkedList<>();

        try {

            // "https://menu-processor-api.fly.dev/restaurants/w7cg"
            URL url = new URL(restAPI);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            String output;
            RestaurantResponse restaurantResponse = new RestaurantResponse();

            while ((output = br.readLine()) != null) {

                restaurantResponse = JSON.parseObject(output, RestaurantResponse.class);
                List<Data> dataList = restaurantResponse.getData();

                for (Data data :  dataList) {

                    List<Menu> menus = data.getMenus();
                    for (Menu menu : menus) {

                        List<MenuCategory> menuCategories = menu.getMenu_categories();
                        for (MenuCategory menuCategory : menuCategories) {

                            List<Product> products = menuCategory.getProducts();
                            for (Product product : products) {


                                MenuItem menuItem = new MenuItem();
                                menuItem.setItem_id(product.getId());
                                menuItem.setItem_name(product.getName());
                                menuItem.setRestaurant(data.getName());
                                menuItem.setRating(data.getRating());
                                menuItem.setCategory(menuCategory.getName());

                                float price = 0;

                                List<ProductVariation> productVariations = product.getProduct_variations();
                                for (ProductVariation productVariation : productVariations) {
                                    price += productVariation.getPrice();
                                }

                                menuItem.setItem_price(price);

                                menuItemList.add(menuItem);
                            }
                        }
                    }
                }
            }

            conn.disconnect();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return menuItemList;
    }
}
