package com.ax.common.util.exception;

import com.ax.common.util.result.ResultCodeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 自定义全局异常类
 *
 * @author xing
 * @date 2022/3/20 01:36
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(value = "自定义全局异常类")
@ToString
public class ServiceException extends RuntimeException {

    @ApiModelProperty(value = "异常状态码")
    private Integer code;

    /**
     * 通过状态码和错误消息创建异常对象
     */

    public ServiceException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public ServiceException(ResultCodeEnum codeEnum) {
        super(codeEnum.getMessage());
        this.code = codeEnum.getCode();
    }

    public static ServiceException message(String message) {
        return new ServiceException(201, message);
    }

}
