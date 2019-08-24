package com.projekti.bonappe.ListItems;

import android.media.Image;

import com.bumptech.glide.Glide;

public class Reservation {
    private String ora, data,lloji,rasti;
    private String restImgLink;

    public Reservation( String ora, String data,String lloji,String rasti) {
this.rasti=rasti;
        this.ora = ora;
        this.data = data;
        this.lloji=lloji;
        this.restImgLink=restImgLink;
        }



    public String getOra() {
        return ora;
    }

    public String getData() {
        return data;
    }

    public String getLloji(){return lloji;}
    public String getRasti(){return rasti;}


}
