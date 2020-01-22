package com.sippnex.landscape.addressbook;

import com.sippnex.firemaw.FiremawProperty;
import com.sippnex.firemaw.FiremawType;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class AddressEntry {

    @Id
    @FiremawProperty(name="id", type= FiremawType.TextField, disabled = true)
    private String id;

    @FiremawProperty(name="firstName", type= FiremawType.TextField)
    private String firstName;

    @FiremawProperty(name="lastName", type= FiremawType.TextField)
    private String lastName;

    @FiremawProperty(name="email", type= FiremawType.TextField)
    private String email;

    public AddressEntry() {
    }

    public AddressEntry(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void update(AddressEntry addressEntry) {
        this.firstName = addressEntry.firstName;
        this.lastName = addressEntry.lastName;
        this.email = addressEntry.email;
    }
}
