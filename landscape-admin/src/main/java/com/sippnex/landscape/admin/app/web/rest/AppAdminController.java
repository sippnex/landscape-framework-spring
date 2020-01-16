package com.sippnex.landscape.admin.app.web.rest;

import com.sippnex.firemaw.FiremawDto;
import com.sippnex.firemaw.FiremawProcessor;
import com.sippnex.landscape.core.app.AppRegistry;
import com.sippnex.landscape.core.app.domain.App;
import com.sippnex.landscape.core.app.service.AppService;
import com.sippnex.landscape.core.app.web.dto.AppDto;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;

@RestController
@RequestMapping("/api/admin/apps")
public class AppAdminController {

    private final AppRegistry appRegistry;

    private final AppService appService;

    private final FiremawProcessor firemawProcessor;

    private final ModelMapper modelMapper;

    public AppAdminController(AppRegistry appRegistry, AppService appService, FiremawProcessor firemawProcessor, ModelMapper modelMapper) {
        this.appRegistry = appRegistry;
        this.appService = appService;
        this.firemawProcessor = firemawProcessor;
        this.modelMapper = modelMapper;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public Page<AppDto> getAllApps(@RequestHeader(value = "Accept-Language", required = false) String locale,
                                   @RequestParam(value = "page", defaultValue = "0", required = false) Integer page,
                                   @RequestParam(value = "size", defaultValue = "20", required = false) Integer size,
                                   @RequestParam(value = "sortDir", defaultValue = "DESC", required = false) String sortDir,
                                   @RequestParam(value = "sort", defaultValue = "id", required = false) String sort) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.fromString(sortDir), sort);
        return appService.getAllApps(pageRequest)
                .map( this::convertToDto);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public ResponseEntity<FiremawDto> getAppById(@PathVariable("id") String id) {
        return appService.getAppById(id)
                .map(app -> new ResponseEntity<>(firemawProcessor.serialize(app), HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @RequestMapping(value = "/skeleton/{type}", method = RequestMethod.GET)
    public ResponseEntity<FiremawDto> getAppSkeleton(@PathVariable("type") String type) {
        Optional<Class<? extends App>> optionalAppClass = appRegistry.getAppClass(type);
        if (optionalAppClass.isPresent()) {
            Class<? extends App> appClass = optionalAppClass.get();
            try {
                App app = appClass.newInstance();
                FiremawDto firemawDto = firemawProcessor.serialize(app);
                return new ResponseEntity<>(firemawDto, HttpStatus.OK);
            } catch (Exception e) {
                e.printStackTrace();
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<Void> createNewApp(@RequestBody FiremawDto firemawDto, UriComponentsBuilder ucBuilder) {

        App postedApp = convertToApp(firemawDto);
        if (postedApp == null) {
            System.out.println("App Creation failed: App Parsing error!");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        // check name conflict
        Optional<? extends App> optionalApp = appService.getAppByName(postedApp.getName());
        if (optionalApp.isPresent()) {
            System.out.println("App Creation failed: App with same Name already exists!");
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        App storedApp = appService.save(postedApp);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/api/admin/apps/{id}").buildAndExpand(storedApp.getId()).toUri());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "", method = RequestMethod.PUT)
    public ResponseEntity<Void> updateApp(@RequestBody FiremawDto firemawDto, UriComponentsBuilder ucBuilder) {

        App postedApp = convertToApp(firemawDto);
        if (postedApp == null) {
            System.out.println("App Update failed: App Parsing error!");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Optional<? extends App> optionalApp = appService.getAppById(postedApp.getId());
        if (!optionalApp.isPresent()) {
            System.out.println("App Update failed: App does not exist!");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // update app
        App storedApp = optionalApp.get();
        storedApp.update(postedApp);

        // check name conflict
        if (appService.getAppByNameAndIdNot(storedApp.getId(), storedApp.getName()).isPresent()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        storedApp = appService.save(storedApp);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/api/admin/apps/{id}").buildAndExpand(storedApp.getId()).toUri());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    private App convertToApp(FiremawDto firemawDto) {
        String className = (String) firemawDto.getPropertyValue("type");
        if (className.length() == 0) {
            System.out.println("App parsing failed: Unknown app-type!");
            return null;
        }
        return appRegistry.getAppClass(className)
                .map(appClass -> (App) firemawProcessor.deserialize(firemawDto, appClass))
                .orElse(null);
    }

    private AppDto convertToDto(App app) {
        return modelMapper.map(app, app.getDtoClass());
    }

}
