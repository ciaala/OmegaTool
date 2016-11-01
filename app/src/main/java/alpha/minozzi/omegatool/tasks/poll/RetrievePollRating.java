package alpha.minozzi.omegatool.tasks.poll;

import android.annotation.SuppressLint;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by fiduccia on 23/10/16.
 */

public class RetrievePollRating {

    private final String POLL_URL = "http://blogger.grazia.it/talent/darphin";
    final int champion = 2228;

    public RetrievePollRating() {

    }

    public Map<Integer, PollRating> getPollRatings() throws IOException {

        Elements ratingElements = getRatingElements();

        @SuppressLint("UseSparseArrays")
        Map<Integer, PollRating> ratings = new HashMap<>(ratingElements.size());

        for (Element element : ratingElements) {
            Element ratingElement = element.select("span").get(2);
            int rating = Integer.valueOf(ratingElement.text().trim());
            String competitor = element.select("a").get(0).text().trim();
            Integer key = Integer.valueOf(ratingElement.attr("id").substring(8));
            ratings.put(key, new PollRating(key, competitor, rating));
        }
        return ratings;
    }

    private Elements getRatingElements() throws IOException {
        Document doc = Jsoup.connect(POLL_URL).get();
        Elements ratings = doc.select("div[class*=blogger-info-bar]");
        return ratings;
    }


}
