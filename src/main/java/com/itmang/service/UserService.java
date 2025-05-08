package com.itmang.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.itmang.pojo.dto.LoginDTO;
import com.itmang.pojo.dto.UserDTO;
import com.itmang.pojo.dto.UserPageDTO;
import com.itmang.pojo.entity.PageResult;
import com.itmang.pojo.entity.User;


import javax.security.auth.login.AccountNotFoundException;

public interface UserService extends IService<User>{

    /**
     * 登录
     * @param loginDTO
     * @return
     */
    User login(LoginDTO loginDTO);

    /**
     * 分页查询
     * @param userPageDTO
     * @return
     */
    PageResult pageSearch(UserPageDTO userPageDTO);
}
