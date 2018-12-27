package com.cloudm.framework.db.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * @description: 多数据源扩展，
 * @author: Courser
 * @date: 2017/5/10
 * @version: V1.0
 */
 class DynamicDataSource extends AbstractRoutingDataSource {
    /**
     * 确定当前查找键。这通常会被实现来检查线程绑定事务上下文。允许任意键。返回的关键需求*存储查找键类型匹配
     * @return
     */
    @Override
    protected Object determineCurrentLookupKey() {
        return DataSourceContextHolder.getDsName();
    }
}