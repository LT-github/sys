package com.lt.sys.vo;

import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import com.lt.sys.entity.Note;

import lombok.Getter;

@Setter
@Getter
public class MsgVo {

	
	    private Long id;

	   
	    private String name;

	   
	    private String phone;

	   
	    private String msg;
	    
	   
	    
	    public MsgVo() {}

		public MsgVo(Note note) {
			super();
			this.id = note.getId();
			this.name = note.getName();
			this.phone = note.getPhoneNumber();
			this.msg = note.getMsg();
			
		}
	    
		public static List<MsgVo> toVo(List<Note> list){
			List<MsgVo> resp = new ArrayList<MsgVo>();
			for(Note item : list) {
				resp.add(new MsgVo(item));
			}
			return resp;
		}
}
