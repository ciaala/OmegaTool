package alpha.minozzi.omegatool.tasks.poll;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by fiduccia on 23/10/16.
 */

public class RetrievePollRatingTest {

    private RetrievePollRating rpr;

    @Before
    public void setUp() {
        rpr = new RetrievePollRating();
    }

    @Test
    public void getRating() throws IOException {
        Assert.assertEquals(10, rpr.getPollRatings().size());
    }

}
