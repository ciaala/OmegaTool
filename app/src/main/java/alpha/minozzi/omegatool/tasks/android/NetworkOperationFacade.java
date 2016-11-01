package alpha.minozzi.omegatool.tasks.android;

import android.content.Context;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.lang.reflect.Method;

/**
 * Created by fiduccia on 23/10/16.
 */

public class NetworkOperationFacade {

    private static final String TAG = NetworkOperationFacade.class.getName();
    private Context context;

    public NetworkOperationFacade(Context mainActivity) {
        context = mainActivity;
    }

    public boolean isCellularOn() {
        boolean mobileYN = false;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR1) {
            mobileYN = Settings.Global.getInt(context.getContentResolver(), "mobile_data", 1) == 1;
        } else {
            mobileYN = Settings.Secure.getInt(context.getContentResolver(), "mobile_data", 1) == 1;
        }
        return mobileYN;
    }

    public void restartCellular() {
        Runnable reset = new Runnable() {
            @Override
            public void run() {

                setMobileDataState(false);
                /*while (getMobileDataState() == true) {
                    Log.e(TAG, "Waiting");
                    Thread.yield();
                }
                */
                setMobileDataState(true);

            }

        };
        new Thread(reset).start();

    }


    public void setMobileDataState(boolean mobileDataEnabled) {
        try {
            TelephonyManager telephonyService = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

            Method setMobileDataEnabledMethod = telephonyService.getClass().getDeclaredMethod("setDataEnabled", boolean.class);

            if (null != setMobileDataEnabledMethod) {
                setMobileDataEnabledMethod.invoke(telephonyService, mobileDataEnabled);
            }
        } catch (Exception ex) {
            Log.e(TAG, "Error setting mobile data state", ex);
        }
    }

    public boolean getMobileDataState() {
        try {
            TelephonyManager telephonyService = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

            Method getMobileDataEnabledMethod = telephonyService.getClass().getDeclaredMethod("getDataEnabled");

            if (null != getMobileDataEnabledMethod) {
                boolean mobileDataEnabled = (Boolean) getMobileDataEnabledMethod.invoke(telephonyService);

                return mobileDataEnabled;
            }
        } catch (Exception ex) {
            Log.e(TAG, "Error getting mobile data state", ex);
        }

        return false;
    }

    public static String getPublicIp()
    {
        String myIp="";

        try{
            Document doc = Jsoup.connect("http://www.checkip.org").get();
            myIp = doc.getElementById("yourip").select("h1").first().select("span").text();
        }
        catch(IOException e){
            Log.e(TAG, "Unable to connect to checkip.org");
        }
        return myIp;
    }
}
