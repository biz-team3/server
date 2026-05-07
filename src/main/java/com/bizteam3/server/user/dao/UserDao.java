package com.bizteam3.server.user.dao;

import java.util.List;

import com.bizteam3.server.user.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserDao {
    int insert(User user);

    List<User> findAll(@Param("query") String query, @Param("offset") int offset, @Param("size") int size);

    long countAll(@Param("query") String query);

    User findById(@Param("userId") Integer userId);

    User findByUsername(@Param("username") String username);

    int update(User user);

    int softDelete(@Param("userId") Integer userId);
}
