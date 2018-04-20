package com.example.dong.kiemsoatxequansu.ui.notebook;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dong.kiemsoatxequansu.R;
import com.example.dong.kiemsoatxequansu.data.model.Specification;
import com.example.dong.kiemsoatxequansu.utils.Commons;

import java.util.List;

/**
 * Created by DONG on 31-Oct-17.
 */

public class SpecificationAdapter extends RecyclerView.Adapter<SpecificationAdapter.RecyclerViewHoder> {
    private List<Specification> list; //danh sách chi tiết phụ tùng
    private Context context;

    SpecificationAdapter(List<Specification> list, Context context) {
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
    public void onBindViewHolder(RecyclerViewHoder holder, @SuppressLint("RecyclerView") final int position) {
        holder.tvId.setText(String.valueOf(list.get(position).getId()));
        holder.tvName.setText(Commons.decodeString(list.get(position).getName()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                try {
                    new Handler().postDelayed(new Runnable() {
                        @SuppressLint("SetTextI18n")
                        @Override
                        public void run() {
                            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                            assert inflater != null;
                            @SuppressLint("InflateParams") View dialogView = inflater.inflate(R.layout.dialog_specification, null);
                            // set the custom dialog components - text, image and button
                            TextView tvId = dialogView.findViewById(R.id.tvId);
                            TextView tvName = dialogView.findViewById(R.id.tvName);
                            LinearLayout llStyle = dialogView.findViewById(R.id.llStyle);
                            TextView tvStyle = dialogView.findViewById(R.id.tvStyle);
                            TextView tvUnit = dialogView.findViewById(R.id.tvUnit);
                            TextView tvQuantity = dialogView.findViewById(R.id.tvQuantity);
                            TextView tvPrice = dialogView.findViewById(R.id.tvPrice);

                            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
                            dialogBuilder.setView(dialogView);
                            dialogBuilder.setIcon(context.getDrawable(R.drawable.ic_infor));
                            dialogBuilder.setCancelable(false);
                            //set value
                            tvId.setText(String.valueOf(list.get(position).getId()));
                            tvName.setText(Commons.decodeString(list.get(position).getName()));//giải mã tên
                            tvUnit.setText(Commons.decodeString(list.get(position).getUnit())); //giải mã đơn vị tính
                            tvQuantity.setText(String.valueOf(list.get(position).getQuantity()));
                            tvPrice.setText(Commons.convertMoneytoVND(list.get(position).getPrice()) + " " + context.getString(R.string.vnd));

                            //Kiểm tra quy cách có null hay rỗng không? nếu có thì không hiển thị thông tin quy cách
                            if (list.get(position).getStyle() != null && !list.get(position).getStyle().isEmpty()) {
                                llStyle.setVisibility(View.VISIBLE);
                                tvStyle.setText(Commons.decodeString(list.get(position).getStyle()));//giải mã quy cách
                            }

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
                    }, 100);

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

    }

    /**
     * Change list
     * Created by Dong on 10-Apr-18
     *
     * @param datas list want change
     */
    public void swap(List<Specification> datas) {
        if (datas == null || datas.size() == 0) {
            Toast.makeText(context, "Không có phụ tùng", Toast.LENGTH_LONG).show();
        }
        if (list != null && list.size() > 0) {
            list.clear();

        }
        list.addAll(datas);
        notifyDataSetChanged();

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class RecyclerViewHoder extends RecyclerView.ViewHolder {
        public TextView tvId, tvName;

        RecyclerViewHoder(View itemView) {
            super(itemView);
            tvId = itemView.findViewById(R.id.tvId);
            tvName = itemView.findViewById(R.id.tvName);
        }
    }
}
