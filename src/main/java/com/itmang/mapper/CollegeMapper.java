package com.itmang.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itmang.annotation.AutoFill;
import com.itmang.enumeration.OperationType;
import com.itmang.pojo.dto.CollegeDTO;
import com.itmang.pojo.dto.CollegePageDTO;
import com.itmang.pojo.dto.UserPageDTO;
import com.itmang.pojo.entity.College;
import com.itmang.pojo.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CollegeMapper extends BaseMapper<College> {


    /**
     * 分页查询学院信息
     * @param collegePageDTO
     * @return
     */
    List<College> pageCollege(CollegePageDTO collegePageDTO);

    /**
     * 修改学院信息
     * @param college
     */
    @AutoFill(value = OperationType.UPDATE)
    void updateCollege(College college);

    @AutoFill(value = OperationType.INSERT)
    void insertCollege(College college);
}
