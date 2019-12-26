package com.sippnex.landscape;

import com.sippnex.landscape.core.app.domain.App;

public class SampleApp extends App {

    public SampleApp() {
        super();
        this.type = "SampleApp";
    }

    public SampleApp(String name, String icon) {
        super(name, icon);
        this.type = "SampleApp";
    }
}
