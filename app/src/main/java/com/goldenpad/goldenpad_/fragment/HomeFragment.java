package com.goldenpad.goldenpad_.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.goldenpad.goldenpad_.MainActivity;
import com.goldenpad.goldenpad_.MyCustomListAdapter;
import com.goldenpad.goldenpad_.R;
import com.goldenpad.goldenpad_.RequestHandler;
import com.goldenpad.goldenpad_.descStory;
import com.goldenpad.goldenpad_.konfigurasi;
import com.goldenpad.goldenpad_.model.model_story;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class HomeFragment extends Fragment {

    private ListView listView;
    private String JSON_STRING;

    @Override
    public void onResume() {
        super.onResume();
        getJSON();
    }

    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        listView = (ListView) v.findViewById(R.id.listView);
        getJSON();

        return v;
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

            MyCustomListAdapter adapter = new MyCustomListAdapter(getActivity(), list);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String storyID = list.get(position).getId();
                    Intent obj = new Intent(getActivity(), descStory.class);
                    obj.putExtra(konfigurasi.EMP_ID_STORY,storyID);
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
                loading = ProgressDialog.show(getActivity(),"Fetching...","Wait...",false,false);
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
                String s = rh.sendGetRequest(konfigurasi.URL_GET_ALL_STORY);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }



}
