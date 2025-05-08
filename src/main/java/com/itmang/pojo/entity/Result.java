package com.itmang.pojo.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "响应结果")
public class Result<T> implements Serializable {
    @ApiModelProperty("响应码")
    private Integer code;//响应码（1表示响应成功，0表示响应失败）
    @ApiModelProperty("响应信息")
    private String msg;//响应信息
    @ApiModelProperty("响应数据")
    private T data;//响应成功返回的数据

    public static <T> Result<T> success() {
        return new Result<T>(1,null,null);
    }

    public static <T> Result<T> success(T object) {
        return new Result<T>(1, null, object);
    }

    public static <T> Result<T> error(String msg) {
        return new Result<T>(0, msg, null);
    }

}

