package crcarcare.datasolution.app.crcarcare;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Page_LoginPin extends MainActivity {

    //https://devahoy.com/posts/android-shared-preferences-tutorial/
    String digi1 = null;
    String digi2 = null;
    String digi3 = null;
    String digi4 = null;
    String pin = null;
    String Spin = null;
    SharedPreferences mPre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_login_pin_screen);
        mPre = getSharedPreferences("pref_user", Context.MODE_PRIVATE);

        final ImageView dot1 = (ImageView) findViewById(R.id.dot1);
        final ImageView dot2 = (ImageView) findViewById(R.id.dot2);
        final ImageView dot3 = (ImageView) findViewById(R.id.dot3);
        final ImageView dot4 = (ImageView) findViewById(R.id.dot4);

        Button num1 = (Button)findViewById(R.id.btn1);
        Button num2 = (Button)findViewById(R.id.btn2);
        Button num3 = (Button)findViewById(R.id.btn3);
        Button num4 = (Button)findViewById(R.id.btn4);
        Button num5 = (Button)findViewById(R.id.btn5);
        Button num6 = (Button)findViewById(R.id.btn6);
        Button num7 = (Button)findViewById(R.id.btn7);
        Button num8 = (Button)findViewById(R.id.btn8);
        Button num9 = (Button)findViewById(R.id.btn9);
        Button num0 = (Button)findViewById(R.id.btn0);
        Button Del = (Button)findViewById(R.id.delete);
        Button Forgot = (Button)findViewById(R.id.forgot);

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
                    dot1.setImageResource(R.drawable.circle);
                    dot2.setImageResource(R.drawable.circle);
                    dot3.setImageResource(R.drawable.circle);
                    dot4.setImageResource(R.drawable.circle);
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
                    dot1.setImageResource(R.drawable.circle);
                    dot2.setImageResource(R.drawable.circle);
                    dot3.setImageResource(R.drawable.circle);
                    dot4.setImageResource(R.drawable.circle);
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
                    dot1.setImageResource(R.drawable.circle);
                    dot2.setImageResource(R.drawable.circle);
                    dot3.setImageResource(R.drawable.circle);
                    dot4.setImageResource(R.drawable.circle);
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
                    dot1.setImageResource(R.drawable.circle);
                    dot2.setImageResource(R.drawable.circle);
                    dot3.setImageResource(R.drawable.circle);
                    dot4.setImageResource(R.drawable.circle);
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
                    dot1.setImageResource(R.drawable.circle);
                    dot2.setImageResource(R.drawable.circle);
                    dot3.setImageResource(R.drawable.circle);
                    dot4.setImageResource(R.drawable.circle);
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
                    dot1.setImageResource(R.drawable.circle);
                    dot2.setImageResource(R.drawable.circle);
                    dot3.setImageResource(R.drawable.circle);
                    dot4.setImageResource(R.drawable.circle);
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
                    dot1.setImageResource(R.drawable.circle);
                    dot2.setImageResource(R.drawable.circle);
                    dot3.setImageResource(R.drawable.circle);
                    dot4.setImageResource(R.drawable.circle);
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
                    dot1.setImageResource(R.drawable.circle);
                    dot2.setImageResource(R.drawable.circle);
                    dot3.setImageResource(R.drawable.circle);
                    dot4.setImageResource(R.drawable.circle);
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
                    dot1.setImageResource(R.drawable.circle);
                    dot2.setImageResource(R.drawable.circle);
                    dot3.setImageResource(R.drawable.circle);
                    dot4.setImageResource(R.drawable.circle);
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
                    dot1.setImageResource(R.drawable.circle);
                    dot2.setImageResource(R.drawable.circle);
                    dot3.setImageResource(R.drawable.circle);
                    dot4.setImageResource(R.drawable.circle);
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

        Forgot.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                AlertDialog.Builder alert = new AlertDialog.Builder(Page_LoginPin.this);
                alert.setTitle("ลืมรหัส PIN ?");
                alert.setMessage("กำหนดรหัส PIN ใหม่โดยใช้ชื่อผู้ใช้และรหัสผ่าน");

                alert.setPositiveButton("ตกลง", new
                        DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {

                                Intent intent = new Intent(Page_LoginPin.this,Page_Login.class);
                                startActivity(intent);
                            }
                        });
                alert.setNegativeButton("ยกเลิก", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                        dialog.cancel();
                    }
                });
                AlertDialog alertDialog = alert.create();
                alertDialog.show();
            }
        });


    }

    private void getPreference(){
        pin = digi1+digi2+digi3+digi4;
        if (mPre.contains("pref_pin")){
            Spin = mPre.getString("pref_pin","");
        }
        if(Spin.equals(pin))
        {
            if (GetLogin()){
                try {
                    Intent i = new Intent(Page_LoginPin.this,Page_Menu.class);
                    startActivity(i);
                    finish();
                } catch (Exception e) {
                    // TODO: handle exception
                    e.printStackTrace();
                }
            }
        }
        else
        {
            digi1 = null;
            digi2 = null;
            digi3 = null;
            digi4 = null;
            Toast.makeText(this, "รหัสไม่ถูกต้อง",Toast.LENGTH_SHORT).show();
        }

    }

    public boolean GetLogin()
    {
        String username = "";
        String password = "";
        String XUsername = "";
        //String pass = "";
        if (mPre.contains("pref_user")){
            username = mPre.getString("pref_user","");
        }
        if (mPre.contains("pref_pass")){
            password = mPre.getString("pref_pass","");
        }

        Connection conn = null;
        ResultSet rs = null;

        String driver = "net.sourceforge.jtds.jdbc.Driver";
        String user = getResources().getString(R.string.usernameDB);
        String pass = getResources().getString(R.string.passwordDB);
        String host = getResources().getString(R.string.Host_port);

        try {
            Class.forName(driver);
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build(); StrictMode.setThreadPolicy(policy);

            conn = DriverManager.getConnection("jdbc:jtds:sqlserver://"+host,user, pass);

            Log.e("MSSQL", "Connected");
            Statement st=conn.createStatement();
            String SQL = "select * from xuser WHERE login = '" + username + "' and isactive = 'Y'";
            //////////////////////////////////
            SharedPreferences.Editor editor = mPre.edit();
            editor.putString("pref_user",username);
            editor.putString("pref_pass",password);
            editor.apply();
            //////////////////////////////////
            Log.d("User",username);
            rs=st.executeQuery(SQL);


            if(!rs.next()){
                Toast.makeText(this, "ชื่อผู้ใช้ หรือ รหัสผ่านไม่ถูกต้อง",Toast.LENGTH_SHORT).show();
                return false;
            }

            XUsername =rs.getString(4);

            while(rs.next()==true)
            {
                XUsername =rs.getString(4);
            }

            Toast.makeText(this, "เข้าสู่ระบบโดย : " + XUsername ,Toast.LENGTH_SHORT).show();

            rs.close();
            st.close();

        }catch (Exception e) {

            e.printStackTrace();
            Log.e("MSSQL", e.toString());

        }
        return true;
    }
}
