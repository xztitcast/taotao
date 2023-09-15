package com.bovine.taotao.admin.web.modelAndView;

import com.bovine.taotao.common.core.Constant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author eden
 * @date 2023/9/13 21:59:59
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MultipleRegionModelAndView {

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 多选区域限制
     */
    private List<List<String>> multipleRegionList;

    /**
     * 转换
     * @return
     */
    public List<String> convert() {
        return this.getMultipleRegionList().stream().map(e -> e.stream().collect(Collectors.joining(Constant.DELIMITER_COMMA))).collect(Collectors.toList());
    }
}
