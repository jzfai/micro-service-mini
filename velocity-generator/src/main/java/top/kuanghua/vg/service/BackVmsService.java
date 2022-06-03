package top.kuanghua.vg.service;

import com.alibaba.fastjson.JSON;
import org.apache.velocity.Template;
import org.apache.velocity.context.Context;
import top.kuanghua.vg.utils.FrontVmsUtils;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

/**
 * @author 邝华
 * @date 2022-06-02 10:59
 **/
public class BackVmsService {
    public static void main(String[] args) throws IOException {
        String string = FrontVmsUtils.readFileToString(FrontVmsUtils.getJsonDataDirPath() + "brandData.json");
        System.out.println(string);
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

        Map<String, Object> dbTableConfig = JSON.parseObject(jsonData.get("dbTableConfig").toString(), Map.class);

        //entity
        Template entityTemp = FrontVmsUtils.getMybatisPlusTemp("entity.vm");
        FileWriter entityWriter = new FileWriter(FrontVmsUtils.TestDir + dbTableConfig.get("tableNameCase") + ".java");
        entityTemp.merge(context, entityWriter);
        entityWriter.close();
        //controller
        Template controllerTemp = FrontVmsUtils.getMybatisPlusTemp("controller.vm");
        FileWriter controllerWriter = new FileWriter(FrontVmsUtils.TestDir + dbTableConfig.get("tableNameCase") + "Controller.java");
        controllerTemp.merge(context, controllerWriter);
        controllerWriter.close();
        //service
        Template serviceTemp = FrontVmsUtils.getMybatisPlusTemp("service.vm");
        FileWriter serviceWriter = new FileWriter(FrontVmsUtils.TestDir + dbTableConfig.get("tableNameCase") + "Service.java");
        serviceTemp.merge(context, serviceWriter);
        serviceWriter.close();
        //mapper
        Template mapperTemp = FrontVmsUtils.getMybatisPlusTemp("mapper.vm");
        FileWriter mapperWriter = new FileWriter(FrontVmsUtils.TestDir + dbTableConfig.get("tableNameCase") + "Mapper.java");
        mapperTemp.merge(context, mapperWriter);
        mapperWriter.close();

        //生成zip包
        FrontVmsUtils.createZipFile(FrontVmsUtils.outputZipPath + "velocity-temp.zip", FrontVmsUtils.needZipDir);
    }
}
