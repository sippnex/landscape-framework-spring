package com.sippnex.landscape.core.app;

import com.sippnex.landscape.core.app.domain.App;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class AppRegistry {

    private Map<String, Class<? extends App>> appClasses = new HashMap<>();

    public Optional<Class<? extends App>> getAppClass(String appType) {
        Class<? extends App> appClass = appClasses.get(appType);
        if(appClass != null) {
            return Optional.of(appClass);
        } else {
            return Optional.empty();
        }
    }

    public void registerApp(String appType, Class<? extends App> appClass) {
        this.appClasses.put(appType, appClass);
    }

}
