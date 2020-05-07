package xyz.liuzhuoming.scheduler.sample.job;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.stereotype.Component;
import xyz.liuzhuoming.simple.scheduler.job.BaseJob;

/**
 * 打印任务（测试实例）
 *
 * @author liuzhuoming
 */
public class PrintJob extends BaseJob implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        String jobName = jobExecutionContext.getJobDetail().getKey().getName();
        String printTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        System.out
            .println("hello " + super.currentJobParams(jobName).get("username") + ", " + printTime);

        //执行之后的清理等工作
        super.afterExecute(this.getClass(), jobExecutionContext);
    }
}
