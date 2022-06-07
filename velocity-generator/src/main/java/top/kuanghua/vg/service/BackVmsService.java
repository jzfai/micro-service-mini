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
 * @author 邝华
 * @date 2022-06-02 10:59
 **/
@Service
public class BackVmsService {
    public void generatorBackTemp(Map generatorData) throws IOException {
//        String string = FrontVmsUtils.readFileToString(FrontVmsUtils.getJsonDataDirPath() + "brandData.json");
//        System.out.println(string);
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

        Map<String, Object> projectOrAuthor = JSON.parseObject(JSON.toJSONString(jsonData.get("projectOrAuthor")), Map.class);

        String tbName = projectOrAuthor.get("tbName").toString();
        //entity
        Template entityTemp = GeneratorTempUtils.getMybatisPlusTemp("entity.vm");
        FileWriter entityWriter = new FileWriter(GeneratorTempUtils.TestDir + tbName + ".java");
        entityTemp.merge(context, entityWriter);
        entityWriter.close();
        //controller
        Template controllerTemp = GeneratorTempUtils.getMybatisPlusTemp("controller.vm");
        FileWriter controllerWriter = new FileWriter(GeneratorTempUtils.TestDir + tbName + "Controller.java");
        controllerTemp.merge(context, controllerWriter);
        controllerWriter.close();
        //service
        Template serviceTemp = GeneratorTempUtils.getMybatisPlusTemp("service.vm");
        FileWriter serviceWriter = new FileWriter(GeneratorTempUtils.TestDir + tbName + "Service.java");
        serviceTemp.merge(context, serviceWriter);
        serviceWriter.close();
        //mapper
        Template mapperTemp = GeneratorTempUtils.getMybatisPlusTemp("mapper.vm");
        FileWriter mapperWriter = new FileWriter(GeneratorTempUtils.TestDir + tbName + "Mapper.java");
        mapperTemp.merge(context, mapperWriter);
        mapperWriter.close();
        //生成zip包
        GeneratorTempUtils.createZipFile(GeneratorTempUtils.outputZipPath + "velocity-temp.zip", GeneratorTempUtils.needZipDir);
    }
}
