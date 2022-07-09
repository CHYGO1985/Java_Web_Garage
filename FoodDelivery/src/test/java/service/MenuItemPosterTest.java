package service;

import org.junit.Before;
import org.junit.Test;
import pojo.MenuItem;
import pojo.PostResonse;

import static org.junit.Assert.*;

/**
 *
 * @author jingjiejiang
 * @history Sep 18, 2021
 *
 */
public class MenuItemPosterTest {

    MenuItem menuItem = new MenuItem();
    MenuItemPoster menuItemPoster = new MenuItemPoster();

    @Before
    public void setUp() throws Exception {

        menuItem.setRating((float) 4.4);
        menuItem.setItem_price(10);
        menuItem.setItem_id(2431937);
        menuItem.setItem_name("Green Vegetable 菜类");
        menuItem.setRestaurant("Panda Noodles");
        menuItem.setCategory("Value Set Meals");

    }

    @Test
    public void postValidMenuItemToDBShouldGetCreated() {

        PostResonse postResonse = menuItemPoster.uploadMenuItemToDB(
                "https://menu-processor-api.fly.dev/1/items",
                menuItem);

        assertTrue(postResonse.isCreated());
    }
}