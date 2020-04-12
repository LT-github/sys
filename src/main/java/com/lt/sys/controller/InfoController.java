package com.lt.sys.controller;

import com.lt.sys.Utils.HttpResult;
import com.lt.sys.Utils.IdWorker;
import com.lt.sys.Utils.MyBeanUtils;
import com.lt.sys.Utils.ResultCode;
import com.lt.sys.dao.IInfoRepository;
import com.lt.sys.dto.InfoDto;
import com.lt.sys.entity.Contacts;
import com.lt.sys.entity.Info;
import com.lt.sys.entity.Note;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Set;


@RestController
public class InfoController {


    @Autowired
    private IdWorker idWorker;

    @Autowired
    private IInfoRepository iInfoRepository;

    @PostMapping("save")
    public HttpResult saveInfo(@RequestBody InfoDto dto){
        try {
            Info info = new Info();
            BeanUtils.copyProperties(dto,info);
            info.setId(idWorker.nextId());
            if (null != dto.getNoteDtos() && 0 != dto.getNoteDtos().size()) {
                Set<Note> notes = new HashSet<>();
                dto.getNoteDtos().stream().forEach(noteDto -> {
                    Note note = new Note();
                    note.setId(idWorker.nextId());
                    BeanUtils.copyProperties(noteDto,note);
                    notes.add(note);
                });
                info.setNotes(notes);
            }
            if (null != dto.getContactsDtos() && 0 != dto.getContactsDtos().size()) {
                Set<Contacts> contactses = new HashSet<>();
                dto.getContactsDtos().stream().forEach(contactsDto -> {
                    Contacts contacts = new Contacts();
                    contacts.setId(idWorker.nextId());
                    BeanUtils.copyProperties(contactsDto, contacts);
                    contactses.add(contacts);
                });
                info.setContacts(contactses);
            }
            iInfoRepository.save(info);
        } catch (Exception e){
            e.printStackTrace();
            return HttpResult.failure(ResultCode.SERVER_ERROR.getCode(),e.getMessage());
        }

        return HttpResult.success(null,"Ok");
    }

}
