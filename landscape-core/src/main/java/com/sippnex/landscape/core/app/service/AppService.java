package com.sippnex.landscape.core.app.service;

import com.sippnex.landscape.core.app.domain.App;
import com.sippnex.landscape.core.app.repository.AppRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AppService {

    private final AppRepository appRepository;

    public AppService(AppRepository appRepository) {
        this.appRepository = appRepository;
    }

    public Page<App> getAllApps(Pageable pageable) {
        return appRepository.findAll(pageable);
    }

    public List<App> getAllApps() {
        return appRepository.findAll();
    }

    public Optional<App> getAppById(String id) {
        return appRepository.findById(id);
    }

    public Optional<App> getAppByName(String name) {
        return appRepository.findByName(name);
    }

    public Optional<App> getAppByNameAndIdNot(String name, String id) {
        return appRepository.findByNameAndIdNot(name, id);
    }

    public App save(App app) {
        return appRepository.save(app);
    }

}
