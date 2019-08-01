package test.boot;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

/**
 * @author: zscome
 * DateTime: 2019-07-31 18:47
 */
@Service
public class MailListener {

    @EventListener(AlarmEvent.class)
    public void onApplicationEvent(AlarmEvent event) {
        System.out.println("=MailListener=>event");
    }
}
