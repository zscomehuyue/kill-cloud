package test.boot.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import test.boot.AlarmEvent;

/**
 * @author: zscome
 * DateTime: 2019-07-31 18:53
 */
@Service
public class SinkService implements Action {
    @Autowired
    private ApplicationContext applicationContext;


    public void sink() {
        applicationContext.publishEvent(new AlarmEvent(this, "sink"));
    }

}
