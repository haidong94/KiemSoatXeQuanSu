package com.example.dong.kiemsoatxequansu.ui.notebook;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dong.kiemsoatxequansu.R;

import java.util.List;

/**
 * Created by DONG on 31-Oct-17.
 */

public class SoTayAdapter extends RecyclerView.Adapter<SoTayAdapter.RecyclerViewHoder> {
private List<String> list;
private Integer[] listImage;
private Context context;

public SoTayAdapter(List<String> list, Integer[] listImage, Context context){
        this.list=list;
        this.listImage=listImage;
        this.context=context;
        }


@Override
public RecyclerViewHoder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View itemView=inflater.inflate(R.layout.item_layout,parent,false);

        return new RecyclerViewHoder(itemView);
        }

@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
@Override
public void onBindViewHolder(RecyclerViewHoder holder, int position) {
        holder.txtTen.setText(list.get(position).toString());
        holder.imageItem.setImageResource(listImage[position]);

        }

@Override
public int getItemCount() {
        return list.size();
        }

public class RecyclerViewHoder extends RecyclerView.ViewHolder {
    public TextView txtTen;
    public ImageView imageItem;

    public RecyclerViewHoder(View itemView) {
        super(itemView);
        txtTen= (TextView) itemView.findViewById(R.id.txtTen);
        imageItem= (ImageView) itemView.findViewById(R.id.imageItem);
    }
}
}
