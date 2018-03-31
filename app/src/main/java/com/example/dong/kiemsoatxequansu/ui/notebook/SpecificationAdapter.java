package com.example.dong.kiemsoatxequansu.ui.notebook;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dong.kiemsoatxequansu.R;
import com.example.dong.kiemsoatxequansu.data.model.Specification;

import java.util.List;

/**
 * Created by DONG on 31-Oct-17.
 */

public class SpecificationAdapter extends RecyclerView.Adapter<SpecificationAdapter.RecyclerViewHoder> {
    private List<Specification> list;
    private Integer[] listImage;
    private Context context;

    public SpecificationAdapter(List<Specification> list, Context context) {
        this.list = list;
        this.context = context;
    }


    @Override
    public RecyclerViewHoder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_specification, parent, false);

        return new RecyclerViewHoder(itemView);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(RecyclerViewHoder holder, final int position) {
        holder.tvId.setText(String.valueOf(list.get(position).getId()));
        holder.tvName.setText(list.get(position).getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
               try {
                   new Handler().postDelayed(new Runnable() {
                       @Override
                       public void run() {
                           LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
                           View dialogView = inflater.inflate(R.layout.dialog_specification, null);
                           // set the custom dialog components - text, image and button
                           TextView tvId = dialogView.findViewById(R.id.tvId);
                           TextView tvName = dialogView.findViewById(R.id.tvName);
                           TextView tvUnit = dialogView.findViewById(R.id.tvUnit);
                           TextView tvQuantity = dialogView.findViewById(R.id.tvQuantity);
                           TextView tvPrice = dialogView.findViewById(R.id.tvPrice);

                           AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
                           dialogBuilder.setView(dialogView);
                           dialogBuilder.setIcon(context.getDrawable(R.drawable.ic_infor));
                           dialogBuilder.setCancelable(false);
                           //set value
                           tvId.setText(String.valueOf(list.get(position).getId()));
                           tvName.setText(list.get(position).getName());
                           tvUnit.setText(list.get(position).getUnit());
                           tvQuantity.setText(String.valueOf(list.get(position).getQuantity()));
                           tvPrice.setText(String.valueOf(list.get(position).getPrice()));

                           final AlertDialog dialog = dialogBuilder.create();
                           dialog.show();

                           TextView tvClose = dialogView.findViewById(R.id.tvClose);
                           tvClose.setOnClickListener(new View.OnClickListener() {
                               @Override
                               public void onClick(View v) {
                                   dialog.dismiss();
                               }
                           });
                       }
                   },100);

               } catch (Exception e) {
                  e.printStackTrace();
               }

            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class RecyclerViewHoder extends RecyclerView.ViewHolder {
        public TextView tvId, tvName;

        public RecyclerViewHoder(View itemView) {
            super(itemView);
            tvId = (TextView) itemView.findViewById(R.id.tvId);
            tvName = (TextView) itemView.findViewById(R.id.tvName);
        }
    }
}
