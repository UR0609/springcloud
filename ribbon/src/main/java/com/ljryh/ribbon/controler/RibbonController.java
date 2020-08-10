package com.ljryh.ribbon.controler;

import com.ljryh.ribbon.service.RibbonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RibbonController {

    @Autowired
    private RibbonService ribbonService;

    @RequestMapping(value = "/getRibbon",method = RequestMethod.GET)
    public String getRibbon(@RequestParam String data) {
        return ribbonService.get(data);
    }

    @RequestMapping(value = "/postRibbon",method = RequestMethod.POST)
    public String postRibbon() {
        return ribbonService.post();
    }

}
