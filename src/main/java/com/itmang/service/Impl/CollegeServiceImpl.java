package com.itmang.service.Impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itmang.constant.MessageConstant;
import com.itmang.constant.RoleConstant;
import com.itmang.constant.StatusConstant;
import com.itmang.context.BaseContext;
import com.itmang.exception.*;
import com.itmang.mapper.CollegeMapper;
import com.itmang.mapper.UserCollegeMapper;
import com.itmang.mapper.UserMapper;
import com.itmang.pojo.dto.*;
import com.itmang.pojo.entity.College;
import com.itmang.pojo.entity.PageResult;
import com.itmang.pojo.entity.User;
import com.itmang.pojo.entity.UserCollege;
import com.itmang.pojo.vo.UserCollegePageVO;
import com.itmang.service.CollegeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Slf4j
@Service
public class CollegeServiceImpl extends ServiceImpl<CollegeMapper, College> implements CollegeService {


    @Autowired
    private CollegeMapper collegeMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserCollegeMapper userCollegeMapper;


    /**
     * 分页查询学院信息
     * @param collegePageDTO
     * @return
     */
    public PageResult pageCollege(CollegePageDTO collegePageDTO) {
        //开始分页
        PageHelper.startPage(collegePageDTO.getPageNum(), collegePageDTO.getPageSize());
        //查询
        List<College> collegeList = collegeMapper.pageCollege(collegePageDTO);
        PageInfo<College> pageInfo = new PageInfo<>(collegeList);
        log.info("分页查询学院信息成功，返回结果为：{}", pageInfo.toString());
        return new PageResult(pageInfo.getTotal(), pageInfo.getList());
    }

    /**
     * 修改学院信息
     * @param collegeDTO
     */
    public void updateCollege(CollegeDTO collegeDTO) {
        //获取当前用户
        Long userId = BaseContext.getCurrentId();
        User user = userMapper.selectById(userId);
        //判断是否查询到用户
        if (user == null) {
            throw new UserNotFindException(MessageConstant.USER_NOT_FOUND);
        }
        //判断用户是否为管理员
        if (user.getRole().equals(RoleConstant.ADMIN)) {
            //是管理员，则直接修改
            log.info("用户{}修改学院信息成功", user.getName());
            //将数据拷贝
            College college = BeanUtil.copyProperties(collegeDTO, College.class);
            collegeMapper.updateCollege(college);
        }else {//不是则，报错权限不足
            log.info("用户{}修改学院信息失败，权限不足", user.getName());
            throw new InsufficientPermissionsException(MessageConstant.INSUFFICIENTPERMISSIONS);
        }
    }

    /**
     * 新增学院
     * @param addCollegeDTO
     */
    public void addCollege(AddCollegeDTO addCollegeDTO) {
        //判断权限
        Long userId = BaseContext.getCurrentId();
        User user = userMapper.selectById(userId);
        if (user == null) {
            throw new UserNotFindException(MessageConstant.USER_NOT_FOUND);
        }
        if(user.getRole().equals(RoleConstant.ADMIN)){
            //判断学院是否存在
            College college = collegeMapper.selectOne(new QueryWrapper<College>()
                    .eq("name", addCollegeDTO.getName()));
            if(college != null){
                log.info("用户{}新增学院失败，学院已存在", user.getName());
                throw new CollegeExistException(MessageConstant.COLLEGE_EXIST);
            }else{
                //新增
                log.info("用户{}新增学院成功", user.getName());
                collegeMapper.insertCollege(BeanUtil.copyProperties(addCollegeDTO, College.class));
            }
        }else{
            log.info("用户{}新增学院失败，权限不足", user.getName());
            throw new InsufficientPermissionsException(MessageConstant.INSUFFICIENTPERMISSIONS);
        }

    }

    /**
     * 批量删除学院
     * @param ids
     */
    public void deleteCollege(Long[] ids) {
        //判断权限
        Long userId = BaseContext.getCurrentId();
        User user = userMapper.selectById(userId);
        if (user == null) {
            throw new UserNotFindException(MessageConstant.USER_NOT_FOUND);
        }
        if(user.getRole().equals(RoleConstant.ADMIN)){
            //用户是管理员，可以进行删除
            collegeMapper.deleteCollege(ids);
            log.info("用户{}删除学院成功", user.getName());
        }else{
            log.info("用户{}删除学院失败，权限不足", user.getName());
            throw new InsufficientPermissionsException(MessageConstant.INSUFFICIENTPERMISSIONS);
        }
    }

    /**
     * 新增用户到学院
     * @param addCollegeUserDTO
     */
    public void addCollegeUser(AddCollegeUserDTO addCollegeUserDTO) {
        //判断权限
        Long userId = BaseContext.getCurrentId();
        User user = userMapper.selectById(userId);
        if (user == null) {
            throw new UserNotFindException(MessageConstant.USER_NOT_FOUND);
        }
        //判断用户的状态
        User addUser = userMapper.selectById(addCollegeUserDTO.getUserId());
        if (addUser == null) {
            //被添加的学生不存在
            log.info("用户{}新增用户到学院失败，该学生不存在", user.getName());
            throw new UserNotFindException(MessageConstant.USER_NOT_FOUND);
        }else{
            if (addUser.getStatus().equals(StatusConstant.DISABLE)) {
                log.info("用户{}新增用户到学院失败，用户已禁用", user.getName());
                throw new AccountLockedException(MessageConstant.ACCOUNT_LOCKED);
            }
        }
        if(user.getRole().equals(RoleConstant.ADMIN)) {
            //用户是管理员，可以进行删除
            //判断学生是否已经有学院
            UserCollege userCollege = userCollegeMapper.selectOne(new QueryWrapper<UserCollege>()
                    .eq("user_id", addCollegeUserDTO.getUserId()));
            if (userCollege != null) {
                log.info("用户{}新增用户到学院失败，该学生已有学院", user.getName());
                throw new UserCollegeExistException(MessageConstant.USER_COLLEGE_EXIST);
            }else{
                userCollegeMapper.addCollegeUser(BeanUtil.copyProperties(addCollegeUserDTO, UserCollege.class));
                //修改学院的改动信息和信息人
                collegeMapper.updateInformation(College.builder().id(addCollegeUserDTO.getCollegeId()).build());
                log.info("用户{}新增用户到学院成功", user.getName());
            }
        } else if (user.getRole().equals(RoleConstant.TEACHER)) {
            //用户是教师，可以新增学生到学院
            //判断新增的用户的角色是否是学生
            //判断学生的角色
            if (addUser.getRole().equals(RoleConstant.STUDENT)) {
                //判断学生是否已经有学院
                UserCollege userCollege = userCollegeMapper.selectOne(new QueryWrapper<UserCollege>()
                        .eq("user_id", addCollegeUserDTO.getUserId()));
                if (userCollege != null) {
                    log.info("用户{}新增用户到学院失败，该学生已有学院", user.getName());
                    throw new UserCollegeExistException(MessageConstant.USER_COLLEGE_EXIST);
                }else{
                    userCollegeMapper.addCollegeUser(BeanUtil.copyProperties(addCollegeUserDTO, UserCollege.class));
                    //修改学院的改动信息和信息人
                    collegeMapper.updateInformation(College.builder().id(addCollegeUserDTO.getCollegeId()).build());
                    log.info("用户{}新增用户到学院成功", user.getName());
                }
            }else{//不是学生，无法添加
                log.info("用户{}新增用户到学院失败，添加的用户不是学生,", user.getName());
                throw new InsufficientPermissionsException(MessageConstant.INSUFFICIENTPERMISSIONS);
            }
        }else{
            //没有权限
            log.info("用户{}新增用户到学院失败，权限不足", user.getName());
            throw new InsufficientPermissionsException(MessageConstant.INSUFFICIENTPERMISSIONS);
        }
    }

    /**
     * 批量删除学院中的用户
     * @param ids
     * @param collegeId
     */
    public void deleteCollegeUser(Long[] ids, Long collegeId) {
        //判断权限
        Long userId = BaseContext.getCurrentId();
        User user = userMapper.selectById(userId);
        if (user == null) {
            throw new UserNotFindException(MessageConstant.USER_NOT_FOUND);
        }
        if(user.getRole().equals(RoleConstant.ADMIN)){
            //用户是管理员，可以进行删除
            userCollegeMapper.deleteCollegeUser(ids,collegeId);
            //修改学院的修改时间与修改人
            collegeMapper.updateInformation(College.builder().id(collegeId).build());
        } else if (user.getRole().equals(RoleConstant.TEACHER)) {
            //用户是教师，可以删除学生
            //判断被删除的是否是学生
            //创建一个可以删除的id集合和不可以删除的id集合
            List<Long> canDeleteIds = new ArrayList<>();
            List<Long> cannotDeleteIds = new ArrayList<>();
            for (Long id : ids) {
                User addUser = userMapper.selectById(id);
                if (addUser == null) {
                    //被添加的学生不存在
                    throw new UserNotFindException(MessageConstant.USER_NOT_FOUND);
                }
                if (addUser.getRole().equals(RoleConstant.STUDENT)) {
                    //被删除的用户是学生，可以删除
                    canDeleteIds.add(id);
                }else{
                    //被删除的用户不是学生，不可以删除
                    cannotDeleteIds.add(id);
                }
            }
            if(canDeleteIds.size() > 0){
                //有满足删除的id，进行删除
                userCollegeMapper.deleteCollegeUser(canDeleteIds.toArray(new Long[0]),collegeId);
                //修改学院的修改时间与修改人
                collegeMapper.updateInformation(College.builder().id(collegeId).build());
                //判断师傅又不满足的id
                if(cannotDeleteIds.size() > 0){
                    log.info("用户{}删除学院中的用户失败，{}不是学生", user.getName(), cannotDeleteIds);
                    throw new FailDeletePartException(MessageConstant.FAIL_DELETE_PART);
                }
            }else{
                log.info("用户{}删除学院中的用户失败，权限不足", user.getName());
                throw new InsufficientPermissionsException(MessageConstant.INSUFFICIENTPERMISSIONS);
            }
        }else{
            //没有权限
            log.info("用户{}删除学院中的用户失败，权限不足", user.getName());
            throw new InsufficientPermissionsException(MessageConstant.INSUFFICIENTPERMISSIONS);
        }
    }

    /**
     * 分页查询学院中的用户
     * @param userCollegePageDTO
     * @return
     */
    public PageResult pageCollegeUser(UserCollegePageDTO userCollegePageDTO) {
        //开始分页
        PageHelper.startPage(userCollegePageDTO.getPageNum(), userCollegePageDTO.getPageSize());
        List<UserCollegePageVO> list = userCollegeMapper.pageCollegeUser(userCollegePageDTO);
        PageInfo<UserCollegePageVO> pageInfo = new PageInfo<>(list);
        log.info("分页查询结果:{}", pageInfo.toString());
        return new PageResult(pageInfo.getTotal(), pageInfo.getList());

    }


}
