package alpha.minozzi.omegatool.tasks.poll;

import java.io.IOException;
import java.util.Map;

/**
 * Created by fiduccia on 23/10/16.
 */

public class AnalysePollRating implements PollResult {
    private PollRating bestAdversary;
    private PollRating champion;
    private final int championKey;

    public AnalysePollRating(int championKey) {
        this.championKey = championKey;
    }

    public void update() throws IOException {
        RetrievePollRating retrievePollRating = new RetrievePollRating();
        Map<Integer, PollRating> pollRatings = retrievePollRating.getPollRatings();
        champion = pollRatings.get(championKey);
        for (PollRating pollRating : pollRatings.values()) {
            if (pollRating.getKey() != championKey) {
                if ((bestAdversary == null) || (pollRating.getRating() > bestAdversary.getRating())) {
                    bestAdversary = pollRating;
                }
            }
        }
    }

    @Override
    public PollRating getBestAdversary() {
        return bestAdversary;
    }

    @Override
    public PollRating getChampion() {
        return champion;
    }
}
