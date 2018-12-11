package com.goldenpad.goldenpad_;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class profile extends AppCompatActivity implements View.OnClickListener {


    private EditText gambar_profil;
    private EditText nama_profil;
//    private EditText gender_profil;
    private EditText DOB_profil;
    private EditText email_profil;
    private EditText password_profil;
    private EditText iduserP;

    private RadioGroup gender_profil;
    private RadioButton genderMale;
    private RadioButton genderFemale;

    private Button update_profile_button;
    private String id;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Intent intent = getIntent();

        id = intent.getStringExtra(konfigurasi.EMP_AUTH_STORY);

        gambar_profil = (EditText) findViewById(R.id.gambar_profil);
        nama_profil = (EditText) findViewById(R.id.nama_profil);
//        gender_profil = (EditText) findViewById(R.id.gender_profil);

        gender_profil = (RadioGroup) findViewById(R.id.gender_profil);
        genderMale = (RadioButton) findViewById(R.id.male);
        genderFemale = (RadioButton) findViewById(R.id.female);

        DOB_profil = (EditText) findViewById(R.id.DOB_profil);
        email_profil = (EditText) findViewById(R.id.email_profil);
        password_profil = (EditText) findViewById(R.id.password_profil);
        iduserP = (EditText) findViewById(R.id.iduserP);
        iduserP.setVisibility(View.INVISIBLE);

        update_profile_button = (Button) findViewById(R.id.update_profile_button);
        update_profile_button.setOnClickListener(this);

        iduserP.setText(id);

        getEmployee();

    }

    private void getEmployee() {
        class GetEmployee extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(profile.this, "Fetching...", "Wait...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                showEmployee(s);
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequestParam(konfigurasi.URL_GET_EMP_EDIT_PROFILE, id);
                return s;
            }
        }
        GetEmployee ge = new GetEmployee();
        ge.execute();
    }


    private void showEmployee(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray result = jsonObject.getJSONArray(konfigurasi.TAG_JSON_ARRAY);
            JSONObject c = result.getJSONObject(0);
            String img = c.getString(konfigurasi.TAG_IMAGE);
            String nem = c.getString(konfigurasi.TAG_NAME);
            String gen = c.getString(konfigurasi.TAG_GENDER);
            String db = c.getString(konfigurasi.TAG_DOB);
            String ime = c.getString(konfigurasi.TAG_EMAIL);
            String pss = c.getString(konfigurasi.TAG_PASSWORD);

            gambar_profil.setText(img);
            nama_profil.setText(nem);

//            gender_profil.setText(gen);

            if (gen.equals("Male")) {
                genderMale.setChecked(true);
            } else if (gen.equals("Female")) {
                genderFemale.setChecked(true);
            }

            DOB_profil.setText(db);
            email_profil.setText(ime);
            password_profil.setText(pss);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void updateEmployee() {
        final String imeg = gambar_profil.getText().toString().trim();
        final String nem = nama_profil.getText().toString().trim();

//        final String gen = gender_profil.getText().toString().trim();

        final int gen = gender_profil.getCheckedRadioButtonId();
        RadioButton radioButton = (RadioButton) findViewById(gen);
        final String genderFinal = radioButton.getText().toString().trim();

        final String db = DOB_profil.getText().toString().trim();
        final String ime = email_profil.getText().toString().trim();
        final String pas = password_profil.getText().toString().trim();

        class UpdateEmployee extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(profile.this, "Updating...", "Wait...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(profile.this, s, Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... params) {
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put(konfigurasi.KEY_EMP_ID, id);
                hashMap.put(konfigurasi.KEY_EMP_IMAGE, imeg);
                hashMap.put(konfigurasi.KEY_EMP_NAME, nem);
                hashMap.put(konfigurasi.KEY_EMP_GENDER, genderFinal);
                hashMap.put(konfigurasi.KEY_EMP_DOB, db);
                hashMap.put(konfigurasi.KEY_EMP_EMAIL, ime);
                hashMap.put(konfigurasi.KEY_EMP_PASSWORD, pas);

                RequestHandler rh = new RequestHandler();

                String s = rh.sendPostRequest(konfigurasi.URL_UPDATE_EMP_MY_PROFILE, hashMap);

                return s;
            }
        }

        UpdateEmployee ue = new UpdateEmployee();
        ue.execute();
        finish();
    }

    @Override
    public void onClick(View v) {
        if (v == update_profile_button) {
            if (email_profil.getText().toString().equals("")) {
                Toast.makeText(getApplicationContext(), "Please fill the email", Toast.LENGTH_SHORT).show();
            } else if (password_profil.getText().toString().equals("")) {
                Toast.makeText(getApplicationContext(), "Please fill the password", Toast.LENGTH_SHORT).show();
            } else {
                updateEmployee();
            }
        }
    }

    public void back(View view) {
        Intent i = new Intent(profile.this , MainActivity.class);
        startActivity(i);
    }
}
