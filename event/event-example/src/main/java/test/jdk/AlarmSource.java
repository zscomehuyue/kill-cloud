package test.jdk;


import java.util.HashSet;
import java.util.Set;

/**
 * @author: zscome
 * DateTime: 2019-07-31 18:01
 */
public class AlarmSource {

    private Set<AlarmListener> set = new HashSet<>();

    public void addListener(AlarmListener AlarmListener) {
        set.add(AlarmListener);
    }

    public void unRegister(AlarmListener AlarmListener) {
        if (null != AlarmListener) {
            set.remove(AlarmListener);
        }
    }

    public void triggerEvent(AlarmEvent alarmEvent) {
        set.forEach(ob -> ob.onAlarm(alarmEvent));
    }

    //发送消息
    public void triggerMsg(String msg) {
        set.forEach(ob -> ob.onAlarm(new AlarmEvent(this, msg)));
    }

    public void triggerEvent() {
        set.forEach(AlarmListener::onAlarm);
    }


}
