package com.cw.bookappointment.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;

@Configuration
@PropertySources(
        {
                @PropertySource("classpath:env/dev/config.properties")
        }
)
public class EnvMyBean {

    @Autowired
    Environment env;

    public String getProperty(String key){
        return  env.getProperty(key);
    }

}
