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
 * 任务管理器
 *
 * @author liuzhuoming
 */
@Component
@ConditionalOnBean(JobManager.class)
@Slf4j
public class DefaultJobManager implements JobManager {

    /**
     * id:jobName
     */
    public static Map<String, String> idWithJobName = new HashMap<>();
    /**
     * jobName:params
     */
    public static Map<String, Map<String, Object>> jobNameWithParams = new HashMap<>();
    /**
     * jobName:executeTime
     */
    public static Map<String, Date> jobNameWithExecuteTime = new HashMap<>();

    @Override
    public List<JobData> getUnExecutedJobs() {
        //do nothing
        return new ArrayList<>(0);
    }

    @Override
    public void addJob(JobData jobData) {
        idWithJobName.put(jobData.getRelatedId(), jobData.getJobName());
        jobNameWithParams.put(jobData.getJobName(), jobData.getJobParams());
        jobNameWithExecuteTime.put(jobData.getJobName(), jobData.getExecuteTime());
        log.info(jobData.toString());
    }

    @Override
    public boolean isExist(String classname, String relatedId) {
        return idWithJobName.containsKey(relatedId);
    }

    @Override
    public void deleteJobByJobName(String jobName) {
        String id = null;
        for (String key : idWithJobName.keySet()) {
            if (jobName.equals(idWithJobName.get(key))) {
                id = key;
                break;
            }
        }
        idWithJobName.remove(id);
        jobNameWithParams.remove(jobName);
        jobNameWithExecuteTime.remove(jobName);
    }

    @Override
    public String getJobNameByRelatedId(String className, String relatedId) {
        return idWithJobName.get(relatedId);
    }

    @Override
    public Map<String, Object> getJobParamsByJobName(String jobName) {
        return jobNameWithParams.get(jobName);
    }

    @Override
    public Date getExecuteTime(String jobName) {
        return jobNameWithExecuteTime.get(jobName);
    }

    @Override
    public int getJobSum() {
        return idWithJobName.size();
    }
}
