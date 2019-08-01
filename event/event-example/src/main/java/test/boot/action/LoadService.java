package test.boot.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import test.boot.AlarmEvent;
import test.boot.MsgEvent;

import java.util.List;

/**
 * @author: zscome
 * DateTime: 2019-07-31 18:50
 */
@Service
public class LoadService implements Action {

    @Autowired
    private ApplicationContext applicationContext;


    public void load2Es(List<MsgEvent> msgs) {
        try {
            //FIXME DO load 2 es
        } catch (Exception e) {

            applicationContext.publishEvent(new AlarmEvent(this, "Load es errors:" + e.getMessage()));

        }
    }

}
