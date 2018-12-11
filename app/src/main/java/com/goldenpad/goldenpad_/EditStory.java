package com.goldenpad.goldenpad_;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class EditStory extends AppCompatActivity implements View.OnClickListener{

    private EditText imageEdit;
    private EditText judul;
//    private EditText genreEdit;
    private EditText desc;
    private EditText storiyEdit;
    private EditText dateEdit;
    private EditText IDedit;

    private RadioGroup genreEdit;
    private RadioButton genreRom;
    private RadioButton genreHor;
    private RadioButton genreCom;
    private RadioButton genreThrill;

    private Button update_story_button;
    private Button delete_story_button;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_story);

        Intent intent = getIntent();

        id = intent.getStringExtra(konfigurasi.EMP_ID_STORY);

        imageEdit = (EditText) findViewById(R.id.imageEdit);
        judul = (EditText) findViewById(R.id.judul);
//        genreEdit = (EditText) findViewById(R.id.genreEdit);

        genreEdit = (RadioGroup) findViewById(R.id.genreEdit);
        genreRom = (RadioButton) findViewById(R.id.genreRom);
        genreHor = (RadioButton) findViewById(R.id.genreHor);
        genreCom = (RadioButton) findViewById(R.id.genreCom);
        genreThrill = (RadioButton) findViewById(R.id.genreThrill);

        desc = (EditText) findViewById(R.id.desc);
        storiyEdit = (EditText) findViewById(R.id.storiyEdit);
        dateEdit = (EditText) findViewById(R.id.dateEdit);
        IDedit = (EditText) findViewById(R.id.IDedit);
        IDedit.setVisibility(View.INVISIBLE);

        update_story_button = (Button) findViewById(R.id.update_story_button);
        delete_story_button = (Button) findViewById(R.id.delete_story_button);

        update_story_button.setOnClickListener(this);
        delete_story_button.setOnClickListener(this);

        IDedit.setText(id);

        getEmployee();
    }

    private void getEmployee(){
        class GetEmployee extends AsyncTask<Void,Void,String> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(EditStory.this,"Fetching...","Wait...",false,false);
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
                String s = rh.sendGetRequestParam(konfigurasi.URL_GET_EMP_EDIT_STORY,id);
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
            String img = c.getString(konfigurasi.TAG_IMAGE_MY_STORY);
            String ttl = c.getString(konfigurasi.TAG_TITLE_MY_STORY);

            String gnr = c.getString(konfigurasi.TAG_GENRE_MY_STORY);
            String des = c.getString(konfigurasi.TAG_DESC_MY_STORY);

            String stry = c.getString(konfigurasi.TAG_MY_STORY);
            String det = c.getString(konfigurasi.TAG_DATE_MY_STORY);

            imageEdit.setText(img);
            judul.setText(ttl);
            if (gnr.equals("Romance")) {
                genreRom.setChecked(true);
            } else if (gnr.equals("Horror")) {
                genreHor.setChecked(true);
            } else if (gnr.equals("Comedy")) {
                genreCom.setChecked(true);
            } else if (gnr.equals("Thriller")) {
                genreThrill.setChecked(true);
            }
            desc.setText(des);
            storiyEdit.setText(stry);
            dateEdit.setText(det);


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void updateEmployee(){
        final String imeg = imageEdit.getText().toString().trim();
        final String jud = judul.getText().toString().trim();
//        final String gen = genreEdit.getText().toString().trim();

        final int gen = genreEdit.getCheckedRadioButtonId();
        RadioButton radioButton = (RadioButton) findViewById(gen);
        final String genreFinal = radioButton.getText().toString().trim();

        final String dez = desc.getText().toString().trim();
        final String sto = storiyEdit.getText().toString().trim();
        final String dat = dateEdit.getText().toString().trim();

        class UpdateEmployee extends AsyncTask<Void,Void,String>{
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(EditStory.this,"Updating...","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(EditStory.this,s,Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... params) {
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put(konfigurasi.KEY_EMP_ID_STORY,id);
                hashMap.put(konfigurasi.KEY_EMP_IMAGE_STORY,imeg);
                hashMap.put(konfigurasi.KEY_EMP_TITLE_STORY,jud);
                hashMap.put(konfigurasi.KEY_EMP_GENRE_STORY,genreFinal);
                hashMap.put(konfigurasi.KEY_EMP_DESC_STORY,dez);
                hashMap.put(konfigurasi.KEY_EMP_STORY,sto);
                hashMap.put(konfigurasi.KEY_EMP_DATE_STORY,dat);

                RequestHandler rh = new RequestHandler();

                String s = rh.sendPostRequest(konfigurasi.URL_UPDATE_EMP_MY_STORY,hashMap);

                return s;
            }
        }

        UpdateEmployee ue = new UpdateEmployee();
        ue.execute();
        finish();
    }

    private void deleteEmployee(){
        class DeleteEmployee extends AsyncTask<Void,Void,String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(EditStory.this, "Updating...", "Wait...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(EditStory.this, s, Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequestParam(konfigurasi.URL_DELETE_EMP_MY_STORY, id);
                return s;
            }
        }

        DeleteEmployee de = new DeleteEmployee();
        de.execute();
    }

    private void confirmDeleteEmployee(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Are you sure want to delete story?");

        alertDialogBuilder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        deleteEmployee();
                        startActivity(new Intent(EditStory.this,storyList.class));
                    }
                });

        alertDialogBuilder.setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    @Override
    public void onClick(View v) {
        if(v == update_story_button) {
            if (judul.getText().toString().equals("")) {
                Toast.makeText(getApplicationContext(), "Please fill the title", Toast.LENGTH_SHORT).show();
            } else if (desc.getText().toString().equals("")) {
                Toast.makeText(getApplicationContext(), "Please fill the description story", Toast.LENGTH_SHORT).show();
            } else if (storiyEdit.getText().toString().equals("")) {
                Toast.makeText(getApplicationContext(), "Please fill the story", Toast.LENGTH_SHORT).show();
            }else {
                updateEmployee();
            }
        }

        if(v == delete_story_button){
            confirmDeleteEmployee();
        }
    }

    public void back(View view) {
        Intent i = new Intent(EditStory.this , storyList.class);
        startActivity(i);
    }
}
