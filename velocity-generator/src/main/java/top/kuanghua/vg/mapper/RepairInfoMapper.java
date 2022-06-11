package top.kuanghua.vg.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

public interface RepairInfoMapper {
    Page<Map> queryRepairInfo(Page<Map> pagination,
                              @Param("params") Map params);

    Map selectById(@Param("sn") String associationKey);
}
