package com.sippnex.landscape.core.app.domain;

import com.sippnex.landscape.core.security.domain.User;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

public class AppSubscription {

    @Id
    private String id;

    private Integer x;

    private Integer y;

    private Integer cols;

    private Integer rows;

    @DBRef
    private App app;

    @DBRef
    private User user;

    public AppSubscription() {

    }

    public AppSubscription(Integer x, Integer y, Integer cols, Integer rows, App app, User user) {
        this.x = x;
        this.y = y;
        this.cols = cols;
        this.rows = rows;
        this.app = app;
        this.user = user;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    public Integer getCols() {
        return cols;
    }

    public void setCols(Integer cols) {
        this.cols = cols;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    public App getApp() {
        return app;
    }

    public void setApp(App app) {
        this.app = app;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
