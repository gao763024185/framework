package com.cloudm.framework.common.init;

import com.cloudm.framework.common.web.result.Result;

/**
 * @description: 业务需要在框架启动的调用方法
 * @author: Courser
 * @date: 2017/6/29
 * @version: V1.0
 */
public interface IbizInit<T> {

    /**
     * 初始化方法
     */
    void init();

    /**
     * 获取总条数
     * @return
     */
    Integer getCount();

    /**
     * 返回数据类型
     * @param <T>
     * @return
     */
    <T> Result<T> getData();


}
