package api.model;

/**
 * Created by yangshiyou on 2017/9/19.
 */

public class OrderInfo {

//    {"id":"2855","pre_price":"1","total_price":"1","type":"3","create_time":"1506677173"}

    private int id;
    private float pre_price;
    private float total_price;
    private int type;//3：余额
    private String type_title;
    private long create_time;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getPre_price() {
        return pre_price;
    }

    public void setPre_price(float pre_price) {
        this.pre_price = pre_price;
    }

    public float getTotal_price() {
        return total_price;
    }

    public void setTotal_price(float total_price) {
        this.total_price = total_price;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getType_title() {
        return type_title;
    }

    public void setType_title(String type_title) {
        this.type_title = type_title;
    }

    public long getCreate_time() {
        return create_time;
    }

    public void setCreate_time(long create_time) {
        this.create_time = create_time;
    }
}
