package cn.sbx0.zhibei.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
/**
 * 页面跳转
 */
@Controller
public class HomeController {

    /**
     * 进入后台管理
     *
     * @return
     */
    @GetMapping("/admin")
    public String login() {
        return "redirect:/user/admin";
    }

    /**
     * 错误页面
     *
     * @return
     */
    @GetMapping("/error")
    public String error() {
        return "error";
    }

}
