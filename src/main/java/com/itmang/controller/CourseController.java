package com.itmang.controller;


import com.itmang.constant.JwtClaimsConstant;
import com.itmang.constant.MessageConstant;
import com.itmang.pojo.dto.LoginDTO;
import com.itmang.pojo.dto.RegisterUserDTO;
import com.itmang.pojo.dto.UserDTO;
import com.itmang.pojo.dto.UserPageDTO;
import com.itmang.pojo.entity.PageResult;
import com.itmang.pojo.entity.Result;
import com.itmang.pojo.entity.User;
import com.itmang.pojo.vo.LoginVO;
import com.itmang.properties.JwtProperties;
import com.itmang.service.UserService;
import com.itmang.utils.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
@Tag(name = "课程模块")
@RequestMapping("/course")
public class CourseController {

    /**
     * 用户模块的功能
     * 登录、登出、注册、分页查询、查询用户具体信息、编辑用户信息
     * 修改用户账户转态、批量删除用户，修改用户的角色
     */

    @Autowired
    private UserService userService;




}
