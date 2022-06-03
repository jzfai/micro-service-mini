package top.kuanghua.vg.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.kuanghua.common.entity.ResResult;
import top.kuanghua.vg.service.DataBaseService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Map;

/**
 * @Title: DataBaseController
 * @Description:
 * @Auther: kuanghua
 * @create 2022/6/3 8:53
 */
@Api(tags = "获取数据库表和字段信息")
@RestController
@RequestMapping("dataBase")
@CrossOrigin
public class DataBaseController {
    @Resource
    private DataBaseService dataBaseService;

    @GetMapping("getAllTableFromDb")
    @ApiOperation(value = "mysql获取所有表")
    public ResResult getAllTableFromDb(String dbName) {
        ArrayList<Map> allTableFromDb = this.dataBaseService.getAllTableFromDb(dbName);
        return new ResResult().success(allTableFromDb);
    }

    /**
     * @param dbName
     * @param tbName
     * @return
     */
    @GetMapping("getAllColumnFromTb")
    @ApiOperation(value = "mysql获取表所有字段信息")
    public ResResult getAllColumnFromTb(String dbName, String tbName) {
        ArrayList<Map> allColumnFromTb = this.dataBaseService.getAllColumnFromTb(dbName, tbName);
        return new ResResult().success(allColumnFromTb);
    }
}
