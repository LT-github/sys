package com.lt.sys.vo;

import java.util.ArrayList;
import java.util.List;
import com.lt.sys.entity.Info;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class InfoVo {

	//用户id
    private Long id;
	
    //邀请码
    private String registration;

    //经度
    private String longitude;

    //纬度
    private String latitude;
    
    //注册时间
    private Long createTime;

   

    public InfoVo() {}
	public InfoVo(Info info) {
		super();
		this.id = info.getId();
		this.registration = info.getRegistration();
		this.longitude = info.getLongitude();
		this.latitude = info.getLatitude();
		
		
	}
   
	public static List<InfoVo> toVo(List<Info> list){
		List<InfoVo> resp = new ArrayList<InfoVo>();
		for(Info item : list) {
			resp.add(new InfoVo(item));
		}
		return resp;
	}

    
}
