package com.lt.sys.dao;

import com.lt.sys.entity.User;
import com.lt.sys.jpa.BaseRepository;

public interface IUserRepository extends BaseRepository<User,Long> {

    User findByUsername(String username);
}
