package api;

import org.springframework.web.bind.annotation.*;

/**
 * @author: zscome
 * DateTime: 2019-06-20 20:10
 */
@RequestMapping("v1/nodes")
public interface NodeApi {

    @GetMapping("getNode/{id}")
    String getNode(@PathVariable("id")  Long id);

    @RequestMapping(value = "test/{id}" ,method = RequestMethod.GET)
    String test(@PathVariable("id") Long id);

    //FIXME 当参数为复杂对象时，FeignClient 不能使用GetMapping注解，使用RequestMapping，这时会自动转换为post方法；
    //FIXME 会把参数自动转为id=id & name=name 转为user对象；
    //FIXME @RequestBody 时也不行，使用jsonstr方式，或者RequestParam标注每个参数；
    //FIXME 在head里面增加toPost参数，后台自动转为post请求，如果参数为时，向body写入数据，避免参数错误；
    @RequestMapping(value = "users" ,method = RequestMethod.GET)
    User getUser(@RequestBody  User user);

    //FIXME rest 本身是可以正常访问的
    @GetMapping(value = "test" )
    User test(User user);


}
