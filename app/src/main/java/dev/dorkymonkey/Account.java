package dev.dorkymonkey;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Button;
import android.util.Log;
import android.view.View.OnClickListener;
import java.net.URL;
import java.net.HttpURLConnection;
import java.io.ObjectOutputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import android.util.Log;

public class Account extends Activity {
    public String stdName;
    public Integer stdId;
    private Button clearBtn;
    private Button  signinBtn;
    private EditText nameEditText;
    private EditText idEditText;
    public StringBuilder urlStr; 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accountview);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        urlStr = new StringBuilder();
        nameEditText = (EditText)findViewById(R.id.studentName);
        idEditText = (EditText)findViewById(R.id.studentId);
        clearBtn = (Button)findViewById(R.id.clear);
        signinBtn = (Button)findViewById(R.id.signIn);
        clearBtn.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    nameEditText.setText("");
                    idEditText.setText("");
                }
            });
        signinBtn.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    /*
                    Intent simpleIntent = new Intent(); // this part could be broken
                    simpleIntent.setClass(Account.this, MainActivity.class);
                    Bundle simpleBundle = new Bundle();
                    simpleBundle.putString("name", nameEditText.getText().toString().trim());
                    simpleBundle.putString("id", idEditText.getText().toString().trim());
                    simpleIntent.putExtras(simpleBundle);
                    */
                    // try server call first
                    String [] words = nameEditText.getText().toString().trim().split(" ");
                    StringBuilder res = new StringBuilder(idEditText.getText().toString().trim());
                    res.append("?name=");
                    for (int i = 0; i < words.length - 1; i++) 
                        res.append(words[i]).append("_");
                    res.append(words[words.length - 1]);
                    urlStr.append("http://52.53.254.77:7777/signinstudent/").append(res);
                    //System.out.println("urlStr.toString(): " + urlStr.toString()); 

                    Thread thread = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                HttpURLConnection connection = null;
                                try {
                                    URL url = new URL(urlStr.toString()); 
                                    connection = (HttpURLConnection) url.openConnection();
                                    InputStreamReader read = new InputStreamReader(connection.getInputStream());
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
                                    if (connection != null) connection.disconnect();
                                }
                            }
                        });
                    thread.start();
                }
            });
    }
}
