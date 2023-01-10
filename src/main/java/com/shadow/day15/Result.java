package com.shadow.day15;

/**
 * @ClassName Result
 * @Description TODO
 * @Author 18451
 * @Date 2023/1/10 20:32
 * @Version 1.0
 **/
public class Result {
    private int code;
    private String msg;
    private Object data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
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

    private Result(int code, Object data) {
        this.code = code;
        this.data = data;
    }

    public static Result ok() {
        return new Result(200, null);
    }

    public static Result ok(Object data) {
        return new Result(200, data);
    }

    @Override
    public String toString() {
        return "Result{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }

    public static Result error(String msg) {
        return new Result(500, "服务器内部错误");
    }
}
