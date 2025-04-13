package com.hucho.feign.demos.feign;

import com.hucho.feign.demos.dto.Message;
import com.hucho.feign.demos.dto.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Component
@FeignClient(name = "collect",url = "http://localhost:8080/message/"
,configuration = FullLogConfiguration.class
)
public interface CollectMessageClient {

    @RequestMapping(value = "/process", method = RequestMethod.POST
            , produces = {"application/json; charset=UTF-8"}
    )
    Result<Void> sendMessage(@RequestBody List<Message> messages);

    @RequestMapping(value = "/process_str", method = RequestMethod.POST, produces = {"application/json; charset=UTF-8"})
    Result<Void> sendMessageStr(@RequestBody String message);
}
