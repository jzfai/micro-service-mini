package top.kuanghua.vg.execl.imp;

import lombok.Data;

import java.util.Date;

/**
 * @Title: RepairBo
 * @Description:
 * @Auther: kuanghua
 * @create 2021-11-02 14:51
 */


@Data
public class RepairExcelImp {
    private String sn;
    //目前状态：0：报废、1：在用、2：维修、3：更换、4：替换件"
    private Integer currentStatus;
    //1.蓝牙模块
    private Integer problemClassificationId;
    private String problemDescription;
    private Date feedbackTime;
    private String proposer;
    private String contactInfo;
    private Date recoveryDate;
    private Date returnDate;
    private String expressId;
    private String reason;
    private String processingMethod;
    private String remarks;
    private String newSnId;


}
