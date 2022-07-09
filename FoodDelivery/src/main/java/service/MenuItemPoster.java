package service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import pojo.MenuItem;
import pojo.PostResonse;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 *
 * This is for posting menu items to specified restAPI.
 *
 * @author jingjiejiang
 * @history Sep 18, 2021
 *
 */
public class MenuItemPoster {

    public static PostResonse uploadMenuItemToDB(String restAPI, MenuItem menuItem){

        PostResonse postResonse = new PostResonse();

        try {

            // https://menu-processor-api.fly.dev/1/items
            URL url = new URL(restAPI);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");

            String input = JSON.toJSONString(menuItem);

            OutputStream os = conn.getOutputStream();
            os.write(input.getBytes());
            os.flush();

            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            String output;

            while ((output = br.readLine()) != null) {
                postResonse = JSON.parseObject(output, PostResonse.class);
            }

            conn.disconnect();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return postResonse;
    }
}
