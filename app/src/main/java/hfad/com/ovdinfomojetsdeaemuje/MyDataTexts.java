package hfad.com.ovdinfomojetsdeaemuje;


import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

@RealmClass
public class MyDataTexts extends RealmObject{
    @PrimaryKey
    private int nid;

    private String title;
    private String date;
    private String link;
    private String text;
    private String image;

    public MyDataTexts() {
    }

    public MyDataTexts(int nid, String title, String date, String link, String text, String image) {
        this.nid = nid;
        this.title = title;
        this.date = date;
        this.link = link;
        this.text = text;
        this.image = image;
    }


    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getNid() {
        return nid;
    }


    public void setNid(int nid) {
        this.nid = nid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {

        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
