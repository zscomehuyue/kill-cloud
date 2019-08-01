package test.observer;

/**
 * @author: zscome
 * DateTime: 2019-07-31 17:42
 */
public interface Subject {

    void addListener(Observer observer);

    void unRegister(Observer observer);

    void triggerMsg(String msg);

    void triggerMsg();
}
