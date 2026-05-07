package com.bizteam3.server.user.service;

import com.bizteam3.server.global.exception.BusinessException;
import com.bizteam3.server.global.exception.common.DatabaseException;
import com.bizteam3.server.user.dao.UserDao;
import com.bizteam3.server.user.dto.UserCreateRequest;
import com.bizteam3.server.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserDao userDao;

    public void create(UserCreateRequest request){
        int insert = userDao.insert(UserCreateRequest.toEntity(request));
        if (insert != 1){
            throw new DatabaseException("회원가입에 실패하였습니다");
        }
    }


}
