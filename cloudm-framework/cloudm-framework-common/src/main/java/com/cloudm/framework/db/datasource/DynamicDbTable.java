package com.cloudm.framework.db.datasource;

import java.io.Serializable;

/**
 * @description: 数据库上下文持有者（工具类）
 * @author: Courser
 * @date: 2017/5/10
 * @version: V1.0
 */
public interface DynamicDbTable extends Serializable {
    /**
     *  获取数据源
     * @return
     */
    String getDsName();

    /**
     * 获取数据表名字
     * @return
     */
    String getTableName() ;

}
