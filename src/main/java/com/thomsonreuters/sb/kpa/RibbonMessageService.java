package com.thomsonreuters.sb.kpa;

import com.netflix.ribbon.proxy.annotation.Http;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;


//Resttemplate is Ribbon aware and will perform load balancing among
// all instances of "eureka-client-test" registered in eureka.
//application.properties does not contain eureka server connection commands because it's using default localhost:8671

@Controller
@EnableAutoConfiguration
@EnableEurekaClient
public class RibbonMessageService  {
    @Autowired
    private RestTemplate restTemplate;


    public static void main(String[] args) throws Exception {
        SpringApplication.run(RibbonMessageService.class, args);
    }

    @RequestMapping("/")
    @ResponseBody
    String home() {
        return "Hello World!";
    }


    @RequestMapping(value = "/{message}", method = RequestMethod.GET)
    @ResponseBody
    public String passMessage(@PathVariable String message){
        System.out.println("Request received " + message);
        return restTemplate.getForObject("http://eureka-client-test/{message}", String.class, (Object) message);
    }
}
