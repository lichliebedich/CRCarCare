package crcarcare.datasolution.app.crcarcare;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class Page_RepairDetail extends Activity {
	String T_LicenseId, T_RepairNo, T_MileNo, T_Brand, T_Model, T_VenDor, T_CustName, T_CustTel;
	ListView listView;
	private static final int REQUEST_PHONE_CALL = 1;
	TextView TV1, TV2, TV3, TV4, TV5, TV6, TV7, TV8, TV9;
	String Str_Licenseno;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.repair_screen);

		TV1 = findViewById(R.id.tv_license_id);
		TV2 = findViewById(R.id.tv_mileno);
		TV3 = findViewById(R.id.tv_Depart);
		TV4 = findViewById(R.id.tv_brand);
		TV5 = findViewById(R.id.tv_model);
		TV6 = findViewById(R.id.tv_vendorname);
		TV7 = findViewById(R.id.tv_vendortel);
		TV7.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try {

					Toast.makeText(getApplicationContext(),"กำลังทำการโทร  " + TV7.getText().toString() ,Toast.LENGTH_SHORT).show();
					Intent callIntent = new Intent(Intent.ACTION_CALL);
					callIntent.setData(Uri.parse("tel:" + TV7.getText().toString()));
					callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					if (ActivityCompat.checkSelfPermission(getApplicationContext(),
							Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
						return;
					}
					startActivity(callIntent);
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}


			}
		});
		TV8 = (TextView) findViewById(R.id.tv_custname);
		TV9 = (TextView) findViewById(R.id.tv_custtel);

		TV9.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try {
					Toast.makeText(getApplicationContext(),"กำลังทำการโทร  " + TV9.getText().toString() ,Toast.LENGTH_SHORT).show();
					Intent callIntent = new Intent(Intent.ACTION_CALL);
					callIntent.setData(Uri.parse("tel:" + TV9.getText().toString()));
					callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
						ActivityCompat.requestPermissions((Activity) getApplicationContext(), new String[]{Manifest.permission.CALL_PHONE},REQUEST_PHONE_CALL);
					}
					else
					{
						startActivity(callIntent);
					}
					
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
				
			}
		});
		
		T_LicenseId = getIntent().getStringExtra("T_LicenseId");
		T_RepairNo = getIntent().getStringExtra("T_RepairNo");
		T_CustName = getIntent().getStringExtra("T_CustName");
		T_CustTel = getIntent().getStringExtra("T_CustTel");
		
	    listView = (ListView) findViewById(R.id.list);
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Object item = parent.getItemAtPosition ( position );
	
				Intent PageSparePart =new Intent(getApplicationContext(),Page_RepairDetailPart.class);
				PageSparePart.putExtra("T_LicenseId", T_LicenseId);
				PageSparePart.putExtra("T_RepairNo", T_RepairNo);
				PageSparePart.putExtra("T_MileNo", T_MileNo);
				PageSparePart.putExtra("T_Brand", T_Brand);
				PageSparePart.putExtra("T_Model", T_Model);
				startActivity(PageSparePart);
			}
		});
		
		Show_Repair_Detail();
	
		if (T_VenDor.equalsIgnoreCase("null")){
			TV6.setText("ศูนย์บริการ :-");
			TV7.setText("เบอร์ติดต่อ :-");
		}else{
			GetVendor();
		}
		
		
	}
	

	
	private void Show_Repair_Detail() {
		// TODO Auto-generated method stub
		
		connectTodatabase();
		getRepairDetail();
		GetInfo();
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
    
        Log.e("MSSQL", "Connected");
        Log.d("OK", "b");
        Statement st=conn.createStatement();
        String SQL = "select * from cr_history_repair_detail  WHERE repair_no = '" + T_RepairNo +"'";
        Log.d("LOG", SQL);
        rs=st.executeQuery(SQL);
     
        ArrayList<String> dirArray = new ArrayList<String>();
        if(!rs.next()){

        	listView.setAdapter(null);
        	return;
        }
        dirArray.add(rs.getString(2) +"."+rs.getString(3) + "\n");
        while(rs.next())
        {
        	 dirArray.add(rs.getString(2) +"."+rs.getString(3) + "\n");
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
	
	private void getRepairDetail() {
		// TODO Auto-generated method stub
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
	    
	  
	        Statement st=conn.createStatement();

	  		 String SQL2 = "select * FROM [Siamraj].[dbo].[cr_repair] where repair_no = '" + T_RepairNo +"'";
	         rs=st.executeQuery(SQL2);
	         Log.e("MSSQL", SQL2);
		        Log.d("OK", "b");
	         if(!rs.next()){

	         	return;
	         }


			TV1.setText("ทะเบียนรถ : " + rs.getString(17));
			TV2.setText("เลขไมล์ : " + rs.getString(18));
			TV3.setText("เลขที่ใบแจ้งซ่อม : " + rs.getString(1));
			TV7.setText(rs.getString(9));
			TV8.setText("ผู้แจ้งซ่อม : " +  rs.getString(14));
	 		TV9.setText( rs.getString(13));
	 		
			T_MileNo = rs.getString(18);
			T_VenDor = rs.getString(8);
	         
	        rs.close();
	        st.close();
	    
	    }catch (Exception e) {

	            e.printStackTrace();
	            Log.e("MSSQL", e.toString());

	        }
	}

	public void GetInfo() 
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
      
        Log.e("MSSQL", "Connected");
        Log.d("OK", "b");
        Statement st=conn.createStatement();
        String SQL = "select a.license_id,b.brand_name,c.model_name from cr_vehicle a " +
        			 "left outer join cr_ms_brand b " +
        			 "on a.brand_code = b.brand_code " +
        			 "left outer join cr_ms_model c " +
        			 "on a.model_code = c.model_code " +
        			 "where a.status ='A' and license_id = '" + T_LicenseId +"'";
        
        Log.d("LOG", SQL);
        rs=st.executeQuery(SQL);
     
        if(!rs.next()){

        	return;
        }
        	T_Brand = rs.getString(2);
        	T_Model = rs.getString(3);
        	TV4.setText("ยี่ห้อ : " + rs.getString(2));
        	TV5.setText("รุ่น : " + rs.getString(3));
        while(rs.next()==true)
        {
         	T_Brand = rs.getString(2);
        	T_Model = rs.getString(3);
        	TV4.setText("ยี่ห้อ : " + rs.getString(2));
        	TV5.setText("รุ่น : " + rs.getString(3));
        }
       
        rs.close();
        st.close();
    
    }catch (Exception e) {

            e.printStackTrace();
            Log.e("MSSQL", e.toString());

        }
    }
	
	public void GetVendor() 
    {
     
    Connection conn = null;
    ResultSet rs = null;
    String driver = "net.sourceforge.jtds.jdbc.Driver";

		String user = getResources().getString(R.string.usernameDB);
		String pass = getResources().getString(R.string.passwordDB);
		String host = getResources().getString(R.string.Host_port);

    try {
        Class.forName(driver);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build(); StrictMode.setThreadPolicy(policy);
		conn = DriverManager.getConnection("jdbc:jtds:sqlserver://"+host,user, pass);

        Statement st=conn.createStatement();
        String SQL = "SELECT [vendor_name],[phone] FROM [Siamraj].[dbo].[pc_vendor] WHERE vendor_code = '" + T_VenDor +"'";
        
        Log.d("LOG", SQL);
        rs=st.executeQuery(SQL);
     
        if(!rs.next()){

        	return;
        }

			TV6.setText("ศูนย์บริการ : " + rs.getString(1));
        	TV7.setText(rs.getString(2));
        while(rs.next())
        {
			TV6.setText("ศูนย์บริการ : " + rs.getString(1));
        	TV7.setText(rs.getString(2));
        }
       
        rs.close();
        st.close();
    
    }catch (Exception e) {

            e.printStackTrace();
            Log.e("MSSQL", e.toString());

        }
    }
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		
	}
	
}
