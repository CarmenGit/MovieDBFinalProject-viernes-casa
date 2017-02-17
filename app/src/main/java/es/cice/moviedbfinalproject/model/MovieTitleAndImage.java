package es.cice.moviedbfinalproject.model;

/**
 * Created by Carmen on 16/02/2017.
 */

public class MovieTitleAndImage {
    private String title;
    private String nameImage;

    public MovieTitleAndImage(String title, String nameImage) {
        this.title = title;
        this.nameImage = nameImage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNameImage() {
        return nameImage;
    }

    public void setNameImage(String nameImage) {
        this.nameImage = nameImage;
    }
}
