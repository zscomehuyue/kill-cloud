package test.observer;

/**
 * @author: zscome
 * DateTime: 2019-07-31 17:00
 */
public class Main {

    public static void main(String args[]) {
        Subject subject = new AlarmSubject();
        subject.addListener(new SmsObserver());
        subject.addListener(new DingTalkObserver());
        subject.addListener(new MailObserver());
//        subject.triggerMsg(new AlarmEvent());

    }
}
