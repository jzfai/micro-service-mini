package top.kuanghua.vg.controller;

import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import top.kuanghua.common.entity.ResResult;
import top.kuanghua.vg.service.BackVmsMulTableService;
import top.kuanghua.vg.service.GeneratorService;
import top.kuanghua.vg.utils.GeneratorTempUtils;
import top.kuanghua.vg.utils.ObjSelfUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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

    @ApiOperation(value = "批量上传模版生成模版")
    @PostMapping("uploadFiles")
    public void getUploadFileToVms(HttpServletResponse response,@RequestParam("file") List<MultipartFile> files,@RequestParam("jsonData") String  jsonData ) {
        String tmpSaveDir = GeneratorTempUtils.getTmpSaveDir();
        //接收文件并保存文件到本地
        for (MultipartFile file : files) {
            String filename = file.getOriginalFilename();
            File f = new File(tmpSaveDir+filename);
            //保存文件
            try {
                file.transferTo(f);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //获取数据并生成vm模版
        Map<String, Object> JsonMap=JSON.parseObject(jsonData, Map.class);
        //生成模板
        String exportFilePath= backVmsMulTableService.generatorTmpDirTemp(files,JsonMap,tmpSaveDir);
        response.setContentType("application/zip");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Access-Control-Expose-Headers", "exportFileName");
        response.setHeader("exportFileName", "back-temp-"+ ObjSelfUtils.getCurrentDateTimeTrim()+".zip");
        //你压缩包路径
        GeneratorTempUtils.downloadZip(response, exportFilePath);
    }
}
