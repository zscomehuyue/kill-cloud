package test.resource;

import org.springframework.web.bind.annotation.*;
import test.domain.model.dal.UserInfo;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: zscome
 * DateTime: 2019-08-30 13:47
 */
@RestController
@RequestMapping("v1/userInfos")
public class UserInfoResource {

    @GetMapping("{id}")
    public UserInfo getUserInfo(@PathVariable("id") Long id) {
        return UserInfo.builder()
                .id(id)
                .name("Name_" + id)
                .build();
    }

    @GetMapping
    public UserInfo getUserInfo(@RequestParam("list") List<String> list) {
        UserInfo build = UserInfo.builder()
                .name("Name_" + list.stream().collect(Collectors.joining("_")))
                .build();
        System.out.println(build);
        return build;
    }


}
