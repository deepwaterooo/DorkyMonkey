package dev.dorkymonkey;

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
            
            //IntentIntegrator scanIntegrator = new IntentIntegrator(this);
            //scanIntegrator.initiateScan();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        //IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        super.onActivityResult(requestCode, resultCode, intent);
        switch (requestCode) {
        case SCANNIN_GREQUEST_CODE:
            if (resultCode == RESULT_OK) {
				Bundle bundle = intent.getExtras();
				contentTxt.setText(bundle.getString("result"));
				mImageView.setImageBitmap((Bitmap) intent.getParcelableExtra("bitmap"));
            }
        }
        /* if (scanningResult != null) {
            // we have a result
            String scanContent = scanningResult.getContents();
            String scanFormat = scanningResult.getFormatName();
            fmtTxt.setText("FORMAT: " + scanFormat);
            contentTxt.setText("CONTENT: " + scanContent);
        } else {
            Toast toast = Toast.makeText(getApplicationContext(), "No scan data received!", Toast.LENGTH_SHORT);
            toast.show();
            } */
    }
}
