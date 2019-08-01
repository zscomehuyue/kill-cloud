package test.jdk;

/**
 * @author: zscome
 * DateTime: 2019-07-31 17:58
 */
public class DingTalkListener implements AlarmListener {
    @Override
    public void onAlarm(AlarmEvent... alarmEvent) {
        System.out.println("=DingTalkObserver=>onAlarm");
    }
}
