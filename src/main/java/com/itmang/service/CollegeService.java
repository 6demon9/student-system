package com.itmang.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.itmang.pojo.dto.*;
import com.itmang.pojo.entity.College;
import com.itmang.pojo.entity.PageResult;

public interface CollegeService extends IService<College>{


    /**
     * 分页查询学院信息
     * @param collegePageDTO
     * @return
     */
    PageResult pageCollege(CollegePageDTO collegePageDTO);

    /**
     * 修改学院信息
     * @param collegeDTO
     */
    void updateCollege(CollegeDTO collegeDTO);

    /**
     * 新增学院
     * @param addCollegeDTO
     */
    void addCollege(AddCollegeDTO addCollegeDTO);

    /**
     * 批量删除学院
     * @param ids
     */
    void deleteCollege(Long[] ids);

    /**
     * 新增用户到学院
     * @param addCollegeUserDTO
     */
    void addCollegeUser(AddCollegeUserDTO addCollegeUserDTO);

    /**
     * 删除学院中的用户
     * @param ids
     * @param collegeId
     */
    void deleteCollegeUser(Long[] ids, Long collegeId);

    /**
     * 分页查询学院中的用户
     * @param userCollegePageDTO
     * @return
     */
    PageResult pageCollegeUser(UserCollegePageDTO userCollegePageDTO);
}
