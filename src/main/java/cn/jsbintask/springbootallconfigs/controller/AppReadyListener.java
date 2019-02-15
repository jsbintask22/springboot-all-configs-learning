package cn.jsbintask.springbootallconfigs.controller;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author jsbintask@gmail.com
 * @date 2019/2/15 16:27
 */
@Component
public class AppReadyListener {
    @EventListener(ApplicationReadyEvent.class)
    public void init() {
        System.out.println("AppReadyListener.init");
    }

    @PostConstruct
    public void postConstruct() {
        System.out.println("AppReadyListener.postConstruct");
    }
}
