package com.itmang.pojo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(name = "AddCollegeUserDTO" ,description = "新增用户到学院DTO")
public class AddCollegeUserDTO implements Serializable {

    @Schema(name = "userId",description = "用户id")
    private Long userId;
    @Schema(name = "collegeId",description = "学院id")
    private Long collegeId;
}
