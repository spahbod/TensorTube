package pl.tube.tensortube.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.tube.tensortube.logic.file.ProcessFile;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class FileProcessingJob implements Job {

    private static final Logger log = LoggerFactory.getLogger(FileProcessingJob.class);
    private static final Lock lock = new ReentrantLock();

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("START FileProcessingJob");
        if (lock.tryLock()) {
            try {
                ProcessFile processFile = (ProcessFile) jobExecutionContext.getJobDetail().getJobDataMap().get("processFile");
                processFile.processFile();
            } catch (Exception e) {
                log.error("FileProcessingJob -> execute", e);
            } finally {
                lock.unlock();
            }
        }
        log.info("STOP FileProcessingJob");
    }
}
