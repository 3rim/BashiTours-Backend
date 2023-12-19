package com.bashitours.webapp.controller;

import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class HugMeController {

    @RequestMapping(path = "/hug")
    public ModelAndView sendHug(String name, ModelMap map){
        map.addAttribute("name",name);
        return new ModelAndView("besa.html",map);
    }
}
