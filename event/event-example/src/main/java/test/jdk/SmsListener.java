package test.jdk;

/**
 * @author: zscome
 * DateTime: 2019-07-31 17:57
 */
public class SmsListener implements AlarmListener {

    @Override
    public void onAlarm(AlarmEvent... alarmEvent) {
        System.out.println("=SmsListener=>onAlarm");
        alarmEvent[0].getSource();
    }
}
