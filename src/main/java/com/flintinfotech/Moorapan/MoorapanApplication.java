package com.flintinfotech.Moorapan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class MoorapanApplication extends SpringBootServletInitializer {

    // Required to support WAR deployment
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(MoorapanApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(MoorapanApplication.class, args);

        System.out.println("\n\n");

        System.err.println("  *****    *******  *******       *****   *******    *****    ******   *******");
        System.err.println(" *     *   *      *    *         *           *      *     *   *     *     *   ");
        System.err.println("*       *  *      *    *         *           *     *       *  *     *     *   ");
        System.err.println("*       *  *******     *          *****      *     *       *  ******      *   ");
        System.err.println("*********  *           *               *     *     *********  *   *       *   ");
        System.err.println("*       *  *           *               *     *     *       *  *    *      *   ");
        System.err.println("*       *  *        *******       *****      *     *       *  *     *     *   ");
    }
}
