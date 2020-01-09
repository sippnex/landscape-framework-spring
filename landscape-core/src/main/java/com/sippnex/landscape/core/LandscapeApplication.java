package com.sippnex.landscape.core;


import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import com.sippnex.fileblade.EnableFileblade;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@SpringBootApplication
@EnableFileblade
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import({ LandscapeConfig.class})
public @interface LandscapeApplication {

}