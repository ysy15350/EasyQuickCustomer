package api.base.model;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import common.string.JsonConvertor;


public class Response {

    // 08-08 02:05:14.442: I/json(7336):
    // {"scenicCount":"5","orderCount":"7","code":200,"result":[{"ddCount":"1","noPays":null,"noEnter":null,"scenic":"\u7f19\u4e91\u5c71","ids":"5"},{"ddCount":"3","noPays":null,"noEnter":null,"scenic":"\u6b4c\u4e50\u5c71","ids":"4"},{"ddCount":"1","noPays":null,"noEnter":null,"scenic":"\u91d1\u4f5b\u5c71","ids":"1"},{"ddCount":"1","noPays":null,"noEnter":null,"scenic":"\u4ed9\u5973\u5c71","ids":"2"},{"ddCount":"1","noPays":null,"noEnter":null,"scenic":"\u56db\u9762\u5c71","ids":"3"}],"message":"\u4fe1\u606f\u83b7\u53d6\u6210\u529f"}

    private int code;

    private String token;

    private int uid;

    private String message;

    private Object result;

    // ------------图片上传-------------------
    private String pic;

    private int id;

    // ------------图片上传end -------------------

    // ------------充值订单-------------------


    private String order_number;
    private String title;
    private String description;
    private String sign_list;

    private float total_price;

    // ------------充值订单end-------------------

    // ------------捐款-------------------
    private String ewmimg;


    // ------------捐款end-------------------

    //    提现
    private float txprice;

    private float txrate;

    private float user_rate;

    //扫描二维码
    private int cid;
    private String fullname;


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrder_number() {
        return order_number;
    }

    public void setOrder_number(String order_number) {
        this.order_number = order_number;
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

    public String getSign_list() {
        return sign_list;
    }

    public void setSign_list(String sign_list) {
        this.sign_list = sign_list;
    }

    public float getTotal_price() {
        return total_price;
    }

    public void setTotal_price(float total_price) {
        this.total_price = total_price;
    }

    public String getEwmimg() {
        return ewmimg;
    }

    public void setEwmimg(String ewmimg) {
        this.ewmimg = ewmimg;
    }

    public float getTxprice() {
        return txprice;
    }

    public void setTxprice(float txprice) {
        this.txprice = txprice;
    }

    public float getTxrate() {
        return txrate;
    }

    public void setTxrate(float txrate) {
        this.txrate = txrate;
    }

    public float getUser_rate() {
        return user_rate;
    }

    public void setUser_rate(float user_rate) {
        this.user_rate = user_rate;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    /**
     * 获取指定数据类型
     *
     * @param type 数据类型
     * @return
     */
    public <T> T getData(Type type) {
        try {
            String dataJson = JsonConvertor.toJson(this.result);

            dataJson = dataJson.replace("\n", "");

            T t = JsonConvertor.fromJson(dataJson, type);

            return t;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }

    public String getResultJson() {

        try {
            return JsonConvertor.toJson(this.getResult());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    // public void setData(T data) {
    // this.data = data;
    // }

    private Type getType() {
        Type genType = this.getClass().getGenericSuperclass();

        Type type = null;

        try {
            Type[] types = ((ParameterizedType) genType).getActualTypeArguments();

            if (types.length > 0)
                type = types[0];

            return type;
        } catch (Exception exception) {

        }

        return null;
    }

}
