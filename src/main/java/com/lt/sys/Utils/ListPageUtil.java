package com.lt.sys.Utils;

import java.util.Collections;
import java.util.List;

import lombok.Getter;
@Getter
public class ListPageUtil<T> {
	 /**
     * 原集合
     */
    private List<T> data;
 
    /**
     * 上一页
     */
    private int lastPage;
 
    /**
     * 当前页
     */
    private int page;
 
    /**
     * 下一页
     */
    private int nextPage;
 
    /**
     * 每页条数
     */
    private int size;
 
    /**
     * 总页数
     */
    private int totalPage;
 
    /**
     * 总数据条数
     */
    private int totalNum;
    
    private List<T> dataList;//每页显示的数据
 
    public ListPageUtil(List<T> data, Integer page, Integer size) {
        if (data == null || data.isEmpty()) {
            throw new IllegalArgumentException("data must be not empty!");
        }
        if (page == null){
	        page=1;
	    }
        if (size == null){
        	size=10;
	    } 
        this.data = data;
        this.size = size;
        this.page = page;
        this.totalNum = data.size();
        
        this.totalPage = (totalNum + size - 1) / size;
        this.lastPage = page - 1 > 1 ? page - 1 : 1;
        this.nextPage = page >= totalPage ? totalPage : page + 1;
       
    }
 
    /**
     * 得到分页后的数据
     *
     * @return 分页后结果
     */
    public PagingList getPagedList() {
    	PagingList pageList=new PagingList();
        int fromIndex = (page - 1) * size;
        if (fromIndex >= data.size()) {
            return pageList;//空对象
        }
        if (fromIndex < 0) {
            return pageList;//空对象
        }
        int toIndex = page * size;
        if (toIndex >= data.size()) {
            toIndex = data.size();
        }
        this.dataList=data.subList(fromIndex, toIndex);
        pageList.setDataList(dataList);
        pageList.setPage(page);
        pageList.setSize(size);
        pageList.setTotalNum(totalNum);
        pageList.setTotalPage(totalPage);
        
        return pageList;
    }
 
    

}
