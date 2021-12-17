package org.debug.smartdeviceiot.server.controller;

import org.debug.smartdeviceiot.server.entity.Paper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/paper")
public class PaperController {
    private static final Logger log= LoggerFactory.getLogger(PaperController.class);

    @RequestMapping(value = "info",method = RequestMethod.GET)
    public Paper getInfo(Integer paperId,String paperName){
        Paper paper = new Paper();
        paper.setPaperId(paperId);
        paper.setName(paperName);
        return paper;
    }
}

//http://localhost:10001/smartdeviceiot/paper/info?paperId=612&paperName=智能装备物联网监控服务平台

