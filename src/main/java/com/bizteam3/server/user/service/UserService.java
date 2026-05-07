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

    // 회원가입
    public void create(UserCreateRequest request){
        int insert = userDao.insert(UserCreateRequest.toEntity(request));
        if (insert != 1){
            throw new DatabaseException("회원가입에 실패하였습니다");
        }
    }
    // 회원정보 수정
    public void modify(User user) {
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            // 새 비밀번호가 입력된 경우
            user.setPassword(user.getPassword());
        }

        int rows = userDao.update(user); // 몇개의 행이 업데이트 되었는지 반환
        if(rows != 1) {
            // 업데이트된 회원 정보 조회
//            User dbUser = userDao.selectByUserId(user.getUserId());
            throw new DatabaseException("회원정보 수정에 실패하였습니다");
        }
    }

    // 회원 탈퇴
    public boolean remove(String mid) {
        int rows = userDao.delete(mid); // 몇개의 행이 삭제 되었는지 반환
        if(rows == 1) {
            return true; // 삭제 성공 시 true 반환 (true는 삭제 성공, 회원 정보가 더 이상 존재하지 않음을 의미)
        }
        else {
            return false; // 삭제 실패 시 false 반환 (false는 삭제 실패, 회원 정보가 여전히 존재함을 의미)
        }
    }
}
