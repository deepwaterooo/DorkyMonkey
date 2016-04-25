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
import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener {
	private final static int SCANNIN_GREQUEST_CODE = 1;
    private ImageButton accountBtn, cameraBtn;
    private TextView contentTxt;
    private boolean getNewResult = false;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
        
        accountBtn = (ImageButton)findViewById(R.id.account_btn);
        accountBtn.setOnClickListener(this);
        cameraBtn = (ImageButton)findViewById(R.id.camera_btn);
        cameraBtn.setOnClickListener(this);

        if (savedInstanceState == null) {
            //if (getNewResult) {
            getFragmentManager().beginTransaction().add(R.id.fragment, new MyFragment()).commit();
        }
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
                getNewResult = true;
                Bundle bundle = intent.getExtras(); // this section works
                System.out.println("bundle.getString(\"result\"): " + bundle.getString("result"));
                contentTxt = (TextView) getFragmentManager().findFragmentById(R.id.fragment).getView().findViewById(R.id.parsedResult);
				contentTxt.setText(bundle.getString("result"));
                Button cancelBtn = (Button) getFragmentManager().findFragmentById(R.id.fragment).getView().findViewById(R.id.cancelBtn);
                Button confirmBtn = (Button) getFragmentManager().findFragmentById(R.id.fragment).getView().findViewById(R.id.confirmBtn);
                
            }
        }  
    }
}
