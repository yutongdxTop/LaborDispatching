package com.yutongdxTop.LaborDispatching.util;
/**
 * @Description: 自定义响应数据结构
 * 				这个类是提供给门户，ios，安卓，微信商城用的
 * 				门户接受此类数据后需要使用本类的方法转换成对于的数据类型格式（类，或者list）
 * 				其他自行处理
 */
public class JSONResult {

    private Integer code;

    private String msg;

    private Integer count;

    private Object data;

    public static JSONResult build(String msg, Integer count, Object data) {
        return new JSONResult(msg, count, data);
    }

    public static JSONResult ok(Integer count, Object data) {
        return new JSONResult(count, data);
    }

    public  static JSONResult ok(String msg) { return  new JSONResult(msg); }

    public static JSONResult ok(Object data) {
        return new JSONResult(data);
    }

    public static JSONResult ok(Object data, String msg) {
        return new JSONResult(msg, data);
    }

    public static JSONResult ok() {
        return new JSONResult(null);
    }

    public static JSONResult errorMsg(String msg) {
        return new JSONResult(msg, null);
    }

    public JSONResult() {

    }

//    public static LeeJSONResult build(Integer status, String msg) {
//        return new LeeJSONResult(status, msg, null);
//    }

    public JSONResult(Integer count, Object data) {
        this.code = 0;
        this.msg = "OK";
        this.count = count;
        this.data = data;
    }

    public JSONResult(String msg, Integer count, Object data) {
        this.code = 0;
        this.msg = msg;
        this.count = count;
        this.data = data;
    }

    public JSONResult(String msg, Object data) {
        this.code = 0;
        this.msg = msg;
        this.count = 0;
        this.data = data;
    }

    public JSONResult(Object data) {
        this.code = 0;
        this.msg = "OK";
        this.count = 1;
        this.data = data;
    }

    public JSONResult(String msg) {
        this.code = 0;
        this.msg = msg;
        this.count = 1;
        this.data = null;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

}
