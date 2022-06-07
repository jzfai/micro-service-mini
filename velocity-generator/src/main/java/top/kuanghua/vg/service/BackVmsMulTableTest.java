package top.kuanghua.vg.service;

import com.alibaba.fastjson.JSON;
import org.apache.velocity.Template;
import org.apache.velocity.context.Context;
import top.kuanghua.vg.utils.GeneratorTempUtils;
import top.kuanghua.vg.utils.ObjSelfUtils;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

/**
 * @Title: BackVmsTestd
 * @Description:
 * @Auther: kuanghua
 * @create 2022/6/7 21:48
 */
public class BackVmsMulTableTest {
    public static void main(String[] args) throws IOException {
        String string = GeneratorTempUtils.readFileToString(GeneratorTempUtils.getJsonDataDirPath() + "multiTableData.json");
        System.out.println(string);
        Map<String, Object> jsonData = JSON.parseObject(string, Map.class);

        Context context = GeneratorTempUtils.getVelocityContext();
        context.put("totalData", jsonData);
        context.put("projectOrAuthor", jsonData.get("projectOrAuthor"));
        context.put("multiTableConfig", jsonData.get("multiTableConfig"));
        context.put("queryConfig", jsonData.get("queryConfig"));
        context.put("tableConfig", jsonData.get("tableConfig"));
        context.put("formConfig", jsonData.get("formConfig"));

        Map<String, Object> projectOrAuthor = ObjSelfUtils.changeToMap(jsonData.get("projectOrAuthor"));
        ArrayList<Map<String, Object>> multiTableConfig = ObjSelfUtils.changeToArrayMap(jsonData.get("multiTableConfig"));

        multiTableConfig.forEach((fItem) -> {
            //entity
            FileWriter entityWriter = null;
            try {
                Template entityTemp = GeneratorTempUtils.getMybatisPlusMulTbTemp("entity.vm");
                context.put("currentTbConfig", fItem);
                context.put("tableFieldArr", fItem.get("tableFieldArr"));
                entityWriter = new FileWriter(GeneratorTempUtils.TestDir + fItem.get("tableNameCase") + ".java");
                entityTemp.merge(context, entityWriter);
                entityWriter.close();
            } catch (IOException e) {
                throw new RuntimeException("生成实体类报错" + e);
            }
        });
        String tbName = projectOrAuthor.get("multiTableNameCase").toString();

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

        //xml
        Template xmlTemp = GeneratorTempUtils.getMybatisPlusTemp("xml.vm");
        FileWriter xmlWriter = new FileWriter(GeneratorTempUtils.TestDir + tbName + "Mapper.xml");
        mapperTemp.merge(context, xmlWriter);
        xmlWriter.close();
    }
}
