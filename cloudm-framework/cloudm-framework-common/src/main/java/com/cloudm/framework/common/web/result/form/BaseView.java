package com.cloudm.framework.common.web.result.form;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @description: 用于接口返回供显示用
 * @author: Courser
 * @date: 2017/4/13
 * @version: V1.0
 */
@Setter
@Getter
public class BaseView implements Serializable {
    /**
     * 创建人名称
     */
    private String creatorName ;
    /**
     * 更新人名称
     */
    private String modifierName ;
    /**
     * 是否删除展示
     */
    private String ynName ;
    /**
     * 启动状态名称
     */
    private String enabledStateName ;
    /**
     * 主状态名称
     */
    private String masterStateName ;
}
