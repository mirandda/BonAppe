package com.projekti.bonappe.ListItems;

public class Comment {
    private String emri,komenti,photo;

    public Comment(String emri,String komenti,String photo){
        this.emri=emri;
        this.komenti=komenti;
        this.photo=photo;
    }

    public String getEmri() {
        return emri;
    }

    public void setEmri(String emri) {
        this.emri = emri;
    }

    public String getKomenti() {
        return komenti;
    }

    public void setKomenti(String komenti) {
        this.komenti = komenti;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}


