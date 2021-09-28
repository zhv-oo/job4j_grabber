package ru.job4j.grabber;

/**
 * класс для работы с quartz, чтением и записью данных с сайта в хранилище
 * @version 0.8
 * @author zhitenev
 */

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import ru.job4j.grabber.utils.SqlRuDateTimeParser;
import ru.job4j.html.SqlRuParse;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

public class Grabber implements Grab {
    private final Properties cfg = new Properties();

    /**
     * метод возвращает тип хранилища
     *
     * @return класс хранения данных
     */

    public Store store() {
        return new PsqlStore("./psql.properties");
    }

    /**
     * метод описывает планировщика
     *
     * @return планировщик
     * @throws SchedulerException обрабатываемые исключения
     */
    public Scheduler scheduler() throws SchedulerException {
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
        scheduler.start();
        return scheduler;
    }

    /**
     * метод описывает чтение файла с настройками
     *
     */
    public void cfg() {
        try (InputStream in = PsqlStore.class.getClassLoader()
                .getResourceAsStream("./app.properties")) {
            cfg.load(in);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    /**
     * инициализаци задания
     *
     * @param parse парсер
     * @param store хранилище
     * @param scheduler планировщик
     * @throws SchedulerException обрабатываемые исключения
     */
    @Override
    public void init(Parse parse, Store store, Scheduler scheduler) throws SchedulerException {
        JobDataMap data = new JobDataMap();
        data.put("store", store);
        data.put("parse", parse);
        JobDetail job = newJob(GrabJob.class)
                .usingJobData(data)
                .build();
        SimpleScheduleBuilder times = simpleSchedule()
                .withIntervalInSeconds(Integer.parseInt(cfg.getProperty("time")))
                .repeatForever();
        Trigger trigger = newTrigger()
                .startNow()
                .withSchedule(times)
                .build();
        scheduler.scheduleJob(job, trigger);
    }

    /**
     * Веб сервис получения данных из хранилища
     *
     * @param store хранилище данных
     */
    public void web(Store store) {
        new Thread(() -> {
            try (ServerSocket server =
                         new ServerSocket(Integer.parseInt(cfg.getProperty("port")))) {
                while (!server.isClosed()) {
                    Socket socket = server.accept();
                    try (OutputStream out = socket.getOutputStream()) {
                        out.write("HTTP/1.1 200 OK\r\n\r\n".getBytes());
                        for (Post post : store.getAll()) {
                            out.write(post.toString().getBytes(Charset.forName("Windows-1251")));
                            out.write(System.lineSeparator().getBytes());
                        }
                    } catch (IOException io) {
                        io.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    /**
     * Класс работы с данными
     *
     */
    public static class GrabJob implements Job {

        /**
         * метод описывает выполнения задания
         *
         * @param context контекст
         * @throws JobExecutionException обрабатываемые исключения
         * осуществляем парисинг в лист затем записываем лист в БД, для контроля
         * выводим содержимое на экран.
         */
        @Override
        public void execute(JobExecutionContext context) throws JobExecutionException {
            JobDataMap map = context.getJobDetail().getJobDataMap();
            Store store = (Store) map.get("store");
            Parse parse = (Parse) map.get("parse");
            List<Post> list = new ArrayList<>();
            try {
                list = parse.list("https://www.sql.ru/forum/job-offers/");
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (!list.isEmpty()) {
                for (Post post : list) {
                    store.save(post);
                }
                store.getAll().forEach(System.out::println);
            }
        }
    }

    public static void main(String[] args) throws Exception {
        Grabber grab = new Grabber();
        grab.cfg();
        Scheduler scheduler = grab.scheduler();
        Store store = grab.store();
        grab.init(new SqlRuParse(new SqlRuDateTimeParser()), store, scheduler);
        grab.web(store);
    }
}