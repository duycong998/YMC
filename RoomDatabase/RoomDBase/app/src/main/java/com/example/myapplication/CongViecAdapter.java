package com.example.myapplication;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.myapplication.MainActivity;
import com.example.myapplication.db.CongViec;

import java.util.List;

public class CongViecAdapter extends BaseAdapter {

    private MainActivity context;    // Kế thừa hàm DialogSuaCV
    private int layout;
    private List<CongViec> congViecList;

    public CongViecAdapter(MainActivity context, int layout, List<CongViec> congViecList) {
        this.context = context;
        this.layout = layout;
        this.congViecList = congViecList;
    }

    @Override
    public int getCount() {
        return congViecList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private class ViewHolder{
        TextView txtTen;
        ImageView imgEdit,imgDelete;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder  ;

        if(convertView == null){
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout,null);

            holder.txtTen    = (TextView) convertView.findViewById(R.id.txtTenCV);
            holder.imgEdit   = (ImageView) convertView.findViewById(R.id.ImgEdit);
            holder.imgDelete = (ImageView) convertView.findViewById(R.id.ImgDelete);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        final CongViec congViec = congViecList.get(position);

        holder.txtTen.setText(congViec.getTenCV());

         //bắt sự kiện Edit
        holder.imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                   context.DiaLogSuaCV(congViec);
       //         context.DiaLogSuaCV(congViec.getTenCV(),congViec.getCvID()); // nhận 2 giá trị sau khi Edit
            }
        });

        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.diaLogXoaCV(congViec);
            }
        });
        return convertView;
    }
}
