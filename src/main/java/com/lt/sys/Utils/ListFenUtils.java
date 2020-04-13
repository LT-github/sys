package com.lt.sys.Utils;

import java.util.List;


public class ListFenUtils<T> {

	public PagingList fen(PagingList page,List<T> showdata){

    	
    	   
    	    //刚开始的页面为第一页
    	    if (page.getPage() == null){
    	        page.setPage(1);
    	    } else {
    	        page.setPage(page.getPage());
    	    }
    	    //设置每页数据为十条
    	   
    	    page.setSize(10);
    	    //每页的开始数
    	    page.setStar((page.getPage() - 1) * page.getSize());
    	    //list的大小
    	    int count = showdata.size();
    	    //设置总页数
    	    page.setTotalPage(count % 10 == 0 ? count / 10 : count / 10 + 1);
    	    //对list进行截取
    	    page.setDataList(showdata.subList(page.getStar(),count-page.getStar()>page.getSize()?page.getStar()+page.getSize():count));
    	    //数据总条数
    	    page.setTotalNum(showdata.size());
    	    return page;
    	    
    }	
}
