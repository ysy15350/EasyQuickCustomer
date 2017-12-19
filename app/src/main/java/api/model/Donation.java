package api.model;

/**
 * Created by yangshiyou on 2017/11/1.
 */

public class Donation {


    /**
     * id : 3
     * name : 公益广告
     * icon : 88
     * typeid : 6
     * sort : 1
     * url : 1
     * remark :
     * status : 1
     * images : /Uploads/Picture/2017-11-01/59f93ea526203.jpg
     */

    private String id;
    private String name;
    private String icon;
    private String typeid;
    private String sort;
    private String url;
    private String remark;
    private String status;
    private String images;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getTypeid() {
        return typeid;
    }

    public void setTypeid(String typeid) {
        this.typeid = typeid;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }
}
