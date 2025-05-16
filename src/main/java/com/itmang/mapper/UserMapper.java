package com.itmang.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.pagehelper.Page;
import com.itmang.pojo.dto.UserPageDTO;
import com.itmang.pojo.entity.PageResult;
import com.itmang.pojo.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User> {

    /**
     * 分页查询用户信息
     * @param userPageDTO
     * @return
     */
    List<User> pageSearch(UserPageDTO userPageDTO);

}
