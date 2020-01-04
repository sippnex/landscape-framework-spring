package com.sippnex.landscape;

import com.sippnex.firemaw.FiremawProperty;
import com.sippnex.firemaw.FiremawType;
import com.sippnex.landscape.core.app.domain.App;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "app")
public class SampleApp extends App {

    @FiremawProperty(name = "content", type = FiremawType.RichTextField)
    private String content;

    public SampleApp() {
        super();
        this.type = "SampleApp";
    }

    public SampleApp(String name, String icon) {
        super(name, icon);
        this.type = "SampleApp";
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
