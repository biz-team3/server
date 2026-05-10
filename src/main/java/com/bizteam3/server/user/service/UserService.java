package com.bizteam3.server.user.service;

import java.util.List;

import com.bizteam3.server.common.dto.PageRequest;
import com.bizteam3.server.common.dto.PageResponse;
import com.bizteam3.server.global.exception.BusinessException;
import com.bizteam3.server.global.exception.common.DatabaseException;
import com.bizteam3.server.user.dao.UserDao;
import com.bizteam3.server.user.dto.UserCreateRequest;
import com.bizteam3.server.user.dto.UserResponse;
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

    public PageResponse<UserResponse> findUsers(PageRequest pageRequest) {
        List<User> users = userDao.selectPage(pageRequest.getOffset(), pageRequest.getSize());
        int total = userDao.countAll();
        return new PageResponse<>(
            UserResponse.toFindDto(users),
            pageRequest,
            total
        );
    }


    public void modify(User user) {
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            user.setPassword(user.getPassword());
        }

        int rows = userDao.update(user);
        if(rows != 1) {
            throw new DatabaseException("회원정보 수정에 실패하였습니다");
        }
    }

    public void remove(String mid) {
        int rows = userDao.delete(mid);
        if(rows == 1) {
            throw new DatabaseException("회원정보 삭제에 실패하였습니다");
        }
    }
}
