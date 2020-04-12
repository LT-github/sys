package com.lt.sys.vo;

import java.util.ArrayList;
import java.util.List;
import com.lt.sys.entity.Contacts;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ContactsVo {

	
	    private Long id;

	   
	    private String phoneNumber;

	  
	    private String name;
	    
	    public ContactsVo() {}

		public ContactsVo(Contacts contacts) {
			super();
			this.id = contacts.getId();
			this.phoneNumber = contacts.getPhoneNumber();
			this.name = contacts.getName();
		}
		public static List<ContactsVo> toVo(List<Contacts> list){
			List<ContactsVo> resp = new ArrayList<ContactsVo>();
			for(Contacts item : list) {
				resp.add(new ContactsVo(item));
			}
			return resp;
		}    
	    
}
