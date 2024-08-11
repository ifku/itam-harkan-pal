package com.ifkusyoba.itam_harkan_pal;

import com.ifkusyoba.itam_harkan_pal.core.config.WebConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(WebConfiguration.class)
public class ItamHarkanPalApplication {

    public static void main(String[] args) {
        SpringApplication.run(ItamHarkanPalApplication.class, args);
    }
}