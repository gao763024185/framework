package com.cloudm.framework.common.web.result.page;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * @description: 分页请求参数
 * @author: Courser
 * @date: 2017/3/23
 * @version: V1.0
 */
@Setter
@Getter
public class PageableRequest implements Serializable{
    private static final long serialVersionUID = 4887208329743771100L;
    /**
     *  页码
     */
    private int pageNumber;
    /**
     * 每页的大小
     */
    private int pageSize;
    private List<Sorter> sorterList = Collections.emptyList();

    /**
     * 偏移量
     * @return
     */
    public int getOffset() {
        return pageNumber * pageSize;
    }
}
