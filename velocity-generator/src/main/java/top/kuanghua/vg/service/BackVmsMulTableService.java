package top.kuanghua.vg.service;

import com.alibaba.fastjson.JSON;
import org.apache.velocity.Template;
import org.apache.velocity.context.Context;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import top.kuanghua.vg.utils.GeneratorTempUtils;
import top.kuanghua.vg.utils.ObjSelfUtils;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Title: BackVmsTestd
 * @Description:
 * @Auther: kuanghua
 * @create 2022/6/7 21:48
 */
@Service
public class BackVmsMulTableService {
    public String generatorMulTemp(Map jsonData) {
        Context context = GeneratorTempUtils.getVelocityContext();
        context.put("totalData", jsonData);
        context.put("projectOrAuthor", jsonData.get("projectOrAuthor"));
        context.put("multiTableConfig", jsonData.get("multiTableConfig"));
        context.put("queryConfig", jsonData.get("queryConfig"));
        context.put("tableConfig", jsonData.get("tableConfig"));
        context.put("formConfig", jsonData.get("formConfig"));
        context.put("dbTableConfig", jsonData.get("dbTableConfig"));

        Map<String, Object> dbTableConfig = ObjSelfUtils.changeToMap(jsonData.get("dbTableConfig"));
        ArrayList<Map<String, Object>> multiTableConfig = ObjSelfUtils.changeToArrayMap(jsonData.get("multiTableConfig"));

        multiTableConfig.forEach((fItem) -> {
            //entity
            FileWriter entityWriter = null;
            try {
                Template entityTemp = GeneratorTempUtils.getMybatisPlusMulTbTemp("entity.vm");
                context.put("currentTbConfig", fItem);
                context.put("tableFieldArr", fItem.get("tableFieldArr"));
                entityWriter = new FileWriter(GeneratorTempUtils.getExportFileDir() + fItem.get("tableNameCase") + ".java");
                entityTemp.merge(context, entityWriter);
                entityWriter.close();

                //single-mapper
                Template mapperTemp = GeneratorTempUtils.getMybatisPlusMulTbTemp("mapper.vm");
                FileWriter mapperWriter = new FileWriter(GeneratorTempUtils.getExportFileDir() + fItem.get("tableNameCase") + "Mapper.java");
                mapperTemp.merge(context, mapperWriter);
                mapperWriter.close();
            } catch (IOException e) {
                throw new RuntimeException("生成实体类报错" + e);
            }
        });
        String tbName = dbTableConfig.get("multiTableNameCase").toString();

        try {
            //controller
            Template controllerTemp = GeneratorTempUtils.getMybatisPlusMulTbTemp("controllerMul.vm");
            FileWriter controllerWriter = new FileWriter(GeneratorTempUtils.getExportFileDir() + tbName + "Controller.java");
            controllerTemp.merge(context, controllerWriter);
            controllerWriter.close();
            //service
            Template serviceTemp = GeneratorTempUtils.getMybatisPlusMulTbTemp("serviceMul.vm");
            FileWriter serviceWriter = new FileWriter(GeneratorTempUtils.getExportFileDir() + tbName + "Service.java");
            serviceTemp.merge(context, serviceWriter);
            serviceWriter.close();


            //mul-entity
            Template mapperMulTemp = GeneratorTempUtils.getMybatisPlusMulTbTemp("mapperMul.vm");
            FileWriter mapperMulWriter = new FileWriter(GeneratorTempUtils.getExportFileDir() + tbName + "Mapper.java");
            mapperMulTemp.merge(context, mapperMulWriter);
            mapperMulWriter.close();

            //entity-vo
            Template entityVoTemp = GeneratorTempUtils.getMybatisPlusMulTbTemp("entityVo.vm");
            FileWriter entityVoWriter = new FileWriter(GeneratorTempUtils.getExportFileDir() + tbName + "Vo.java");
            entityVoTemp.merge(context, entityVoWriter);
            entityVoWriter.close();

            //xml
            Template xmlTemp = GeneratorTempUtils.getMybatisPlusMulTbTemp("xmlMul.vm");
            FileWriter xmlWriter = new FileWriter(GeneratorTempUtils.getExportFileDir() + tbName + "Mapper.xml");
            xmlTemp.merge(context, xmlWriter);
            xmlWriter.close();

            String exportFilePath = GeneratorTempUtils.getOutputZipPath() + ObjSelfUtils.getCurrentDateTimeTrim() + ".zip";
            //生成zip包
            GeneratorTempUtils.createZipFile(exportFilePath, GeneratorTempUtils.getNeedZipDir());

            return exportFilePath;
        } catch (IOException e) {
            throw new RuntimeException("生成实体类报错" + e);
        }
    }

    public String generatorTmpDirTemp(List<MultipartFile> files, Map jsonData, String tmpSaveDir) {
        Context context = GeneratorTempUtils.getVelocityContext();
        context.put("totalData", jsonData);
        context.put("projectOrAuthor", jsonData.get("projectOrAuthor"));
        context.put("multiTableConfig", jsonData.get("multiTableConfig"));
        context.put("queryConfig", jsonData.get("queryConfig"));
        context.put("tableConfig", jsonData.get("tableConfig"));
        context.put("formConfig", jsonData.get("formConfig"));
        context.put("dbTableConfig", jsonData.get("dbTableConfig"));
        files.forEach((file) -> {
            String originalFilename = file.getOriginalFilename();
            Template xmlTemp = GeneratorTempUtils.getTmpSaveDirTemp(tmpSaveDir, originalFilename);
            try {
                FileWriter xmlWriter = new FileWriter(GeneratorTempUtils.getExportFileDir() + originalFilename);
                xmlTemp.merge(context, xmlWriter);
                xmlWriter.close();
            } catch (IOException e) {
                throw new RuntimeException("生成临时模版报错" + e);
            }
        });

        String exportFilePath = GeneratorTempUtils.getOutputZipPath() + ObjSelfUtils.getCurrentDateTimeTrim() + ".zip";
        //生成zip包
        GeneratorTempUtils.createZipFile(exportFilePath, GeneratorTempUtils.getNeedZipDir());
        return exportFilePath;
    }
}
