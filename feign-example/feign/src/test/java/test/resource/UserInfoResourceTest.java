package test.resource;

import feign.Feign;
import feign.Param;
import feign.RequestLine;
import feign.gson.GsonDecoder;
import org.junit.Test;
import org.springframework.util.Assert;
import test.domain.model.dal.UserInfo;

/**
 * @author: zscome
 * DateTime: 2019-08-30 13:53
 */
public class UserInfoResourceTest {

    public interface UserService {

        @RequestLine("GET /userInfos/{id}")
        UserInfo getUserInfo(@Param("id") Long id);
    }


    @Test
    public void getUserInfo() {

        UserInfoResourceTest.UserService service =

                Feign.builder()
                .decoder(new GsonDecoder())
                .target(UserInfoResourceTest.UserService.class, "http://localhost:8080/v1");

        UserInfo userInfo = service.getUserInfo(1L);


        Assert.notNull(userInfo, "not null ");
        Assert.isTrue(userInfo.getId().equals(1L), "id not null");
        System.err.println(service.toString());

    }



}
