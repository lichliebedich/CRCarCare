package crcarcare.datasolution.app.crcarcare;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class Page_FindDepart extends Fragment {
	Spinner MySpinner;
	ListView listView;
	String Str_SiteCode,Str_LicenseId,Str_RepairNo,Str_MileNo,Str_Depart;
	View v;
	public Page_FindDepart(){}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		 v = inflater.inflate(R.layout.depart_screen, container, false);
         
		 hideKeyboard(v);
		 listView = (ListView) v.findViewById(R.id.list);
		 TextView Tv_Depart = (TextView) v.findViewById(R.id.tv_Depart);
		 Tv_Depart.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			   	AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
				alert.setTitle("ค้นหาด้วยรหัสไซต์"); //Set Alert dialog title here
				alert.setMessage("ใส่รหัสใช้งาน เช่น 58CRL0001"); //Message here
	 
	            // Set an EditText view to get user input 
	            final EditText input = new EditText(getActivity());
	            input.setText("58CRL0001");
	            alert.setView(input);
	 
	        	alert.setPositiveButton("ตกลง", new DialogInterface.OnClickListener()
				{

					public void onClick(DialogInterface dialog, int whichButton)
					{
						Str_SiteCode = input.getEditableText().toString();
	        	
	        	 	connectTodatabase();
	        	 	GetSiteCodeB(Str_SiteCode);
	        		} // End of onClick(DialogInterface dialog, int whichButton)
	        }); //End of alert.setPositiveButton
	        	alert.setNegativeButton("ยกเลิก", new DialogInterface.OnClickListener() {
	        	  public void onClick(DialogInterface dialog, int whichButton) {
	        	    // Canceled.
	        		  dialog.cancel();
	        	  }
	        }); //End of alert.setNegativeButton
	        	AlertDialog alertDialog = alert.create();
	        	alertDialog.show();
			}
		});
	    
	      MySpinner = (Spinner) v.findViewById(R.id.sp_Depart);
		  MySpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

	            @Override
	            public void onItemSelected(AdapterView<?> arg0, View arg1,
	                    int arg2, long arg3) {
	            	
	                try {
	                	Str_SiteCode = MySpinner.getSelectedItem().toString().substring(0,10);
		            	connectTodatabase();
					} catch (Exception e) {
						// TODO: handle exception
					}

	            }

	            @Override
	            public void onNothingSelected(AdapterView<?> arg0) {
	                // TODO Auto-generated method stub
	            }
	        });
		  
		  MySpinner.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				try {
					GetSiteCode();
				} catch (Exception e) {
					// TODO: handle exception
				}
				
				return false;
			}
		});
		  
		 
			GetSiteCode();
			
			listView = (ListView) v.findViewById(R.id.list);		
			listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					// TODO Auto-generated method stub
					Object item = parent.getItemAtPosition ( position );
					Str_LicenseId = item.toString();
					Log.d("A", Str_LicenseId);
					
				
						Intent PageMain =new Intent(getActivity().getApplicationContext(),MainActivity.class);
						PageMain.putExtra("T_LicenseId", Str_LicenseId);
						PageMain.putExtra("PutData", 1);
						startActivity(PageMain);
				}
			});
			  return v;
		}
	
	
		public void connectTodatabase() 
	    {
	     
	    Connection conn = null;
	    ResultSet rs = null;
	    
	    String driver = "net.sourceforge.jtds.jdbc.Driver";
	    listView.setAdapter(null);

			String user = getResources().getString(R.string.usernameDB);
			String pass = getResources().getString(R.string.passwordDB);
			String host = getResources().getString(R.string.Host_port);

	    try {
	        Class.forName(driver);
	     
	        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll()
					.build(); StrictMode.setThreadPolicy(policy);
			conn = DriverManager.getConnection("jdbc:jtds:sqlserver://"+host,user, pass);
	        
	        
	        Statement st=conn.createStatement();
	        String SQL = "SELECT license_id FROM cr_repair WHERE site_code ='"+Str_SiteCode+"' " +
					"GROUP BY license_id ORDER BY license_id";
	        Log.d("SQL",SQL);
	        rs=st.executeQuery(SQL);
	     
	        ArrayList<String> dirArray = new ArrayList<String>();
	        if(!rs.next()){
	        	Toast.makeText(getActivity(), "ไม่พบข้อมูล กรุณาตรวจสอบ",Toast.LENGTH_SHORT).show();
	        	listView.setAdapter(null);
	        	
	        	return;
	        }
	        
	        Str_LicenseId = rs.getString(1);
	        dirArray.add(rs.getString(1) + "\n");
	        while(rs.next()==true)
	        {
	          dirArray.add(rs.getString(1) + "\n");
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
	        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll()
					.build(); StrictMode.setThreadPolicy(policy);
			conn = DriverManager.getConnection("jdbc:jtds:sqlserver://"+host,user, pass);
	        listView = (ListView) v.findViewById(R.id.list);
	        Log.e("MSSQL", "Connected");
	        Log.d("OK", "b");
	        Statement st=conn.createStatement();
	        String SQL = "SELECT site_code,site_name FROM [Siamraj].[dbo].[ms_site] where status = 'A'" +
	        			  "and contract_expired>= GETDATE() and site_code like '%CR%'  " +
					      "group by site_code,site_name order by site_code";
	        
	        Log.d("LOG", SQL);
	        rs=st.executeQuery(SQL);
	      
	    ArrayList<String> dirArray = new ArrayList<String>();
	      if(!rs.next()){
	       	Toast.makeText(getActivity(), "ไม่พบข้อมูล กรุณาตรวจสอบ",Toast.LENGTH_SHORT).show();
	       	MySpinner.setAdapter(null);
	        	
	        	return;
	     }
	    
	      Str_RepairNo =rs.getString(1);
	      Log.d("LOGOK", Str_RepairNo);
	      dirArray.add(rs.getString(1)+"|" +rs.getString(2));
	      while(rs.next())
	       {
	    	  dirArray.add(rs.getString(1)+"|" +rs.getString(2));
		   }
	      
	       ArrayAdapter<String> adapterDir = new ArrayAdapter<String>(getActivity(),R.layout.list_item,dirArray);
	       MySpinner.setAdapter(adapterDir);
	        rs.close();
	        st.close();
	    
	    }catch (Exception e) {

	            e.printStackTrace();
	            Log.e("MSSQL", e.toString());

	        }
	    }
		
		public void GetSiteCodeB(String StrSite)
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
	        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll()
					.build(); StrictMode.setThreadPolicy(policy);
			conn = DriverManager.getConnection("jdbc:jtds:sqlserver://"+host,user, pass);
	        listView = (ListView) v.findViewById(R.id.list);
	        Log.e("MSSQL", "Connected");
	        Log.d("OK", "b");
	        Statement st=conn.createStatement();
	        String SQL = "SELECT site_code,site_name FROM [Siamraj].[dbo].[ms_site] where status = 'A'" +
	        			  "and contract_expired>= GETDATE() and site_code = '" + StrSite +"' "
	        			  		+ "group by site_code,site_name order by site_code";
	        
	        Log.d("LOG", SQL);
	        rs=st.executeQuery(SQL);
	      
	    ArrayList<String> dirArray = new ArrayList<String>();
	      if(!rs.next()){
	       	Toast.makeText(getActivity(), "ไม่พบข้อมูล กรุณาตรวจสอบ",Toast.LENGTH_SHORT).show();
	       	MySpinner.setAdapter(null);
	        	
	        	return;
	     }
	    
	      Str_RepairNo =rs.getString(1);
	      Log.d("LOGOK", Str_RepairNo);
	      dirArray.add(rs.getString(1)+ "|" +rs.getString(2));
	      while(rs.next()==true)
	    	  
	       {
	    	  dirArray.add(rs.getString(1)+"|" +rs.getString(2));
		   }
	      
	       ArrayAdapter <String> adapterDir = new ArrayAdapter<String>(getActivity(),R.layout.list_item,dirArray);
	       MySpinner.setAdapter(adapterDir);
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
}
