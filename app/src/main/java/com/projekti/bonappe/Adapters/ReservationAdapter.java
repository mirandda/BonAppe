package com.projekti.bonappe.Adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.projekti.bonappe.ListItems.Reservation;
import com.projekti.bonappe.R;

import java.util.ArrayList;

public class ReservationAdapter extends BaseAdapter {
    ArrayList<Reservation> items =new ArrayList<>();

    public ReservationAdapter(ArrayList<Reservation> people) {
        this.items = people;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        ViewHolder holder;
        if (convertView==null){
            Log.d("blu3","NULL");
            convertView= LayoutInflater.from(parent.getContext()).inflate(R.layout.reservation_listitem,parent,false);
            holder=new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        else {Log.d("blu3","NULL");
            holder=(ViewHolder) convertView.getTag();

        }
        if(position%2==0)
        {
            holder.nrPersonave.setText(items.get(position).getLloji();
        }
        holder.ora.setText("Ora: "+items.get(position).getOra());
        holder.data.setText("Data: "+items.get(position).getData());
        holder.rasti.setText("Rasti :"+items.get(position).getRasti());
       //-- Glide.with().load(items.get(position).getRestImgLink()).into(holder.restImg);
        //this is where you can also put eventlisteners to the views
        return convertView;
    }

    class ViewHolder{

        TextView nrPersonave;
        TextView ora,data,rasti;
        //ImageView restImg;
        ViewHolder(View v){

            nrPersonave =(TextView) v.findViewById(R.id.tv_lloji);
            ora=(TextView) v.findViewById(R.id.tv_ora);
            data=(TextView) v.findViewById(R.id.tv_data);
          //  restImg=(ImageView) v.findViewById(R.id.iv_reserv);
            rasti=(TextView) v.findViewById(R.id.reserv_1);
        }
    }
}




