package test.observer;


import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author: zscome
 * DateTime: 2019-07-31 16:45
 */
@Getter
@Setter
public class AlarmEvent implements Serializable {
    private String id;
    private String msg;
    private String projectName;
    private String subject;

}
