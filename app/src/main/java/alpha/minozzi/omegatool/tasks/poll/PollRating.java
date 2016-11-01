package alpha.minozzi.omegatool.tasks.poll;

/**
 * Created by fiduccia on 23/10/16.
 */
public class PollRating {
    private final int key;
    private final String competitor;
    private final int rating;

    public PollRating(int key, String competitor, int rating) {

        this.key = key;
        this.competitor = competitor;
        this.rating = rating;
    }

    public int getKey() {
        return key;
    }

    public String getCompetitor() {
        return competitor;
    }

    public int getRating() {
        return rating;
    }
}
