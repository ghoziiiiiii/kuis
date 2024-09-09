package com.ghoziakbar.uts.kuis.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ghoziakbar.uts.kuis.model.Mhs;
import com.ghoziakbar.uts.kuis.R;

import java.util.List;

public class MhsAdapter extends BaseAdapter {

    Context context;
    List<Mhs> mhsList;

    public MhsAdapter(Context context, List<Mhs> mhsList) {
        this.context = context;
        this.mhsList = mhsList;
    }

    @Override
    public int getCount() {
        return mhsList.size();
    }

    @Override
    public Object getItem(int position) {
        return mhsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
        }

        TextView tvNama = convertView.findViewById(R.id.tv_nama);
        TextView tvNim = convertView.findViewById(R.id.tv_nim);
        TextView tvProdi = convertView.findViewById(R.id.tv_prodi);

        Mhs mhs = mhsList.get(position);
        tvNama.setText(mhs.nama);
        tvNim.setText(mhs.nim);
        tvProdi.setText(mhs.prodi);

        return convertView;
    }
}
