package test.jdk;


/**
 * @author: zscome
 * DateTime: 2019-07-31 18:04
 */
public class Main {

    public static void main(String args[]) {
        AlarmSource source = new AlarmSource();
        source.addListener(new SmsListener());
        source.addListener(new DingTalkListener());
        source.addListener(new MailListener());

        source.triggerEvent(new AlarmEvent(source));

    }

}
