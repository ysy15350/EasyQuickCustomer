package api.model;

/**
 * Created by yangshiyou on 2017/9/19.
 */

/**
 * 收益明细信息
 */
public class ProfitInfo {
//    "result":[{"id":"6","uid":"33","rate":"9.9","price":"0.01","total_price":"0.01",
// "type":"1","c_type":"1","create_time":"1506742005","return_id":"0"}],"total_price":{"total_price":"0.01"}}

    private int id;
    private int uid;
    private float rate;
    private float price;
    private float total_price;
    private int type;
    private int c_type;
    private long create_time;
    private int return_id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
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

    public int getC_type() {
        return c_type;
    }

    public void setC_type(int c_type) {
        this.c_type = c_type;
    }

    public long getCreate_time() {
        return create_time;
    }

    public void setCreate_time(long create_time) {
        this.create_time = create_time;
    }

    public int getReturn_id() {
        return return_id;
    }

    public void setReturn_id(int return_id) {
        this.return_id = return_id;
    }
}
