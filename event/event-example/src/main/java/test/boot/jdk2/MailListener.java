package test.boot.jdk2;

import java.util.Observable;
import java.util.Observer;

/**
 * @author: zscome
 * DateTime: 2019-08-01 11:25
 */
public class MailListener implements Observer {
    @Override
    public void update(Observable o, Object arg) {
        System.out.println("=MailListener=>event " + arg);
    }
}
