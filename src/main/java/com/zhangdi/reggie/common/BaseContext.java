package com.zhangdi.reggie.common;

/**
 * 基于ThreadLocal封装工具类，用于用户保存和获取当前登录用户ID
 */
public class BaseContext {
    private static ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    /**
     * 将获得的id值设置到线程中去
     * @param id
     */
    public static void setCurrentId(Long id){
        threadLocal.set(id);
    }

    /**
     * 获得线程中存储的id
     * @return
     */
    public static Long getCurrentId(){
        return threadLocal.get();
    }
}
