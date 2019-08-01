package test.observer;

/**
 * @author: zscome
 * DateTime: 2019-07-31 16:49
 */
public class DingTalkObserver implements Observer {

    @Override
    public void handle(AlarmEvent... alarmEvent) {
        System.out.println("=DingTalkObserver=>onAlarm size="+alarmEvent.length);
    }
}
