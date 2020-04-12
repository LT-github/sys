package com.lt.sys.dao;

import com.lt.sys.entity.Info;
import com.lt.sys.jpa.BaseRepository;

public interface IInfoRepository extends BaseRepository<Info,Long> {

    Info findByDeviceId(String deviceId);
}
