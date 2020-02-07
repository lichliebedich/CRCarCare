package crcarcare.datasolution.app.crcarcare;

import android.Manifest;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.ActivityCompat;
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class HomeFragment extends Fragment  {
	EditText etLicense1,etLicense2;
	Button B1;
	ListView listView;
	String Str_RepairNo,Str_MileNo,Str_Depart,Str_Vendor,Str_CustName,Str_CustTel=null;
	String Str_LicenseId="";
	int CHECKPUTDATA;
	View v;
	boolean Search_License;
	private String TAG = "PermissionDemo";
	private int RECORD_REQUEST_CODE = 101;
	public HomeFragment(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        v = inflater.inflate(R.layout.activity_main, container, false);
		isWriteStoragePermissionGranted();
    
        Str_LicenseId = getActivity().getIntent().getStringExtra("T_LicenseId");
		CHECKPUTDATA = getActivity().getIntent().getIntExtra("PutData", 0);



		etLicense1 =(EditText) v.findViewById(R.id.et_license1);
		etLicense1.setInputType(InputType.TYPE_NULL);
		etLicense1.setOnTouchListener(new View.OnTouchListener() {
	        public boolean onTouch(View v, MotionEvent event) {
	        	etLicense1.setInputType(InputType.TYPE_CLASS_TEXT);
	        	etLicense1.onTouchEvent(event); // call native handler
	        return true; // consume touch even
	        }


	    });
		etLicense1.setOnKeyListener(new OnKeyListener() {
			
			@Override
		    public boolean onKey(View v, int keyCode, KeyEvent event) {
		        // If the event is a key-down event on the "enter" button
		        if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
		            (keyCode == KeyEvent.KEYCODE_ENTER)){
		        	etLicense2.setSelectAllOnFocus(true);
		        	return true;
		        }
		        return false;
		    }
		});
		
		etLicense2 =(EditText) v.findViewById(R.id.et_license2);
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
		
		
		B1 = (Button) v.findViewById(R.id.bn_SearchOk);
		B1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Str_LicenseId="";
				String LICENSE_ID1,LICENSE_ID2="";
				LICENSE_ID1 = etLicense1.getText().toString().trim();
				LICENSE_ID2 = etLicense2.getText().toString().trim();
				if (LICENSE_ID1.equalsIgnoreCase("")){
					Search_License = true;
					GetLicense(LICENSE_ID2);
				}else{
					Search_License = false;
					connectTodatabase() ;
					hideKeyboard(v);
				}
							
			}

		});	
		
		listView = (ListView) v.findViewById(R.id.list);		
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Object item = parent.getItemAtPosition (position);
				Log.d("XXX", String.valueOf(Search_License));
				
				if (Search_License==true){
					if (item.toString().trim().length() == 9) {
						Str_LicenseId = item.toString().substring(0, 9);
					}else if (item.toString().trim().length() == 8){
						Str_LicenseId = item.toString().substring(0, 8);
					}else{
						Str_LicenseId = item.toString().substring(0, 7);
					}
					   Log.d("length",String.valueOf(item.toString().length()));
						Log.d("licen", Str_LicenseId);
						connectTodatabase();
				}else if (Search_License==false){
					try {
						
						if (Str_Vendor==null){
							Str_Vendor="000000";
						}
						if (Str_CustTel==null){
							Str_CustTel="ไม่พบเบอร์ติดต่อ";
						}
						
						
							Str_RepairNo =  item.toString().substring(0, 10);
							Intent PageRepair =new Intent(getActivity().getApplicationContext(),Page_RepairDetail.class);
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
			}							
		});
		

		if (CHECKPUTDATA>0){
			
			etLicense1.setText(Str_LicenseId.toString().substring(0,2));
			etLicense2.setText(Str_LicenseId.toString().substring(3,7));
			connectTodatabase() ;
		}
		hideKeyboard(v);
        return v;
    }
		
		private void GetLicense(String license2) {
			// TODO Auto-generated method stub
		    Connection conn = null;
		    ResultSet rs = null;
			String user = getResources().getString(R.string.usernameDB);
			String pass = getResources().getString(R.string.passwordDB);
			String host = getResources().getString(R.string.Host_port);

		    String driver = "net.sourceforge.jtds.jdbc.Driver";
		  
		    try {
		        Class.forName(driver);
		        Log.d("OK", "a");
		        
		        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
						.permitAll().build(); StrictMode.setThreadPolicy(policy);
				conn = DriverManager.getConnection("jdbc:jtds:sqlserver://"+host,user, pass);
		        listView = (ListView) v.findViewById(R.id.list);
		        
		        Log.e("MSSQL","Connected");
		        Log.d("OK", "b");
		        Statement st=conn.createStatement();
		        		   		       
		        String SQL = "SELECT license_id FROM cr_repair  WHERE license_id like '%"
						+ license2 +"%' GROUP BY license_id";
		        Log.d("LOG", SQL);
		        rs=st.executeQuery(SQL);
		     
		        ArrayList<String> dirArray = new ArrayList<String>();
		        if(!rs.next()){
		        	Toast.makeText(getActivity(), "ไม่พบข้อมูล กรุณาตรวจสอบ",Toast.LENGTH_SHORT).show();
		        	
		        	listView.setAdapter(null);
		        	
		        	return;
		        }
		        
		        
		        Str_LicenseId = rs.getString(1);
		        dirArray.add(Str_LicenseId + "\n");
		        
		        while(rs.next()==true)
		        {
		        	 Str_LicenseId = rs.getString(1);
				     dirArray.add(Str_LicenseId + "\n");
		        }
		        
		        ArrayAdapter<String> adapterDir = new ArrayAdapter<String>(getActivity(),R.layout.list_item,dirArray);
		  		listView.setAdapter(adapterDir);
		        rs.close();
		        st.close();
		    
		    }catch (Exception e) {

		            e.printStackTrace();
		            Log.e("MSSQL", e.toString());

		        }
		}
	
	
	public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager) getActivity()
				.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
	
	public void connectTodatabase() 
    {
     
    Connection conn = null;
    ResultSet rs = null;
    String STR_LICENSE_ID;
    STR_LICENSE_ID = etLicense1.getText().toString().trim();
    STR_LICENSE_ID += "-" ;
    STR_LICENSE_ID += etLicense2.getText().toString().trim();
    Search_License=false;
    String driver = "net.sourceforge.jtds.jdbc.Driver";

		String user = getResources().getString(R.string.usernameDB);
		String pass = getResources().getString(R.string.passwordDB);
		String host = getResources().getString(R.string.Host_port);
  
    try {
        Class.forName(driver);
        Log.d("OK", "a");
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build(); StrictMode.setThreadPolicy(policy);
		conn = DriverManager.getConnection("jdbc:jtds:sqlserver://"+host,user, pass);
        listView = (ListView) v.findViewById(R.id.list);
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
		
       
        String SQL = "select * from cr_repair  WHERE license_id = '" + STR_LICENSE_ID+"' ORDER BY repair_date DESC ";
        Log.d("LOG", SQL);
        rs=st.executeQuery(SQL);
     
        ArrayList<String> dirArray = new ArrayList<String>();
        if(!rs.next()){
        	Toast.makeText(getActivity(), "ไม่พบข้อมูลกรุณาตรวจสอบ",Toast.LENGTH_SHORT).show();
        	
        	listView.setAdapter(null);
        	
        	return;
        }
        
        Str_RepairNo =rs.getString(1);
        Str_LicenseId = rs.getString(17);
        Str_MileNo =rs.getString(18);
        Str_Vendor =rs.getString(8);
        Str_CustName = rs.getString(14);
        Str_CustTel = rs.getString(13);
        String Repair_code,Date,Repair_type,Site_code;
        
       
        Repair_code = rs.getString(1);
        Site_code = rs.getString(5);
        if (Site_code == null){
        	Site_code = "ไม่ระบุไซต์";
		}
        Date=  rs.getString(2).substring(0,10);
        Date=  Date.substring(8,10)+"/"+Date.substring(5,7)+"/";
        int Y = Integer.parseInt(rs.getString(2).substring(0,4))+543;

        Date= Date+Y;
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
        dirArray.add(Repair_code+"| วันที่แจ้งซ่อม :"+Date + "\n" +"(" + Repair_type +") "+ Site_code + "\n");
        while(rs.next()==true)
        {
          Repair_code = rs.getString(1);
          Date=  rs.getString(2).substring(0,10);
          Date=  Date.substring(8,10)+"/"+Date.substring(5,7)+"/";
          int X = Integer.parseInt(rs.getString(2).substring(0,4))+543;
     
          Date= Date+X;
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
          dirArray.add(Repair_code+"| วันที่แจ้งซ่อม :"+Date + "\n" +"(" + Repair_type +") "+ Site_code + "\n");
        }
        
        ArrayAdapter<String> adapterDir = new ArrayAdapter<String>(getActivity(),R.layout.list_item,dirArray);
  		listView.setAdapter(adapterDir);
        rs.close();
        st.close();
    
    }catch (Exception e) {

            e.printStackTrace();
            Log.e("MSSQL", e.toString());

        }
    }

	public  void isWriteStoragePermissionGranted() {
		if (Build.VERSION.SDK_INT >= 23) {
			if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE)
					== PackageManager.PERMISSION_GRANTED) {
				Log.v(TAG,"Permission is granted2");

			} else {

				Log.v(TAG,"Permission is revoked2");
				ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE},
						RECORD_REQUEST_CODE);

			}
		}
		else { //permission is automatically granted on sdk<23 upon installation
			Log.v(TAG,"Permission is granted2");

		}
	}



	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		switch (requestCode) {
			case 101:
				Log.d(TAG, "CELL PHONE");
				// Check if the only required permission has been granted
				if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
					// Camera permission has been granted, preview can be displayed
					Log.i(TAG, "permission has now been granted. Showing preview.");

				} else {
					Log.i(TAG, "permission was NOT granted.");


				}
				// END_INCLUDE(permission_result)
				break;

		}
	}





}