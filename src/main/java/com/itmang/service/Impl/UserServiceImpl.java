package com.itmang.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itmang.constant.MessageConstant;
import com.itmang.constant.StatusConstant;
import com.itmang.exception.AccountLockedException;
import com.itmang.exception.AccountNotFoundException;
import com.itmang.mapper.UserMapper;
import com.itmang.pojo.dto.LoginDTO;
import com.itmang.pojo.entity.User;
import com.itmang.service.UserService;
import com.itmang.exception.PasswordErrorException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper,User> implements UserService {


    @Autowired
    private UserMapper userMapper;




    /**
     * 用户登录
     * @param loginDTO
     * @return
     */
    public User login(LoginDTO loginDTO) {
        //查询用户的信息是否存在
        String name = loginDTO.getNumber();
        String password = loginDTO.getPassword();
        //构建查询构造器
        User user = userMapper.selectOne(new QueryWrapper<User>().eq("number", name));
        //判断是否查询到了
        if(user == null){
            //没有查询到，则返回信息
            log.info("用户登录失败，用户名不存在");
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }

        //将明文密码转成md5的加密数组
//        password= DigestUtils.md5DigestAsHex(password.getBytes());
        if (!password.equals(user.getPassword())) { //将密码进行比对
            //密码错误
            log.info("用户登录失败，密码错误");
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }
        //判断账号是否被锁定
        if (user.getStatus().equals(StatusConstant.DISABLE)) {
            //账号被锁定
            throw new AccountLockedException(MessageConstant.ACCOUNT_LOCKED);
        }
        //3、返回实体对象
        return user;
    }
}
