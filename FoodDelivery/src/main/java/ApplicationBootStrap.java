import pojo.MenuItem;
import pojo.Restaurant;
import processor.MenuItemConsumer;
import processor.MenuItemProducer;
import service.MenuItemGenerator;

import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

public class ApplicationBootStrap {

    public static final String RESTAURANTS_API = "https://menu-processor-api.fly.dev/restaurants";

    public static final String POST_API_PREFIX = "https://menu-processor-api.fly.dev/flzxsqc20210918/items";

    public static void main(String[] args) {

        LinkedBlockingQueue<MenuItem> queue = new LinkedBlockingQueue<>(20);
        MenuItemGenerator menuItemGenerator = new MenuItemGenerator();

        List<Restaurant> restaurantList = menuItemGenerator.generateRestaurantList(RESTAURANTS_API);

        for (Restaurant restaurant : restaurantList) {

            String code = restaurant.getCode();
            String url = "https://menu-processor-api.fly.dev/restaurants/" + code;
            List<MenuItem> menuItemList = menuItemGenerator.getMenuItemFromRestaurant(url);
            new Thread(new MenuItemProducer(queue, menuItemList)).start();
        }

        /*
        for (int count = 0; count < Runtime.getRuntime().availableProcessors(); count ++) {
            new Thread(new MenuItemConsumer(queue, POST_API_PREFIX)).start();
        }
        */


        System.out.println("Generating menu items...");
    }
}
