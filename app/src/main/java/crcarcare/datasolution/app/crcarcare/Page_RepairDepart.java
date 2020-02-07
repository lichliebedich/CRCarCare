package crcarcare.datasolution.app.crcarcare;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class Page_RepairDepart extends Activity {
	Spinner MySpinner;
	ListView listView;
	String Str_SiteCode,Str_LicenseId,Str_RepairNo,Str_MileNo,Str_Depart;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.depart_screen);
		MySpinner = (Spinner) findViewById(R.id.sp_Depart);
		MySpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                    int arg2, long arg3) {
                
            	Str_SiteCode =MySpinner.getSelectedItem().toString().substring(0,10);
            	Log.d("Sitecode", Str_SiteCode);
            	connectTodatabase();

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });
		GetSiteCode();
		
		listView = (ListView) findViewById(R.id.list);		
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Object item = parent.getItemAtPosition ( position );
				Log.d("A", Str_LicenseId);
				
				Str_RepairNo =  item.toString().substring(0, 10);
					Intent PageMain =new Intent(getApplicationContext(),MainActivity.class);
					PageMain.putExtra("T_LicenseId", Str_LicenseId);
					PageMain.putExtra("PutData", 1);
					startActivity(PageMain);
			}
		});
		
	}
	public void connectTodatabase() 
    {
     
    Connection conn = null;
    ResultSet rs = null;
    

    String driver = "net.sourceforge.jtds.jdbc.Driver";
        String user = getResources().getString(R.string.usernameDB);
        String pass = getResources().getString(R.string.passwordDB);
        String host = getResources().getString(R.string.Host_port);

    try {
        Class.forName(driver);
        Log.d("OK", "a");
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build(); StrictMode.setThreadPolicy(policy);
        conn = DriverManager.getConnection("jdbc:jtds:sqlserver://"+host,user, pass);
        listView = (ListView) findViewById(R.id.list);
        Log.e("MSSQL", "Connected");
        Log.d("OK", "b");
        Statement st=conn.createStatement();
        String SQL = "SELECT license_id FROM cr_repair WHERE site_code ='"+Str_SiteCode+"' GROUP BY license_id ORDER BY license_id";
        Log.d("LOG", SQL);
        rs=st.executeQuery(SQL);
     
        ArrayList<String> dirArray = new ArrayList<String>();
        if(!rs.next()){
            Toast.makeText(this, "ไม่พบข้อมูล กรุณาตรวจสอบ",Toast.LENGTH_SHORT).show();
        	listView.setAdapter(null);
        	
        	return;
        }
        
        Str_LicenseId = rs.getString(1);
        dirArray.add(rs.getString(1) + "\n");
        while(rs.next()==true)
        {
          
          dirArray.add(rs.getString(1) + "\n");
        }
        
        ArrayAdapter<String> adapterDir = new ArrayAdapter<String>(this,R.layout.list_item,dirArray);
  		listView.setAdapter(adapterDir);
        rs.close();
        st.close();
    
    }catch (Exception e) {

            e.printStackTrace();
            Log.e("MSSQL", e.toString());

        }
    }
	
		
	public void GetSiteCode() 
    {
     
    Connection conn = null;
    ResultSet rs = null;
    
    String driver = "net.sourceforge.jtds.jdbc.Driver";
        String user = getResources().getString(R.string.usernameDB);
        String pass = getResources().getString(R.string.passwordDB);
        String host = getResources().getString(R.string.Host_port);

    try {
        Class.forName(driver);
        Log.d("OK", "a");
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build(); StrictMode.setThreadPolicy(policy);
        conn = DriverManager.getConnection("jdbc:jtds:sqlserver://"+host,user, pass);
        listView = (ListView) findViewById(R.id.list);
        Log.e("MSSQL", "Connected");
        Log.d("OK", "b");
        Statement st=conn.createStatement();
        String SQL = "SELECT site_code,site_name FROM [Siamraj].[dbo].[ms_site] where status = 'A'" +
        			  "and contract_expired>= GETDATE() and site_code like '%CR%'  group by site_code,site_name order by site_code";
        
        Log.d("LOG", SQL);
        rs=st.executeQuery(SQL);
      
    ArrayList<String> dirArray = new ArrayList<String>();
      if(!rs.next()){
          Toast.makeText(this, "ไม่พบข้อมูล กรุณาตรวจสอบ",Toast.LENGTH_SHORT).show();
       	MySpinner.setAdapter(null);
        	
        	return;
     }
    
      Str_RepairNo =rs.getString(1);
      Log.d("LOGOK", Str_RepairNo);
      dirArray.add(rs.getString(1)+"|" +rs.getString(2));
      while(rs.next()==true)
       {
    	  dirArray.add(rs.getString(1)+"|" +rs.getString(2));
	   }
      
       ArrayAdapter<String> adapterDir = new ArrayAdapter<String>(this,R.layout.list_item,dirArray);
       MySpinner.setAdapter(adapterDir);
        rs.close();
        st.close();
    
    }catch (Exception e) {

            e.printStackTrace();
            Log.e("MSSQL", e.toString());

        }
    }

}
