# 开始使用

本项目目的是解决类似 定时发送邮件 这种任务比较多并且每个任务绑定不同的外部数据id，但是各个任务触发条件不同的情况。  

1. 在mysql数据库运行docs/job.sql
2. install项目simple-scheduler-spring-boot-starter
3. 运行项目simple-scheduler-sample的SampleApplication.java 
4. 之后用Postman请求JobController的接口
5. 等待运行结果