package test.jdk;

import java.util.EventObject;

/**
 * @author: zscome
 * DateTime: 2019-07-31 17:57
 * Java事件机制包括三个部分：事件、事件监听器、事件源。
 * FIXME  其中事件类中包含事件源的实例，来标识事件的发出者；
 * 事件监听器类则包含了事件被触发时的响应函数，业务逻辑写在该响应函数中；
 * 而事件源则有一个事件监听器列表，
 * 当事件触发时，通知所有的监听者，采用的是观察者模式 (发布-订阅模式)。
 */
public class AlarmEvent extends EventObject {

    private String msg;

    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public AlarmEvent(Object source) {
        super(source);
    }

    public AlarmEvent(Object source, String msg) {
        super(source);
        this.msg = msg;
    }

}
