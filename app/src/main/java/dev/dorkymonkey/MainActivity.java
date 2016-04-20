package dev.dorkymonkey;

import dev.dorkymonkey.Attendance;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.graphics.Bitmap;

public class MainActivity extends Activity implements OnClickListener {
	private final static int SCANNIN_GREQUEST_CODE = 1;
    private ImageButton accountBtn, cameraBtn;
    private TextView contentTxt;
	private ImageView mImageView;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
        
        accountBtn = (ImageButton)findViewById(R.id.account_btn);
        accountBtn.setOnClickListener(this);
        cameraBtn = (ImageButton)findViewById(R.id.camera_btn);
        cameraBtn.setOnClickListener(this);
        contentTxt = (TextView)findViewById(R.id.scan_content);
        mImageView = (ImageView)findViewById(R.id.qrcode_bitmap);
	}

    public void onClick(View v) {
        if(v.getId() == R.id.account_btn) {
            Intent a = new Intent(this, Account.class);
            startActivity(a);
        } else if (v.getId() == R.id.camera_btn) { // scan
            Intent intent = new Intent();
            intent.setClass(MainActivity.this, MipcaActivityCapture.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivityForResult(intent, SCANNIN_GREQUEST_CODE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        switch (requestCode) {
        case SCANNIN_GREQUEST_CODE:
            if (resultCode == RESULT_OK) {
				Bundle bundle = intent.getExtras();
				contentTxt.setText(bundle.getString("result"));
				mImageView.setImageBitmap((Bitmap) intent.getParcelableExtra("bitmap"));

                // for http get
                Attendance att = new Attendance();
                att.setCheckType = "in";
                att.setDate = "2016-06-17";
                att.setCourseCode = "SWE 500";
                att.setCourse = "Software Engineering";
                URL url = null;
                ObjectOutputStream oos = null;
                try {
                    url = new URL("http://52.53.254.77:7777");  // still some work here
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setDoInput(true);
                    connection.setDoOutput(true);
                    connection.setConnectTimeout(10000);
                    connection.setReadTimeout(10000);
                    connection.setRequestMethod("POST");
                    oos = new ObjectOutputStream(connection.getOutputStream());
                    oos.writeObject(att);
                    InputStreamReader read = new InputStreamReader(connection.getInputStream()); // 向J2EE服务器发送消息 
                    BufferedReader br = new BufferedReader(read);
                    String line = "";
                    while ((line = br.readLine()) != null) {
                        Log.d("TAG", "line is " + line);
                    }
                    br.close();
                    //read.close();  // which one is needed here, or all of them?
                    connection.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                }
            }
        }
    }
}
