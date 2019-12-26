package com.sippnex.landscape.core.app.domain;

import com.sippnex.firemaw.FiremawProperty;
import com.sippnex.firemaw.FiremawType;
import com.sippnex.landscape.core.app.web.dto.AppDto;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "app")
public abstract class App {

    @Transient
    @FiremawProperty(name="type", type= FiremawType.TextField, disabled = true)
    protected String type = "App";

    @Transient
    protected Class<? extends AppDto> dtoClass = AppDto.class;

    @Id
    @FiremawProperty(name="id", type= FiremawType.TextField, disabled = true)
    private String id;

    @FiremawProperty(name="name", type= FiremawType.TextField)
    private String name;

    @FiremawProperty(name="icon", type= FiremawType.FilebladeField)
    private String icon;

    public String getType() {
        return type;
    }

    public Class<? extends AppDto> getDtoClass() {
        return dtoClass;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    // Empty constructor for Spring Data
    public App() {
    }

    public App(String name, String icon) {
        this.name = name;
        this.icon = icon;
    }

}
