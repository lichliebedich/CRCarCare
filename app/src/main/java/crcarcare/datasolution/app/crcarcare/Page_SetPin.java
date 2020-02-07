package crcarcare.datasolution.app.crcarcare;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class Page_SetPin extends MainActivity {
    String digi1 = null;
    String digi2 = null;
    String digi3 = null;
    String digi4 = null;
    SharedPreferences mPre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_set_pin_screen);
        mPre = getSharedPreferences("pref_user", Context.MODE_PRIVATE);

        final ImageView dot1 = findViewById(R.id.dot1);
        final ImageView dot2 = findViewById(R.id.dot2);
        final ImageView dot3 = findViewById(R.id.dot3);
        final ImageView dot4 = findViewById(R.id.dot4);


        Button num1 = findViewById(R.id.btn1);
        Button num2 = findViewById(R.id.btn2);
        Button num3 = findViewById(R.id.btn3);
        Button num4 = findViewById(R.id.btn4);
        Button num5 = findViewById(R.id.btn5);
        Button num6 = findViewById(R.id.btn6);
        Button num7 = findViewById(R.id.btn7);
        Button num8 = findViewById(R.id.btn8);
        Button num9 = findViewById(R.id.btn9);
        Button num0 = findViewById(R.id.btn0);
        Button Del = findViewById(R.id.delete);

        num1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if(digi1 == null && digi2 == null && digi3 == null && digi4 == null)
                {
                    digi1="1";
                    dot1.setImageResource(R.drawable.dot);
                }
                else if(digi1 != null && digi2 == null && digi3 == null && digi4 == null)
                {
                    digi2="1";
                    dot2.setImageResource(R.drawable.dot);
                }
                else if(digi1 != null && digi2 != null && digi3 == null && digi4 == null)
                {
                    digi3="1";
                    dot3.setImageResource(R.drawable.dot);
                }
                else if(digi1 != null && digi2 != null && digi3 != null && digi4 == null)
                {
                    digi4="1";
                    dot4.setImageResource(R.drawable.dot);
                }
                if(digi1 != null && digi2 != null && digi3 != null && digi4 != null)
                {
                    getPreference();
                }
            }
        });
        num2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if(digi1 == null && digi2 == null && digi3 == null && digi4 == null)
                {
                    digi1="2";
                    dot1.setImageResource(R.drawable.dot);
                }
                else if(digi1 != null && digi2 == null && digi3 == null && digi4 == null)
                {
                    digi2="2";
                    dot2.setImageResource(R.drawable.dot);
                }
                else if(digi1 != null && digi2 != null && digi3 == null && digi4 == null)
                {
                    digi3="2";
                    dot3.setImageResource(R.drawable.dot);
                }
                else if(digi1 != null && digi2 != null && digi3 != null && digi4 == null)
                {
                    digi4="2";
                    dot4.setImageResource(R.drawable.dot);
                }
                if(digi1 != null && digi2 != null && digi3 != null && digi4 != null)
                {
                    getPreference();
                }
            }
        });
        num3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if(digi1 == null && digi2 == null && digi3 == null && digi4 == null)
                {
                    digi1="3";
                    dot1.setImageResource(R.drawable.dot);
                }
                else if(digi1 != null && digi2 == null && digi3 == null && digi4 == null)
                {
                    digi2="3";
                    dot2.setImageResource(R.drawable.dot);
                }
                else if(digi1 != null && digi2 != null && digi3 == null && digi4 == null)
                {
                    digi3="3";
                    dot3.setImageResource(R.drawable.dot);
                }
                else if(digi1 != null && digi2 != null && digi3 != null && digi4 == null)
                {
                    digi4="3";
                    dot4.setImageResource(R.drawable.dot);
                }
                if(digi1 != null && digi2 != null && digi3 != null && digi4 != null)
                {
                    getPreference();
                }
            }
        });
        num4.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if(digi1 == null && digi2 == null && digi3 == null && digi4 == null)
                {
                    digi1="4";
                    dot1.setImageResource(R.drawable.dot);
                }
                else if(digi1 != null && digi2 == null && digi3 == null && digi4 == null)
                {
                    digi2="4";
                    dot2.setImageResource(R.drawable.dot);
                }
                else if(digi1 != null && digi2 != null && digi3 == null && digi4 == null)
                {
                    digi3="4";
                    dot3.setImageResource(R.drawable.dot);
                }
                else if(digi1 != null && digi2 != null && digi3 != null && digi4 == null)
                {
                    digi4="4";
                    dot4.setImageResource(R.drawable.dot);
                }
                if(digi1 != null && digi2 != null && digi3 != null && digi4 != null)
                {
                    getPreference();
                }
            }
        });
        num5.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if(digi1 == null && digi2 == null && digi3 == null && digi4 == null)
                {
                    digi1="5";
                    dot1.setImageResource(R.drawable.dot);
                }
                else if(digi1 != null && digi2 == null && digi3 == null && digi4 == null)
                {
                    digi2="5";
                    dot2.setImageResource(R.drawable.dot);
                }
                else if(digi1 != null && digi2 != null && digi3 == null && digi4 == null)
                {
                    digi3="5";
                    dot3.setImageResource(R.drawable.dot);
                }
                else if(digi1 != null && digi2 != null && digi3 != null && digi4 == null)
                {
                    digi4="5";
                    dot4.setImageResource(R.drawable.dot);
                }
                if(digi1 != null && digi2 != null && digi3 != null && digi4 != null)
                {
                    getPreference();
                }
            }
        });
        num6.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if(digi1 == null && digi2 == null && digi3 == null && digi4 == null)
                {
                    digi1="6";
                    dot1.setImageResource(R.drawable.dot);
                }
                else if(digi1 != null && digi2 == null && digi3 == null && digi4 == null)
                {
                    digi2="6";
                    dot2.setImageResource(R.drawable.dot);
                }
                else if(digi1 != null && digi2 != null && digi3 == null && digi4 == null)
                {
                    digi3="6";
                    dot3.setImageResource(R.drawable.dot);
                }
                else if(digi1 != null && digi2 != null && digi3 != null && digi4 == null)
                {
                    digi4="6";
                    dot4.setImageResource(R.drawable.dot);
                }
                if(digi1 != null && digi2 != null && digi3 != null && digi4 != null)
                {
                    getPreference();
                }
            }
        });
        num7.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if(digi1 == null && digi2 == null && digi3 == null && digi4 == null)
                {
                    digi1="7";
                    dot1.setImageResource(R.drawable.dot);
                }
                else if(digi1 != null && digi2 == null && digi3 == null && digi4 == null)
                {
                    digi2="7";
                    dot2.setImageResource(R.drawable.dot);
                }
                else if(digi1 != null && digi2 != null && digi3 == null && digi4 == null)
                {
                    digi3="7";
                    dot3.setImageResource(R.drawable.dot);
                }
                else if(digi1 != null && digi2 != null && digi3 != null && digi4 == null)
                {
                    digi4="7";
                    dot4.setImageResource(R.drawable.dot);
                }
                if(digi1 != null && digi2 != null && digi3 != null && digi4 != null)
                {
                    getPreference();
                }
            }
        });
        num8.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if(digi1 == null && digi2 == null && digi3 == null && digi4 == null)
                {
                    digi1="8";
                    dot1.setImageResource(R.drawable.dot);
                }
                else if(digi1 != null && digi2 == null && digi3 == null && digi4 == null)
                {
                    digi2="8";
                    dot2.setImageResource(R.drawable.dot);
                }
                else if(digi1 != null && digi2 != null && digi3 == null && digi4 == null)
                {
                    digi3="8";
                    dot3.setImageResource(R.drawable.dot);
                }
                else if(digi1 != null && digi2 != null && digi3 != null && digi4 == null)
                {
                    digi4="8";
                    dot4.setImageResource(R.drawable.dot);
                }
                if(digi1 != null && digi2 != null && digi3 != null && digi4 != null)
                {
                    getPreference();
                }
            }
        });
        num9.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if(digi1 == null && digi2 == null && digi3 == null && digi4 == null)
                {
                    digi1="9";
                    dot1.setImageResource(R.drawable.dot);
                }
                else if(digi1 != null && digi2 == null && digi3 == null && digi4 == null)
                {
                    digi2="9";
                    dot2.setImageResource(R.drawable.dot);
                }
                else if(digi1 != null && digi2 != null && digi3 == null && digi4 == null)
                {
                    digi3="9";
                    dot3.setImageResource(R.drawable.dot);
                }
                else if(digi1 != null && digi2 != null && digi3 != null && digi4 == null)
                {
                    digi4="9";
                    dot4.setImageResource(R.drawable.dot);
                }
                if(digi1 != null && digi2 != null && digi3 != null && digi4 != null)
                {
                    getPreference();
                }
            }
        });
        num0.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if(digi1 == null && digi2 == null && digi3 == null && digi4 == null)
                {
                    digi1="0";
                    dot1.setImageResource(R.drawable.dot);
                }
                else if(digi1 != null && digi2 == null && digi3 == null && digi4 == null)
                {
                    digi2="0";
                    dot2.setImageResource(R.drawable.dot);
                }
                else if(digi1 != null && digi2 != null && digi3 == null && digi4 == null)
                {
                    digi3="0";
                    dot3.setImageResource(R.drawable.dot);
                }
                else if(digi1 != null && digi2 != null && digi3 != null && digi4 == null)
                {
                    digi4="0";
                    dot4.setImageResource(R.drawable.dot);
                }
                if(digi1 != null && digi2 != null && digi3 != null && digi4 != null)
                {
                    getPreference();
                }
            }
        });

        Del.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                digi1 = null;
                digi2 = null;
                digi3 = null;
                digi4 = null;
                dot1.setImageResource(R.drawable.circle);
                dot2.setImageResource(R.drawable.circle);
                dot3.setImageResource(R.drawable.circle);
                dot4.setImageResource(R.drawable.circle);

            }
        });

    }


    private void getPreference(){
        String Spin = digi1+digi2+digi3+digi4;
        SharedPreferences.Editor editor = mPre.edit();
        editor.putString("pref_Spin",Spin);
        editor.apply();
        Intent i = new Intent(Page_SetPin.this,Page_Confirm_Pin.class);
        startActivity(i);
        finish();
    }


}
