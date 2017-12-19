package api.model;

/**
 * Created by yangshiyou on 2017/8/31.
 */

public class Banner {

//    {"icon":"\/Uploads\/Picture\/2017-08-30\/59a655b3831a8.jpg","url":"1"}

    private String images;

    private String url;

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
