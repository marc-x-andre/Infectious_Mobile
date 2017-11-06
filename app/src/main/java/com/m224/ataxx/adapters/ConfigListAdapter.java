package com.m224.ataxx.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.m224.ataxx.R;

import java.util.List;

/**
 * Created by Ravi Tamada on 18/05/16.
 */
public class ConfigListAdapter extends RecyclerView.Adapter<ConfigListAdapter.Card> {

    private List<String> titleList;
    private List<Integer> imageResList;
    private Context context;

    public class Card extends RecyclerView.ViewHolder {
        public TextView title;
        public ImageView preview;

        public Card(View view) {
            super(view);
            title = view.findViewById(R.id.title);
            preview = view.findViewById(R.id.preview);
        }
    }

    public ConfigListAdapter(Context context, List<String> titleList, List<Integer> imageResList) {;
        this.titleList = titleList;
        this.imageResList = imageResList;
        this.context = context;
    }

    @Override
    public Card onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_config_info, parent, false);

        return new Card(itemView);
    }

    @Override
    public void onBindViewHolder(final Card holder, final int position) {
        holder.title.setText(titleList.get(position));
        holder.preview.setImageResource(imageResList.get(position));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "At : "+position, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return titleList.size();
    }
}