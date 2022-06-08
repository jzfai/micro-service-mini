package top.kuanghua.vg.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 维修信息表实体类
 *
 * @author 熊猫哥
 * @since 2021-09-15 11:54:49
 */
@Data
@ApiModel("维修信息表")
public class RepairInfo {
    @ApiModelProperty(value = "品牌名称")
    private String name;
    @ApiModelProperty(value = "品牌名称")
    private String name2;
    @ApiModelProperty(value = "品牌名称")
    private String name3;

}
