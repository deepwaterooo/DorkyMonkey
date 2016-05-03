package dev.dorkymonkey;

import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.graphics.Bitmap;
import java.net.URL;
import java.net.HttpURLConnection;
import java.io.ObjectOutputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import android.util.Log;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Button;
import android.widget.FrameLayout;
import android.provider.Settings.Secure;

import java.net.URL;
import java.net.HttpURLConnection;
import java.io.ObjectOutputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import android.util.Log;
import android.widget.Toast;
import android.os.Looper;

import java.util.ArrayList;
import java.util.HashMap;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;

public class MainActivity extends Activity implements OnClickListener {
	private final static int SCANNIN_GREQUEST_CODE = 1;
    private final static String [] checkType = {"checkinstudent", "checkoutstudent"};
    private ImageButton accountBtn, cameraBtn;
    private TextView contentTxt;
    private Button cancelBtn; 
    private Button confirmBtn;
    private String deviceId;
    private boolean checkedIn;
    private String name;
    private String id;
    private String qrString;

    private static final String TAG_DATE = "date";
    private static final String TAG_COURSE = "course";
    private static final String TAG_COURSE_CODE = "course_code";
    private static final String TAG_CHECK = "check";
    HashMap<String, String> obj;// = ParseJSON(qrString);
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
        accountBtn = (ImageButton)findViewById(R.id.account_btn);
        accountBtn.setOnClickListener(this);
        cameraBtn = (ImageButton)findViewById(R.id.camera_btn);
        cameraBtn.setOnClickListener(this);
        checkedIn = false;

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction().add(R.id.fragment, new MyFragment()).commit();
        }
	}

    private HashMap<String, String> ParseJSON(String json) {
        if (json != null) {
            try {
                HashMap<String, String> obj = new HashMap<String, String>();
                JSONObject c = new JSONObject(json);
                String date = c.getString(TAG_DATE);
                String course = c.getString(TAG_COURSE);
                String course_code = c.getString(TAG_COURSE_CODE);
                String check = c.getString(TAG_CHECK);
                obj.put(TAG_DATE, date);
                obj.put(TAG_COURSE, course);
                obj.put(TAG_COURSE_CODE, course_code);
                obj.put(TAG_CHECK, check);
                return obj;
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
        } else {
            Log.e("ServiceHandler", "No data received from HTTP request");
            return null;
        }
    }
    
    public void onClick(View v) {
        switch (v.getId()) {
        case R.id.account_btn: 
            Intent a = new Intent(this, Account.class);
            startActivityForResult(a, 2);
            //startActivity(a);
            break;
        case R.id.camera_btn:
            Intent intent = new Intent();
            intent.setClass(MainActivity.this, MipcaActivityCapture.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivityForResult(intent, SCANNIN_GREQUEST_CODE);
            break;
        case R.id.cancelBtn:
            Intent activity = new Intent();
            activity.setClass(MainActivity.this, MainActivity.class);
            startActivity(activity);
            break;
        case R.id.confirmBtn:
            deviceId = Secure.getString(v.getContext().getContentResolver(), Secure.ANDROID_ID); // I/System.out: deviceId: cb023ed0a6983fc4
            System.out.println("deviceId: " + deviceId);
            
            // try parse the string to a JSON object
            System.out.println("qrString: " + qrString);

            obj = ParseJSON(qrString);
            /*
            System.out.println("qrString: " + qrString);
            for (int i = 0; i < obj.size(); i++) {
                System.out.println("obj.get(\"TAG_DATE\"): " + obj.get("TAG_DATE"));
                System.out.println("obj.get(\"TAG_COURSE\"): " + obj.get("TAG_COURSE"));
                } */
            
            Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        HttpURLConnection connection = null;
                        try {
                            StringBuilder urlStr = new StringBuilder();
                            urlStr.append("http://52.53.254.77:7777/");
                            if (!checkedIn)
                                urlStr.append(checkType[0]);
                            else urlStr.append(checkType[1]);

                            urlStr.append("/SWE500?sid=");
                            /*
                            urlStr.append("/");
                            String course = obj.get("TAG_COURSE");
                            urlStr.append(course);
                            urlStr.append("?sid=");
                            */
                            System.out.println("urlStr.toString(): " + urlStr.toString());
                            urlStr.append(id);
                            System.out.println("after id urlStr.toString(): " + urlStr.toString());
                            urlStr.append("&flag=deviceid&addi=");
                            urlStr.append(deviceId);
                            System.out.println("after Device id urlStr.toString(): " + urlStr.toString());
                            
                            //System.out.println("urlStr.toString(): " + urlStr.toString());
                            URL url = new URL(urlStr.toString()); 
                            connection = (HttpURLConnection) url.openConnection();
                            InputStreamReader read = new InputStreamReader(connection.getInputStream());
                            BufferedReader br = new BufferedReader(read);
                            //String line = ""; // skip the first line
                            String line = br.readLine();
                            while ((line = br.readLine()) != null) { // parse json object later
                                //Log.d("TAG", "line is " + line);
                                System.out.println("line.substring(11, 12): " + line.substring(11, 12));
    
                                if (line.substring(11, 12) == "St") {
                                    Looper.prepare();
                                    Toast.makeText(MainActivity.this, "Check In FAILED!", Toast.LENGTH_LONG).show();
                                    Looper.loop();
                                    break;
                                } else {
                                    Looper.prepare();
                                    Toast.makeText(MainActivity.this, "Check In SUCCEED!", Toast.LENGTH_LONG).show();                                                          Looper.loop();
                                    break;
                                } 
                            }
                            br.close();
                            connection.disconnect();
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            if (connection != null) connection.disconnect();
                        }

                    }
                });
            thread.start();
            // 
        }
        //break;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        switch (requestCode) {
        case SCANNIN_GREQUEST_CODE:
            if (resultCode == RESULT_OK) {
                Bundle bundle = intent.getExtras(); // this section works
                qrString = bundle.getString("result");
                //System.out.println("bundle.getString(\"result\"): " + bundle.getString("result"));
                //System.out.println("qrString: " + qrString);

                contentTxt = (TextView) getFragmentManager().findFragmentById(R.id.fragment).getView().findViewById(R.id.parsedResult);
				contentTxt.setText(bundle.getString("result"));
                
                cancelBtn = (Button) getFragmentManager().findFragmentById(R.id.fragment).getView().findViewById(R.id.cancelBtn);
                confirmBtn = (Button) getFragmentManager().findFragmentById(R.id.fragment).getView().findViewById(R.id.confirmBtn);
                cancelBtn.setOnClickListener(this);
                confirmBtn.setOnClickListener(this);
                cancelBtn.setVisibility(View.VISIBLE);
                confirmBtn.setVisibility(View.VISIBLE);
            }
            break;
        case 2:
            if (resultCode == RESULT_OK) {
                Bundle bundle = intent.getExtras(); 
                System.out.println("bundle.getString(\"name\"): " + bundle.getString("name"));
                System.out.println("bundle.getString(\"id\"): " + bundle.getString("id"));
                this.id = bundle.getString("id").trim().toString();
                this.name = bundle.getString("name").trim().toString();
            }
        }
    }
}
