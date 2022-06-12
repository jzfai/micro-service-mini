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
import java.util.UUID;

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



    //win
    public static final String ExportFileDir = "D:\\temp-dir\\export-dir\\";
    public static final String NeedZipDir = "D:\\temp-dir\\export-dir\\";
    public static final String OutputZipPath = "D:\\temp-dir\\";
    public static final String TmpSaveDir = "D:\\temp-dir\\";
    //mac和Linux
    public static final String MacExportFileDir = "/tmp/export-dir/";
    public static final String MacNeedZipDir = "/tmp/export-dir/";
    public static final String MacOutputZipPath = "/tmp/";
    public static final String MacTmpSaveDir = "/tmp/";
    /**
     *
     * @return
     * @author 邝华
     * @email kuanghua@aulton.com
     * @date 2022-06-10 13:54
     */
    public  static String getExportFileDir(){
        String os = System.getProperty("os.name");
        String path="";
        if(os.toLowerCase().startsWith("win")){
            path=  ExportFileDir;
        }else{
            path= MacExportFileDir;
        }
        return  fileMkdir(path);
    }

    /**
     * 获取临时存储目录路径
     * @return
     * @author 邝华
     * @email kuanghua@aulton.com
     * @date 2022-06-10 13:54
     */
    public  static String getTmpSaveDir(){
        String os = System.getProperty("os.name");
        String path="";
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        if(os.toLowerCase().startsWith("win")){
            path=  TmpSaveDir+uuid+File.separator;
        }else{
            path= MacTmpSaveDir+uuid+File.separator;
        }
        return  fileMkdir(path);
    }

    /**

     * @return
     * @author 邝华
     * @email kuanghua@aulton.com
     * @date 2022-06-10 13:53
     */
    public  static String getNeedZipDir(){
        String os = System.getProperty("os.name");
        String path="";
        if(os.toLowerCase().startsWith("win")){
            path=  NeedZipDir;
        }else{
            path= MacNeedZipDir;
        }
        return fileMkdir(path);
    }

    /**
     *
     * @return
     * @author 邝华
     * @email kuanghua@aulton.com
     * @date 2022-06-10 13:53
     */
    public  static String getOutputZipPath(){
        String os = System.getProperty("os.name");
        String path="";
        if(os.toLowerCase().startsWith("win")){
            path= OutputZipPath;
        }else{
            path= MacOutputZipPath;
        }
        return   fileMkdir(path);
    }

    /**
     *
     * @return
     * @author 邝华
     * @email kuanghua@aulton.com
     * @date 2022-06-10 13:56
     */
    public  static String fileMkdir(String path){
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        return path;
    }


    /**
     *
     * @return
     * @author 邝华
     * @email kuanghua@aulton.com
     * @date 2022-06-10 13:56
     */
    public  static String getAultonAssetDir(){
        String os = System.getProperty("os.name");
        String path="";
        if(os.toLowerCase().startsWith("win")){
            path= "D:\\work\\aulton\\front-generator\\aulton-asset";
        }else{
            path= "/data/front-generator/aulton-asset";
        }
        return   fileMkdir(path);
    }


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
    public static Template getTmpSaveDirTemp(String tmpSaveDir,String tempName) {
        VelocityEngine ve = new VelocityEngine();
        ve.setProperty(RuntimeConstants.FILE_RESOURCE_LOADER_PATH, tmpSaveDir);
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
