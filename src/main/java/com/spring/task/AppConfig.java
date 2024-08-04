package com.spring.task;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class AppConfig {


    @Bean
    @Primary
    public FirstCourseRecommender firstCourseRecommender(){
        return new FirstCourseRecommender();
    }

    @Bean
    public SecondCourseRecommender secondCourseRecommender(){
        return new SecondCourseRecommender();
    }


}
