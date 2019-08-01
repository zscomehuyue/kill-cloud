package test.observer;

/**
 * @author: zscome
 * DateTime: 2019-07-31 16:44
 */
public interface Observer {

    void handle(AlarmEvent... alarmEvent);
}
