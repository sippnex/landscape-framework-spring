package com.sippnex.landscape;

import com.sippnex.landscape.core.app.domain.App;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
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
