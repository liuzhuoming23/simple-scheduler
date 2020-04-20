package xyz.liuzhuoming.simple.scheduler.scheduler;

import java.util.Date;
import java.util.List;
import java.util.Map;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.stereotype.Component;
import xyz.liuzhuoming.simple.scheduler.job.JobData;
import xyz.liuzhuoming.simple.scheduler.job.JobManager;
import xyz.liuzhuoming.simple.scheduler.prop.SchedulerProperties;

/**
 * quartz操作
 *
 * @author liuzhuoming
 */
@Slf4j
@Component
@Setter
public class SchedulerTemplateImpl implements SchedulerTemplate {

    private final SchedulerFactory schedulerFactory = new StdSchedulerFactory();

    private SchedulerProperties schedulerProperties;
    private JobManager jobManager;
    private KeyGenerator keyGenerator;

    @Override
    public String generateKey() {
        return keyGenerator.generate();
    }

    @Override
    public Scheduler getScheduler() {
        try {
            //不自定义调度器名称的话quartz默认返回同一个调度器
            return schedulerFactory.getScheduler();
        } catch (SchedulerException e) {
            log.error("Init scheduler failed, {}", e.getMessage());
            return null;
        }
    }

    @Override
    public void start() {
        try {
            getScheduler().start();
            log.info("Scheduler is start: {}", getScheduler().isStarted());
        } catch (SchedulerException e) {
            log.error("Scheduler start failed, {}", e.getMessage());
        }
    }

    @Override
    public void restore() {
        List<JobData> jobDataList = jobManager.getUnExecutedJobs();
        jobDataList.forEach(jobData -> {
            String className = jobData.getClassName();
            String jobName = jobData.getJobName();

            String jobGroupName = "group:" + className;
            String triggerName = "trigger:" + generateKey();
            String triggerGroupName = "triggerGroup:" + className;

            try {
                Class clazz = Class.forName(className);

                if (jobData.getExecuteTime().before(new Date())) {
                    this.deleteByJobName(clazz, jobName);
                    throw new SchedulerException("Execute Time is before current time");
                }

                JobDetail jobDetail = JobBuilder
                    .newJob(clazz)
                    .withIdentity(jobName, jobGroupName)
                    .build();
                Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity(triggerName, triggerGroupName)
                    .startAt(jobData.getExecuteTime())
                    .build();
                getScheduler().scheduleJob(jobDetail, trigger);
                log.info("Restore jobDetail successful, {}#{}", jobName, jobGroupName);
            } catch (SchedulerException | ClassNotFoundException e) {
                log.error("Restore jobDetail failed, {}", e.getMessage());
            }

        });
    }

    @Override
    public <JOB extends Job> void add(Class<JOB> clazz, String relatedId,
        Map<String, Object> params,
        Date executeTime) {
        try {
            if (jobManager.getJobSum() >= schedulerProperties.getSchedulerMaxSize()) {
                throw new SchedulerException("The maximum number of jobs has exceeded");
            }
            String jobName = "job:" + generateKey();
            String jobGroupName = "group:" + clazz.getName();
            String triggerName = "trigger:" + generateKey();
            String triggerGroupName = "triggerGroup:" + clazz.getName();

            if (jobManager.isExist(clazz.getName(), relatedId)) {
                throw new SchedulerException(
                    "Job {" + clazz.getName() + "#" + relatedId + "} is existed");
            }

            JobData jobData = new JobData();
            jobData.setRelatedId(relatedId);
            jobData.setJobName(jobName);
            jobData.setJobParams(params);
            jobData.setExecuteTime(executeTime);
            jobData.setClassName(clazz.getName());
            jobManager.addJob(jobData);

            JobDetail jobDetail = JobBuilder
                .newJob(clazz)
                .withIdentity(jobName, jobGroupName)
                .build();
            Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity(triggerName, triggerGroupName)
                .startAt(executeTime)
                .build();
            getScheduler().scheduleJob(jobDetail, trigger);

            log.info("Add jobDetail successful, {}#{}", jobName, jobGroupName);
        } catch (SchedulerException e) {
            log.error("Add jobDetail failed, {}", e.getMessage());
        }
    }

    @Override
    public <JOB extends Job> void deleteByRelatedId(Class<JOB> clazz, String relatedId) {
        String jobName = jobManager.getJobNameByRelatedId(clazz.getName(), relatedId);
        this.deleteByJobName(clazz, jobName);
    }

    @Override
    public <JOB extends Job> void deleteByJobName(Class<JOB> clazz, String jobName) {
        JobKey jobKey = new JobKey(jobName, "group:" + clazz.getName());
        try {
            getScheduler().deleteJob(jobKey);
            jobManager.deleteJobByJobName(jobName);
            log.info("Delete jobDetail successful, {}#{}", jobName, "group:" + clazz.getName());
        } catch (SchedulerException e) {
            log.error("Delete jobDetail failed, {}", e.getMessage());
        }
    }
}
