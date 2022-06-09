package com.ljryh.client.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class PageResponse implements Serializable {

    private static final long serialVersionUID = -3816770964793753188L;

    @ApiModelProperty(value = "页码", name = "pageNo", required = true, example = "1")
    @NotBlank(message = "页码不能为空")
    @TableField(exist = false)
    private Integer pageNo;
    @ApiModelProperty(value = "数量", name = "pageSize", required = true, example = "10")
    @NotBlank(message = "数量不能为空")
    @TableField(exist = false)
    private Integer pageSize;
    @TableField(exist = false)
    private Long generatedKey;
}
