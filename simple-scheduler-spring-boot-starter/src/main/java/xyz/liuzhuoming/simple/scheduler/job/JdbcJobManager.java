package xyz.liuzhuoming.simple.scheduler.job;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import xyz.liuzhuoming.simple.scheduler.mapper.JobDataMapper;
import xyz.liuzhuoming.simple.scheduler.po.JobDataPo;

/**
 * 简单基于mysql的任务管理器实现
 *
 * @author liuzhuoming
 */
@Component
@RequiredArgsConstructor
public class JdbcJobManager implements JobManager {

    private final JobDataMapper jobDataMapper;

    @Override
    public List<JobData> getUnExecutedJobs() {
        LambdaQueryWrapper<JobDataPo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(JobDataPo::getIsDeleted, 0);
        List<JobDataPo> jobDataPoList = jobDataMapper.selectList(queryWrapper);
        return jobDataPoList.stream().map(po -> {
            JobData jobData = new JobData();
            jobData.setJobName(po.getJobName());
            jobData.setRelatedId(po.getRelatedId());
            jobData.setExecuteTime(po.getExecuteTime());
            jobData.setJobParams(JSONObject.parseObject(po.getParamsJson(), Map.class));
            jobData.setClassName(po.getClassName());
            return jobData;
        }).collect(Collectors.toList());
    }

    @Override
    public void addJob(JobData jobData) {
        JobDataPo jobDataPo = new JobDataPo();
        jobDataPo.setJobName(jobData.getJobName());
        jobDataPo.setExecuteTime(jobData.getExecuteTime());
        jobDataPo.setRelatedId(jobData.getRelatedId());
        jobDataPo.setParamsJson(JSONObject.toJSONString(jobData.getJobParams()));
        jobDataPo.setClassName(jobData.getClassName());
        jobDataMapper.insert(jobDataPo);
    }

    @Override
    public boolean isExist(String className, String relatedId) {
        LambdaQueryWrapper<JobDataPo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(JobDataPo::getRelatedId, relatedId);
        queryWrapper.eq(JobDataPo::getClassName, className);
        queryWrapper.eq(JobDataPo::getIsDeleted, 0);
        return jobDataMapper.selectCount(queryWrapper) > 0;
    }

    @Override
    public void deleteJobByJobName(String jobName) {
        LambdaUpdateWrapper<JobDataPo> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(JobDataPo::getJobName, jobName);
        JobDataPo po = new JobDataPo();
        po.setIsDeleted(1);
        jobDataMapper.update(po, updateWrapper);
    }

    @Override
    public String getJobNameByRelatedId(String className, String relatedId) {
        LambdaQueryWrapper<JobDataPo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(JobDataPo::getRelatedId, relatedId);
        queryWrapper.eq(JobDataPo::getClassName, className);
        return jobDataMapper.selectOne(queryWrapper).getJobName();
    }

    @Override
    public Map<String, Object> getJobParamsByJobName(String jobName) {
        LambdaQueryWrapper<JobDataPo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(JobDataPo::getJobName, jobName);
        String json = jobDataMapper.selectOne(queryWrapper).getParamsJson();
        return (Map<String, Object>) JSONObject.parseObject(json, Map.class);
    }

    @Override
    public Date getExecuteTime(String jobName) {
        LambdaQueryWrapper<JobDataPo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(JobDataPo::getJobName, jobName);
        return jobDataMapper.selectOne(queryWrapper).getExecuteTime();
    }

    @Override
    public int getJobSum() {
        LambdaQueryWrapper<JobDataPo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(JobDataPo::getIsDeleted, 0);
        return jobDataMapper.selectCount(queryWrapper);
    }
}
