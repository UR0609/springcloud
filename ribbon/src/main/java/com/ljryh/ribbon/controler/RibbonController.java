package com.ljryh.ribbon.controler;

import com.ljryh.ribbon.service.RibbonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RibbonController {

    @Autowired
    private RibbonService ribbonService;

    @RequestMapping(value = "/getRibbon")
    public String getRibbon(@RequestParam String data) {
        return ribbonService.get(data);
    }

    @RequestMapping(value = "/postRibbon")
    public String postRibbon() {
        return ribbonService.post();
    }

}
