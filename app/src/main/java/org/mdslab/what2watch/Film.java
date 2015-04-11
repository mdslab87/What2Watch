package org.mdslab.what2watch;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mdslab on 10/04/15.
 */
public class Film {

    private int id;

    @SerializedName("idIMDB")
    private String idIMDB;

    @SerializedName("rating")
    private String rating;

    @SerializedName("title")
    private String title;

    @SerializedName("urlPoster")
    private String imageURL;

    @SerializedName("year")
    private String year;

    public Film(){

    }
    public int getId() { return id; }
    public void setId(int id) { this.id = id;}
    public String getIdIMDB(){
        return idIMDB;
    }
    public void setIdIMDB(String idIMDB){
        this.idIMDB = idIMDB;
    }
    public String getRating(){
        return rating;
    }
    public void setRating(String rating){
        this.rating = rating;
    }
    public String getTitle(){
        return title;
    }
    public void setTitle(String title){
        this.title = title;
    }
    public String getImageURL(){
        return imageURL;
    }
    public void setImageURL(String imageURL){
        this.imageURL = imageURL;
    }
    public String getYear(){
        return year;
    }
    public void setYear(String year){
        this.year = year;
    }
}




