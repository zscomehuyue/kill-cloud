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
}
