package alpha.minozzi.omegatool.tasks.poll;

import android.text.Html;
import android.util.Log;

import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.util.HashSet;
import java.util.Locale;

import alpha.minozzi.omegatool.UI;
import alpha.minozzi.omegatool.tasks.android.NetworkOperationFacade;

/**
 * Created by fiduccia on 23/10/16.
 */

public class DoVote implements Runnable {
    private static final String TAG = DoVote.class.getName();
    public static final String NEW_RATE = "new_rate";
    public static final String JSON_PROPERTY_ERROR = "error";
    private UI ui;

    private final String votingUrl = "http://blogger.grazia.it/wordpress/wp-admin/admin-ajax.php?action=contest_rate&blogger_id=2228&contest_id=3552";
    private long lastValidVote;
    private HashSet<String> alreadyUsedIps = new HashSet<>();

    public DoVote(UI ui) {
        this.ui = ui;
    }

    public DoVote() {
        this.ui = null;
    }

    @Override
    public void run() {
        try {
            //final Document document =
            Connection connection = Jsoup.connect(votingUrl);
            connection.userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0");
            connection.ignoreContentType(true);
            Connection.Response response = connection.execute();


            final JSONObject jsonObject = new JSONObject(response.body());
            String responseText = Html.fromHtml((jsonObject.getString("ajax_html") + jsonObject.getString(JSON_PROPERTY_ERROR)).trim()).toString();

            boolean newVote = jsonObject.has(NEW_RATE);
            if (newVote) {
                long updateValidVote = System.currentTimeMillis();
                double timeDiff = updateValidVote - lastValidVote;
                timeDiff /= 1000;
                responseText = String.format(Locale.ENGLISH, "%s: %d %.2g", responseText, jsonObject.getInt(NEW_RATE), timeDiff);
                lastValidVote = updateValidVote;
                if (ui != null) ui.vibrate();
            }
            String publicIp = NetworkOperationFacade.getPublicIp();
            responseText = new StringBuilder().append("[").append(publicIp).append("] ").append(responseText).toString();

            boolean skipLog = false;
            if (!newVote) {
                if (!alreadyUsedIps.contains(publicIp)) {
                    alreadyUsedIps.add(publicIp);
                } else {
                    skipLog = true;
                }
            } else {
                alreadyUsedIps.add(publicIp);
            }

            if (!skipLog) {
                Log.e(TAG, responseText);
                if (ui != null) {
                    ui.logAction(responseText);
                }
            }
        } catch (Exception e) {
        }

    }

    public void setUi(UI ui) {
        this.ui = ui;
    }
}
