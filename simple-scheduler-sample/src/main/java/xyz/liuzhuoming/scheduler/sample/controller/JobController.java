package xyz.liuzhuoming.scheduler.sample.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
        Map<String, Object> params = new HashMap<String, Object>() {{
            put("username", username);
        }};
        quartzTemplate.add(PrintJob.class, relatedId, params,
            new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(executeTime));
    }

    @DeleteMapping("{relatedId}")
    public void delete(@PathVariable String relatedId) {
        quartzTemplate.deleteByRelatedId(PrintJob.class, relatedId);
    }
}
