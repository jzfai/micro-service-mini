package top.kuanghua.vg.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import top.kuanghua.vg.service.GeneratorService;
import top.kuanghua.vg.utils.FrontVmsUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author 邝华
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
    private GeneratorService generatorService;


    @PostMapping("generatorVms")
    @ApiOperation(value = "generatorVms")
    public void generatorVms(HttpServletResponse response, @RequestBody Map generatorData) {
        //生成模板
        this.generatorService.GeneratorVmsTemplate(generatorData);
        response.setContentType("application/zip");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Access-Control-Expose-Headers", "exportFileName");
        response.setHeader("exportFileName", "velocity-temp.zip");
        //你压缩包路径
        FrontVmsUtils.downloadZip(response, FrontVmsUtils.outputZipPath + "velocity-temp.zip");
    }
}
