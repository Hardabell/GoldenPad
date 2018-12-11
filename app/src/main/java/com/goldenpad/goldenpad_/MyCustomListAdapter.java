package com.goldenpad.goldenpad_;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.goldenpad.goldenpad_.model.model_story;

import java.util.ArrayList;
import java.util.List;

public class MyCustomListAdapter extends ArrayAdapter<model_story> {



    private Context context;
    private List<model_story> storyHomeList = new ArrayList<model_story>();
    public MyCustomListAdapter(Context context, ArrayList<model_story> list) {
        super(context, 0, list);
        this.context = context;
        storyHomeList = list;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null) {
            listItem = LayoutInflater.from(context).inflate(R.layout.my_costum_list_story_all,
                            parent, false);
        }

        model_story currentStory = storyHomeList.get(position);
        TextView txt_id = listItem.findViewById(R.id.id_story);
        TextView txt_title = listItem.findViewById(R.id.title_story);
        TextView txt_author = listItem.findViewById(R.id.author_story);
        TextView txt_genre = listItem.findViewById(R.id.genre_story);
        TextView txt_desc = listItem.findViewById(R.id.desc_story);
        txt_id.setText(currentStory.getId());
        txt_title.setText(currentStory.getTitle());
        txt_author.setText(currentStory.getAuthor());
        txt_genre.setText(currentStory.getGenre());
        txt_desc.setText(currentStory.getStory_desc());
        return listItem;
    }
}