package com.lt.sys.dto;


import com.lt.sys.jpa.support.DataQueryObjectPage;
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
		
	    private String registration;

	    //经度
	    private String longitude;

	    //纬度
	    private String latitude;
}
