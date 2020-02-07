package crcarcare.datasolution.app.crcarcare;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
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

public class Page_FindInsurance extends Fragment {
	EditText etLicense1,etLicense2;
	Button B1;
	ListView listView;
	String Str_Insurance,Str_LicenseId,Str_PeriodFrom,
	       Str_PeriodTo,Str_Payinname,Str_SumInsured,
	       Str_Mandatory="";
	Boolean Search_License=true;
	View v;
	
	public Page_FindInsurance(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View v = inflater.inflate(R.layout.insurance_screen, container, false);
         
        etLicense1 =(EditText) v.findViewById(R.id.et_license1);
		//etLicense1.setSelectAllOnFocus(true);
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
				connectTodatabase() ;
				hideKeyboard(v);
				
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
				Log.d("license", Str_LicenseId);
				
				if (Search_License==true){
						Str_LicenseId = item.toString().substring(0, 7);
						connectTodatabase();
				}else if (Search_License==false){
					try {
						Log.d("OK", Str_LicenseId);	
						Str_Insurance =item.toString().substring(0, 19);
						Intent PageDetail =new Intent(getActivity().getApplicationContext(),Page_InsuranceDetail.class);
						PageDetail.putExtra("T_LicenseId", Str_LicenseId.toString());
						PageDetail.putExtra("T_Payinname", Str_Payinname);
						PageDetail.putExtra("T_Insurance", Str_Insurance);
						PageDetail.putExtra("T_PeriodFrom", Str_PeriodFrom);
						PageDetail.putExtra("T_PeriodTo", Str_PeriodTo);
						PageDetail.putExtra("T_SumInsured", Str_SumInsured);
						PageDetail.putExtra("T_Mandatory", Str_Mandatory);
							
							startActivity(PageDetail);
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}
					
				}
			}							
		});
		
		return v;
	}

	public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager) 
        		getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }	
	
public void connectTodatabase() 
    {
     
    Connection conn = null;
    ResultSet rs = null;
    String STR_LICENSE_ID;
    Search_License=false;
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
    	    
    	    Statement st=conn.createStatement();
    	    
    	
    	STR_LICENSE_ID = etLicense1.getText().toString().trim();
    	STR_LICENSE_ID += "-" ;
    	STR_LICENSE_ID += etLicense2.getText().toString().trim();
    	 
       
        String SQL = "select * from cr_insurance  WHERE license_id = '" + STR_LICENSE_ID+"' and is_date_sub ='Y' ORDER BY period_insured_to DESC";
        Log.d("LOG", SQL);
        rs=st.executeQuery(SQL);
     
        ArrayList<String> dirArray = new ArrayList<String>();
        if(!rs.next()){
        	Toast.makeText(getActivity(), "ไม่พบข้อมูล กรุณาตรวจสอบ",Toast.LENGTH_SHORT).show();
        	listView.setAdapter(null);
        	
        	return;
        }
        
        
        Str_LicenseId = rs.getString(2);
        
        String PolicyNo,Sum_Insured_to,Sum_Insured_From;
        
        Str_Insurance=rs.getString(5);
        Str_SumInsured=rs.getString(11);
        Str_Mandatory=rs.getString(12);
        
        PolicyNo = rs.getString(5);
        
        Sum_Insured_to =	rs.getString(11).substring(0,10);
        Sum_Insured_to =  Sum_Insured_to.substring(8,10)+"/"+Sum_Insured_to.substring(5,7)+"/";
        int X = Integer.parseInt(rs.getString(11).substring(0,4))+543;
        Sum_Insured_to = Sum_Insured_to+X;
        
        Sum_Insured_From =	rs.getString(10).substring(0,10);
        Sum_Insured_From =  Sum_Insured_From.substring(8,10)+"/"+Sum_Insured_From.substring(5,7)+"/";
        X = Integer.parseInt(rs.getString(10).substring(0,4))+543;
        Sum_Insured_From = Sum_Insured_From+X;
        
        Str_Insurance=rs.getString(5);
        Str_Mandatory=rs.getString(12);
        Str_SumInsured=rs.getString(13);
        Str_PeriodFrom = Sum_Insured_From;
        Str_PeriodTo = Sum_Insured_to;
        Str_Payinname = rs.getString(66);
        
        dirArray.add(PolicyNo + " | "+ Sum_Insured_to +"\n");
        
        while(rs.next()==true)
        {
        
        	 Str_LicenseId = rs.getString(2);
        	 PolicyNo=rs.getString(5);
        	 Sum_Insured_to =	rs.getString(11).substring(0,10);
             Sum_Insured_to =  Sum_Insured_to.substring(8,10)+"/"+Sum_Insured_to.substring(5,7)+"/";
             X = Integer.parseInt(rs.getString(11).substring(0,4))+543;
             Sum_Insured_to = Sum_Insured_to+X;
             
             Sum_Insured_From =	rs.getString(10).substring(0,10);
             Sum_Insured_From =  Sum_Insured_From.substring(8,10)+"/"+Sum_Insured_From.substring(5,7)+"/";
             X = Integer.parseInt(rs.getString(10).substring(0,4))+543;
             Sum_Insured_From = Sum_Insured_From+X;
             
             Str_Insurance=rs.getString(5);
             Str_Mandatory=rs.getString(12);
             Str_SumInsured=rs.getString(13);
             Str_PeriodFrom = Sum_Insured_From;
             Str_PeriodTo=Sum_Insured_to;
             Str_Payinname=rs.getString(66);
             
             dirArray.add(PolicyNo + " | " + Sum_Insured_to + "\n");
                       
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
 
}
