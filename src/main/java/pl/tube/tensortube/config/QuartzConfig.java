package pl.tube.tensortube.config;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.tube.tensortube.job.FileProcessingJob;
import pl.tube.tensortube.logic.file.ProcessFile;

@Configuration
public class QuartzConfig {

    @Autowired
    public ProcessFile processFile;

    @Value("${process.file.cron}")
    public String processFileCron;

    @Bean
    public Scheduler scheduler() throws SchedulerException {

        JobDetail job = JobBuilder.newJob(FileProcessingJob.class)
                .withIdentity("FileProcessingJob", "FileProcessingJob").build();

        job.getJobDataMap().put("processFile", processFile);

        Trigger trigger = TriggerBuilder
                .newTrigger()
                .withIdentity("FileProcessingJob", "FileProcessingJob")
                .withSchedule(
                        CronScheduleBuilder.cronSchedule(processFileCron))
                .build();

        Scheduler scheduler = new StdSchedulerFactory().getScheduler();
        scheduler.start();
        scheduler.scheduleJob(job, trigger);
        return scheduler;
    }
}
