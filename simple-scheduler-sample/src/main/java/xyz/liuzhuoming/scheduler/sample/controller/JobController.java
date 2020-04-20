package xyz.liuzhuoming.scheduler.sample.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.liuzhuoming.scheduler.sample.job.PrintJob;
import xyz.liuzhuoming.simple.scheduler.scheduler.SchedulerTemplate;

/**
 * 简单任务api
 *
 * @author liuzhuoming
 */
@RestController
@RequestMapping("/job")
@RequiredArgsConstructor
public class JobController {

    private final SchedulerTemplate quartzTemplate;

    @PostMapping
    public void add(String relatedId, String executeTime, String username)
        throws ParseException {
        for (int i = 0; i < 1000; i++) {
            Map<String, Object> params = new HashMap<>();
            params.put("username", username + i);
            quartzTemplate.addJob(PrintJob.class, relatedId + "--" + i, params,
                new Date(
                    new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(executeTime).getTime()));
        }
    }
}
