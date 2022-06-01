package ru.ivanov.AlfaBankTestTask.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.SimpleDateFormat;

@Configuration
public class DateConfig {

    //formatting date-time
    @Bean("date_bean")
    public SimpleDateFormat simpleDateFormat() {
        return new SimpleDateFormat("yyyy-MM-dd");
    }
    @Bean("time_bean")
    public SimpleDateFormat simpleTimeFormat() {
        return new SimpleDateFormat("yyyy-MM-dd HH");
    }
}
