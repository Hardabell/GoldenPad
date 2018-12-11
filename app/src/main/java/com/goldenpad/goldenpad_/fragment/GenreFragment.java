package com.goldenpad.goldenpad_.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.goldenpad.goldenpad_.GenreComedy;
import com.goldenpad.goldenpad_.GenreHorror;
import com.goldenpad.goldenpad_.GenreRomance;
import com.goldenpad.goldenpad_.GenreThriller;
import com.goldenpad.goldenpad_.R;


public class GenreFragment extends Fragment implements View.OnClickListener {


//    private ImageButton genreRomance;
//    private ImageButton genreThriller;
//    private ImageButton genreHorror;
//    private ImageButton genreComedy;

    private Button genreRomance;
    private Button genreThriller;
    private Button genreHorror;
    private Button genreComedy;

    public GenreFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_genre, container, false);

//        genreRomance=(ImageButton) v.findViewById(R.id.Gromance);
//        genreThriller=(ImageButton) v.findViewById(R.id.Gthriller);
//        genreHorror=(ImageButton) v.findViewById(R.id.Ghorror);
//        genreComedy=(ImageButton) v.findViewById(R.id.Gcomedy);

        genreRomance=(Button) v.findViewById(R.id.Gromance);
        genreThriller=(Button) v.findViewById(R.id.Gthriller);
        genreHorror=(Button) v.findViewById(R.id.Ghorror);
        genreComedy=(Button) v.findViewById(R.id.Gcomedy);
        genreRomance.setOnClickListener(this);
        genreThriller.setOnClickListener(this);
        genreHorror.setOnClickListener(this);
        genreComedy.setOnClickListener(this);


        return v;
    }

    @Override
    public void onClick(View v) {
        if(v == genreRomance){
            Intent i = new Intent(getActivity() , GenreRomance.class);
            startActivity(i);
        } else if(v == genreThriller){
            Intent i = new Intent(getActivity() , GenreThriller.class);
            startActivity(i);
        } else if(v == genreHorror){
            Intent i = new Intent(getActivity() , GenreHorror.class);
            startActivity(i);
        } else if(v == genreComedy){
            Intent i = new Intent(getActivity() , GenreComedy.class);
            startActivity(i);
        }
    }
}