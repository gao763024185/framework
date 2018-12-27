package com.cloudm.framework.common.web.dto.query;


import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * @description: 最基础的查询类
 * @author: Courser
 * @date: 2017/4/6
 * @version: V1.0
 */
@Data
public class QueryDTO implements Serializable {
    /**
     * ID
     */
    private Integer id ;
    /**
     * 创建人
     */
    private Integer creator;
    /**
     * 创建开始时间
     */
    private Date gmtCreateStart ;
    /**
     * 创建结束时间
     */
    private Date gmtCreateEnd ;
    /**
     * 修改开始时间
     */
    private Date gmtModifiedStart ;
    /**
     * 修改结束时间
     */
    private Date gmtModifiedEnd ;
    /**
     * 是否删除
     */
    private Integer yn  ;
    /**
     * 启动状态
     */
    private  Integer enabledState ;
}
