package top.kuanghua.vg.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Title: Repair
 * @Description:
 * @Auther: kuanghua
 * @create 2021/10/26 13:53
 */
@Data
@ApiModel("售后维修表")
public class RepairVo {
    @ApiModelProperty(value = "SN_ID")
    private String sn;
}
