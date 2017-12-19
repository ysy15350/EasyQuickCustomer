package api.model;

/**
 * Created by yangshiyou on 2017/9/27.
 */

/**
 * 银行信息列表
 */
public class BankInfo {

    private int id;
    private String title;
    private String icon;

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
}
