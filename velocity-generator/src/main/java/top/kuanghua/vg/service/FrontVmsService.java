package top.kuanghua.vg.service;

import com.alibaba.fastjson.JSON;
import org.apache.velocity.Template;
import org.apache.velocity.context.Context;
import org.springframework.stereotype.Service;
import top.kuanghua.vg.utils.GeneratorTempUtils;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

/**
 * @Title: FrontVmsService
 * @Description:
 * @Auther: kuanghua
 * @create 2022/6/1 22:18
 */
@Service
public class FrontVmsService {
    public void generatorFrontVms(Map generatorData) throws IOException {
        //String string = FrontVmsUtils.readFileToString(FrontVmsUtils.getJsonDataDirPath() + "data.json");
        Map<String, Object> jsonData = generatorData;
        Context context = GeneratorTempUtils.getVelocityContext();
        context.put("configData", jsonData);
        context.put("projectOrAuthor", jsonData.get("projectOrAuthor"));
        context.put("dbTableConfig", jsonData.get("dbTableConfig"));
        context.put("apiConfig", jsonData.get("apiConfig"));
        context.put("queryConfig", jsonData.get("queryConfig"));
        context.put("tableConfig", jsonData.get("tableConfig"));
        context.put("formConfig", jsonData.get("formConfig"));
        context.put("commonConfig", jsonData.get("commonConfig"));
        Template template = GeneratorTempUtils.getElementPlusTemp("CRUD.vm");
        FileWriter fileWriter = new FileWriter(GeneratorTempUtils.getExportFileDir() + "CRUD.vue");
        template.merge(context, fileWriter);
        fileWriter.close();
        //第二个模板
        Template addModal = GeneratorTempUtils.getElementPlusTemp("CRUDForm.vm");
        FileWriter addModalWriter = new FileWriter(GeneratorTempUtils.getExportFileDir() + "CRUDForm.vue");
        addModal.merge(context, addModalWriter);
        addModalWriter.close();

        //生成zip包
//        FrontVmsUtils.createZipFile(FrontVmsUtils.outputZipPath + "velocity-temp.zip", FrontVmsUtils.needZipDir);
    }

    public static void main(String[] args) throws IOException {
        String string = GeneratorTempUtils.readFileToString(GeneratorTempUtils.getJsonDataDirPath() + "tb_brand.json");
        Map<String, Object> jsonData = JSON.parseObject(string, Map.class);

        Context context = GeneratorTempUtils.getVelocityContext();
        context.put("configData", jsonData);
        context.put("companyOrAuthor", jsonData.get("companyOrAuthor"));
        context.put("dbTableConfig", jsonData.get("dbTableConfig"));
        context.put("apiConfig", jsonData.get("apiConfig"));
        context.put("queryConfig", jsonData.get("queryConfig"));
        context.put("tableConfig", jsonData.get("tableConfig"));
        context.put("formConfig", jsonData.get("formConfig"));
        context.put("commonConfig", jsonData.get("commonConfig"));
        Template template = GeneratorTempUtils.getElementPlusTemp("CRUD.vm");
        FileWriter fileWriter = new FileWriter(GeneratorTempUtils.getExportFileDir() + "CRUD.vue");
        template.merge(context, fileWriter);
        fileWriter.close();
        //第二个模板
        Template addModal = GeneratorTempUtils.getElementPlusTemp("CRUDForm.vm");
        FileWriter addModalWriter = new FileWriter(GeneratorTempUtils.getExportFileDir() + "CRUDForm.vue");
        addModal.merge(context, addModalWriter);
        addModalWriter.close();
    }
}
