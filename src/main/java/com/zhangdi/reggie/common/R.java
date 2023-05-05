package com.zhangdi.reggie.common;

import lombok.Data;
import java.util.HashMap;
import java.util.Map;

/**
 * 通用返回结果，服务端响应的数据最终都会封装成此对象
 * Common return result, all data responded from the server will be encapsulated into this object ultimately.
 */
@Data
public class R<T> {

    private Integer code; //编码：1成功，0和其它数字为失败 1 means successful, 0 and others mean fail

    private String msg; //错误信息 error message

    private T data; //数据 data

    private Map map = new HashMap(); //动态数据

    public static <T> R<T> success(T object) {
        R<T> r = new R<T>();
        r.data = object;
        r.code = 1;
        return r;
    }

    public static <T> R<T> error(String msg) {
        R r = new R();
        r.msg = msg;
        r.code = 0;
        return r;
    }

    public R<T> add(String key, Object value) {
        this.map.put(key, value);
        return this;
    }

}
