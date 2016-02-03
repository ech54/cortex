package lu.cortex.domain.user.model;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Table;

import lu.cortex.domain.user.api.Address;
import lu.cortex.domain.user.api.User;
import lu.cortex.model.AbstractDataModel;

@Entity
@Table(name="USER")
public class UserDefault extends AbstractDataModel implements User {

    private String login;
    private String name;
    private String firstName;
    private List<Address> postalAddresses = new ArrayList<>();
    private List<Address> emailAddresses = new ArrayList<>();

    public UserDefault() {}

    @Override
    public String getLogin() {
        return login;
    }

    @Override
    public void setLogin(String login) {
        this.login = login;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(final String name) {
        this.name = name;
    }

    @Override
    public String getFirstName() {
        return firstName;
    }

    @Override
    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    @Override
    public List<Address> getPostalAddresses() {
        return postalAddresses;
    }

    @Override
    public void setPostalAddresses(final List<Address> postalAddresses) {
        this.postalAddresses = postalAddresses;
    }

    @Override
    public List<Address> getEmailAddresses() {
        return emailAddresses;
    }

    @Override
    public void setEmailAddresses(final List<Address> emailAddresses) {
        this.emailAddresses = emailAddresses;
    }
}
