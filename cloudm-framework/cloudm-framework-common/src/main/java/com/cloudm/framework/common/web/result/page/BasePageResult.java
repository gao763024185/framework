package com.cloudm.framework.common.web.result.page;

import lombok.Getter;
import lombok.Setter;

/**
 * @description: 我们自己定义返回对象要继承这个类，为了把分页数据和总条数一起带走
 * @author: Courser
 * @date: 2017/3/23
 * @version: V1.0
 */
@Setter
@Getter
public class BasePageResult {
    /**
     * 总条数
     */
    Integer totleSize = 0;
}