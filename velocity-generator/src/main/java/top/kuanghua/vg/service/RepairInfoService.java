package top.kuanghua.vg.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Service;
import top.kuanghua.common.entity.CommonParams;
import top.kuanghua.vg.mapper.RepairInfoMapper;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author 邝华
 * @date 2022-06-07 11:43
 **/
@Service
public class RepairInfoService {

    @Resource
    private RepairInfoMapper repairInfoMapper;

    public Page<Map> queryRepairInfo(CommonParams commonParams, Map repairVo) {
        return repairInfoMapper.queryRepairInfo(new Page<Map>(commonParams.getPageNum(), commonParams.getPageSize()), repairVo);
    }
}
