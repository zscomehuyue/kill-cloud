package test.domain.model.dal;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

/**
 * @author: zscome
 * DateTime: 2019-08-30 13:48
 */
@Data
@Builder
@ToString
public class UserInfo {
    private Long id;
    private String name="userName";
}
