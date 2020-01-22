package com.sippnex.landscape.core.app.domain;

public class AppEntity<Entity> {

    private String name;

    private String api;

    private Class<Entity> entityClass;

    public AppEntity(String name, String api, Class<Entity> entityClass) {
        this.name = name;
        this.api = api;
        this.entityClass = entityClass;
    }

    public String getName() {
        return name;
    }

    public String getApi() {
        return api;
    }

    public Class<Entity> getEntityClass() {
        return entityClass;
    }
}
