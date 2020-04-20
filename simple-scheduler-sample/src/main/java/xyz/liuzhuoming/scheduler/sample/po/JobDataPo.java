package xyz.liuzhuoming.scheduler.sample.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import lombok.Data;

/**
 * 任务po
 *
 * @author liuzhuoming
 */
@TableName("jobs")
@Data
public class JobDataPo {

    @TableId(type = IdType.AUTO)
    private Integer id;
    @TableField("related_id")
    private String relatedId;
    @TableField("job_name")
    private String jobName;
    @TableField("params_json")
    private String paramsJson;
    @TableField("execute_time")
    private Date executeTime;
    @TableField("class_name")
    private String className;
    @TableField("is_deleted")
    private int isDeleted;
}
