package com.goldenpad.goldenpad_;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

public class regist extends AppCompatActivity implements View.OnClickListener {

    private EditText email;
    private EditText username;
    private EditText password;

    private Button email_sign_in_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);

        email = (EditText) findViewById(R.id.email);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);

        email_sign_in_button = (Button) findViewById(R.id.email_sign_in_button);
        email_sign_in_button.setOnClickListener(this);
    }

    private void addStory() {

        final String mail = email.getText().toString().trim();
        final String usrnm = username.getText().toString().trim();
        final String pswrd = password.getText().toString().trim();

        class Addstory extends AsyncTask<Void, Void, String> {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(regist.this, "Fetching...", "Wait...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(regist.this, s, Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... v) {
                HashMap<String, String> params = new HashMap<>();
                params.put(konfigurasi.KEY_EMP_EMAIL, mail);
                params.put(konfigurasi.KEY_EMP_USERNAME, usrnm);
                params.put(konfigurasi.KEY_EMP_PASSWORD, pswrd);

                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(konfigurasi.URL_ADD_USER, params);
                return res;
            }
        }

        Addstory as = new Addstory();
        as.execute();
        finish();
        Intent i = new Intent(regist.this , login.class);
        startActivity(i);
    }

    @Override
    public void onClick(View v) {
        if (v == email_sign_in_button) {
            if (email.getText().toString().equals("")) {
                Toast.makeText(getApplicationContext(), "Please fill the value", Toast.LENGTH_SHORT).show();
            } else if (email.getText().toString().equals("")) {
                Toast.makeText(getApplicationContext(), "Please fill the value", Toast.LENGTH_SHORT).show();
            } else if (password.getText().toString().equals("")) {
                Toast.makeText(getApplicationContext(), "Please fill the value", Toast.LENGTH_SHORT).show();
            } else {
                addStory();
            }
        }
    }
}
