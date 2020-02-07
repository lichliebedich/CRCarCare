package crcarcare.datasolution.app.crcarcare;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class Page_RepairDetailPart extends Activity {
	String T_LicenseId,T_RepairNo,T_MileNo,T_Brand,T_Model;
	ListView listView;
	
	TextView TV1,TV2,TV3,TV4,TV5;
	String Str_Licenseno;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.part_screen);
		TV1 =(TextView)findViewById(R.id.tv_license_id);
		TV2 =(TextView)findViewById(R.id.tv_mileno);
		TV3 =(TextView)findViewById(R.id.tv_Depart);
		TV4 =(TextView)findViewById(R.id.tv_brand);
		TV5 =(TextView)findViewById(R.id.tv_model);
		
		T_LicenseId = getIntent().getStringExtra("T_LicenseId");
		T_RepairNo = getIntent().getStringExtra("T_RepairNo");
		T_MileNo = getIntent().getStringExtra("T_MileNo");
		T_Brand = getIntent().getStringExtra("T_Brand");
		T_Model = getIntent().getStringExtra("T_Model");

		listView = (ListView) findViewById(R.id.list);
			
		Log.d("A", T_LicenseId);
		Log.d("B", T_RepairNo);
		Log.d("C", T_MileNo);
		Show_Repair_Detail();
		GetInfo();
		
		
	}
	private void Show_Repair_Detail() {
		
		// TODO Auto-generated method stub
        TV1.setText("ทะเบียนรถ : " + T_LicenseId);
        TV2.setText("เลขไมล์ : " + T_MileNo);
        TV3.setText("เลขใบแจ้งซ่อม : " + T_RepairNo.toString().trim());
        TV4.setText("ยี่ห้อ : " + T_Brand);
        TV5.setText("รุ่น : " + T_Model);
		connectTodatabase();
		
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
        	//Toast.makeText(this, "��辺������ ��سҵ�Ǩ�ͺ !!",Toast.LENGTH_SHORT).show();
        	return;
        }
        if(rs.getString(4).equalsIgnoreCase("null")){
        	dirArray.add("ไม่พบข้อมูลอะไหล่" + "\n");
        }else{
        	dirArray.add(rs.getString(2) +"."+rs.getString(4) + "\n");
        }

        while(rs.next()==true)
        {          
        	if(rs.getString(4).equalsIgnoreCase("null")){
            	dirArray.add("ไม่พบข้อมูลอะไหล่" + "\n");
            }else{
            	dirArray.add(rs.getString(2) +"."+rs.getString(4) + "\n");
            }
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
        	//Toast.makeText(this, "��辺������ ��سҵ�Ǩ�ͺ !!",Toast.LENGTH_SHORT).show();
        	return;
        }

            TV4.setText("ยี่ห้อ : " + rs.getString(2));
            TV5.setText("รุ่น : " + rs.getString(3));
        while(rs.next()==true)
        {
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
}
