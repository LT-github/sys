package com.lt.sys.dao;

import java.util.List;

import com.lt.sys.entity.Info;
import com.lt.sys.entity.Note;
import com.lt.sys.jpa.BaseRepository;

public interface INoteRepository extends BaseRepository<Note,Long> {
	
	List<Note>findAllByInfo(Info info);
}
