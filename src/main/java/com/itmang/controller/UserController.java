package com.itmang.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
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
@Tag(name = "用户模块")
@RequestMapping("/user")
public class UserController {

    /**
     * 用户模块的功能
     * 登录、登出、注册、分页查询、查询用户具体信息、编辑用户信息
     * 修改用户账户转态、批量删除用户，修改用户的角色
     */

    @Autowired
    private UserService userService;
    @Autowired
    private JwtProperties jwtProperties;//导入jwt配置类

    /**
     * 登录接口
     * @param loginDTO
     * @return
     */

    @Operation(summary = "登录接口")
    @PostMapping("/login")
    public Result<LoginVO> login(@RequestBody LoginDTO loginDTO){
        log.info("用户登录,登录的信息:{}", loginDTO);
        //先查询数据库是否有这个用户的信息
        User user = userService.login(loginDTO);
        //查询到了，直接生成jwt令牌，用户之后验证
        //1、使用键值对的方式存储信息
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.USER_ID,user.getId());//将用户的id存储进声明中
        //生成token令牌
        String token = JwtUtil.createJWT(
                jwtProperties.getAdminSecretKey(),//设置关键字
                jwtProperties.getAdminTtl(),//设置token有效时间
                claims);//设置声明信息（键值对）
        LoginVO loginVO = LoginVO.builder()
                .id(user.getId())
                .name(user.getName())
                .token(token)
                .build();
        return Result.success(loginVO);
    }

    /**
     * 退出登录接口
     * @return
     */
    @Operation(summary = "退出登录接口")
    @PostMapping("/logout")
    public Result logout(){
        log.info("用户退出登录");
        return Result.success();
    }

    /**
     * 注册用户接口
     * @param registerUserDTO
     * @return
     */
    @Operation(summary = "注册用户接口")
    @PostMapping("/register")
    public Result save(@RequestBody RegisterUserDTO registerUserDTO){
        log.info("注册用户信息:{}", registerUserDTO);
        userService.register(registerUserDTO);
        return Result.success();
    }

    /**
     * 分页查询用户接口
     * @param userPageDTO
     * @return
     */
    @Operation(summary = "分页查询用户接口")
    @GetMapping("/page")
    public Result<PageResult> page(UserPageDTO userPageDTO){
        log.info("分页查询用户信息:{}", userPageDTO);
        PageResult pageResult = userService.pageSearch(userPageDTO);
        return Result.success(pageResult);
    }

    /**
     * 查询用户具体信息接口
     * @param id
     * @return
     */
    @Operation(summary = "查询用户具体信息接口")
    @GetMapping("/{id}")
    public Result<User> getById(@PathVariable Long id){
        log.info("查询用户具体信息:{}", id);
        User user = userService.getById(id);
        if(user == null){
            return Result.error(MessageConstant.USER_NOT_FOUND);
        }
        return Result.success(user);
    }

    /**
     * 编辑用户信息接口
     * @param userDTO
     * @return
     */
    @Operation(summary = "编辑用户信息接口")
    @PostMapping("/update")
    public Result update(@RequestBody UserDTO userDTO){
        log.info("编辑用户信息:{}", userDTO);
        userService.updateUser(userDTO);
        return Result.success();
    }

    /**
     * 修改用户账户转态接口
     * @param status
     * @return
     */
    @Operation(summary = "修改用户账户转态接口")
    @PostMapping("status/{status}")
    public Result updateStatus(@PathVariable Integer status,Long id){
        log.info("修改用户账户转态:{}", status);
        userService.updateStatus(status,id);
        return Result.success();
    }

    @Operation(summary = "批量删除用户接口")
    @PostMapping("/delete/{ids}")
    public Result delete(@PathVariable Long[] ids){
        log.info("批量删除用户:{}", ids);
        userService.deleteByIds(ids);
        return Result.success();
    }

    @Operation(summary = "修改用户的角色接口")
    @PostMapping("/role/{role}")
    public Result updateRole(@PathVariable Integer role,Long id){
        log.info("修改用户的角色:{}", role);
        userService.updateRole(role,id);
        return Result.success();
    }


}
