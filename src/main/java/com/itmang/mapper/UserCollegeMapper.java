package com.itmang.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itmang.annotation.AutoFill;
import com.itmang.enumeration.OperationType;
import com.itmang.pojo.dto.CollegePageDTO;
import com.itmang.pojo.dto.UserCollegePageDTO;
import com.itmang.pojo.entity.College;
import com.itmang.pojo.entity.UserCollege;
import com.itmang.pojo.vo.UserCollegePageVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserCollegeMapper extends BaseMapper<UserCollege> {


    /**
     * 新增用户到学院
     * @param userCollege
     */
   @AutoFill(value = OperationType.INSERT)
    void addCollegeUser(UserCollege userCollege);

    /**
     * 批量删除学院中的用户
     * @param ids
     * @param collegeId
     */
    void deleteCollegeUser(Long[] ids, Long collegeId);

    /**
     * 分页查询学院中的用户
     * @param userCollegePageDTO
     * @return
     */
    List<UserCollegePageVO> pageCollegeUser(UserCollegePageDTO userCollegePageDTO);
}
