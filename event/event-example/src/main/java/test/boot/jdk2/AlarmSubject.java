package test.boot.jdk2;

import java.util.Observable;

/**
 * @author: zscome
 * DateTime: 2019-08-01 11:19
 */
public class AlarmSubject extends Observable {

    public void fireMsg(String msg) {
        setChanged();
        notifyObservers(msg);
    }
}
