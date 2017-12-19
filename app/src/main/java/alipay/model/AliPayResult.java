package alipay.model;

import common.string.JsonConvertor;

/**
 * Created by yangshiyou on 2017/9/29.
 */

public class AliPayResult {

    private int resultStatus;

    private Object result;


    public int getResultStatus() {
        return resultStatus;
    }

    public void setResultStatus(int resultStatus) {
        this.resultStatus = resultStatus;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public String getResultJson() {
        if (result != null)
            return JsonConvertor.toJson(result);
        return "";
    }


}
