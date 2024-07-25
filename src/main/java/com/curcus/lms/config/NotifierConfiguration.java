//package com.curcus.lms.config;
//
//import de.codecentric.boot.admin.server.domain.entities.InstanceRepository;
//import de.codecentric.boot.admin.server.notify.Notifier;
//import de.codecentric.boot.admin.server.notify.RemindingNotifier;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.scheduling.annotation.EnableScheduling;
//import org.springframework.scheduling.annotation.Scheduled;
//
//import java.time.Duration;
//import java.util.concurrent.TimeUnit;
//
//@Configuration
//@EnableScheduling
//public class NotifierConfiguration {
//    @Autowired
//    private Notifier notifier;
//
//    @Autowired
//    private InstanceRepository instanceRepository;
//
//    @Bean
//    @Primary
//    public CustomRemindingNotifier remindingNotifier() {
//        CustomRemindingNotifier remindingNotifier = new CustomRemindingNotifier(notifier, instanceRepository);
//        remindingNotifier.setReminderPeriod(Duration.ofMillis(5 * 60 * 1000)); // 5 minutes
//        return remindingNotifier;
//    }
//
//    @Scheduled(fixedRate = 60_000L)
//    public void remind() {
//        remindingNotifier().sendRemindersPublic();
//    }
//
//    public static class CustomRemindingNotifier extends RemindingNotifier {
//
//        public CustomRemindingNotifier(Notifier delegate, InstanceRepository repository) {
//            super(delegate, repository);
//        }
//
//        public void sendRemindersPublic() {
//            sendReminders();
//        }
//    }
//}
