package com.lt.sys.controller;

import com.lt.sys.Utils.HttpResult;
import com.lt.sys.Utils.IdWorker;
import com.lt.sys.Utils.ListFenUtils;
import com.lt.sys.Utils.ListPageUtil;
import com.lt.sys.Utils.MyBeanUtils;
import com.lt.sys.Utils.PageResp;
import com.lt.sys.Utils.PagingList;
import com.lt.sys.Utils.ResultCode;
import com.lt.sys.annotation.UserLoginToken;
import com.lt.sys.controller.req.PageGetReq;
import com.lt.sys.dao.IContactsRepository;
import com.lt.sys.dao.IInfoRepository;
import com.lt.sys.dao.INoteRepository;
import com.lt.sys.dto.InfoDto;
import com.lt.sys.dto.InfoGetDto;
import com.lt.sys.entity.Contacts;
import com.lt.sys.entity.Info;
import com.lt.sys.entity.Note;
import com.lt.sys.exception.ClientErrorException;
import com.lt.sys.vo.ContactsVo;
import com.lt.sys.vo.InfoVo;
import com.lt.sys.vo.MsgVo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("info")
public class InfoController {


	@Autowired
	private IdWorker idWorker;

	@Autowired
	private IInfoRepository iInfoRepository;

	@Autowired
	private INoteRepository iNoteRepository;

	@Autowired
	private IContactsRepository iContactsRepository;

	@PostMapping("save")
	public HttpResult saveInfo(@RequestBody InfoDto dto){
		System.out.println(dto.toString());
		if (null != dto.getDeviceId()) {
			Info info = iInfoRepository.findByDeviceId(dto.getDeviceId());
			if (null != info) {
				return null;
			}
		}
		try {
			Info info = new Info();
			BeanUtils.copyProperties(dto,info);
			info.setId(idWorker.nextId());
			info.setCreateTime(System.currentTimeMillis());
			iInfoRepository.save(info);
			if (null != dto.getNoteDtos() && 0 != dto.getNoteDtos().size()) {
				dto.getNoteDtos().stream().forEach(noteDto -> {
					Note note = new Note();
					note.setId(idWorker.nextId());
					BeanUtils.copyProperties(noteDto,note);
					note.setInfo(info);
					try{
						iNoteRepository.save(note);
					}catch (Exception e){
						e.printStackTrace();
					}
				});
			}
			if (null != dto.getContactsDtos() && 0 != dto.getContactsDtos().size()) {
				dto.getContactsDtos().stream().forEach(contactsDto -> {
					Contacts contacts = new Contacts();
					contacts.setId(idWorker.nextId());
					BeanUtils.copyProperties(contactsDto, contacts);
					contacts.setInfo(info);
					iContactsRepository.save(contacts);
				});
			}
		} catch (Exception e){
			e.printStackTrace();
			return HttpResult.failure(ResultCode.SERVER_ERROR.getCode(),e.getMessage());
		}

		return HttpResult.success(null,"Ok");
	}


	//根据用户id查询用户或查询所有，分页查询
	@UserLoginToken
	@PostMapping("getInfo")
	public HttpResult<Object> getInfo(@RequestBody InfoGetDto dto) {

		dto.setPropertyName("createTime");
		dto.setAscending(false);
		try {
			if(dto.getReferralCode()=="") dto.setReferralCode(null);
			if(dto.getRegistration()=="") dto.setRegistration(null);
			if(dto.getDeviceId()=="") dto.setDeviceId(null);					
			Page<Info> page = iInfoRepository.findAll(dto);
			PageResp resp=new PageResp<>(page);
			resp.setData(InfoVo.toVo(page.getContent()));

			return HttpResult.success(resp,"查询成功");
		} catch (Exception e) {
			e.printStackTrace();
			return HttpResult.failure(ResultCode.SERVER_ERROR.getCode(),e.getMessage());
		}
			


	}

	//获取用户短息，分页
	@UserLoginToken
	@PostMapping("/getMsg")
	public HttpResult<Object> getInfoMsg(@RequestBody PageGetReq req) throws ClientErrorException{


		try {
			
			
			if(req.getId()==null) throw new ClientErrorException("用户标识异常");
			Optional<Info> op = iInfoRepository.findById(req.getId());			
			if(!op.isPresent()) throw new ClientErrorException("用户标识异常");
			List<Note> notes = iNoteRepository.findAllByInfo(op.get());
			if(notes==null || notes.isEmpty()) throw new ClientErrorException("短信录没有任何数据");						
			ListPageUtil<MsgVo> listPageUtil = new ListPageUtil<MsgVo>(MsgVo.toVo(notes),req.getPage(),req.getSize());			
	        PagingList page = listPageUtil.getPagedList();
			return HttpResult.success(page,"查询成功");
		} catch (Exception e) {			
			return HttpResult.failure(ResultCode.CLIENT_ERROR.getCode(),e.getMessage());
		}
		
	}
	//获取通讯录,分页
	@UserLoginToken
	@PostMapping("/getInfoContacts")
	public HttpResult<Object> getInfoContacts(@RequestBody PageGetReq req) throws ClientErrorException{

		try {
			if(req.getId()==null) throw new ClientErrorException("用户标识异常");
			Optional<Info> op = iInfoRepository.findById(req.getId());			
			if(!op.isPresent()) throw new ClientErrorException("用户标识异常");
			List<Contacts> contacts = iContactsRepository.findAllByInfo(op.get());
			if(contacts==null || contacts.isEmpty()) throw new ClientErrorException("通讯录没有任何数据");
			ListPageUtil<ContactsVo> listPageUtil = new ListPageUtil<ContactsVo>(ContactsVo.toVo(contacts),req.getPage(),req.getSize());
			 PagingList page = listPageUtil.getPagedList();
			return HttpResult.success(page,"查询成功");
		} catch (Exception e) {			
			return HttpResult.failure(ResultCode.CLIENT_ERROR.getCode(),e.getMessage());
		}
		

	}
	
	//获取经纬度
	    @UserLoginToken
		@PostMapping("/getInfoJW")
		public HttpResult<Object> getInfoJW(@RequestBody PageGetReq req) throws ClientErrorException{
			
			try {
				if(req.getId()==null) throw new ClientErrorException("用户标识异常");
				String latitude = "";
				String longitude ="";
				String address = "";
				Optional<Info> op = iInfoRepository.findById(req.getId());
				if(!op.isPresent()) throw new ClientErrorException("该用户不存在");
				Map<String,String> map=new HashMap<>();				
				if(!op.isPresent()) {map.put("longitude", longitude);map.put("latitude", latitude); map.put("address", address); return HttpResult.success(map,"用户标识异常");}
				Info info = op.get();
				//纬度
				 latitude = info.getLatitude();
				//经度
				 longitude = info.getLongitude();
				 //地址
				 address = info.getAddress();
				 if(longitude!=null)
				map.put("longitude", longitude);
				 if(latitude!=null)
				map.put("latitude", latitude);
				 if(address!=null)
				map.put("address", address);
								  
				return HttpResult.success(map,"查询成功");
				
			} catch (Exception e) {
				e.printStackTrace();
				return HttpResult.failure(ResultCode.SERVER_ERROR.getCode(),e.getMessage());
			}
			
		}
}
