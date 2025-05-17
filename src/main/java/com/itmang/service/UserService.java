package com.itmang.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.itmang.pojo.dto.LoginDTO;
import com.itmang.pojo.dto.RegisterUserDTO;
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

    /**
     * 注册
     * @param registerUserDTO
     */
    void register(RegisterUserDTO registerUserDTO);

    /**
     * 修改用户信息
     * @param userDTO
     */
    void updateUser(UserDTO userDTO);

    /**
     * 修改用户状态
     * @param status
     */
    void updateStatus(Integer status,Long id);

    /**
     * 批量删除用户
     * @param ids
     */
    void deleteByIds(Long[] ids);

    /**
     * 修改用户角色
     * @param role
     * @param id
     */
    void updateRole(Integer role, Long id);

    /**
     * 根据id查询用户信息
     * @param id
     * @return
     */
    User selectUser(Long id);
}
