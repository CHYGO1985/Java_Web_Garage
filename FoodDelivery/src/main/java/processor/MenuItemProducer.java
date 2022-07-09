package processor;

import pojo.MenuItem;

import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;

/**
 *
 * The class for genetating menu items.
 *
 * @author jingjiejiang
 * @history Sep 18, 2021
 *
 */
public class MenuItemProducer implements Runnable {

    private LinkedBlockingQueue<MenuItem> menuItemsQueue;
    private List<MenuItem> menuItemList;

    public MenuItemProducer(LinkedBlockingQueue<MenuItem> menuItemsQueue,
                            List<MenuItem> menuItemList) {

        this.menuItemsQueue = menuItemsQueue;
        this.menuItemList = menuItemList;
    }

    @Override
    public void run() {
        try {
            for (MenuItem menuItem : menuItemList) {

                menuItemsQueue.put(menuItem);

//                System.out.println(menuItem.getItem_name() + " is generated.");

                // DEBUG: repeat 2431937
                if (menuItem.getItem_id() == 2431937) {

//                    System.out.println("product: " + product.getName());
//                    System.out.println("manu category: " + menuCategory.getName());
//                    System.out.println("menu: " + menu.getName());

                    System.out.println(" 2431937 put it");

                    System.out.println("==================");
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println("The menu item fail to add to DB");
            Thread.currentThread().interrupt();
        }
    }

}
