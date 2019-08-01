package test.boot;

import org.springframework.context.ApplicationEvent;

/**
 * @author: zscome
 * DateTime: 2019-07-31 18:45
 */
public class AlarmEvent extends ApplicationEvent {
    private String msg;

    public AlarmEvent(Object source) {
        super(source);
    }

    public AlarmEvent(Object source, String msg) {
        super(source);
        this.msg = msg;
    }

}
