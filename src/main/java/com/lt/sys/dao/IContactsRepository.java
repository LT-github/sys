package com.lt.sys.dao;

import java.util.List;

import com.lt.sys.entity.Contacts;
import com.lt.sys.entity.Info;
import com.lt.sys.jpa.BaseRepository;

public interface IContactsRepository extends BaseRepository<Contacts,Long> {
	
	List<Contacts> findAllByInfo(Info info);  
}
