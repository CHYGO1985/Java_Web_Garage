package service;

import com.google.gson.Gson;
import model.News;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;

public class FeedDataService {

    private News[] newsArray;

    public FeedDataService() {

    }

    public FeedDataService(News[] newsArray) {
        this.newsArray = newsArray;
    }

    public News[] getNewsArray() {
        return newsArray;
    }

    public void setNewsArray(News[] newsArray) {
        this.newsArray = newsArray;
    }

    public News[] getFeedDataFromApi(String url) throws Exception {

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response responses = client.newCall(request).execute();

        String jsonData = responses.body().string();
        JSONObject jsonObject = new JSONObject(jsonData);
        JSONArray jsonArray = jsonObject.getJSONArray("hits");

        Gson gson = new Gson();
        newsArray = new News[jsonArray.length()];

        for (int idx = 0; idx < jsonArray.length(); idx ++) {
            JSONObject object = jsonArray.getJSONObject(idx);
            newsArray[idx] = gson.fromJson(object.toString(), News.class);
        }

        return newsArray;
    }
}
