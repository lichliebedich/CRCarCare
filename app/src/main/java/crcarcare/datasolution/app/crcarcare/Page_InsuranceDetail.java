package crcarcare.datasolution.app.crcarcare;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class Page_InsuranceDetail extends Activity {
	String T_LicenseId,T_Payinname,T_Insurance,T_PeriodFrom,T_PeriodTo,T_SumInsured,T_Mandatory;
	TextView tv1,tv2,tv3,tv4,tv5,tv6,tv7,tv8;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.insurance_detail_screen);
		try {
			tv1 = findViewById(R.id.tv_license_id);
			tv2 = findViewById(R.id.tv_policy);
			tv3 = findViewById(R.id.tv_payinname);
			tv4 = findViewById(R.id.tv_periodfrom);
			tv5 = findViewById(R.id.tv_periodto);
			tv6 = findViewById(R.id.tv_suminsured);
			tv7 = findViewById(R.id.tv_mandatory);
			
			
			T_LicenseId= getIntent().getStringExtra("T_LicenseId");
			T_Payinname=getIntent().getStringExtra("T_Payinname");
			T_Insurance= getIntent().getStringExtra("T_Insurance");
			T_PeriodFrom= getIntent().getStringExtra("T_PeriodFrom");
			T_PeriodTo= getIntent().getStringExtra("T_PeriodTo");
			T_SumInsured= getIntent().getStringExtra("T_SumInsured");
			T_Mandatory= getIntent().getStringExtra("T_Mandatory");
			
			connectTodatabase();
			SET_INSURANCE_DETAIL();
			
			
		} catch (Exception e) {
			// TODO: handle exception
			Toast.makeText(this, "ไม่พบข้อมูล กรุณาลองใหม่",Toast.LENGTH_SHORT).show();
		}
	
	}

	private void SET_INSURANCE_DETAIL() {
		// TODO Auto-generated method stub
		try {

			tv1.setText("ทะเบียนรถ : " + T_LicenseId);
			tv2.setText("กรมธรรม์เลขที่ : " + T_Insurance);
			tv3.setText("บริษัทประกัน : "+ T_Payinname);
			tv4.setText("ประกันเริ่มวันที่ : " + T_PeriodFrom);
			tv5.setText("สิ้นสุดวันที่ : " + T_PeriodTo);
			tv6.setText("เบี้ยประกันหลัก : " + T_SumInsured);
			tv7.setText("ทุนประกัน : " + T_Mandatory);
			
			
		} catch (Exception e) {
			// TODO: handle exception
			Toast.makeText(this, "ไม่พบข้อมูล กรุณาตรวจสอบ",Toast.LENGTH_SHORT).show();
		}

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
    	    
    	    Statement st=conn.createStatement();
    	    
    	String SQL = "select a.*,b.vendor_name" + 
    			" from cr_insurance  a" +
    			" left join pc_vendor b" +
    			" on a.policy_code = b.vendor_code" +
    			" WHERE a.policy_no = '" + T_Insurance+"' " +
    			" and a.is_date_sub ='Y' " +
    			" ORDER BY period_insured_to DESC " ;
       
    	//String SQL = "select * from cr_insurance  WHERE policy_no = '" + T_Insurance+"' and is_date_sub ='Y' ORDER BY period_insured_to DESC";
    	Log.d("LOG", SQL);
        rs=st.executeQuery(SQL);
     
        ArrayList<String> dirArray = new ArrayList<String>();
        if(!rs.next()){
        	Toast.makeText(this, "ไม่พบข้อมูล กรุณาตรวจสอบ",Toast.LENGTH_SHORT).show();
        	
        	
        	return;
        }
        
        
        T_LicenseId = rs.getString(2);
        
        String PolicyNo,Sum_Insured_to,Sum_Insured_From;
        
        T_Insurance=rs.getString(5);
        T_SumInsured=rs.getString(11);
        T_Mandatory=rs.getString(12);
        
        PolicyNo = rs.getString(5);
        
        Sum_Insured_to =	rs.getString(11).substring(0,10);
        Sum_Insured_to =  Sum_Insured_to.substring(8,10)+"/"+Sum_Insured_to.substring(5,7)+"/";
        int X = Integer.parseInt(rs.getString(11).substring(0,4))+543;
        Sum_Insured_to = Sum_Insured_to+X;
        
        Sum_Insured_From =	rs.getString(10).substring(0,10);
        Sum_Insured_From =  Sum_Insured_From.substring(8,10)+"/"+Sum_Insured_From.substring(5,7)+"/";
        X = Integer.parseInt(rs.getString(10).substring(0,4))+543;
        Sum_Insured_From = Sum_Insured_From+X;
        
        T_Insurance=rs.getString(5);
        T_Mandatory=rs.getString(12);
        T_SumInsured=rs.getString(13);
        T_PeriodFrom = Sum_Insured_From;
        T_PeriodTo = Sum_Insured_to;
        T_Payinname = rs.getString(66);
        
        dirArray.add(PolicyNo + " | "+ Sum_Insured_to +"\n");
        
        while(rs.next()==true)
        {
        
        	 T_LicenseId = rs.getString(2);
        	 PolicyNo=rs.getString(5);
        	 Sum_Insured_to =	rs.getString(11).substring(0,10);
             Sum_Insured_to =  Sum_Insured_to.substring(8,10)+"/"+Sum_Insured_to.substring(5,7)+"/";
             X = Integer.parseInt(rs.getString(11).substring(0,4))+543;
             Sum_Insured_to = Sum_Insured_to+X;
             
             Sum_Insured_From =	rs.getString(10).substring(0,10);
             Sum_Insured_From =  Sum_Insured_From.substring(8,10)+"/"+Sum_Insured_From.substring(5,7)+"/";
             X = Integer.parseInt(rs.getString(10).substring(0,4))+543;
             Sum_Insured_From = Sum_Insured_From+X;
             
             T_Insurance=rs.getString(5);
             T_Mandatory=rs.getString(12);
             T_SumInsured=rs.getString(13);
             T_PeriodFrom = Sum_Insured_From;
             T_PeriodTo=Sum_Insured_to;
             T_Payinname=rs.getString(66);
             
             dirArray.add(PolicyNo + " | " + Sum_Insured_to + "\n");
                       
        }
        
        
        rs.close();
        st.close();
    
    }catch (Exception e) {

            e.printStackTrace();
            Log.e("MSSQL", e.toString());

        }
    }
}
