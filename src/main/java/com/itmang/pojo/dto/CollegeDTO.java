package com.itmang.pojo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(name = "CollegeDTO" ,description = "编辑学院信息DTO")
public class CollegeDTO implements Serializable {

    @Schema(name = "id",description = "学院id")
    private Long id;
    @Schema(name = "name",description = "学院名称")
    private String name;
    @Schema(name = "concise",description = "学院简介")
    private String concise;
    @Schema(name = "majors",description = "学院专业")
    private String majors;
}
