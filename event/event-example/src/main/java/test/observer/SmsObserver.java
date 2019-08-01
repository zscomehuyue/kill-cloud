package test.observer;

/**
 * @author: zscome
 * DateTime: 2019-07-31 16:50
 */
public class SmsObserver implements Observer {

    @Override
    public void handle(AlarmEvent... alarmEvent) {
        System.out.println("=SmsObserver=>onAlarm");
    }
}
