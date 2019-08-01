package api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author: zscome
 * DateTime: 2019-06-20 20:11
 */
@RequestMapping("v1/managers")
public interface ManagerApi {

    @GetMapping("getManager/{id}")
    String getManager(@PathVariable("id") Long id);

    @RequestMapping(value = "test/{id}" ,method = RequestMethod.GET)
    String test(@PathVariable("id") Long id);


    /**
     * Whitelabel Error Page
     * This application has no explicit mapping for /error, so you are seeing this as a fallback.
     *
     * Thu Aug 01 17:22:39 CST 2019
     * There was an unexpected error (type=Internal Server Error, status=500).
     * status 405 reading NodeFeignClient#test(User);
     * content: {"timestamp":1564651359837,"status":405,"error":"Method Not Allowed"
     * ,"exception":"org.springframework.web.HttpRequestMethodNotSupportedException"
     * ,"message":"Request method 'POST' not supported","path":"/v1/nodes/test"}
     *
     * @param user
     * @return
     */
    //FIXME TEST FeignClient get方法时 多个参数 是否能自动组合成对象
    @GetMapping("test")
    User test(User user);



    //FIXME TEST FeignClient get方法时 多个参数 是否能自动组合成对象
    @GetMapping("test2")
    User test2(User user);

}
