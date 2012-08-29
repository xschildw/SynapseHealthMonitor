package org.sagebionetworks.synapsehealthmonitor;

import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import static org.quartz.JobBuilder.*;
import static org.quartz.TriggerBuilder.*;
import static org.quartz.SimpleScheduleBuilder.*;

public class HealthMonitorApp {

	public static void main(String[] args) throws SchedulerException, InterruptedException {
		String repoEndpoint, authEndpoint, userName, password;
		
		SchedulerFactory factory = new org.quartz.impl.StdSchedulerFactory();
		Scheduler scheduler = factory.getScheduler();

		scheduler.start();
		
		JobDetail job = newJob(CrudMonitor.class)
				.withIdentity("crudJob")
				.usingJobData("repoEndpoint", repoEndpoint)
				.usingJobData("authEndpoint", authEndpoint)
				.usingJobData("userName", userName)
				.usingJobData("password", password)
				.build();
		Trigger trigger = newTrigger()
				.withIdentity("crudTrigger")
				.startNow()
				.withSchedule(simpleSchedule()
				.withIntervalInSeconds(10)
				.repeatForever())
				.build();
		
		scheduler.scheduleJob(job, trigger);
		
		System.out.println("Main thread sleeping for 100s...");
		Thread.sleep(100000);

		scheduler.shutdown();
	}
}
