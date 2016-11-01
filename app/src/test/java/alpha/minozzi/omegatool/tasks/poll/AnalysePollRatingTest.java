package alpha.minozzi.omegatool.tasks.poll;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by fiduccia on 23/10/16.
 */

public class AnalysePollRatingTest {

    private AnalysePollRating apr;

    @Before
    public void setUp() throws IOException {

        apr = new AnalysePollRating(2228);
        apr.update();
    }

    @Test
    public void getChampionRating() throws IOException {
        Assert.assertNotNull(apr.getBestAdversary());
        Assert.assertNotNull(apr.getChampion());
    }

}
