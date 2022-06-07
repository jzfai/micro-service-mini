package top.kuanghua.vg.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.kuanghua.common.entity.CommonParams;
import top.kuanghua.vg.service.RepairInfoService;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author 邝华
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
}
