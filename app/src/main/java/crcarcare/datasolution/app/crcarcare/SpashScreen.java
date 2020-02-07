package crcarcare.datasolution.app.crcarcare;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

public class SpashScreen extends Activity {
    Handler handler;
    Runnable runnable;
    long delay_time;
    long time = 700L;
    SharedPreferences mPre;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        mPre = getSharedPreferences("pref_user", Context.MODE_PRIVATE);
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.spashscreen);
        handler = new Handler();

        String user = "";
        if (mPre.contains("pref_pin"))
        {
            user = mPre.getString("pref_pin", "");
        }
        if(user == "")
        {

            Intent intent = new Intent(SpashScreen.this,Page_Login.class);
            startActivity(intent);
            finish();

        }
        runnable = new Runnable() {
            public void run() {
                Intent intent = new Intent(SpashScreen.this,Page_LoginPin.class);
                startActivity(intent);
                finish();
            }
        };
    }

    public void onResume() {
        super.onResume();
        delay_time = time;
        handler.postDelayed(runnable, delay_time);
        time = System.currentTimeMillis();
    }

    public void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);
        time = delay_time - (System.currentTimeMillis() - time);
    }

}
