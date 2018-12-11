package com.goldenpad.goldenpad_;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.goldenpad.goldenpad_.model.model_story;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class GenreThriller extends AppCompatActivity {

    private ListView listView;

    private String JSON_STRING;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genre_thriller);

        listView = (ListView) findViewById(R.id.listViewGenreT);
        getJSON();
    }

    @Override
    protected void onResume() {
        super.onResume();
        listView = (ListView) findViewById(R.id.listViewGenreT);
        getJSON();
    }


    private void showEmployee() {
        JSONObject jsonObject = null;
        final ArrayList<model_story> list = new
                ArrayList<>();
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(konfigurasi.TAG_JSON_ARRAY);

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

            MyCustomListAdapter adapter = new MyCustomListAdapter(GenreThriller.this, list);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String storyID = list.get(position).getId();
                    Intent obj = new Intent(getApplicationContext(), descStory.class);
                    obj.putExtra(konfigurasi.EMP_ID_STORY, storyID);
                    startActivity(obj);
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void getJSON() {
        class GetJSON extends AsyncTask<Void, Void, String> {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(GenreThriller.this, "Fetching...","Wait...", false, false);
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
                String s = rh.sendGetRequest(konfigurasi.URL_GET_EMP_GENRE_THRILLER);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }

    public void back(View view) {

        Intent i = new Intent(GenreThriller.this , MainActivity.class);
        startActivity(i);
    }
}