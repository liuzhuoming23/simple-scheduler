# 开始使用

本项目目的是解决类似 定时发送邮件 这种任务比较多并且每个任务绑定不同的外部数据id，但是各个任务触发条件不同的情况。  

## 功能上和xxljob等调度器的区别：

|              | xxljob                                          | simple-schduler                          |
| ------------ | ----------------------------------------------- | ---------------------------------------- |
| 使用场景     | 同一个任务的重复调度                            | 不同任务的单次调度                       |
| 调度方式     | 多调度器，多触发器                              | 单调度器，单触发器，一次性任务，用完即弃 |
| 使用场景示例 | 每日00:30统计当日数据；每分钟拉取在线人数并存储 | 写完一封邮件后设置定时发送；日历提醒     |
| 易用性       | 还可以                                          | 基本没有                                 |

## 使用方式

1. 在mysql数据库运行docs/job.sql
2. install项目simple-scheduler-spring-boot-starter
3. 运行项目simple-scheduler-sample的SampleApplication.java 
4. 之后用Postman请求JobController的接口
5. 等待运行结果