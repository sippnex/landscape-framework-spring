package com.sippnex.landscape.core.app.domain;

import com.sippnex.firemaw.FiremawDto;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

public interface AppEntityController {

    Page<FiremawDto> getAll(String locale, Integer page, Integer size, String sortDir, String sort);

    ResponseEntity<FiremawDto> get(String id);

    ResponseEntity<FiremawDto> getSkeleton();

    ResponseEntity<Void> create(FiremawDto dto, UriComponentsBuilder ucBuilder);

    ResponseEntity<Void> update(FiremawDto dto, UriComponentsBuilder ucBuilder);

    ResponseEntity<Void> delete(String id);

}
