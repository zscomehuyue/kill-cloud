package test.boot.jdk2;

/**
 * @author: zscome
 * DateTime: 2019-08-01 11:23
 */
public class Main {

    public static void main(String args[]) {
        AlarmSubject subject = new AlarmSubject();
        subject.addObserver(new SmsListener());
        subject.addObserver(new MailListener());
        subject.fireMsg(" error test");

    }
}
