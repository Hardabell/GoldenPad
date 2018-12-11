package com.goldenpad.goldenpad_;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class descStory extends AppCompatActivity {

    private TextView titleS;
    private TextView authorS;
    private TextView genreS;
    private TextView descS;
    private TextView idStory;

    private String id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_desc_story);

        Intent intent = getIntent();

        id = intent.getStringExtra(konfigurasi.EMP_ID_STORY);

        titleS = (TextView) findViewById(R.id.titleS);
        authorS = (TextView) findViewById(R.id.authorS);
        genreS = (TextView) findViewById(R.id.genreS);
        descS = (TextView) findViewById(R.id.descS);
        idStory = (TextView) findViewById(R.id.idStory);
        idStory.setVisibility(View.INVISIBLE);

        idStory.setText(id);

        getEmployee();
    }

    private void getEmployee(){
        class GetEmployee extends AsyncTask<Void,Void,String> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(descStory.this,"Fetching...","Wait...",false,false);
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
                String s = rh.sendGetRequestParam(konfigurasi.URL_GET_EMP_DESCSTORY,id);
                return s;
            }
        }
        GetEmployee ge = new GetEmployee();
        ge.execute();
    }

    private void showEmployee(String json){
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray result = jsonObject.getJSONArray(konfigurasi.TAG_JSON_ARRAY);
            JSONObject c = result.getJSONObject(0);
            String ttl = c.getString(konfigurasi.TAG_TITLE_STORY);
            String atr = c.getString(konfigurasi.TAG_AUTHOR_STORY);
            String gnr = c.getString(konfigurasi.TAG_GENRE_STORY);
            String dsc = c.getString(konfigurasi.TAG_DESC_STORY);

            titleS.setText(ttl);
            authorS.setText(atr);
            genreS.setText(gnr);
            descS.setText(dsc);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public void back(View view) {

        Intent i = new Intent(descStory.this , MainActivity.class);
        startActivity(i);
    }


    public void read_button(View view) {
        Intent i = new Intent(descStory.this , ReadStory.class);
        i.putExtra(konfigurasi.EMP_ID_STORY,id);
        startActivity(i);
    }
}
