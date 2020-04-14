package com.lt.sys.vo;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.lt.sys.entity.Contacts;
import com.lt.sys.entity.Info;
import com.lt.sys.entity.Note;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class InfoVo {

	//用户id
    private Long id;	
    //邀请码
    private String registration;     
    //注册时间
    private Long createTime;
    //设备id
    private String deviceId;
    //地址
    private String address;
    //邀请码
    private String referralCode;
    
   
    

    public InfoVo() {}
	public InfoVo(Info info) {
		super();
		this.id = info.getId();
		if(info.getRegistration()==null) {this.registration="";} else {this.registration = info.getRegistration();}
		if(info.getDeviceId()==null) {this.deviceId="";} else {this.deviceId=info.getDeviceId();}	
		if(info.getAddress()==null) {this.address="";} else {this.address=info.getAddress();}
		if(info.getReferralCode()==null) {this.referralCode="";} else {this.referralCode=info.getReferralCode();}
		if(info.getCreateTime()!=null)
		this.createTime=info.getCreateTime();
		
						
	}
   
	public static List<InfoVo> toVo(List<Info> list){
		List<InfoVo> resp = new ArrayList<InfoVo>();
		for(Info item : list) {
			resp.add(new InfoVo(item));
		}
		return resp;
	}

    
}
