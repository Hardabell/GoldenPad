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

import java.util.Calendar;
import java.util.HashMap;

public class AddStory extends AppCompatActivity implements View.OnClickListener  {

    SessionManager sessionManager;

    private EditText gambar;
    private EditText judul;
//    private EditText genre;
    private RadioGroup genre;
//    private RadioButton genreRomm;
//    private RadioButton genreHor;
//    private RadioButton genreCom;
//    private RadioButton genreThrill;
    private EditText desc;
    private EditText story;
    private EditText IDuser;
    private EditText author;
    private EditText dateStory;

    private Button add_story_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_story);

        gambar = (EditText) findViewById(R.id.gambar);
        judul = (EditText) findViewById(R.id.judul);
//        genre = (EditText) findViewById(R.id.genre);
        genre = (RadioGroup) findViewById(R.id.genre);
//        genreRom = (RadioButton) findViewById(R.id.genreRom);
//        genreHor = (RadioButton) findViewById(R.id.genreHor);
//        genreCom = (RadioButton) findViewById(R.id.genreCom);
//        genreThrill = (RadioButton) findViewById(R.id.genreThrill);
        desc = (EditText) findViewById(R.id.desc);
        story = (EditText) findViewById(R.id.story);
        IDuser = (EditText) findViewById(R.id.IDuser);
        IDuser.setVisibility(View.INVISIBLE);
        author = (EditText) findViewById(R.id.author);
        author.setVisibility(View.INVISIBLE);
        dateStory = (EditText) findViewById(R.id.dateStory);
        String bulan = getCurrentDateAll();
        dateStory.setText(bulan);

        add_story_button = (Button) findViewById(R.id.add_story_button);
        add_story_button.setOnClickListener(this);

        sessionManager = new SessionManager(getApplicationContext());
        final HashMap<String, String> user = sessionManager.getUserDetails();
        final String name = user.get(SessionManager.kunci_email);
        IDuser.setText(Html.fromHtml("<b>" + name + "</b>"));

        sessionManager = new SessionManager(getApplicationContext());
        final HashMap<String, String> aut = sessionManager.getUserDetails();
        final String penulis = aut.get(SessionManager.kunci_email);
        author.setText(Html.fromHtml("<b>" + penulis + "</b>"));
    }

    private void addStory() {

        final String gmbr = gambar.getText().toString().trim();
        final String jdl = judul.getText().toString().trim();
//        final String gnr = genre.getText().toString().trim();
        final int gnr = genre.getCheckedRadioButtonId();
        RadioButton radioButton = (RadioButton) findViewById(gnr);
        final String genreFinal = radioButton.getText().toString().trim();
        final String des = desc.getText().toString().trim();
        final String stry = story.getText().toString().trim();
        final String usr = IDuser.getText().toString().trim();
        final String auth = author.getText().toString().trim();
        final String dat = dateStory.getText().toString().trim();




        class Addstory extends AsyncTask<Void, Void, String> {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(AddStory.this, "Fetching...","Wait...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(AddStory.this, s, Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... v) {
                HashMap<String, String> params = new HashMap<>();
                params.put(konfigurasi.KEY_EMP_IMAGE_STORY, gmbr);
                params.put(konfigurasi.KEY_EMP_TITLE_STORY, jdl);
                params.put(konfigurasi.KEY_EMP_DESC_STORY, des);
                params.put(konfigurasi.KEY_EMP_GENRE_STORY, genreFinal);
                params.put(konfigurasi.KEY_EMP_STORY,stry);
                params.put(konfigurasi.KEY_EMP_AUTHOR_STORY,auth);
                params.put(konfigurasi.KEY_EMP_DATE_STORY,dat);
                params.put(konfigurasi.KEY_EMP_ID_USER,usr);

                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(konfigurasi.URL_ADD_STORY, params);
                return res;

            }
        }

        Addstory as = new Addstory();
        as.execute();

    }

    public String getCurrentDateAll(){
        final Calendar c = Calendar.getInstance();
        int day, month, year;
        day = c.get(Calendar.DATE);
        month = c.get(Calendar.MONTH);
        year = c.get(Calendar.YEAR);
        return ""+year+"-"+month+"-"+day+"";
    }

    @Override
    public void onClick(View v) {
        final int gnrV = genre.getCheckedRadioButtonId();
        RadioButton radioButton = (RadioButton) findViewById(gnrV);
        final String genreFinalV = radioButton.getText().toString().trim();

        if(v == add_story_button) {
            if (judul.getText().toString().equals("")) {
                Toast.makeText(getApplicationContext(), "Please fill the title", Toast.LENGTH_SHORT).show();
            }
//            else if (genreFinal.equals(null)) {
//                Toast.makeText(getApplicationContext(), "Please choose the genre", Toast.LENGTH_SHORT).show();
//            }
        else if (desc.getText().toString().equals("")) {
                Toast.makeText(getApplicationContext(), "Please fill the description story", Toast.LENGTH_SHORT).show();
            } else if (story.getText().toString().equals("")) {
                Toast.makeText(getApplicationContext(), "Please fill the story", Toast.LENGTH_SHORT).show();
            }  else {
                addStory();
                finish();
            }
        }
    }

    public void back(View view) {

        Intent i = new Intent(AddStory.this , MainActivity.class);
        startActivity(i);
    }
}

