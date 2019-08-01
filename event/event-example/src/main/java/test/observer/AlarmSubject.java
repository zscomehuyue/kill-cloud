package test.observer;

import java.util.HashSet;
import java.util.Set;

/**
 * @author: zscome
 * DateTime: 2019-07-31 16:44
 */
public class AlarmSubject implements Subject {

    private Set<Observer> set = new HashSet<>();

    @Override
    public void addListener(Observer observer) {
        set.add(observer);
    }

    @Override
    public void unRegister(Observer observer) {
        if (null != observer) {
            set.remove(observer);
        }
    }

    @Override
    public void triggerMsg(String msg) {
        AlarmEvent alarmEvent = new AlarmEvent();
        alarmEvent.setMsg(msg);
        set.forEach(observer -> observer.handle(alarmEvent));
    }

    @Override
    public void triggerMsg() {
        set.forEach(Observer::handle);
    }
}
