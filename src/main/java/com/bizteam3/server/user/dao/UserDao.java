package com.bizteam3.server.user.dao;

import java.util.List;

import com.bizteam3.server.user.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserDao {

    int insert(User user);

    int update(User user);

    int delete(@Param("userId") String userId);

    List<User> selectPage(@Param("offset") int offset,@Param("size") int size);
}
