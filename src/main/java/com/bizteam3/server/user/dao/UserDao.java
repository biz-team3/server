package com.bizteam3.server.user.dao;

import com.bizteam3.server.user.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDao {
    int insert(User user);
}
