package org.sagebionetworks.synapsehealthmonitor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import org.apache.log4j.Logger;

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

	private static Logger logger = Logger.getLogger(HealthMonitorApp.class);

	/**
	 *
	 * @param args
	 * @throws InterruptedException
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static void main(String[] args) throws InterruptedException, FileNotFoundException, IOException {

		if ((args == null) || (args.length != 1)) {
			throw new IllegalArgumentException("Missing argument <path_to_config_file");
		}
		String configPath = args[0];
		Properties props = loadProperties(configPath);
		Configuration config = new Configuration(props);

		String repoEndpoint, authEndpoint, userName, password;
		repoEndpoint = config.getRepoEndpoint();
		authEndpoint = config.getAuthEndpoint();
		userName = config.getUserName();
		password = config.getUserPassword();

		logger.debug("HealthMonitorApp starting...");

		try {
			SchedulerFactory factory = new org.quartz.impl.StdSchedulerFactory();
			Scheduler scheduler = factory.getScheduler();

			JobDetail crudJob = newJob(CrudMonitor.class)
					.withIdentity("crudJob")
					.usingJobData("repoEndpoint", repoEndpoint)
					.usingJobData("authEndpoint", authEndpoint)
					.usingJobData("userName", userName)
					.usingJobData("password", password)
					.build();

			Trigger crudTrigger = newTrigger()
					.withIdentity("crudTrigger")
					.startNow()
					.withSchedule(simpleSchedule()
					.withIntervalInSeconds(30)
					.repeatForever())
					.build();

			JobDetail searchJob = newJob(SearchMonitor.class)
					.withIdentity("searchJob")
					.usingJobData("repoEndpoint", repoEndpoint)
					.usingJobData("authEndpoint", authEndpoint)
					.usingJobData("userName", userName)
					.usingJobData("password", password)
					.build();
			Trigger searchTrigger = newTrigger()
					.withIdentity("searchTrigger")
					.startNow()
					.withSchedule(simpleSchedule()
					.withIntervalInSeconds(30)
					.repeatForever())
					.build();

			scheduler.start();

			scheduler.scheduleJob(crudJob, crudTrigger);
			scheduler.scheduleJob(searchJob, searchTrigger);

			final long sleepTime = 10000;
			long upTime = 0;
			while (upTime < 60000) {
				logger.info("UpTime: " + upTime);
				logger.debug("Main thread sleeping for ..." + sleepTime + "ms...");
				Thread.sleep(sleepTime);
				upTime += sleepTime;
			}

			scheduler.shutdown();
		} catch (SchedulerException se) {
			se.printStackTrace();
		} finally {
			logger.debug("HealthMonitorApp stopping...");
		}
	}

	private static Properties loadProperties(String path) throws FileNotFoundException, IOException {
		File f = new File(path);
		if (!f.exists()) {
			throw new IllegalArgumentException("File does not exist: " + f.getAbsolutePath());
		}
		FileInputStream fis = new FileInputStream(f);
		try {
			Properties props = new Properties();
			props.load(fis);
			return props;
		} finally {
			fis.close();
		}
	}
}
