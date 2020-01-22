package com.sippnex.landscape.addressbook;

import com.sippnex.landscape.core.app.domain.App;
import com.sippnex.landscape.core.app.domain.AppEntity;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "app")
public class AddressBookApp extends App {

    private AppEntity<AddressEntry> addressEntryEntity = new AppEntity<>(
            "Address Entry",
            "/api/custom/address-book-app/entries",
            AddressEntry.class
    );

    public AppEntity<AddressEntry> getAddressEntryEntity() {
        return addressEntryEntity;
    }

    @Override
    public void update(App app) {
        super.update(app);
        // TODO: write update code
    }

}
