package top.kuanghua.vg.service;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Map;

/**
 * @author 猫哥
 * @date 2022-06-06 13:31
 **/
@Service
public class GeneratorService {
    @Resource
    private FrontVmsService frontVmsService;
    @Resource
    private BackVmsService backVmsService;
    
    public void GeneratorVmsTemplate(Map generatorData) {
        try {
            //生成前端模板
            this.frontVmsService.generatorFrontVms(generatorData);
            //生成后端模板
            this.backVmsService.generatorBackTemp(generatorData);
        } catch (IOException e) {
            throw new RuntimeException("生成模板报错" + e.getMessage());
        }
    }

}
