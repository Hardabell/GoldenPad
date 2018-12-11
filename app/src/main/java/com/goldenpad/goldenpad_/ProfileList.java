package com.goldenpad.goldenpad_;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ProfileList extends AppCompatActivity implements  ListView.OnItemClickListener {

    SessionManager sessionManager;

    private Button btn_tampilP;
    private EditText usernameP;
    private ListView profile_list;
    private String JSON_STRING;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_list);

        usernameP = (EditText) findViewById(R.id.usernameP);
        usernameP.setVisibility(View.INVISIBLE);
        sessionManager = new SessionManager(getApplicationContext());
        HashMap<String, String> auth = sessionManager.getUserDetails();
        String author = auth.get(SessionManager.kunci_email);
        usernameP.setText(Html.fromHtml("<b>" + author + "</b>"));

        profile_list = (ListView) findViewById(R.id.profile_list);
        profile_list.setOnItemClickListener(this);
        getJSON();
    }



    private void addStory() {
        final String auth = usernameP.getText().toString().trim();

        class Addstory extends AsyncTask<Void, Void, String> {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(ProfileList.this, "Fetching...","Wait...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                JSON_STRING = s;
                showEmployee();
            }

            @Override
            protected String doInBackground(Void... v) {
                HashMap<String, String> params = new HashMap<>();
                params.put(konfigurasi.KEY_EMP_USERNAME,auth);

                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(konfigurasi.URL_GET_ALL_PROFILE, params);
                return res;
            }
        }

        Addstory as = new Addstory();
        as.execute();
    }

    @Override
    public void onResume() {
        super.onResume();
        addStory();
    }

    private void showEmployee(){
        JSONObject jsonObject = null;
        ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String, String>>();
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(konfigurasi.TAG_JSON_ARRAY_STORY);

            for(int i = 0; i<result.length(); i++){
                JSONObject jo = result.getJSONObject(i);
                String ids = jo.getString(konfigurasi.TAG_ID);
                String nem = jo.getString(konfigurasi.TAG_NAME);


                HashMap<String,String> employees = new HashMap<>();
                employees.put(konfigurasi.TAG_ID,ids);
                employees.put(konfigurasi.TAG_NAME,nem);


                list.add(employees);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        ListAdapter adapter = new SimpleAdapter(
                ProfileList.this, list, R.layout.profile_list,
                new String[]{konfigurasi.TAG_ID,konfigurasi.TAG_NAME},

                new int[]{R.id.idP, R.id.nameP});

        profile_list.setAdapter(adapter);
    }

    private void getJSON(){
        class GetJSON extends AsyncTask<Void,Void,String> {

            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(ProfileList.this,"Fetching...","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                JSON_STRING = s;
                showEmployee();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest(konfigurasi.URL_GET_ALL_PROFILE);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, profile.class);
        HashMap<String,String> map = (HashMap)parent.getItemAtPosition(position);
        String empId = map.get(konfigurasi.TAG_ID).toString();
        intent.putExtra(konfigurasi.EMP_AUTH_STORY,empId);
        startActivity(intent);
    }


    public void back(View view) {

        Intent i = new Intent(ProfileList.this , MainActivity.class);
        startActivity(i);
    }

}
