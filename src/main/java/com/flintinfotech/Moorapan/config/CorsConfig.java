package com.flintinfotech.Moorapan.config;

import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

//public class CorsConfig {
//
//    @Bean
//    public CorsConfigurationSource corsConfigurationSource() {
//
//        return new MyCorsConfigurationSource();
//
//    }
//
//    // Named class implementing CorsConfigurationSource
//
//    public static class MyCorsConfigurationSource implements CorsConfigurationSource {
//
//        @Override
//
//        public CorsConfiguration getCorsConfiguration(javax.servlet.http.HttpServletRequest request) {
//
//            CorsConfiguration config = new CorsConfiguration();
//
//            config.setAllowedOrigins(Collections.singletonList("Authorization"));
//
//            config.setAllowedMethods(Arrays.asList("GET", "POST"));
//
//            config.setAllowCredentials(true);
//
//            config.setAllowedHeaders(Collections.singletonList("Authorization"));
//
//            config.setExposedHeaders(Collections.singletonList("Authorization"));
//
//            config.setMaxAge(3600L);
//
//            return config;
//
//        }
//
//    }
//
//}

public class CorsConfig {

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        return new MyCorsConfigurationSource();
    }

    public static class MyCorsConfigurationSource implements CorsConfigurationSource {

        @Override
        public CorsConfiguration getCorsConfiguration(javax.servlet.http.HttpServletRequest request) {
            CorsConfiguration config = new CorsConfiguration();

            // ✅ Allow all origins with credentials
            config.setAllowedOriginPatterns(Collections.singletonList("*"));

            // ✅ Allow all HTTP methods
            config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));

            // ✅ Allow credentials
            config.setAllowCredentials(true);

            // ✅ Allow all request headers
            config.setAllowedHeaders(Collections.singletonList("*"));

            // ✅ Expose Authorization header
            config.setExposedHeaders(Collections.singletonList("Authorization"));

            // ✅ Cache preflight results for 1 hour
            config.setMaxAge(3600L);

            return config;
        }
    }
}