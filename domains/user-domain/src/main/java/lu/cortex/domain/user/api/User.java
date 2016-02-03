package lu.cortex.domain.user.api;

import java.util.List;

/**
 * The data model defines all properties
 *  allowing to operate on <code>User</code> object.
 */
public interface User {
    /**
     * Accessor in reading on the login.
     * @return The login.
     */
    String getLogin();

    /**
     * Accessor in writing on the login.
     * @param login The login.
     */
    void setLogin(final String login);

    /**
     * Accessor in reading on the user's name.
     * @return The name.
     */
    String getName();

    /**
     * Accessor in writing on the user's name.
     * @param name The name.
     */
    void setName(final String name);

    /**
     * Accessor in reading on the user's first name.
     * @return The first name.
     */
    String getFirstName();

    /**
     * Accessor in writing on the user's first name.
     * @param firstName The first name.
     */
    void setFirstName(final String firstName);

    /**
     * Accessor in reading on the postal addresses.
     * @return The addresses.
     */
    List<Address> getPostalAddresses();

    /**
     * Accessor in writing on the postal addresses.
     * @param addresses The addresses.
     */
    void setPostalAddresses(final List<Address> addresses);

    /**
     * Accessor in reading on the email addresses.
     * @return The email addresses.
     */
    List<Address> getEmailAddresses();

    /**
     * Accessor in writing on the email addresses.
     * @param addresses The email addresses.
     */
    void setEmailAddresses(final List<Address> addresses);
}
