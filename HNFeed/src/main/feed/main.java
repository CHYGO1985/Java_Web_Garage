import service.FeedDataService;
import model.News;
import utils.Contants;

public class main {
    public static void main(String[] args) {

        FeedDataService feedDataService = new FeedDataService();
        News[] newsArray = null;

        try {
            newsArray = feedDataService.getFeedDataFromApi(Contants.FEED_API_URL);
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        // newsArray contains all the feed data from url https://hn.algolia.com/api/v1/search_by_date?query=java%20
        // then the next step is to store the data into mongoDB, and when the API is invoked by a user, read the data from
        // db and provide them to the user.
    }
}
