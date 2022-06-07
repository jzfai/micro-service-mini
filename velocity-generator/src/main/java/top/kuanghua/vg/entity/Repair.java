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
 * 售后维修表(Repair)表实体类
 *
 * @author kuanghua
 * @since 2021-10-26 13:50:02
 */
@ApiModel("售后维修表")
@TableName(value = "tb_repair")
@Data
public class Repair extends Model<Repair> {
    @ApiModelProperty(value = "ID")
    private Integer id;
    @ApiModelProperty(value = "SN_ID")
    private String sn;
    @ApiModelProperty(value = "快递信息ID")
    private String expressId;
    @ApiModelProperty(value = "问题归类ID")
    private Integer problemClassificationId;
    @ApiModelProperty(value = "目前状态：0：报废、1：在用、2：维修、3：更换、4：替换件")
    private Integer currentStatus;
    @ApiModelProperty(value = "问题描述")
    private String problemDescription;
    @ApiModelProperty(value = "反馈时间")
    private Date feedbackTime;
    @ApiModelProperty(value = "提出人")
    private String proposer;
    @ApiModelProperty(value = "联系方式")
    private String contactInfo;
    @ApiModelProperty(value = "回收日期")
    private Date recoveryDate;
    @ApiModelProperty(value = "返回日期")
    private Date returnDate;
    @ApiModelProperty(value = "返修分析的原因")
    private String reason;
    @ApiModelProperty(value = "处理方法")
    private String processingMethod;
    @ApiModelProperty(value = "备注")
    private String remarks;
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

