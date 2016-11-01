package alpha.minozzi.omegatool.tasks.job;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import alpha.minozzi.omegatool.tasks.poll.AnalysePollRating;
import alpha.minozzi.omegatool.tasks.poll.PollResult;

/**
 * Created by fiduccia on 23/10/16.
 */

public class UpdateJob {

    public static final int CHAMPION_KEY = 2228;
    private static final Logger LOGGER = Logger.getLogger(UpdateJob.class.getName());

    public PollResult update() throws IOException {
        LOGGER.log(Level.SEVERE, "Start UpdateJob#update");

        AnalysePollRating analysePollRating = new AnalysePollRating(CHAMPION_KEY);

        analysePollRating.update();
        return analysePollRating;
    }
}
