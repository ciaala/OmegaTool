package alpha.minozzi.omegatool;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import alpha.minozzi.omegatool.service.VoteService;
import alpha.minozzi.omegatool.tasks.android.NetworkOperationFacade;

public class MainActivity extends AppCompatActivity {


    private UI ui;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ProgressBar progressBar = (ProgressBar) this.findViewById(R.id.progressBar);
        TextView tv0 = (TextView) this.findViewById(R.id.textView0);
        TextView tv1 = (TextView) this.findViewById(R.id.textView1);
        TextView tv2 = (TextView) this.findViewById(R.id.textView2);
        TextView tv3 = (TextView) this.findViewById(R.id.textView3);
        TextView tv4 = (TextView) this.findViewById(R.id.textView4);
        TextView tv5 = (TextView) this.findViewById(R.id.textView5);
        ui = new UI(this, progressBar, tv0, tv1, tv2, tv3, tv4, tv5);
        //UpdateUI updateUI = new UpdateUI(ui);
        //setNetworkStatus();
       // updateUI.start();
    }

    private void setNetworkStatus() {
        NetworkOperationFacade network = new NetworkOperationFacade(this);
        ui.getNetworkStatusView()
                .setText("Network is " + (network.isCellularOn() ? "connected" : "disconnected"));
    }

    public void selfDestruct(View view) {
        Intent voteServiceIntent = new Intent(this, VoteService.class);
        ServiceConnection serviceConnection = new VoteServiceConnection();
        bindService(voteServiceIntent, serviceConnection, BIND_AUTO_CREATE);
    }

    class VoteServiceConnection implements ServiceConnection {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            if (service instanceof VoteService.VoteServiceBinder) {
                VoteService.VoteServiceBinder voteService = (VoteService.VoteServiceBinder) service;
                voteService.getVoteService().setUi(ui);
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            ui.logAction("Service disconnected");
        }
    }


}
