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

    List<User> selectPage(@Param("offset") int offset, @Param("size") int size);

    /** 단건 조회 - 탈퇴 여부 포함 전체 컬럼 반환, 없으면 null */
    User selectById(@Param("userId") Integer userId);
}
