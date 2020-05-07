package com.cc.framework.freemarker.template.controller;

import com.cc.framework.freemarker.template.common.TemplateOutInfo;
import com.cc.framework.freemarker.template.utils.FreemarkerUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;

/**
 * @author chea0515@163.com
 */
@Controller
@RequestMapping("/index")
public class IndexController {

    @GetMapping("")
    public String goIndex(ModelMap model) {
        //
        Map<String, Object> fillParam = new HashMap<>();
        fillParam.put("testTxt", "测试模板内容输出:" + System.currentTimeMillis());
        TemplateOutInfo outInfo = FreemarkerUtils.createTemplateFile("outDemo.ftlh", fillParam);
        model.put("outInfo", outInfo);
        return "index";
    }
}
