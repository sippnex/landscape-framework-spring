package com.sippnex.landscape.core.app.web.rest;

import com.sippnex.landscape.core.app.domain.App;
import com.sippnex.landscape.core.app.domain.AppSubscription;
import com.sippnex.landscape.core.app.service.AppService;
import com.sippnex.landscape.core.app.service.AppSubscriptionService;
import com.sippnex.landscape.core.app.web.dto.AppDto;
import com.sippnex.landscape.core.security.domain.CustomUserDetails;
import com.sippnex.landscape.core.security.domain.User;
import com.sippnex.landscape.core.security.service.UserService;
import com.sippnex.landscape.core.security.util.SecurityContextHelper;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/core/app-store")
public class AppStoreController {

    private final AppService appService;

    private final AppSubscriptionService appSubscriptionService;

    private final UserService userService;

    private final ModelMapper modelMapper;

    public AppStoreController(AppService appService, AppSubscriptionService appSubscriptionService, UserService userService, ModelMapper modelMapper) {
        this.appService = appService;
        this.appSubscriptionService = appSubscriptionService;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @RequestMapping("")
    public List<AppDto> getAvailableApps(@RequestHeader(value = "Accept-Language", required = false) String locale) {
        return appService.getAllApps().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @RequestMapping("{id}")
    public ResponseEntity<AppDto> getAvailableAppById(@PathVariable("id") String id, @RequestHeader(value = "Accept-Language", required = false) String locale) {
        return appService.getAppById(id)
                .map(app -> new ResponseEntity<>(convertToDto(app), HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @RequestMapping(value = "subscribe", method = RequestMethod.GET)
    public void subscribeApp(String appId) {

        // 1. Get app entity from service
        Optional<? extends App> optionalApp = appService.getAppById(appId);
        if (!optionalApp.isPresent()) {
            return;
        }
        App app = optionalApp.get();

        // 2. Get user id from security context
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = ((CustomUserDetails) authentication.getPrincipal()).getId();

        // 3. Get user entity from service
        Optional<User> optionalUser = userService.getUserById(userId);
        if (!optionalUser.isPresent()) {
            return;
        }
        User user = optionalUser.get();

        // Create and persist new app subscription relationship entity
        AppSubscription appSubscription = new AppSubscription();
        appSubscription.setX(0);
        appSubscription.setY(0);
        appSubscription.setCols(1);
        appSubscription.setRows(1);
        appSubscription.setApp(app);
        appSubscription.setUser(user);
        appSubscriptionService.save(appSubscription);
    }

    @RequestMapping(value = "unsubscribe", method = RequestMethod.GET)
    public void unsubscribeApp(String appId) {
        appSubscriptionService.delete(appId, getUserId());
    }

    private AppDto convertToDto(App app) {
        AppDto appDto = modelMapper.map(app, AppDto.class);
        appDto.setSubscribed(appSubscriptionService.isSubscribed(app.getId(), getUserId()));
        return appDto;
    }

    private String getUserId() {
        return SecurityContextHelper.getUser().getId();
    }
}
