package xyz.liuzhuoming.scheduler.sample.config;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import xyz.liuzhuoming.scheduler.sample.mapper.JobDataMapper;
import xyz.liuzhuoming.scheduler.sample.po.MyJobDataPo;
import xyz.liuzhuoming.simple.scheduler.job.JobData;
import xyz.liuzhuoming.simple.scheduler.job.JobManager;

/**
 * 简单基于mysql的任务管理器实现
 *
 * @author liuzhuoming
 */
@Component
@RequiredArgsConstructor
public class MyJobManager implements JobManager {

    private final JobDataMapper jobDataMapper;

    @Override
    public List<JobData> getUnExecutedJobs() {
        LambdaQueryWrapper<MyJobDataPo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MyJobDataPo::getIsDeleted, 0);
        List<MyJobDataPo> myJobDataPoList = jobDataMapper.selectList(queryWrapper);
        return myJobDataPoList.stream().map(po -> {
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
        MyJobDataPo myJobDataPo = new MyJobDataPo();
        myJobDataPo.setJobName(jobData.getJobName());
        myJobDataPo.setExecuteTime(jobData.getExecuteTime());
        myJobDataPo.setRelatedId(jobData.getRelatedId());
        myJobDataPo.setParamsJson(JSONObject.toJSONString(jobData.getJobParams()));
        myJobDataPo.setClassName(jobData.getClassName());
        jobDataMapper.insert(myJobDataPo);
    }

    @Override
    public boolean isExist(String relatedId) {
        LambdaQueryWrapper<MyJobDataPo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MyJobDataPo::getRelatedId, relatedId);
        queryWrapper.eq(MyJobDataPo::getIsDeleted, 0);
        return jobDataMapper.selectCount(queryWrapper) > 0;
    }

    @Override
    public void deleteJobByJobName(String jobName) {
        LambdaUpdateWrapper<MyJobDataPo> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(MyJobDataPo::getJobName, jobName);
        MyJobDataPo po = new MyJobDataPo();
        po.setIsDeleted(1);
        jobDataMapper.update(po, updateWrapper);
    }

    @Override
    public String getJobNameByRelatedId(String relatedId) {
        LambdaQueryWrapper<MyJobDataPo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MyJobDataPo::getRelatedId, relatedId);
        return jobDataMapper.selectOne(queryWrapper).getJobName();
    }

    @Override
    public Map<String, Object> getJobParamsByJobName(String jobName) {
        LambdaQueryWrapper<MyJobDataPo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MyJobDataPo::getJobName, jobName);
        String json = jobDataMapper.selectOne(queryWrapper).getParamsJson();
        return (Map<String, Object>) JSONObject.parseObject(json, Map.class);
    }

    @Override
    public Date getExecuteTime(String jobName) {
        LambdaQueryWrapper<MyJobDataPo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MyJobDataPo::getJobName, jobName);
        return jobDataMapper.selectOne(queryWrapper).getExecuteTime();
    }

    @Override
    public int getJobSum() {
        LambdaQueryWrapper<MyJobDataPo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MyJobDataPo::getIsDeleted, 0);
        return jobDataMapper.selectCount(queryWrapper);
    }
}
