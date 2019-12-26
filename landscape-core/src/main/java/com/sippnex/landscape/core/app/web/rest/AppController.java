package com.sippnex.landscape.core.app.web.rest;

import com.sippnex.landscape.core.app.domain.App;
import com.sippnex.landscape.core.app.domain.AppSubscription;
import com.sippnex.landscape.core.app.service.AppService;
import com.sippnex.landscape.core.app.service.AppSubscriptionService;
import com.sippnex.landscape.core.app.web.dto.AppDto;
import com.sippnex.landscape.core.security.util.SecurityContextHelper;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/core/apps")
public class AppController {

    private final AppService appService;

    private final AppSubscriptionService appSubscriptionService;

    private final ModelMapper modelMapper;

    public AppController(AppService appService, AppSubscriptionService appSubscriptionService, ModelMapper modelMapper) {
        this.appService = appService;
        this.appSubscriptionService = appSubscriptionService;
        this.modelMapper = modelMapper;
    }

    @RequestMapping("")
    public List<AppDto> getApps(@RequestHeader(value = "Accept-Language", required = false) String locale) {
        List<AppSubscription> appSubscriptions = appSubscriptionService.getAppSubscriptions(getUserId());
        return appService.getAllApps().stream()
                .filter(app -> appSubscriptions.stream().anyMatch(appSubscription -> appSubscription.getApp().getId().equals(app.getId())))
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @RequestMapping("{appId}")
    public ResponseEntity<AppDto> getAppById(@PathVariable("appId") String appId, @RequestHeader(value = "Accept-Language", required = false) String locale) {
        return appService.getAppById(appId)
                .map(app -> appSubscriptionService.getAppSubscription(appId, getUserId())
                        .map(appSubscription -> new ResponseEntity<>(convertToDto(app), HttpStatus.OK))
                        .orElse(new ResponseEntity<>(HttpStatus.FORBIDDEN)))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    private AppDto convertToDto(App app) {
        AppDto appDto = modelMapper.map(app, app.getDtoClass());
        appDto.setSubscribed(appSubscriptionService.isSubscribed(app.getId(), getUserId()));
        return appDto;
    }

    private String getUserId() {
        return SecurityContextHelper.getUser().getId();
    }

}
