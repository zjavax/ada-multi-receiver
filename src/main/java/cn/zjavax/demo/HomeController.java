package cn.zjavax.demo;

import cn.zjavax.demo.config.PropertiesConfiguration;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @RequestMapping("/")
    public String viewHome() {

//        Environment bean = PropertiesConfiguration.applicationContext.getBean(Environment.class);
//        String property = bean.getProperty("blockfrost.cardano.testnet.project.id");
//
//
//
//        System.out.println(property+ "123");
        System.out.println("123");
        return "123";
    }
}
