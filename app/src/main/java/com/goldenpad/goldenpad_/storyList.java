package com.goldenpad.goldenpad_;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;


import com.goldenpad.goldenpad_.model.model_story;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class storyList extends AppCompatActivity implements View.OnClickListener{

    SessionManager sessionManager;

    private Button btn_tampil;
    private EditText authormystory;
    private ListView listView1;
    private String JSON_STRING;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story_list);

        listView1 = (ListView) findViewById(R.id.story_list);

        authormystory = (EditText) findViewById(R.id.authormystory);
        authormystory.setVisibility(View.INVISIBLE);
        sessionManager = new SessionManager(getApplicationContext());
        HashMap<String, String> auth = sessionManager.getUserDetails();
        String author = auth.get(SessionManager.kunci_email);
        authormystory.setText(Html.fromHtml("<b>" + author + "</b>"));

//        btn_tampil = (Button) findViewById(R.id.btn_tampil);
//        btn_tampil.setOnClickListener(this);

        FloatingActionButton addStory = (FloatingActionButton) findViewById(R.id.addS);
        addStory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(storyList.this , AddStory.class);
                startActivity(i);
            }

        });

        FloatingActionButton reff = (FloatingActionButton) findViewById(R.id.btn_tampil);
        reff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addStory();
            }
        });

        getJSON();
    }

    @Override
    public void onResume() {
        super.onResume();
        addStory();
    }

    private void addStory() {
        final String auth = authormystory.getText().toString().trim();

        class Addstory extends AsyncTask<Void, Void, String> {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(storyList.this, "Fetching...","Wait...", false, false);
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
                params.put(konfigurasi.KEY_EMP_AUTHOR_STORY,auth);

                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(konfigurasi.URL_GET_ALL_MY_STORY, params);
                return res;
            }
        }

        Addstory as = new Addstory();
        as.execute();
    }

    @Override
    public void onClick(View v) {
        if(v == btn_tampil){
            addStory();
        }
    }

    private void showEmployee(){
        JSONObject jsonObject = null;
        final ArrayList<model_story> list = new
                ArrayList<>();
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(konfigurasi.TAG_JSON_ARRAY_STORY);

            for (int i = 0; i < result.length(); i++) {
                try {
                    JSONObject jo = result.getJSONObject(i);
                    String id = jo.getString(konfigurasi.TAG_ID_STORY);
                    String title = jo.getString(konfigurasi.TAG_TITLE_STORY);
                    String author = jo.getString(konfigurasi.TAG_AUTHOR_STORY);
                    String genre = jo.getString(konfigurasi.TAG_GENRE_STORY);
                    String story_desc = jo.getString(konfigurasi.TAG_DESC_STORY);
                    list.add(new model_story(id, title, author, genre, story_desc));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            MyCustomListAdapter adapter = new MyCustomListAdapter(storyList.this, list);
            listView1.setAdapter(adapter);
            listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String storyID = list.get(position).getId();
                    Intent obj = new Intent(storyList.this, EditStory.class);
                    obj.putExtra(konfigurasi.EMP_AUTH_STORY,storyID);
                    startActivity(obj);
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void getJSON(){
        class GetJSON extends AsyncTask<Void,Void,String> {

            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(storyList.this,"Fetching...","Wait...",false,false);
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
                String s = rh.sendGetRequest(konfigurasi.URL_GET_ALL_MY_STORY);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }

//    @Override
//    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        Intent intent = new Intent(this, EditStory.class);
//        HashMap<String,String> map =(HashMap)parent.getItemAtPosition(position);
//        String empId = map.get(konfigurasi.TAG_ID_STORY).toString();
//        intent.putExtra(konfigurasi.EMP_ID_STORY,empId);
//        startActivity(intent);
//    }

    public void back(View view) {
        Intent i = new Intent(storyList.this , MainActivity.class);
        startActivity(i);
    }

}
