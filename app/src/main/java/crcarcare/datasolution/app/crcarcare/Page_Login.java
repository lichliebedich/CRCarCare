package crcarcare.datasolution.app.crcarcare;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Page_Login extends MainActivity{
	String XUser,XUsername,XPassword,Xtest;
	private Toast ms;
	TextView tv_user,tv_password,tv_userlogin;
	private long lastBackPressTime = 0;
	Connection conn = null;
	ResultSet rs = null;
	SharedPreferences mPre;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		mPre = getSharedPreferences("pref_user", Context.MODE_PRIVATE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_screen);
		
		tv_user = findViewById(R.id.username);
		tv_password =  findViewById(R.id.password);
		tv_userlogin = findViewById(R.id.tv_userlogin);
		
		
		Button B1 = findViewById(R.id.btnLogin);
		B1.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub

						if (GetLogin())
						{
							try
							{
								tv_userlogin.setText("เข้าสู่ระบบโดย : " + XUsername);
								Intent i = new Intent(Page_Login.this,Page_SetPin.class);
								startActivity(i);
								finish();

							} catch (Exception e)
							{
								// TODO: handle exception
								e.printStackTrace();
							}
						}
				}
		});
	}
	
	public boolean GetLogin() 
    {

    
    String driver = "net.sourceforge.jtds.jdbc.Driver";
		String user = getResources().getString(R.string.usernameDB);
		String pass = getResources().getString(R.string.passwordDB);
		String host = getResources().getString(R.string.Host_port);



    try
	{
		int i;
		String ls_result = "";
        Class.forName(driver);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
		conn = DriverManager.getConnection("jdbc:jtds:sqlserver://"+host,user, pass);
    
        Log.e("MSSQL", "Connected");
        XUser = tv_user.getText().toString().trim();
		XPassword = tv_password.getText().toString().trim();
		Statement st=conn.createStatement();
		String SQL = "select * from xuser WHERE login = '" + XUser + "' and isactive = 'Y'";
		Log.d("User",XUser);
		rs = st.executeQuery(SQL);

		if(!rs.next())
		{
				Toast.makeText(this, "ชื่อผู้ใช้ หรือ รหัสผ่านไม่ถูกต้อง",Toast.LENGTH_SHORT).show();
				return false;
		}

			i = 0;
			String ps = rs.getString(5);
			while ((i < ps.length()))
			{
				char character = (ps.charAt(i));
				char character1 = (ps.charAt(i+1));
				int ascii = (int)character;
				int ascii1 = (int)character1-33;
				int asc = ascii-ascii1;
				char x = (char)asc;
				ls_result = ls_result+x;
				Log.d("Pass",ls_result);
				i = (i + 2);
			}
//			if(XPassword.equals(ls_result))
//			{
				XUsername =rs.getString(4);
				Toast.makeText(this, "เข้าสู่ระบบโดย : " + XUsername ,Toast.LENGTH_SHORT).show();
				rs.close();
				st.close();
//			}
//			else
//			{
//				Toast.makeText(this, "ชื่อผู้ใช้ หรือ รหัสผ่านไม่ถูกต้อง",Toast.LENGTH_SHORT).show();
//				return false;
//			}


	}
	catch (Exception e)
	{

		e.printStackTrace();
		Log.e("MSSQL", e.toString());

	}
		//////////////////////////////////
		SharedPreferences.Editor editor = mPre.edit();
		editor.putString("pref_user",XUser);
		editor.putString("pref_pass",XPassword);
		editor.apply();
		//////////////////////////////////
		return true;
}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		if(this.lastBackPressTime < System.currentTimeMillis() - 2000){
			ms =Toast.makeText(this, "กด BACK อีกครั้งเพื่อปิดแอพ", 2000);
			ms.show();
			this.lastBackPressTime = System.currentTimeMillis();
		}else{
			if(ms != null){
				ms.cancel();
			}
			super.onBackPressed();
		}


	}


}
