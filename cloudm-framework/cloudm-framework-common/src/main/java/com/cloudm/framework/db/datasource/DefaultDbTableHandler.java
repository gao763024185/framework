package com.cloudm.framework.db.datasource;

import com.cloudm.framework.common.util.JsonUtil;
import com.cloudm.framework.common.util.StringUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @description: 默认实现数据库实例和表的名字
 * @author: Courser
 * @date: 2017/5/23
 * @version: V1.0
 */
@Slf4j
public class DefaultDbTableHandler {


    /**
     * 最大设备分组
     */
    private Integer maxGroupNum=3500;

    /**
     * 单表可以放多少设备
     */
    private Integer tableDeviceSize=35;
    /**
     * 数据源前缀
     */
    private String  defaultDsNamePrefix="db";

    private String  defaultDataSource ="dbDefault";
    /**
     * 表名前缀
     */
    private String  defaultTableNamePrefix="single_data";

    public void setMaxGroupNum(Integer maxGroupNum) {
        this.maxGroupNum = maxGroupNum;
    }

    public void setTableDeviceSize(Integer tableDeviceSize) {
        this.tableDeviceSize = tableDeviceSize;
    }

    public void setDefaultDsNamePrefix(String defaultDsNamePrefix) {
        this.defaultDsNamePrefix = defaultDsNamePrefix;
    }

    public void setDefaultTableNamePrefix(String defaultTableNamePrefix) {
        this.defaultTableNamePrefix = defaultTableNamePrefix;
    }

    public void setDefaultDataSource(String defaultDataSource) {
        this.defaultDataSource = defaultDataSource;
    }

    /**
     * 通过设备序列号创建数据库和表名字
     * 数据库后缀采用4位流水号从0开始，支持1万个数据库的横向扩展
     * 数据库表采用7位流水号，前4位为数据库后缀，后三位为表的流水号，从1开始支持100个表
     * @param  dsNamePrefix datasource bean Name
     * @param  tableNamePrefix 数据表名前缀
     * @param serial 设备序列号
     *
     * @return
     */
    public DynamicDbTable getDbConfig(String dsNamePrefix,String tableNamePrefix,Integer serial){
        if(null == serial){//如果没有安装即序列号不存在则切换到主库
            return new DbTableDO(defaultDataSource,tableNamePrefix);
        }
        Integer dbNum = (serial -1 ) / maxGroupNum + 1;

        Integer tableNum = (serial - (dbNum - 1) * maxGroupNum - 1) / tableDeviceSize + 1 ;
        //数据库后缀
        String dbNameSuffix = StringUtil.genGildeNumber(dbNum,4);

        //数据库表后缀
        String tableNameSuffix= StringUtil.genGildeNumber(tableNum,3);
        DbTableDO config =  new DbTableDO();
        config.setDsName(dsNamePrefix.concat(dbNameSuffix));
        config.setTableName(StringUtil.isNotEmpty(tableNamePrefix)?tableNamePrefix.concat(tableNameSuffix):tableNameSuffix);


        return config ;

    }
    /**
     * 通过设备序列号创建数据库和表名字,采用默认数据库配置
     * 数据库后缀采用4位流水号从0开始，支持1万个数据库的横向扩展
     * 数据库表采用7位流水号，前4位为数据库后缀，后三位为表的流水号，从1开始支持100个表
     * @param  tableNamePrefix 数据表名前缀
     * @param serial 设备序列号
     *
     * @return
     */
    public DynamicDbTable getDbConfig(String tableNamePrefix,Integer serial){
        return getDbConfig(defaultDsNamePrefix,tableNamePrefix,serial) ;
    }

    /**
     * 这个API 不建议使用了 ，在2.0会被淘汰掉
     * 通过设备序列号创建数据库和表名字表明和采用默认配置
     * 数据库后缀采用4位流水号从0开始，支持1万个数据库的横向扩展
     * 数据库表采用7位流水号，前4位为数据库后缀，后三位为表的流水号，从1开始支持100个表
     * @param serial 设备序列号
     * @return
     */
    @Deprecated
    public DynamicDbTable getDbConfig(Integer serial){
        if (null==serial){
            return  new DbTableDO(defaultDsNamePrefix,defaultDataSource);
        }
        Integer dbNum = (serial -1 ) / maxGroupNum + 1;
        Integer tableNum = (serial - (dbNum - 1) * maxGroupNum - 1) / tableDeviceSize + 1 ;

        //数据库后缀
        String dbNameSuffix = StringUtil.genGildeNumber(dbNum,4);

        //数据库表后缀
        String tableNameSuffix= StringUtil.genGildeNumber(tableNum,3);
        DbTableDO config =  new DbTableDO();
        config.setDsName(this.defaultDsNamePrefix.concat(dbNameSuffix));
        config.setTableName(StringUtil.isNotEmpty(defaultTableNamePrefix)?defaultTableNamePrefix.concat(tableNameSuffix):tableNameSuffix);


        return config ;

    }
    @Data
  private  class  DbTableDO implements DynamicDbTable{
        /**
         * 数据源名称
         */
        private String dsName ;
        /**
         * 表名称
         */
        private String tableName;
        public  DbTableDO(){}
        public  DbTableDO(String dsName,String tableName){
            this.dsName=dsName ;
            this.tableName =  tableName ;
        }


    }

    public static void main(String[] args){
        DefaultDbTableHandler handler =  new DefaultDbTableHandler();
        DynamicDbTable con = handler.getDbConfig(3501);
//        DynamicDbTable con1 = handler.getDbConfig(ConstantEnum.DS_PREFIX.getKey(),ConstantEnum.TB_SINGLE_DATA_PREFIX.getKey(),3501);
        System.out.println(JsonUtil.toJson(con));
    }

}
