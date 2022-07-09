package service;

import org.junit.Test;
import pojo.MenuItem;
import pojo.Restaurant;

import java.util.List;

import static org.junit.Assert.*;

/**
 *
 * @author jingjiejiang
 * @history Sep 18, 2021
 *
 */
public class MenuItemGeneratorTest {

    private static final double DELTA = 1e-15;

    MenuItemGenerator menuItemGenerator = new MenuItemGenerator();

    @Test
    public void the3rdItemFromRestaurantAPIShouldBeW7CG() {

        List<Restaurant> restaurantList = menuItemGenerator.generateRestaurantList(
                "https://menu-processor-api.fly.dev/restaurants");

        assertTrue(restaurantList.get(2).getCode().equals("w7cg"));
    }

    @Test
    public void the1stItemPriceOfW7CGShouldBe1Point5() {

        List<MenuItem> menuItemList = menuItemGenerator.getMenuItemFromRestaurant(
                "https://menu-processor-api.fly.dev/restaurants/w7cg");

        assertEquals(1.5d, menuItemList.get(0).getItem_price(), DELTA);
    }

}