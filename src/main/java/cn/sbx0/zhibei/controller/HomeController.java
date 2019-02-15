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
    public String admin() {
        return "redirect:/user/admin";
    }

    /**
     * 进入权限管理
     *
     * @return
     */
    @GetMapping("/permission")
    public String permission() {
        return "redirect:/user/permission";
    }

}
