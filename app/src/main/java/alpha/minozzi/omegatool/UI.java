package alpha.minozzi.omegatool;

import android.widget.ProgressBar;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by fiduccia on 23/10/16.
 */
public class UI {


    //private final WebView webView;
    private final MainActivity mainActivity;
    private final ProgressBar progressBar;
    private final TextView championName;
    private final TextView championRating;
    private final TextView bestAdversary;
    private final TextView bestAdversaryRating;
    private TextView networkStatusView;

    public TextView getFeedbackVote() {
        return feedbackVote;
    }

    private final TextView feedbackVote;

    /*
        public WebView getWebView() {
            return webView;
        }
    */
    public ProgressBar getProgressBar() {
        return progressBar;
    }

    public TextView getChampionName() {
        return championName;
    }

    public TextView getChampionRating() {
        return championRating;
    }

    public TextView getBestAdversary() {
        return bestAdversary;
    }

    public TextView getBestAdversaryRating() {
        return bestAdversaryRating;
    }

    public UI(MainActivity mainActivity, ProgressBar progressBar, TextView tv0, TextView tv1, TextView tv2, TextView tv3,TextView tv4, TextView tv5) {
        this.mainActivity = mainActivity;
        this.progressBar = progressBar;
        this.championName = tv0;
        this.championRating = tv1;
        this.bestAdversary = tv2;
        this.bestAdversaryRating = tv3;
        this.networkStatusView = tv4;
        this.feedbackVote = tv5;
    }

    public void runOnUI(Runnable runnable) {
        mainActivity.runOnUiThread(runnable);

    }

    public void showActiveFeedback() {
        mainActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressBar.incrementSecondaryProgressBy(1);
            }
        });
    }

    public void logAction(final String message) {
        mainActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                feedbackVote.append(DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT, Locale.ENGLISH).format(new Date()) + " " + message + "\n");
            }
        });

    }

    public void vibrate() {
        //Vibrator v = (Vibrator) this.mainActivity.getSystemService(Context.VIBRATOR_SERVICE);
        // Vibrate for 500 milliseconds
        //v.vibrate(500);
    }

    public TextView getNetworkStatusView() {
        return networkStatusView;
    }
}
