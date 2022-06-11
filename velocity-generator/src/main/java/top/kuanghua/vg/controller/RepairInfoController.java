package top.kuanghua.vg.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import top.kuanghua.common.entity.CommonParams;
import top.kuanghua.common.entity.ResResult;
import top.kuanghua.vg.entity.Brand;
import top.kuanghua.vg.service.RepairInfoService;
import top.kuanghua.vg.vo.RepairInfoVo;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author 猫哥
 * @email kuanghua@aulton.com
 * @date 2022-06-06 13:39
 * @Copyright Copyright (c) aulton Inc. All Rights Reserved.
 **/
@Api(tags = "维修信息")
@RestController
@RequestMapping("repairInfo")
@CrossOrigin
public class RepairInfoController {
    @Resource
    private RepairInfoService repairInfoService;

    @ApiOperation(value = "多表测试")
    @GetMapping("queryRepairInfo")
    public Page<Map> queryRepairInfo(CommonParams commonParams, Map repairVo) {
        return repairInfoService.queryRepairInfo(commonParams, repairVo);
    }


    /**
     * 新增数据
     *
     * @param repairInfo 实体对象
     * @return 新增结果
     */
    @ApiOperation(value = "新增数据")
    @PostMapping("insert")
    public ResResult insert(@RequestBody RepairInfoVo repairInfo) {
        this.repairInfoService.insert(repairInfo);
        return new ResResult().success();
    }


    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectById")
    @ApiOperation(value = "通过联合key查询连表数据")
    public ResResult selectById(@RequestParam("id") String id) {
        return new ResResult().success(repairInfoService.selectById(id));
    }

    /**
     * 修改数据
     *
     * @param repairInfo 实体对象
     * @return 修改结果
     */
    @ApiOperation(value = "根据id修改数据")
    @PutMapping("updateById")
    public ResResult updateById(@RequestBody RepairInfoVo repairInfo) {
        this.repairInfoService.updateById(repairInfo);
        return new ResResult().success();
    }


    /**
     * 删除项根据主键key
     * @param id
     * @return
     */
    @DeleteMapping("deleteById")
    public ResResult deleteById(@RequestParam("id") Integer id) {
        this.repairInfoService.deleteById(id);
        return new ResResult().success();
    }
}
