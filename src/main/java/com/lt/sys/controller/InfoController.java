package com.lt.sys.controller;

import com.lt.sys.Utils.HttpResult;
import com.lt.sys.Utils.IdWorker;
import com.lt.sys.Utils.MyBeanUtils;
import com.lt.sys.Utils.ResultCode;
import com.lt.sys.dao.IContactsRepository;
import com.lt.sys.dao.IInfoRepository;
import com.lt.sys.dao.INoteRepository;
import com.lt.sys.dto.InfoDto;
import com.lt.sys.entity.Contacts;
import com.lt.sys.entity.Info;
import com.lt.sys.entity.Note;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
        if (null != dto) {
            System.out.println(dto.toString());
            try {
                Info info = new Info();
                BeanUtils.copyProperties(dto,info);
                info.setId(idWorker.nextId());
                iInfoRepository.save(info);
                if (null != dto.getNoteDtos() && 0 != dto.getNoteDtos().size()) {
                    dto.getNoteDtos().stream().forEach(noteDto -> {
                        Note note = new Note();
                        note.setId(idWorker.nextId());
                        BeanUtils.copyProperties(noteDto,note);
                        note.setInfo(info);
                        iNoteRepository.save(note);
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
        }else {
            return HttpResult.success(null,"ok");
        }
    }

}
