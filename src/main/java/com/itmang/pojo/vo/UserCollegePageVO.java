package com.itmang.pojo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Schema(name = "UserCollegePageVO",description = "分页查询学院中用户VO")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserCollegePageVO {

    @Schema(name = "collegeId",description = "学院id")
    private Long collegeId;
    @Schema(name = "userId",description = "用户id")
    private Long userId;
    @Schema(name = "collegeName",description = "学院名称")
    private String collegeName;
    @Schema(name = "number",description = "学号")
    private String number;
    @Schema(name = "name",description = "姓名")
    private String name;
    @Schema(name ="createTime",description = "创建时间")
    private LocalDateTime createTime;
}
