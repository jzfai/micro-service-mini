package top.kuanghua.vg.service;

import com.alibaba.fastjson.JSON;
import org.apache.velocity.Template;
import org.apache.velocity.context.Context;
import org.springframework.stereotype.Service;
import top.kuanghua.vg.utils.FrontVmsUtils;

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
        Context context = FrontVmsUtils.getVelocityContext();
        context.put("configData", jsonData);
        context.put("projectOrAuthor", jsonData.get("projectOrAuthor"));
        context.put("dbTableConfig", jsonData.get("dbTableConfig"));
        context.put("apiConfig", jsonData.get("apiConfig"));
        context.put("queryConfig", jsonData.get("queryConfig"));
        context.put("tableConfig", jsonData.get("tableConfig"));
        context.put("formConfig", jsonData.get("formConfig"));
        context.put("commonConfig", jsonData.get("commonConfig"));
        Template template = FrontVmsUtils.getElementPlusTemp("CRUD.vm");
        FileWriter fileWriter = new FileWriter(FrontVmsUtils.TestDir + "CRUD.vue");
        template.merge(context, fileWriter);
        fileWriter.close();
        //第二个模板
        Template addModal = FrontVmsUtils.getElementPlusTemp("CRUDForm.vm");
        FileWriter addModalWriter = new FileWriter(FrontVmsUtils.TestDir + "CRUDForm.vue");
        addModal.merge(context, addModalWriter);
        addModalWriter.close();

        //生成zip包
//        FrontVmsUtils.createZipFile(FrontVmsUtils.outputZipPath + "velocity-temp.zip", FrontVmsUtils.needZipDir);
    }

    public static void main(String[] args) throws IOException {
        String string = FrontVmsUtils.readFileToString(FrontVmsUtils.getJsonDataDirPath() + "tb_brand.json");
        Map<String, Object> jsonData = JSON.parseObject(string, Map.class);

        Context context = FrontVmsUtils.getVelocityContext();
        context.put("configData", jsonData);
        context.put("companyOrAuthor", jsonData.get("companyOrAuthor"));
        context.put("dbTableConfig", jsonData.get("dbTableConfig"));
        context.put("apiConfig", jsonData.get("apiConfig"));
        context.put("queryConfig", jsonData.get("queryConfig"));
        context.put("tableConfig", jsonData.get("tableConfig"));
        context.put("formConfig", jsonData.get("formConfig"));
        context.put("commonConfig", jsonData.get("commonConfig"));
        Template template = FrontVmsUtils.getElementPlusTemp("CRUD.vm");
        FileWriter fileWriter = new FileWriter(FrontVmsUtils.TestDir + "CRUD.vue");
        template.merge(context, fileWriter);
        fileWriter.close();
        //第二个模板
        Template addModal = FrontVmsUtils.getElementPlusTemp("CRUDForm.vm");
        FileWriter addModalWriter = new FileWriter(FrontVmsUtils.TestDir + "CRUDForm.vue");
        addModal.merge(context, addModalWriter);
        addModalWriter.close();
    }
}
