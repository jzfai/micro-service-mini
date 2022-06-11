package top.kuanghua.vg.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import top.kuanghua.vg.service.BackVmsMulTableService;
import top.kuanghua.vg.service.GeneratorService;
import top.kuanghua.vg.utils.GeneratorTempUtils;
import top.kuanghua.vg.utils.ObjSelfUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author 猫哥
 * @email kuanghua@aulton.com
 * @date 2022-06-06 13:39
 * @Copyright Copyright (c) aulton Inc. All Rights Reserved.
 **/
@Api(tags = "模板生成")
@RestController
@RequestMapping("Generator")
@CrossOrigin
public class GeneratorController {
    @Resource
    private BackVmsMulTableService backVmsMulTableService;

//    @PostMapping("generatorFrontVms")
//    @ApiOperation(value = "generatorFrontVms")
//    public void generatorFrontVms(HttpServletResponse response, @RequestBody Map generatorData) {
//        //生成模板
//        this.generatorService.GeneratorVmsTemplate(generatorData);
//        response.setContentType("application/zip");
//        response.setCharacterEncoding("utf-8");
//        response.setHeader("Access-Control-Expose-Headers", "exportFileName");
//        response.setHeader("exportFileName", "velocity-temp.zip");
//        //你压缩包路径
//        GeneratorTempUtils.downloadZip(response, GeneratorTempUtils.outputZipPath + "velocity-temp.zip");
//    }

    @PostMapping("generatorBackVms")
    @ApiOperation(value = "generatorBackVms")
    public void generatorBackVms(HttpServletResponse response, @RequestBody Map generatorData) {
        //生成模板
        String exportFilePath=this.backVmsMulTableService.generatorMulTemp(generatorData);
        response.setContentType("application/zip");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Access-Control-Expose-Headers", "exportFileName");
        response.setHeader("exportFileName", "back-temp-"+ ObjSelfUtils.getCurrentDateTimeTrim()+".zip");
        //你压缩包路径
        GeneratorTempUtils.downloadZip(response, exportFilePath);
    }
}
