package xyz.liuzhuoming.simple.scheduler.job;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;

/**
 * 基于内存任务管理器
 *
 * @author liuzhuoming
 */
@Component
@ConditionalOnBean(JobManager.class)
@Slf4j
public class DefaultJobManager implements JobManager {

    /**
     * relatedId:jobData
     */
    public static Map<String, JobData> relatedIdWithJobData = new HashMap<>();
    /**
     * jobName:relatedId
     */
    public static Map<String, String> jobNameWithRelatedId = new HashMap<>();

    @Override
    public List<JobData> getUnExecutedJobs() {
        //do nothing
        return new ArrayList<>(0);
    }

    @Override
    public void addJob(JobData jobData) {
        relatedIdWithJobData.put(jobData.getRelatedId(), jobData);
        log.info(jobData.toString());
    }

    @Override
    public boolean isExist(String classname, String relatedId) {
        return relatedIdWithJobData.containsKey(relatedId);
    }

    @Override
    public void deleteJobByJobName(String jobName) {
        String relatedId = jobNameWithRelatedId.get(jobName);
        relatedIdWithJobData.remove(relatedId);
        jobNameWithRelatedId.remove(jobName);
    }

    @Override
    public String getJobNameByRelatedId(String className, String relatedId) {
        return relatedIdWithJobData.get(relatedId).getJobName();
    }

    @Override
    public Map<String, Object> getJobParamsByJobName(String jobName) {
        String relatedId = jobNameWithRelatedId.get(jobName);
        return relatedIdWithJobData.get(relatedId).getJobParams();
    }

    @Override
    public Date getExecuteTime(String jobName) {
        String relatedId = jobNameWithRelatedId.get(jobName);
        return relatedIdWithJobData.get(relatedId).getExecuteTime();
    }

    @Override
    public int getJobSum() {
        return relatedIdWithJobData.size();
    }
}
