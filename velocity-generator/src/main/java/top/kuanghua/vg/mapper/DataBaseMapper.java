package top.kuanghua.vg.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import top.kuanghua.vg.execl.imp.RepairExcelImp;
import top.kuanghua.vg.vo.RepairVo;

import java.util.ArrayList;
import java.util.Map;

/**
 * @Title: DataBaseMapper
 * @Description:
 * @Auther: kuanghua
 * @create 2022/6/3 8:38
 */
public interface DataBaseMapper {

    /**
     * mysql获取所有表
     *
     * @param dbName
     * @return
     */
    @Select("SELECT table_name tableName,table_comment tableComment, create_time createTime  FROM  information_schema.tables WHERE TABLE_SCHEMA = #{dbName}")
    ArrayList<Map> getAllTableFromDb(@Param("dbName") String dbName);


    /**
     * mysql获取表所有字段信息
     *
     * @param tbName
     * @return
     */
    @Select("SELECT COL.* FROM INFORMATION_SCHEMA.COLUMNS COL Where TABLE_SCHEMA='micro-service-plus' AND  COL.TABLE_NAME=#{tbName}")
    ArrayList<Map> getAllColumnFromTb(@Param("dbName") String dbName, @Param("tbName") String tbName);


    Page<RepairExcelImp> queryRepairAndReplace(Page<RepairExcelImp> pagination,
                                               @Param("params") RepairVo params);
}
