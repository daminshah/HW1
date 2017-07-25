package com.example.daminshah.newsapp.Model;

/**
 * Created by daminshah on 6/26/17.
 */

public class NewsItem {

    String author;
    String title;
    String description;
    String url;
    String urlToimage;
   String publish;



    public NewsItem(String author,String title, String description, String url, String urlToimage,String publish){
         this.author=author;
        this.title=title;
        this.description=description;
        this.url=url;
        this.publish=publish;

        this.urlToimage=urlToimage;
        this.publish=publish;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getUrlToimage() {
        return urlToimage;
    }

    public void setUrlToimage(String urlToimage) {
        this.urlToimage = urlToimage;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPublish() {
        return publish;
    }

    public void setPublish(String publish) {
        this.publish = publish;
    }

}
