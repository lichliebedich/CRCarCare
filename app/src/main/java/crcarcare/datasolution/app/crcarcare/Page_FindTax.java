package crcarcare.datasolution.app.crcarcare;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
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

public class Page_FindTax extends Fragment {
	
	EditText etLicense1,etLicense2;
	Button B1;
	ListView listView;
	String Str_LicenseId;
	int CHECKPUTDATA;
	View v;
	public Page_FindTax(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
    v = inflater.inflate(R.layout.tax_screen, container, false);
       
    Str_LicenseId = getActivity().getIntent().getStringExtra("T_LicenseId");
	CHECKPUTDATA = getActivity().getIntent().getIntExtra("PutData", 0);
       
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
	if (CHECKPUTDATA>0){
		
		etLicense1.setText(Str_LicenseId.toString().substring(0,2));
		etLicense2.setText(Str_LicenseId.toString().substring(3,7));
		connectTodatabase() ;
	}
	
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
    listView = (ListView) v.findViewById(R.id.list);
    Log.e("MSSQL", "Connected");
    
    Statement st=conn.createStatement();
    
	STR_LICENSE_ID = etLicense1.getText().toString().trim();
    STR_LICENSE_ID += "-" ;
    STR_LICENSE_ID += etLicense2.getText().toString().trim();

   
    String SQL = "SELECT * FROM cr_tax  WHERE license_id = '" + STR_LICENSE_ID+"' ORDER BY TAX_DATE DESC";
    Log.d("LOG", SQL);
    rs=st.executeQuery(SQL);
 
    ArrayList<String> dirArray = new ArrayList<String>();
    if(!rs.next()){
    	Toast.makeText(getActivity(), "ไม่พบข้อมูล กรุณาตรวจสอบ",Toast.LENGTH_SHORT).show();
    	listView.setAdapter(null);
    	
    	return;
    }
    
    
    Str_LicenseId = rs.getString(1);
    
    String TaxDate,DueDate,ReceipeNO,TaxAmount="";
    
    TaxDate=  rs.getString(4).substring(0,10);
    TaxDate=  TaxDate.substring(8,10)+"/"+TaxDate.substring(5,7)+"/";
    int X = Integer.parseInt(rs.getString(4).substring(0,4))+543;
    TaxDate= TaxDate+X;
    
    DueDate=  rs.getString(5).substring(0,10);
    DueDate=  DueDate.substring(8,10)+"/"+DueDate.substring(5,7)+"/";
    int Y = Integer.parseInt(rs.getString(5).substring(0,4))+543;
    DueDate= DueDate+Y;
    
    ReceipeNO=rs.getString(6);
    TaxAmount=rs.getString(7);
	dirArray.add(ReceipeNO + "\n" + "วันที่เสียภาษี : "+TaxDate +"\n" + "วันครบกำหนด : " + DueDate +"\n"+"อัตราภาษี : "+ TaxAmount +"\n");
    
    while(rs.next()==true)
    {
    
	 	TaxDate=  rs.getString(4).substring(0,10);
	    TaxDate=  TaxDate.substring(8,10)+"/"+TaxDate.substring(5,7)+"/";
	    X = Integer.parseInt(rs.getString(4).substring(0,4))+543;
	    TaxDate= TaxDate+X;
	    
	    DueDate=  rs.getString(5).substring(0,10);
	    DueDate=  DueDate.substring(8,10)+"/"+DueDate.substring(5,7)+"/";
	    Y = Integer.parseInt(rs.getString(5).substring(0,4))+543;
	    DueDate= DueDate+Y;
        ReceipeNO=rs.getString(6);
        TaxAmount=rs.getString(7);
		dirArray.add(ReceipeNO + "\n" + "วันที่เสียภาษี : "+TaxDate +"\n" + "วันครบกำหนด : " + DueDate +"\n"+"อัตราภาษี : "+ TaxAmount +"\n");
        
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
