package alpha.minozzi.omegatool.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import alpha.minozzi.omegatool.UI;
import alpha.minozzi.omegatool.tasks.poll.DoVote;

/**
 * Created by fiduccia on 24/10/16.
 */

public class VoteService extends Service {
    private static final String TAG = VoteService.class.getName();
    private final DoVote vote;

    public VoteService() {
        this.vote = new DoVote();
    }

    public void setUi(UI ui) {

        this.vote.setUi(ui);
    }

    private synchronized void startTask( ) {
        new CountDownTimer(3 * 24 * 60 * 1000, 1000) {
            @Override

            public void onTick(long millisUntilFinished) {

                //DoVote doVote = new DoVote(ui);
                new Thread(vote).start();
                //ui.showActiveFeedback();
            }

            @Override
            public void onFinish() {
                //ui.logAction("Done !!!");
            }

        }.start();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG, "Service Started");
        startTask();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG, "onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        return new VoteServiceBinder(this);
    }

    public class VoteServiceBinder extends Binder {

        public VoteService getVoteService() {
            return voteService;
        }

        private final VoteService voteService;

        public VoteServiceBinder(VoteService voteService) {
            this.voteService = voteService;
        }


    }


}
