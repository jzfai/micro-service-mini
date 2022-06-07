package top.kuanghua.vg.utils;

import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.apache.velocity.Template;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.context.Context;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.tools.ToolManager;
import org.springframework.util.ClassUtils;
import org.springframework.util.StreamUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * @Title: FrontVmsUtils
 * @Description: 前端模板生成工具类
 * @Auther: kuanghua
 * @create 2022-05-17 13:57
 */
public class GeneratorTempUtils {
    //velocity tools配置文件路径
    public static final String ToolManagerConfigPath = "velocity-tools.xml";
    public static final String ElementPlusDir = "front-vms" + File.separator + "element-plus";
    public static final String MybatisPlusDir = "back-vms" + File.separator + "mybatis-plus";
    public static final String MybatisPlusMulTbDir = "back-vms" + File.separator + "mybatis-plus-multb";
    public static final String TestDir = "D:\\github\\velocity-test\\";
    public static final String needZipDir = "D:\\github\\velocity-test";
    public static final String outputZipPath = "D:\\github\\";

    /**
     * @return velocity Context
     */
    public static Context getVelocityContext() {
        // 加载toolbox
        ToolManager manager = new ToolManager();
        manager.configure(ToolManagerConfigPath);
        return manager.createContext();
    }

    /**
     * @param response
     * @param path     导出文件的路径
     */
    public static void downloadZip(HttpServletResponse response, String path) {
        File file = new File(path);
        if (!file.exists()) {
            throw new RuntimeException("文件不存在");
        }
        try {
            ZipFile zipFile = new ZipFile(file);
            InputStream fis = new FileInputStream(zipFile.getFile());
            IOUtils.copy(fis, response.getOutputStream());
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //    if (file.exists()) {
        //        file.delete();
        //    }
    }

    /**
     * @param tempName 模板名称
     * @return Template
     */
    public static Template getMybatisPlusTemp(String tempName) {
        VelocityEngine ve = new VelocityEngine();
        ve.setProperty(RuntimeConstants.FILE_RESOURCE_LOADER_PATH, getResourceDirPath(MybatisPlusDir));
        ve.init();
        return ve.getTemplate(tempName);
    }

    /**
     * @param tempName 模板名称
     * @return Template
     */
    public static Template getMybatisPlusMulTbTemp(String tempName) {
        VelocityEngine ve = new VelocityEngine();
        ve.setProperty(RuntimeConstants.FILE_RESOURCE_LOADER_PATH, getResourceDirPath(MybatisPlusMulTbDir));
        ve.init();
        return ve.getTemplate(tempName);
    }


    /**
     * @param tempName 模板名称
     * @return Template
     */
    public static Template getElementPlusTemp(String tempName) {
        VelocityEngine ve = new VelocityEngine();
        ve.setProperty(RuntimeConstants.FILE_RESOURCE_LOADER_PATH, getResourceDirPath(ElementPlusDir));
        ve.init();
        return ve.getTemplate(tempName);
    }

    /**
     * 获取Resurce下的目录路径
     *
     * @return 得到的目录名称
     */
    public static String getResourceDirPath(String dirName) {
        return ClassUtils.getDefaultClassLoader().getResource("").getPath() + dirName;
    }

    /**
     * 获取JsonData下的目录路径
     *
     * @return 得到的目录名称
     */
    public static String getJsonDataDirPath() {
        return ClassUtils.getDefaultClassLoader().getResource("json-data").getPath() + File.separator;
    }

    /**
     * @param filePath 相对于resource的路径
     * @return 读取的文件内容
     * @throws IOException
     */
    public static String readFileToString(String filePath) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(new File(filePath));
//        ClassPathResource classPathResource = new ClassPathResource(filePath);
//        InputStream inputStream = classPathResource.getInputStream();
        String s = StreamUtils.copyToString(fileInputStream, StandardCharsets.UTF_8);
        fileInputStream.close();
        return s;
    }


    /**
     * @param outputDirPath 导出zip包的路径 如  file://xxx.zip
     * @param needZipDir    需要压缩的目录名字 如 file://doc
     */
    public static void createZipFile(String outputDirPath, String needZipDir) {
        try {
            new ZipFile(outputDirPath).addFolder(new File(needZipDir));
        } catch (ZipException e) {
            e.printStackTrace();
        }
    }


}
