package top.kuanghua.vg.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 换件表(Replacement)表实体类
 *
 * @author kuanghua
 * @since 2021-10-26 11:38:43
 */
@Data
@ApiModel("换件表")
@TableName(value = "tb_replacement")
public class Replacement extends Model<Replacement> {
    @ApiModelProperty(value = "ID")
    private Integer id;
    @ApiModelProperty(value = "SN_ID")
    private String sn;
    //    @ApiModelProperty(value = "设备类型：0：T-BOX、1：VCI")
//    private Integer equipType;
    @ApiModelProperty(value = "新SN_ID")
    private String newSnId;
    @ApiModelProperty(value = "深度")
    private Integer depth;
    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;

    /**
     * 获取主键值
     *
     * @return 主键值
     */
    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}

