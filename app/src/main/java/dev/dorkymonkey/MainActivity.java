package dev.dorkymonkey;

import dev.dorkymonkey.Attendance;
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
//import android.os.StrictMode;
//import android.os.StrictMode.ThreadPolicy;

public class MainActivity extends Activity implements OnClickListener {
	private final static int SCANNIN_GREQUEST_CODE = 1;
    private ImageButton accountBtn, cameraBtn;
    private TextView contentTxt; // try to remove this one when fragment works
	private ImageView mImageView;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

        // StrickMode trials work now, use regular thread method instead
        /*if (android.os.Build.VERSION.SDK_INT > 9) {  
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();  
            StrictMode.setThreadPolicy(policy);  
            }   */
        
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
				Bundle bundle = intent.getExtras(); // this section works
				contentTxt.setText(bundle.getString("result"));
				mImageView.setImageBitmap((Bitmap) intent.getParcelableExtra("bitmap"));   

                //Bundle bundle = this.getIntent().getExtras(); // do NOT need to take care of this part so far
                /*Bundle bundle = intent.getExtras(); // do NOT need to take care of this part so far
                String name = bundle.getString("name");
                String id = bundle.getString("id");
                Log.v("EditText name: ", name);
                Log.v("EditText id: ", id);   */
                

            }
        }  
    }
}
