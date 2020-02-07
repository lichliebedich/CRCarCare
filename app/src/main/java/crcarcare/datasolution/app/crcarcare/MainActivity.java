package crcarcare.datasolution.app.crcarcare;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class MainActivity extends Activity {

	EditText etLicense1,etLicense2;
	Button B1;
	ListView listView;
	String Str_RepairNo,Str_MileNo,Str_Vendor,Str_CustName,Str_CustTel=null;
	String Str_LicenseId=""; 
	int CHECKPUTDATA;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		 Str_LicenseId = getIntent().getStringExtra("T_LicenseId");
		 //รับค่ามาจากหน้าเมนู
		 CHECKPUTDATA = getIntent().getIntExtra("PutData", 0);
		
			etLicense1 =(EditText) findViewById(R.id.et_license1);
			etLicense1.setInputType(InputType.TYPE_NULL);
			etLicense1.setOnTouchListener(new View.OnTouchListener() {
		        public boolean onTouch(View v, MotionEvent event) {
		        	etLicense1.setInputType(InputType.TYPE_CLASS_TEXT);
		        	etLicense1.onTouchEvent(event); // call native handler
		        return true; // consume touch even
		        }


		    });
			etLicense1.setOnKeyListener(new OnKeyListener() {
			    public boolean onKey(View v, int keyCode, KeyEvent event) {
			        // If the event is a key-down event on the "enter" button
			        if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
			            (keyCode == KeyEvent.KEYCODE_ENTER)) {
			        	etLicense2.setSelectAllOnFocus(true);
			        	return true;
			        }
			        return false;
			    }
			});
			
			etLicense2 =(EditText) findViewById(R.id.et_license2);
			etLicense2.setOnKeyListener(new OnKeyListener() {
				
				@Override
				public boolean onKey(View v, int keyCode, KeyEvent event) {
					// TODO Auto-generated method stub
					  if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
					            (keyCode == KeyEvent.KEYCODE_ENTER)) {
						  		connectTodatabase();
					        	return true;
					        }
					        return false;
					    }
					});
			
			
			B1 = (Button) findViewById(R.id.bn_SearchOk);
			B1.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Str_LicenseId="";
					connectTodatabase() ;
					
					
				}
			});
			
			listView = (ListView) findViewById(R.id.list);		
			listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					// TODO Auto-generated method stub
					Object item = parent.getItemAtPosition ( position );
					
					
					try {
						
						if (Str_Vendor==null){
							Str_Vendor="000000";
						}
						if (Str_CustTel==null){
							Str_CustTel="ไม่พบเบอร์ติดต่อ";
						}
						
						
							Str_RepairNo =  item.toString().substring(0, 10);
							Intent PageRepair =new Intent(getApplicationContext(),Page_RepairDetail.class);
							PageRepair.putExtra("T_LicenseId", Str_LicenseId.trim());
							PageRepair.putExtra("T_RepairNo", Str_RepairNo.trim());
							PageRepair.putExtra("T_MileNo", Str_MileNo);
							PageRepair.putExtra("T_VenDor", Str_Vendor);
							PageRepair.putExtra("T_CustName", Str_CustName);
							PageRepair.putExtra("T_CustTel", Str_CustTel);
							startActivity(PageRepair);
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}
					
				}
			});
			

			if (CHECKPUTDATA>0){
				
				etLicense1.setText(Str_LicenseId.substring(0,2).trim());
				etLicense2.setText(Str_LicenseId.substring(3,7).trim());
				connectTodatabase() ;
			}
			
	     
	    }
		
				
		public void connectTodatabase() 
	    {
	     
	    Connection conn = null;
	    ResultSet rs = null;
	    String STR_LICENSE_ID;
	    STR_LICENSE_ID = etLicense1.getText().toString().trim();
	    STR_LICENSE_ID += "-" ;
	    STR_LICENSE_ID += etLicense2.getText().toString().trim();

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
	        
	        if (Str_LicenseId.equalsIgnoreCase("")){
	        	STR_LICENSE_ID = etLicense1.getText().toString().trim();
	     	    STR_LICENSE_ID += "-" ;
	     	    STR_LICENSE_ID += etLicense2.getText().toString().trim();
			}else{
				STR_LICENSE_ID=Str_LicenseId;
			}
			
	       
	        String SQL = "select * from cr_repair  WHERE license_id = '" + STR_LICENSE_ID.trim()+"'";
	        Log.d("LOG", SQL);
	        rs=st.executeQuery(SQL);
	     
	        ArrayList<String> dirArray = new ArrayList<String>();
	        if(!rs.next()){
	        	Toast.makeText(this, "ไม่พบข้อมูล กรุณาตรวจสอบ",Toast.LENGTH_SHORT).show();
	        	
	        	listView.setAdapter(null);
	        	
	        	return;
	        }
	        
	        Str_RepairNo =rs.getString(1);
	        Str_LicenseId = rs.getString(17);
	        Str_MileNo =rs.getString(18);
	        Str_Vendor =rs.getString(8);
	        Str_CustName = rs.getString(14);
	        Str_CustTel = rs.getString(13);
	        String Repair_code,Date,Repair_type;
	        
	       
	        Repair_code = rs.getString(1);
	        Date=  rs.getString(2).substring(0,10);
	        Date=  Date.substring(8,10)+"/"+Date.substring(5,7)+"/"+Date.substring(0,4);
	        Repair_type = rs.getString(4);
	        switch (Repair_type) {
				case "001" : Repair_type = "ตรวจเช็คระยะ"; break;
				case "002" : Repair_type = "เปลี่ยนยาง"; break;
				case "003" : Repair_type = "อุบัติเหตุ"; break;
				case "004" : Repair_type= "รายการซ่อมอื่นๆ"; break;
				case "005" : Repair_type = "เปลี่ยนแบตเตอรี่"; break;
			 	default:
			 	break;
			}
			dirArray.add(Repair_code+"| วันที่แจ้งซ่อม :"+Date + "\n" +"(" + Repair_type +")"+ "\n");
	        while(rs.next()==true)
	        {
	          Repair_code = rs.getString(1);
	          Date=  rs.getString(2).substring(0,10);
	          Date=  Date.substring(8,10)+"/"+Date.substring(5,7)+"/"+Date.substring(0,4);
	          Repair_type = rs.getString(4);
	          switch (Repair_type) {
				  case "001" : Repair_type = "ตรวจเช็คระยะ"; break;
				  case "002" : Repair_type = "เปลี่ยนยาง"; break;
				  case "003" : Repair_type = "อุบัติเหตุ"; break;
				  case "004" : Repair_type= "รายการซ่อมอื่นๆ"; break;
				  case "005" : Repair_type = "เปลี่ยนแบตเตอรี่"; break;
			 	default:
			 	break;
			}
			dirArray.add(Repair_code+"| วันที่แจ้งซ่อม :"+Date + "\n" +"(" + Repair_type +")"+ "\n");
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
	}
