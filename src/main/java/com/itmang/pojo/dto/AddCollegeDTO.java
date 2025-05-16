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
@Schema(name = "AddCollegeDTO" ,description = "新增学院信息DTO")
public class AddCollegeDTO implements Serializable {

    @Schema(name = "name",description = "学院名称")
    private String name;
    @Schema(name = "concise",description = "学院简介")
    private String concise;
    @Schema(name = "majors",description = "学院专业")
    private String majors;
}
