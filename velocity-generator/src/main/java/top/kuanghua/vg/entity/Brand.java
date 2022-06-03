package top.kuanghua.vg.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 品牌表实体类
 *
 * @author 熊猫哥
 * @since 2021-09-15 11:54:49
 */
@Data
@ApiModel("品牌表")
@TableName(value = "tb_brand")
public class Brand extends Model<Brand> {
    @ApiModelProperty(value = "主键")
    private Integer id;
    @ApiModelProperty(value = "品牌名称")
    private String name;
    @ApiModelProperty(value = "品牌图片地址")
    private String image;
    @ApiModelProperty(value = "品牌的首字母")
    private String letter;
    @ApiModelProperty(value = "排序")
    private Integer seq;

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
