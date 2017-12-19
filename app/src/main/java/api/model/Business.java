package api.model;

/**
 * Created by yangshiyou on 2017/9/19.
 */

/**
 * 店铺信息
 */
public class Business {

//    {"id":"1039","title":"","descrtext":"","distance":16189.3,"icon":"","discount":""}

    private int id;

    private String title;

    private String descrtext;

    private String distance;

    private String icon;

    private String discount;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescrtext() {
        return descrtext;
    }

    public void setDescrtext(String descrtext) {
        this.descrtext = descrtext;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }
}
