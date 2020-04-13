package com.lt.sys.controller;

import com.lt.sys.Utils.HttpResult;
import com.lt.sys.Utils.IdWorker;
import com.lt.sys.Utils.ListFenUtils;
import com.lt.sys.Utils.MyBeanUtils;
import com.lt.sys.Utils.PageResp;
import com.lt.sys.Utils.PagingList;
import com.lt.sys.Utils.ResultCode;
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
import java.util.List;
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
	@PostMapping("getInfo")
	public HttpResult<Object> getInfo(InfoGetDto dto) {

		Page<Info> page = iInfoRepository.findAll(dto);
		PageResp resp=new PageResp<>(page);
		resp.setData(InfoVo.toVo(page.getContent()));

		return HttpResult.success(resp,"查询成功");	


	}

	//获取用户短息，分页
	@PostMapping("/getMsg")
	public HttpResult<Object> getInfoMsg(@RequestBody PageGetReq req) throws ClientErrorException{


		Optional<Info> op = iInfoRepository.findById(req.getId());
		PagingList page = new PagingList();
		if(!op.isPresent()) return HttpResult.success(page,"用户标识异常");
		List<Note> notes = iNoteRepository.findAllByInfo(op.get());

		if(notes==null || notes.isEmpty()) return HttpResult.success(page,"暂无数据");

		ListFenUtils<MsgVo> pageList = new ListFenUtils<MsgVo>();
		page.setPage(req.getPage());
		pageList.fen(page,MsgVo.toVo(notes));
		return HttpResult.success(page,"查询成功");
	}
	//获取通讯录,分页
	@PostMapping("/getInfoContacts")
	public HttpResult<Object> getInfoContacts(@RequestBody PageGetReq req) throws ClientErrorException{

		Optional<Info> op = iInfoRepository.findById(req.getId());
		PagingList page = new PagingList();
		if(!op.isPresent()) return HttpResult.success(page,"用户标识异常");
		List<Contacts> contacts = iContactsRepository.findAllByInfo(op.get());

		if(contacts==null || contacts.isEmpty()) return HttpResult.success(page,"暂无数据");

		ListFenUtils<ContactsVo> pageList = new ListFenUtils<ContactsVo>();
		page.setPage(req.getPage());
		pageList.fen(page,ContactsVo.toVo(contacts));
		return HttpResult.success(page,"查询成功");

	}
	
	//获取通讯录,分页
		@PostMapping("/getInfoJW")
		public HttpResult<Object> getInfoJW(@RequestBody PageGetReq req) throws ClientErrorException{
			String latitude = "";
			String longitude ="";
			Optional<Info> op = iInfoRepository.findById(req.getId());
			List<String> list=new ArrayList<>();
			
			if(!op.isPresent()) { list.add(longitude); list.add(latitude); return HttpResult.success(list,"用户标识异常");}
			Info info = op.get();
			//纬度
			 latitude = info.getLatitude();
			//经度
			 longitude = info.getLongitude();
			
			if(info.getLongitude()!=null)
			list.add(longitude);
			if(info.getLatitude()!=null)
			list.add(latitude);
			  
			return HttpResult.success(list,"查询成功");
			
		}
}
