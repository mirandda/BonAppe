package com.projekti.bonappe.Adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.projekti.bonappe.ListItems.Reservation;
import com.projekti.bonappe.ListItems.RestaurantSearch;
import com.projekti.bonappe.R;

import java.util.ArrayList;

public class RestResultAdapter extends BaseAdapter {

    ArrayList<RestaurantSearch> items =new ArrayList<>();

    public RestResultAdapter(ArrayList<RestaurantSearch> items) {
        this.items = items;
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
        RestResultAdapter.ViewHolder holder;
        if (convertView==null){
            Log.d("blu3","NULL");
            convertView= LayoutInflater.from(parent.getContext()).inflate(R.layout.rest_search_item,parent,false);
            holder=new RestResultAdapter.ViewHolder(convertView);
            convertView.setTag(holder);
        }
        else {Log.d("blu3","NULL");
            holder=(RestResultAdapter.ViewHolder) convertView.getTag();

        }
        holder.emri.setText(items.get(position).getEmri());
        holder.lokacioni.setText(items.get(position).getLokacioni());
        holder.orari.setText(items.get(position).getOrari());
        //holder. imazhi
        //-- Glide.with().load(items.get(position).getRestImgLink()).into(holder.restImg);
        //this is where you can also put eventlisteners to the views
        return convertView;
    }

    class ViewHolder{
        TextView emri;
        TextView lokacioni;
        TextView orari;
        ImageView restImg;
        ViewHolder(View v){
            emri=(TextView) v.findViewById(R.id.textView5);
            lokacioni=(TextView) v.findViewById(R.id.textView6);
            orari=(TextView) v.findViewById(R.id.textView7);

            restImg=(ImageView) v.findViewById(R.id.imageView5);
        }
    }


}
