package top.kuanghua.common.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Title: CommonParams
 * @Description:
 * @Auther: kuanghua
 * @create 2020/12/15 12:01
 */
@Data
@ApiModel
public class CommonParams extends CommonPageParams {
    @ApiModelProperty("开始时间")
    private String startTime;
    @ApiModelProperty("结束时间")
    private String endTime;
}
