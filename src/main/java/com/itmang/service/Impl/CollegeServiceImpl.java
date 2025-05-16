package com.itmang.service.Impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itmang.constant.MessageConstant;
import com.itmang.constant.RoleConstant;
import com.itmang.constant.StatusConstant;
import com.itmang.context.BaseContext;
import com.itmang.exception.*;
import com.itmang.mapper.CollegeMapper;
import com.itmang.mapper.UserMapper;
import com.itmang.pojo.dto.*;
import com.itmang.pojo.entity.College;
import com.itmang.pojo.entity.PageResult;
import com.itmang.pojo.entity.User;
import com.itmang.service.CollegeService;
import com.itmang.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Slf4j
@Service
public class CollegeServiceImpl extends ServiceImpl<CollegeMapper, College> implements CollegeService {


    @Autowired
    private CollegeMapper collegeMapper;
    @Autowired
    private UserService userService;


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
        User user = userService.getById(userId);
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
        User user = userService.getById(userId);
        if (user == null) {
            throw new UserNotFindException(MessageConstant.USER_NOT_FOUND);
        }
        if(user.getRole().equals(RoleConstant.ADMIN)){
            //判断学院是否存在
            College college = collegeMapper.selectOne(new QueryWrapper<College>()
                    .eq("name", addCollegeDTO.getName()));
            if(college != null){
                throw new CollegeExistException(MessageConstant.COLLEGE_EXIST);
            }
            //新增
            collegeMapper.insertCollege(BeanUtil.copyProperties(addCollegeDTO, College.class));
        }else{
            throw new InsufficientPermissionsException(MessageConstant.INSUFFICIENTPERMISSIONS);
        }

    }


}
