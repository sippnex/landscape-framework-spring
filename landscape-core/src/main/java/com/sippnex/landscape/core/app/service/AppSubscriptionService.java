package com.sippnex.landscape.core.app.service;

import com.sippnex.landscape.core.app.domain.AppSubscription;
import com.sippnex.landscape.core.app.repository.AppSubscriptionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class AppSubscriptionService {

    private final AppSubscriptionRepository appSubscriptionRepository;

    public AppSubscriptionService(AppSubscriptionRepository appSubscriptionRepository) {
        this.appSubscriptionRepository = appSubscriptionRepository;
    }

    public List<AppSubscription> getAppSubscriptions(String userId) {
        return appSubscriptionRepository.findByUserId(userId);
    }

    public Optional<AppSubscription> getAppSubscription(String appId, String userId) {
        return appSubscriptionRepository.findByAppIdAndUserId(appId, userId);
    }

    public void saveAppSubscription(AppSubscription appSubscription) {
        appSubscriptionRepository.save(appSubscription);
    }

    public void deleteAppSubscription(String appId, String userId) {
        appSubscriptionRepository.deleteByAppIdAndUserId(appId, userId);
    }

    public Boolean isSubscribed(String appId, String userId) {
        return appSubscriptionRepository.findByAppIdAndUserId(appId, userId).isPresent();
    }

    @Transactional
    public void updatePositionByAppId(String appId, String userId, Integer x, Integer y, Integer rows, Integer cols) {
        if (x < 0 || y < 0) {
            System.out.println("Invalid position value for dashboard app. (x: " + x + ", y: " + y + ")");
            return;
        }
        if (rows <= 0 || cols <= 0) {
            System.out.println("Invalid size value for dashboard app. (rows: " + rows + ", cols: " + cols + ")");
            return;
        }
        Optional<AppSubscription> optional = getAppSubscription(appId, userId);
        if(optional.isPresent()) {
            AppSubscription appSubscription = optional.get();
            appSubscription.setX(x);
            appSubscription.setY(y);
            appSubscription.setRows(rows);
            appSubscription.setCols(cols);
            appSubscriptionRepository.save(appSubscription);
        } else {
            System.out.println("Could not update app position: App subscription for App " + appId + " not found!");
        }
    }



}
