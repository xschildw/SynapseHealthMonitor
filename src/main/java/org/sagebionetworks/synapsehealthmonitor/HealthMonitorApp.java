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

	public static void main(String[] args) throws InterruptedException {
		String repoEndpoint, authEndpoint, userName, password;
		repoEndpoint = "https://repo-staging.sagebase.org/repo/v1";
		authEndpoint = "https://auth-staging.sagebase.org/auth/v1";
		userName = "x.schildwachter@sagebase.org";
		password = "SBXS1964";

		try {
			SchedulerFactory factory = new org.quartz.impl.StdSchedulerFactory();
			Scheduler scheduler = factory.getScheduler();

			JobDetail job = newJob(CrudMonitor.class)
					.withIdentity("crudJob")
					.usingJobData("repoEndpoint", repoEndpoint)
					.usingJobData("authEndpoint", authEndpoint)
					.usingJobData("userName", userName)
					.usingJobData("password", password)
					.build();
			System.out.println("Created job...");
			Trigger trigger = newTrigger()
					.withIdentity("crudTrigger")
					.startNow()
					.withSchedule(simpleSchedule()
					.withIntervalInSeconds(10)
					.repeatForever())
					.build();
			System.out.println("Created trigger...");

	//		JobDetail job = newJob(SearchMonitor.class)
	//				.withIdentity("searchJob")
	//				.usingJobData("repoEndpoint", repoEndpoint)
	//				.usingJobData("authEndpoint", authEndpoint)
	//				.usingJobData("userName", userName)
	//				.usingJobData("password", password)
	//				.build();
	//		Trigger trigger = newTrigger()
	//				.withIdentity("searchTrigger")
	//				.startNow()
	//				.withSchedule(simpleSchedule()
	//				.withIntervalInSeconds(30)
	//				.repeatForever())
	//				.build();

			scheduler.start();

			scheduler.scheduleJob(job, trigger);
//			System.out.println("Scheduled job.");
//
//			System.out.println(scheduler.getJobDetail(job.getKey()).toString());
//
//			System.out.println(scheduler.getCurrentlyExecutingJobs().size());
//
//			System.out.println("Main thread sleeping for 100s...");
			Thread.sleep(30000);
//
			scheduler.shutdown();
		} catch (SchedulerException se) {
			se.printStackTrace();
		}
	}
}
