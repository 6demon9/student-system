package com.itmang.service.Impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itmang.constant.MessageConstant;
import com.itmang.constant.RoleConstant;
import com.itmang.constant.StatusConstant;
import com.itmang.context.BaseContext;
import com.itmang.exception.*;
import com.itmang.mapper.UserMapper;
import com.itmang.pojo.dto.LoginDTO;
import com.itmang.pojo.dto.RegisterUserDTO;
import com.itmang.pojo.dto.UserDTO;
import com.itmang.pojo.dto.UserPageDTO;
import com.itmang.pojo.entity.PageResult;
import com.itmang.pojo.entity.User;
import com.itmang.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


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
        User user = userMapper.selectOne(new QueryWrapper<User>().eq("number", name));
        //判断是否查询到了
        if(user == null){
            //没有查询到，则返回信息
            log.info("用户登录失败，用户名不存在");
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }
        //将明文密码转成md5的加密数组
        password= DigestUtils.md5DigestAsHex(password.getBytes());
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

    /**
     * 分页查询
     * @param userPageDTO
     * @return
     */
    public PageResult pageSearch(UserPageDTO userPageDTO) {
        //使用pageHelper工具进行分页查询
        PageHelper.startPage(userPageDTO.getPageNum(),userPageDTO.getPageSize());
        List<User> userList= userMapper.pageSearch(userPageDTO);//进行条件查询
        PageInfo<User> pageInfo = new PageInfo<>(userList);
        log.info("{}",pageInfo.toString());
        return new PageResult(pageInfo.getTotal(),pageInfo.getList());
    }



    /**
     * 用户注册
     * @param registerUserDTO
     */
    public void register(RegisterUserDTO registerUserDTO) {
        //判断当前的账号是否存在
        User user = userMapper.selectOne(new QueryWrapper<User>()
                .eq("number", registerUserDTO.getNumber()));
        if(user != null){
            //说明账号存在，报错账号存在
            throw new AccountExistException(MessageConstant.ACCOUNT_EXISTS);
        }
        //账号不存在，则进行注册
        //1.先将数据拷贝
        User userInsert = new User();
        BeanUtil.copyProperties(registerUserDTO,userInsert);
        //2.将密码进行加密
        userInsert.setPassword(DigestUtils.md5DigestAsHex(registerUserDTO.getPassword().getBytes()));
        //3.将数据插入到数据库中
        userInsert.setCreateTime(LocalDateTime.now());
        userInsert.setUpdateTime(LocalDateTime.now());
        userMapper.insert(userInsert);
    }

    /**
     * 修改用户信息
     * @param userDTO
     */
    public void updateUser(UserDTO userDTO) {
        //判断权限
        Long userId = BaseContext.getCurrentId();
        User user = userMapper.selectById(userId);
        if(user == null){//判断是否查询到用户
            //没有查询到用户，则抛出异常
            throw new UserNotFindException(MessageConstant.USER_NOT_FOUND);
        }
        //若为管理员则允许进行修改
        if(user.getRole().equals(RoleConstant.ADMIN) || user.getId().equals(userDTO.getId())){
            //管理员可以修改所有用户的信息或者自己可以修改自己的信息
            //1.将数据拷贝
            User userUpdate = new User();
            BeanUtil.copyProperties(userDTO,userUpdate);
            //2.将数据插入到数据库中
            userMapper.updateUser(userUpdate);
            log.info("用户{}修改成功",userUpdate.getName());
        }else {
            //若为普通用户则不允许进行修改
            throw new InsufficientPermissionsException(MessageConstant.INSUFFICIENTPERMISSIONS);
        }
    }

    /**
     * 修改用户状态
     * @param status
     */
    public void updateStatus(Integer status,Long id) {
        //判断当前的用户是否为管理员或老师
        Long userId = BaseContext.getCurrentId();
        User user = userMapper.selectById(userId);
        if(user == null){//判断是否查询到用户
            //没有查询到用户，则抛出异常
            throw new UserNotFindException(MessageConstant.USER_NOT_FOUND);
        }
        //若为管理员则允许进行删除
        if(user.getRole().equals(RoleConstant.ADMIN)){
            //可以修改所有角色的状态
            userMapper.updateUser(User.builder().status(status).id(id).build());
            log.info("用户{}成功修改用户{}的状态为{}",user.getName(),id,status);
        } else if (user.getRole().equals(RoleConstant.TEACHER) ){
            //可以修改学生的状态
            //判断修改的id是否为学生
            User updateUser = userMapper.selectById(id);
            if(user == null){//判断是否查询到用户
                //没有查询到用户，则抛出异常
                throw new UserNotFindException(MessageConstant.USER_NOT_FOUND);
            }
            if(updateUser.getRole().equals(RoleConstant.STUDENT)){//被修改的角色为学生，可以修改
                log.info("用户{}成功修改用户{}的状态为{}",user.getName(),id,status);
                userMapper.updateUser(User.builder().status(status).id(id).build());
            }else{
                throw new InsufficientPermissionsException(MessageConstant.INSUFFICIENTPERMISSIONS);
            }
        } else {
            //若为普通用户则不允许进行删除
            throw new InsufficientPermissionsException(MessageConstant.INSUFFICIENTPERMISSIONS);
        }
    }

    /**
     * 根据id删除用户
     * @param ids
     */
    public void deleteByIds(Long[] ids) {
        //判断当前的用户是否为管理员
        Long userId = BaseContext.getCurrentId();
        User user = userMapper.selectById(userId);
        if(user == null){//判断是否查询到用户
            //没有查询到用户，则抛出异常
            throw new UserNotFindException(MessageConstant.USER_NOT_FOUND);
        }
        //若为管理员则允许进行删除
        if(user.getRole().equals(RoleConstant.ADMIN)){
            //允许删除
            List<Long> canDeleteIds = new ArrayList<>();
            List<Long> canNotDeleteIds = new ArrayList<>();
            //分别判断用户的账号状态是否为禁用状态
            for (Long id : ids) {
                User deleteUser = userMapper.selectById(id);
                if(deleteUser == null) {//判断是否查询到用户
                    //没有查询到用户，则抛出异常
                    throw new UserNotFindException(MessageConstant.USER_NOT_FOUND);
                }
                if(deleteUser.getStatus().equals(StatusConstant.DISABLE)){
                    canDeleteIds.add(id);
                }else{
                    canNotDeleteIds.add(id);
                }
            }
            if(canDeleteIds.size() > 0){
                //判断是否是只删除部分
                if (canNotDeleteIds.size() > 0){
                    userMapper.deleteUserIds(canDeleteIds.toArray(new Long[0]));
                    log.info("用户{}删除用户{}成功，{}失败",user.getName(),canDeleteIds,canNotDeleteIds);
                    throw new FailDeletePartException(MessageConstant.FAIL_DELETE_PART);
                }else{
                    userMapper.deleteUserIds(canDeleteIds.toArray(new Long[0]));
                    log.info("用户{}删除用户{}成功",user.getName(),canDeleteIds);
                }
            }
        }else{
            //若为普通用户则不允许进行删除
            throw new InsufficientPermissionsException(MessageConstant.INSUFFICIENTPERMISSIONS);
        }
    }

    /**
     * 修改用户角色
     * @param role
     * @param id
     */
    public void updateRole(Integer role, Long id) {
        //判断当前的用户是否为管理员
        Long userId = BaseContext.getCurrentId();
        User user = userMapper.selectById(userId);
        if(user == null){//判断是否查询到用户
            //没有查询到用户，则抛出异常
            throw new UserNotFindException(MessageConstant.USER_NOT_FOUND);
        }
        //若为管理员则允许进行删除
        if(user.getRole().equals(RoleConstant.ADMIN)){
            //允许修改角色
            log.info("用户{}修改用户{}的角色为{}",user.getName(),id,role);
            userMapper.updateUser(User.builder().role(role).id(id).build());
        }else{
            //若为普通用户则不允许进行删除
            throw new InsufficientPermissionsException(MessageConstant.INSUFFICIENTPERMISSIONS);
        }
    }

    /**
     * 根据id查询用户
     * @param id
     * @return
     */
    public User selectUser(Long id) {
        //判断权限
        Long userId = BaseContext.getCurrentId();
        User user = userMapper.selectById(userId);
        if (user == null){//判断是否查询到用户
            //没有查询到用户，则抛出异常
            throw new UserNotFindException(MessageConstant.USER_NOT_FOUND);
        }
        if(user.getRole().equals(RoleConstant.ADMIN) || user.getId().equals(id)){
            //可以查询所有用户，或者查询自己
            User resultUser = userMapper.selectById(id);
            log.info("用户{}查询到的信息为：{}",user.getName(),resultUser);
            //将密码进行保密
            resultUser.setPassword("******");
            return resultUser;
        } else{
            //没有权限
            log.info("用户{}没有权限查询用户信息",user.getName());
            throw new InsufficientPermissionsException(MessageConstant.INSUFFICIENTPERMISSIONS);
        }
    }


}
