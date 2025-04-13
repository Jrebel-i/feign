package com.hucho.feign.demos.web;

import com.hucho.feign.demos.dto.Message;
import com.hucho.feign.demos.dto.Result;
import com.hucho.feign.demos.feign.CollectMessageClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequestMapping(value = "/message")
public class MessController {

    @Autowired
    private CollectMessageClient collectMessageClient;

    @RequestMapping(value = "/process",method = RequestMethod.POST,consumes = "application/json")
    @ResponseBody
    public Result<Void> process(HttpServletRequest request, @RequestBody List<Message> messages) {
//        for (Message message : messages) {
//            System.out.println("处理数据："+message);
//        }

        return Result.success();
    }

    @RequestMapping(value = "/process_str",method = RequestMethod.POST,consumes = "application/json")
    @ResponseBody
    public Result<Void> process(HttpServletRequest request, @RequestBody String message) {
        System.out.println("接收到文件大小："+request.getContentLengthLong());

        System.out.println("处理数据："+message);

        return Result.success();
    }

    @RequestMapping(value = "/trigger",method = RequestMethod.GET)
    public Result<Void> trigger(HttpServletRequest request) {
        ArrayList<Message> messages = new ArrayList<>();
        String message = "";
// in Google Browser, switch to Network, right-click the table header and select Content-Encoding under Response Headers. If Gzip is enabled, the Content-Encoding in the corresponding interface will be displayed.
        String str = "OpenF12";
        for (int i = 0; i < 100; i++) {
            Message testVo = new Message();
            testVo.setId(i+ "");
            testVo.setMessage(str);

            messages.add(testVo);
            message+=testVo.toString();
        }

//        System.out.println(message);

        log.info("发送数据大小："+message.getBytes().length);
        collectMessageClient.sendMessage(messages);
        return Result.success();
    }

}