package com.bovine.taotao.admin.web.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author eden
 * @date 2023/7/1 23:51:51
 */
@Getter
@Setter
public class SmsModel implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 手机号
     */
    @NotBlank(message = "手机号不能为空!")
    @Pattern(regexp = "/^1[0-9]{10}$/", message = "非法的手机号格式")
    private String mobile;

    /**
     * 参数(有可能是token有可能是code)
     */
    @NotBlank(message = "参数不能为空!")
    private String param;
}
