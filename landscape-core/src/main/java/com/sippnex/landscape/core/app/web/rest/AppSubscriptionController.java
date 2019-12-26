package com.sippnex.landscape.core.app.web.rest;

import com.sippnex.landscape.core.app.domain.AppSubscription;
import com.sippnex.landscape.core.app.exception.AppSubscriptionNotFoundException;
import com.sippnex.landscape.core.app.service.AppSubscriptionService;
import com.sippnex.landscape.core.app.web.dto.AppSubscriptionDto;
import com.sippnex.landscape.core.security.util.SecurityContextHelper;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/core/app-subs")
public class AppSubscriptionController {

    private final AppSubscriptionService appSubscriptionService;

    private final ModelMapper modelMapper;

    public AppSubscriptionController(AppSubscriptionService appSubscriptionService, ModelMapper modelMapper) {
        this.appSubscriptionService = appSubscriptionService;
        this.modelMapper = modelMapper;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<AppSubscriptionDto> getSubscribedApps() {
        return appSubscriptionService.getAppSubscriptions(getUserId()).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @RequestMapping("{appId}")
    public AppSubscriptionDto getSubscribedAppById(@PathVariable("appId") String appId, @RequestHeader(value = "Accept-Language", required = false) String locale) {
        return appSubscriptionService.getAppSubscription(appId, getUserId())
                .map(this::convertToDto)
                .orElseThrow(() -> new AppSubscriptionNotFoundException(appId));
    }

    @RequestMapping(value = "update-app-position", method = RequestMethod.GET)
    public void updateSubscribedAppPosition(String appId, Integer x, Integer y, Integer rows, Integer cols) {
        appSubscriptionService.updatePositionByAppId(appId, getUserId(), x, y, rows, cols);
    }

    private AppSubscriptionDto convertToDto(AppSubscription appSubscription) {
        AppSubscriptionDto appSubscriptionDto = modelMapper.map(appSubscription, AppSubscriptionDto.class);
        appSubscriptionDto.setApp(modelMapper.map(appSubscription.getApp(), appSubscription.getApp().getDtoClass()));
        appSubscriptionDto.getApp().setSubscribed(true);
        return appSubscriptionDto;
    }

    private String getUserId() {
        return SecurityContextHelper.getUser().getId();
    }

}
