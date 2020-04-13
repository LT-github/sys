package com.lt.sys.dto;

import com.lt.sys.jpa.support.DataQueryObjectPage;
import com.lt.sys.jpa.support.QueryBetween;
import com.lt.sys.jpa.support.QueryField;
import com.lt.sys.jpa.support.QueryType;

import lombok.Getter;
import lombok.Setter;
@Setter
@Getter
public class InfoGetDto extends DataQueryObjectPage{

	    //用户id
	   @QueryField(type = QueryType.EQUAL , name="id")
	    private Long id;
	   @QueryField(type = QueryType.EQUAL , name="registration")
	    private String registration;
	   @QueryField(type = QueryType.BEWTEEN , name="pushEndTime")
		private QueryBetween<Long> createTime;

	    //经度
	    private String longitude;

	    //纬度
	    private String latitude;
	    
	    
	   
}
