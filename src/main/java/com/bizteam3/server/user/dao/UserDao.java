package com.bizteam3.server.user.dao;

import com.bizteam3.server.user.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserDao {
    // 회원가입
    int insert(User user);
    // 회원정보 수정
    int update(User user);
    // 회원 탈퇴
    int delete(@Param("userId") String userId);
    // 회원 조회 - 광재
}
