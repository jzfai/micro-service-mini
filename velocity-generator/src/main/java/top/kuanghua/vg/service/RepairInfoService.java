package top.kuanghua.vg.service;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.kuanghua.common.entity.CommonParams;
import top.kuanghua.vg.entity.Repair;
import top.kuanghua.vg.entity.Replacement;
import top.kuanghua.vg.mapper.RepairInfoMapper;
import top.kuanghua.vg.mapper.RepairMapper;
import top.kuanghua.vg.mapper.ReplacementMapper;
import top.kuanghua.vg.vo.RepairInfoVo;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author 猫哥
 * @date 2022-06-07 11:43
 **/
@Service
public class RepairInfoService {

    @Resource
    private RepairInfoMapper repairInfoMapper;

    @Resource
    private RepairMapper repairMapper;

    @Resource
    private ReplacementMapper replacementMapper;

    public Page<Map> queryRepairInfo(CommonParams commonParams, Map repairVo) {
        return repairInfoMapper.queryRepairInfo(new Page<Map>(commonParams.getPageNum(), commonParams.getPageSize()), repairVo);
    }

    public Map selectById(String id) {
       return repairInfoMapper.selectById(id);
    }

    @Transactional
    public void insert(RepairInfoVo repairInfo) {
        this.repairMapper.insert(JSON.parseObject(JSON.toJSONString(repairInfo), Repair.class));
        this.replacementMapper.insert(JSON.parseObject(JSON.toJSONString(repairInfo), Replacement.class));
    }

    @Transactional
    public void updateById(RepairInfoVo repairInfo) {
        this.repairMapper.updateById(JSON.parseObject(JSON.toJSONString(repairInfo), Repair.class));
        this.replacementMapper.updateById(JSON.parseObject(JSON.toJSONString(repairInfo), Replacement.class));
    }

    @Transactional
    public void deleteById(Integer id) {
        this.repairMapper.deleteById(id);
        this.replacementMapper.deleteById(id);
    }
}
