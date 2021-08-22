package ru.job4j.quartz;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;

import static org.quartz.JobBuilder.*;
import static org.quartz.TriggerBuilder.*;
import static org.quartz.SimpleScheduleBuilder.*;

public class AlertRabbit {

    private static Connection cn;
    private Map<String, String> properties;

    public static void main(String[] args) {
        try {
            init();
            List<Long> store = new ArrayList<>();
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.start();
            JobDataMap data = new JobDataMap();
            data.put("connect", cn);
            JobDetail job = newJob(Rabbit.class)
                    .usingJobData(data)
                    .build();
            int inter = Integer.parseInt(AlertRabbit.getInfo().get("rabbit.interval"));
            SimpleScheduleBuilder times = simpleSchedule()
                    .withIntervalInSeconds(inter)
                    .repeatForever();
            Trigger trigger = newTrigger()
                    .startNow()
                    .withSchedule(times)
                    .build();
            scheduler.scheduleJob(job, trigger);
            Thread.sleep(10000);
            scheduler.shutdown();
            System.out.println(store);
        } catch (Exception se) {
            se.printStackTrace();
        }
    }

    private static Map<String, String> getInfo() throws IOException {
        Map<String, String> rsl = new HashMap<>();
        String path = "./src/main/resources/rabbit.properties";
        try (BufferedReader rd = new BufferedReader(new FileReader(path))) {
            for (String line = rd.readLine(); line != null; line = rd.readLine()) {
                String[] tmp = line.split("=");
                rsl.put(tmp[0], tmp[1]);
            }
        }
        return rsl;
    }

    private static void init() {
        try (InputStream in = AlertRabbit.class.getClassLoader()
                .getResourceAsStream("./src/main/resources/rabbit.properties")) {
            Map<String, String> properties = AlertRabbit.getInfo();
            Class.forName(properties.get("driver-class-name"));
            cn = DriverManager.getConnection(
                    properties.get("url"),
                    properties.get("username"),
                    properties.get("password")
            );
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    public static class Rabbit implements Job {
        public Rabbit() {
            System.out.println(hashCode());
        }

        @Override
        public void execute(JobExecutionContext context) throws JobExecutionException {
            System.out.println("Rabbit runs here ...");
            Connection con = (Connection) context.getJobDetail().getJobDataMap().get("connect");
            try (PreparedStatement statement =
                         con.prepareStatement("insert into rabbit (created_date) values (?);")) {
                statement.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
                statement.execute();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}