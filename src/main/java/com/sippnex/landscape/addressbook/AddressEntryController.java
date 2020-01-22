package com.sippnex.landscape.addressbook;

import com.sippnex.firemaw.FiremawDto;
import com.sippnex.firemaw.FiremawProcessor;
import com.sippnex.landscape.core.app.domain.App;
import com.sippnex.landscape.core.app.domain.AppEntityController;
import com.sippnex.landscape.core.app.web.dto.AppDto;
import org.apache.tomcat.jni.Address;
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
@RequestMapping("/api/custom/address-book-app/entries")
public class AddressEntryController implements AppEntityController {

    private final AddressEntryRepository addressEntryRepository;

    private final FiremawProcessor firemawProcessor;

    public AddressEntryController(AddressEntryRepository addressEntryRepository, FiremawProcessor firemawProcessor) {
        this.addressEntryRepository = addressEntryRepository;
        this.firemawProcessor = firemawProcessor;
    }

    @Override
    @GetMapping("")
    public Page<FiremawDto> getAll(@RequestHeader(value = "Accept-Language", required = false) String locale,
                                   @RequestParam(value = "page", defaultValue = "0", required = false) Integer page,
                                   @RequestParam(value = "size", defaultValue = "20", required = false) Integer size,
                                   @RequestParam(value = "sortDir", defaultValue = "DESC", required = false) String sortDir,
                                   @RequestParam(value = "sort", defaultValue = "id", required = false) String sort) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.fromString(sortDir), sort);
        return addressEntryRepository.findAll(pageRequest)
                .map(this::convertToFiremawDto);
    }

    @Override
    @GetMapping("{id}")
    public ResponseEntity<FiremawDto> get(@PathVariable String id) {
        return addressEntryRepository.findById(id)
                .map(addressEntry -> new ResponseEntity<>(firemawProcessor.serialize(addressEntry), HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Override
    @GetMapping("skeleton")
    public ResponseEntity<FiremawDto> getSkeleton() {
        return new ResponseEntity<>(firemawProcessor.serialize(new AddressEntry()), HttpStatus.OK);
    }

    @Override
    @PostMapping("")
    public ResponseEntity<Void> create(FiremawDto firemawDto, UriComponentsBuilder ucBuilder) {

        AddressEntry entity = convertToEntity(firemawDto);
        if (entity == null) {
            System.out.println("Entity Creation failed: Entity Parsing error!");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        entity = addressEntryRepository.save(entity);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/api/custom/address-book-app/entries/{id}").buildAndExpand(entity.getId()).toUri());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @Override
    @PutMapping("")
    public ResponseEntity<Void> update(FiremawDto firemawDto, UriComponentsBuilder ucBuilder) {
        AddressEntry postedEntity = convertToEntity(firemawDto);
        if (postedEntity == null) {
            System.out.println("Entity Update failed: Entity Parsing error!");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Optional<AddressEntry> optionalEntity = addressEntryRepository.findById(postedEntity.getId());
        if (!optionalEntity.isPresent()) {
            System.out.println("Entity Update failed: Entity does not exist!");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // update entity
        AddressEntry storedEntity = optionalEntity.get();
        storedEntity.update(postedEntity);

        storedEntity = addressEntryRepository.save(storedEntity);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/api/custom/address-book-app/entries/{id}").buildAndExpand(storedEntity.getId()).toUri());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @Override
    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        addressEntryRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private AddressEntry convertToEntity(FiremawDto firemawDto) {
        return (AddressEntry) firemawProcessor.deserialize(firemawDto, AddressEntry.class);
    }

    private FiremawDto convertToFiremawDto(AddressEntry addressEntry) {
        return firemawProcessor.serialize(addressEntry);
    }

}
