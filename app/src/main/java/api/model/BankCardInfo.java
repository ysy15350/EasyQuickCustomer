package api.model;

/**
 * Created by yangshiyou on 2017/9/27.
 */

import java.io.Serializable;

/**
 * 银行信息列表
 */
public class BankCardInfo implements Serializable {

//    {"id":"69","title":"\u4e2d\u56fd\u5de5\u5546\u94f6\u884c","icon":"","number":"5685"}

    private int id;
    private String title;
    private String icon;
    private String number;

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

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
