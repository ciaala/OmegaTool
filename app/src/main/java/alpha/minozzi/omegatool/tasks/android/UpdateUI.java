package alpha.minozzi.omegatool.tasks.android;

import android.os.CountDownTimer;
import android.support.annotation.NonNull;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import alpha.minozzi.omegatool.UI;
import alpha.minozzi.omegatool.tasks.job.UpdateJob;
import alpha.minozzi.omegatool.tasks.poll.PollResult;

/**
 * Created by fiduccia on 23/10/16.
 */

public class UpdateUI {

    public static final Logger LOGGER = Logger.getLogger(UpdateUI.class.getName());
    private final UI ui;

    public UpdateUI(UI ui) {

        this.ui = ui;
    }


    public void update() {
        ui.getProgressBar().setMax(100);
        ui.getProgressBar().setProgress(0);
        FutureTask<PollResult> pollResultFuture = getPollResultFutureTask();

        ui.getProgressBar().setProgress(5);
        PollResult result = this.getPollResult(pollResultFuture);
        if (result != null) {
            ui.getProgressBar().setProgress(80);

            updateUIPollRating(result);
            ui.getProgressBar().setProgress(100);
        } else {
            ui.getProgressBar().setProgress(0);
        }

    }

    private void updateUIPollRating(final PollResult result) {
        ui.runOnUI(new Runnable() {
            @Override
            public void run() {
                ui.getBestAdversary().setText(result.getBestAdversary().getCompetitor());
                ui.getChampionName().setText(result.getChampion().getCompetitor());
                ui.getBestAdversaryRating().setText(String.valueOf(result.getBestAdversary().getRating()));
                ui.getChampionRating().setText(String.valueOf(result.getChampion().getRating()));
            }
        });
    }

    private PollResult getPollResult(FutureTask<PollResult> pollResultFuture) {
        PollResult result = null;
        LOGGER.log(Level.SEVERE, "Start poll request");

        try {
            while (result == null) {
                try {
                    result = pollResultFuture.get(125, TimeUnit.MILLISECONDS);
                    if (ui.getProgressBar().getProgress() < 80) {
                        ui.getProgressBar().incrementProgressBy(1);
                    } else {
                        ui.getProgressBar().incrementSecondaryProgressBy(1);
                    }

                } catch (Exception e) {

                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @NonNull
    private FutureTask<PollResult> getPollResultFutureTask() {
        FutureTask<PollResult> pollResultFutureTask = new FutureTask<>(new Callable<PollResult>() {
            @Override
            public PollResult call() throws Exception {
                UpdateJob job = new UpdateJob();
                LOGGER.log(Level.SEVERE, "Calling UpdateJob#update");
                return job.update();
            }
        });
        ExecutorService executor = Executors.newFixedThreadPool(2);
        executor.execute(pollResultFutureTask);
        return pollResultFutureTask;
    }

    public void start() {
        CountDownTimer countDownTimer = new CountDownTimer(10 * 24 * 60 * 60 * 1000, 15 * 1000) {

            UpdateUI updateUI = UpdateUI.this;

            @Override
            public void onTick(long millisUntilFinished) {
                update();
            }

            @Override
            public void onFinish() {
                updateUI.start();
            }
        }.start();

    }

}