package test.jdk;


import java.util.EventListener;

/**
 * @author: zscome
 * DateTime: 2019-07-31 17:56
 */
public interface AlarmListener extends EventListener {
    void onAlarm(AlarmEvent... alarmEvent);
}
