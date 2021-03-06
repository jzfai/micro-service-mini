package top.kuanghua.vg.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @Title: BaseConfig
 * @Description:
 * @Auther: kuanghua
 * @create 2020/8/23 21:52
 */
/*
 * 总结：1.@Configuration 下的@ComponentScan回将包下带有@Component扫描变成配置类，
 * 而@SpringBootApplication扫描的只会变成普通类
 * */
@Configuration
//mapper包扫描
@MapperScan(basePackages = {"top.kuanghua.vg.mapper"})
public class BaseConfig {

}
