package processor;

import pojo.MenuItem;
import pojo.PostResonse;
import service.MenuItemPoster;

import java.util.concurrent.LinkedBlockingQueue;

/**
 *
 * The class for posting menu items specified restAPI.
 *
 * @author jingjiejiang
 * @history Sep 18, 2021
 *
 */
public class MenuItemConsumer implements Runnable {

    private LinkedBlockingQueue<MenuItem> queue;
    private String restAPI;

    public MenuItemConsumer(LinkedBlockingQueue<MenuItem> queue, String restAPI) {
        this.queue = queue;
        this.restAPI = restAPI;
    }

    @Override
    public void run() {
        try {
            while (true) {
                MenuItem menuItem = queue.take();

                PostResonse resonse = MenuItemPoster.uploadMenuItemToDB(restAPI, menuItem);

                // sout with the response item_name
//                System.out.println(resonse.getItem().getItem_name() + " is created.");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
