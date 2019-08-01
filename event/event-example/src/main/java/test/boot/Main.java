package test.boot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import test.boot.action.LoadService;
import test.boot.action.SinkService;
import test.boot.action.SourceService;

import javax.annotation.PostConstruct;

/**
 * @author: zscome
 * DateTime: 2019-07-31 18:57
 */
@Service
public class Main {

    @Autowired
    private LoadService loadService;

    @Autowired
    private SinkService sinkService;

    @Autowired
    private SourceService sourceService;


    @PostConstruct
    public void init() {
//        loadAction.load();
//        sinkAction.sink();
        sourceService.source();
    }
}
