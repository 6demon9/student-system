package com.itmang.controller;


import com.itmang.constant.MessageConstant;
import com.itmang.pojo.dto.AddCollegeDTO;
import com.itmang.pojo.dto.CollegeDTO;
import com.itmang.pojo.dto.CollegePageDTO;
import com.itmang.pojo.entity.College;
import com.itmang.pojo.entity.PageResult;
import com.itmang.pojo.entity.Result;
import com.itmang.service.CollegeService;
import com.itmang.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@Tag(name = "学院模块")
@RequestMapping("/college")
public class CollegeController {

    /**
     * 学院模块的功能
     * 分页条件查询学院(√)、查询学院具体信息(√)（权限：所有人）
     * 编辑学院的信息(√)、新增学院(√)、批量删除学院()（权限：管理员）
     * 添加用户到学院()、批量删除学院中的用户()、分页条件查询学院中的用户()（权限：老师、管理员）
     */

    @Autowired
    private CollegeService collegeService;


    /**
     * 分页条件查询学院
     * @param collegePageDTO
     * @return
     */
    @Operation(summary = "分页条件查询学院")
    @GetMapping("/page/college")
    public Result<PageResult> pageCollege(CollegePageDTO collegePageDTO){
        log.info("分页条件查询学院：{}",collegePageDTO);
        PageResult pageResult = collegeService.pageCollege(collegePageDTO);
        return Result.success(pageResult);
    }

    @Operation(summary = "查询学院具体信息")
    @GetMapping("/{id}")
    public Result<College> getCollegeById(@PathVariable Long id){
        log.info("查询学院具体信息：{}",id);
        College college = collegeService.getById(id);
        if(college == null){
            return Result.error(MessageConstant.USER_NOT_FOUND);
        }
        return Result.success(college);
    }

    /**
     * 编辑学院的信息
     * @param collegeDTO
     * @return
     */
    @Operation(summary = "编辑学院的信息")
    @PostMapping("/update")
    public Result updateCollege(@RequestBody CollegeDTO collegeDTO){
        log.info("编辑学院的信息：{}",collegeDTO);
        collegeService.updateCollege(collegeDTO);
        return Result.success();
    }

    /**
     * 新增学院
     * @param addCollegeDTO
     * @return
     */
    @Operation(summary = "新增学院")
    @PostMapping("/add")
    public Result addCollege(@RequestBody AddCollegeDTO addCollegeDTO){
        log.info("新增学院：{}",addCollegeDTO);
        collegeService.addCollege(addCollegeDTO);
        return Result.success();
    }

    @Operation(summary = "批量删除学院")
    @PostMapping("/delete/{ids}")
    public Result deleteCollege(@PathVariable Long[] ids){
        return Result.success();
    }

}
