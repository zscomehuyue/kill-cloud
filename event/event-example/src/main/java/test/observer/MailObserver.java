package test.observer;

/**
 * @author: zscome
 * DateTime: 2019-07-31 16:50
 */
public class MailObserver implements Observer {

    @Override
    public void handle(AlarmEvent... alarmEvent) {
        System.out.println("=MailObserver=>onAlarm");
    }
}
