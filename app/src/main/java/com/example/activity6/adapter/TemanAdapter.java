package com.example.activity6.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.activity6.MainActivity;
import com.example.activity6.R;
import com.example.activity6.database.Teman;
import com.example.activity6.database.dbController;
import com.example.activity6.edit_teman;

import java.util.ArrayList;
import java.util.HashMap;

public class TemanAdapter extends RecyclerView .Adapter<TemanAdapter.TemanViewHolder> {
    private ArrayList<Teman> ListData;
    private Context control;

    public TemanAdapter(ArrayList<Teman> listData) {
        this.ListData = listData;
    }

    @Override
    public TemanAdapter.TemanViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInf = LayoutInflater.from(parent.getContext());
        View v = layoutInf.inflate(R.layout.activity_row_data_teman,parent,false);
        control = parent.getContext();
        return new TemanViewHolder(v);
    }

    @Override
    public void onBindViewHolder(TemanAdapter.TemanViewHolder holder, int position) {
        String nma,tlp,id;

        id = ListData.get(position).getId();
        nma = ListData.get(position).getNama();
        tlp = ListData.get(position).getTelpon();
        dbController db = new dbController(control);

        holder.namaTxtView.setTextColor(Color.BLUE);
        holder.namaTxtView.setTextSize(30);
        holder.namaTxtView.setText(nma);
        holder.telponTxtView.setText(tlp);

        holder.cardku.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                PopupMenu popupMenu = new PopupMenu(control, holder.cardku);
                popupMenu.inflate(R.menu.menu);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem Item) {
                        switch (Item.getItemId()){
                            case R.id.mnEdit:
                                Intent i = new Intent(control, edit_teman.class);
                                i.putExtra("id",id);
                                i.putExtra("nama",nma);
                                i.putExtra("telpon",tlp);
                                control.startActivity(i);
                                break;
                            case R.id.mnHapus:
                                HashMap<String,String> values = new HashMap<>();
                                values.put("id",id);
                                db.DeleteData(values);
                                Intent j = new Intent(control, MainActivity.class);
                                control.startActivity(j);
                                break;

                        }
                        return false;
                    }
                });
                popupMenu.show();
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {

        return (ListData != null)?ListData.size() : 0;
    }

    public static class TemanViewHolder extends RecyclerView.ViewHolder {

        private CardView cardku;
        private TextView namaTxtView,telponTxtView;
        public TemanViewHolder(View view) {
            super(view);
            cardku = (CardView) view.findViewById(R.id.recyclerView);
            namaTxtView = (TextView) view.findViewById(R.id.textNama);
            telponTxtView = (TextView) view.findViewById(R.id.textTelpon);
        }
    }

}