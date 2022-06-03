package top.kuanghua.vg.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import top.kuanghua.common.entity.CommonParams;
import top.kuanghua.common.entity.ResResult;
import top.kuanghua.vg.entity.Brand;
import top.kuanghua.vg.service.BrandService;
import top.kuanghua.vg.utils.ObjectUtilsSelf;

import javax.annotation.Resource;
import java.util.List;

/**
 * 品牌表Controller
 *
 * @author 熊猫哥
 * @since 2021-09-15 11:54:49
 */
@Api(tags = "品牌表(Brand)")
@RestController
@RequestMapping("brand")
@CrossOrigin
public class BrandController {

    @Resource
    private BrandService brandService;

    /**
     * 分页查询所有数据
     *
     * @param brand 查询实体
     * @return ResResult
     */
    @GetMapping("selectPage")
    @ApiOperation(value = "分页查询所有数据")
    public ResResult selectPage(Brand brand, CommonParams commonParams) {
        QueryWrapper<Brand> queryWrapper = new QueryWrapper<>();
//品牌名称
        if (ObjectUtilsSelf.isNotEmpty(brand.getName())) {
            queryWrapper.like("name", brand.getName());
        }
        if (ObjectUtilsSelf.isNotEmpty(commonParams.getStartTime())) {
            queryWrapper.between("create_time", commonParams.getStartTime(), commonParams.getEndTime());
        }
        queryWrapper.select("id,name,image,letter,seq");

        Page<Brand> brandPage = this.brandService.selectPage(commonParams.getPageNum(), commonParams.getPageSize(),
                queryWrapper);
        return new ResResult().success(brandPage);
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectById")
    @ApiOperation(value = "通过id主键查询单条数据")
    public ResResult selectById(@RequestParam("id") Integer id) {
        return new ResResult().success(this.brandService.selectById(id));
    }

    /**
     * @Description: 根据id数组查询品牌列表
     * @Param: idList id数组
     * @return: ids列表数据
     */
    @ApiOperation(value = "根据id数组查询品牌列表")
    @PostMapping("selectBatchIds")
    public ResResult selectBatchIds(@RequestParam("idList") List<Integer> idList) {
        return new ResResult().success(this.brandService.selectBatchIds(idList));
    }

    /**
     * 新增数据
     *
     * @param brand 实体对象
     * @return 新增结果
     */
    @ApiOperation(value = "新增数据")
    @PostMapping("insert")
    public ResResult insert(@RequestBody Brand brand) {
        return new ResResult().success(this.brandService.insert(brand));
    }

    /**
     * 修改数据
     *
     * @param brand 实体对象
     * @return 修改结果
     */
    @ApiOperation(value = "根据id修改数据")
    @PutMapping("updateById")
    public ResResult updateById(@RequestBody Brand brand) {
        return new ResResult().success(this.brandService.updateById(brand));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @ApiOperation(value = "根据id数组删除数据")
    @DeleteMapping("deleteBatchIds")
    public ResResult deleteBatchIds(@RequestBody List<Long> idList) {
        return new ResResult().success(this.brandService.deleteBatchIds(idList));
    }

    @DeleteMapping("deleteById")
    public ResResult deleteById(@RequestParam("id") Integer id) {
        return new ResResult().success(this.brandService.deleteById(id));
    }
}
