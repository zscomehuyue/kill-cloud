package test.boot;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.SmartApplicationListener;
import org.springframework.stereotype.Service;
import test.boot.action.Action;

/**
 * @author: zscome
 * DateTime: 2019-07-31 18:48
 */
@Service
public class DingTalkListener implements SmartApplicationListener {

    @Override
    public boolean supportsEventType(Class<? extends ApplicationEvent> eventType) {
        return AlarmEvent.class.isAssignableFrom(eventType);
    }

    @Override
    public boolean supportsSourceType(Class<?> sourceType) {
        return Action.class.isAssignableFrom(sourceType);
    }

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        System.out.println("=DingTalkListener=>event");
    }

    @Override
    public int getOrder() {
        return 1;
    }
}
