package com.cloudm.framework.common.util;

import com.cloudm.framework.common.param.BasePageQueryParam;
import com.google.common.base.Function;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import org.springframework.data.domain.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @description: 分页辅助类
 * @author: Courser
 * @date: 2017/3/23
 * @version: V1.0
 */
public class PageUtil {
    /**
     * @param content  分页结果中content中实际包含的内容
     * @param pageable 分页条件，直接使用controller中的pageable，
     * @param total
     * @param <D>
     * @return
     */
    public static <D> Page<D> newPage(List<D> content, Pageable pageable, int total) {
        int pageNumber = pageable.getPageNumber();
        int pageSize = pageable.getPageSize();
        PageRequest pageRequest = new PageRequest((pageNumber < 1 ? 1 : pageNumber) - 1, pageSize < 1 ? 1 : pageSize, pageable.getSort());
        return new PageImpl<>(content, pageRequest, total);
    }

    /**
     * 返回一个空的page对象
     *
     * @param pageable
     * @param <D>
     * @return
     */
    public static <D> Page<D> newEmptyPage(Pageable pageable) {
        return newPage(new ArrayList<D>(), pageable, 0);
    }

    /**
     * @param param    需要设置分页条件的dao查询条件
     * @param pageable 分页条件，直接使用controller中的pageable
     */
    public static <T extends BasePageQueryParam> void fillParam(T param, Pageable pageable) {
        Iterator<String> it = null;
        if (null != pageable.getSort()) {
            // 设置排序参数
            it = Iterators.transform(pageable.getSort().iterator(), new Function<Sort.Order, String>() {
                @Override
                public String apply(Sort.Order order) {
                    return order.getProperty() + " " + order.getDirection().name();
                }
            });

        }
        // 设置分页参数
        int pageNumber = pageable.getPageNumber();
        int pageSize = pageable.getPageSize();
        PageRequest pageRequest = new PageRequest((pageNumber < 1 ? 1 : pageNumber) - 1, pageSize < 1 ? 1 : pageSize, pageable.getSort());
        param.setSorts(it == null ? null : Lists.newArrayList(it));
        param.setLimit(pageRequest.getPageSize());
        param.setOffset(pageRequest.getOffset());
    }
}
