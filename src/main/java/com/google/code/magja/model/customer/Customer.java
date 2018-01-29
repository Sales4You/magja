/**
 * @author andre
 */
package com.google.code.magja.model.customer;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.google.code.magja.model.BaseMagentoModel;
import com.google.code.magja.utils.MagjaStringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class Customer extends BaseMagentoModel {

    private static final long serialVersionUID = 7260666195808816927L;

    public enum Gender {
        MALE(1), FEMALE(2);
        private final Integer value;

        private Gender(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }
    }

    private String prefix;
    private String firstName;
    private String middleName;
    private String lastName;
    private String suffix;
    private String email;
    private String password;
    private String passwordHash;
    private Integer storeId;
    private Integer websiteId;
    private Integer groupId;
    private Gender gender;
    private String createdAt;
    private String taxvat;

    public static Customer fromAttributes(Map<String, Object> attrs) {
        if (attrs.get("customer_id") != null) {
            Customer customer = new Customer();

            customer.setId(new Integer((String) attrs.get("customer_id")));
            if (attrs.get("customer_email") != null) {
                customer.setEmail((String) attrs.get("customer_email"));
            }
            if (attrs.get("customer_prefix") != null) {
                customer.setPrefix((String) attrs.get("customer_prefix"));
            }
            if (attrs.get("customer_firstname") != null) {
                customer.setFirstName((String) attrs.get("customer_firstname"));
            }
            if (attrs.get("customer_middlename") != null) {
                customer.setMiddleName((String) attrs.get("customer_middlename"));
            }
            if (attrs.get("customer_lastname") != null) {
                customer.setLastName((String) attrs.get("customer_lastname"));
            }
            if (attrs.get("customer_lastname") != null) {
                customer.setLastName((String) attrs.get("customer_lastname"));
            }
            if (attrs.get("customer_group_id") != null) {
                customer.setGroupId(new Integer((String) attrs.get("customer_group_id")));
            }
            if (attrs.get("customer_gender") != null) {
                Integer gender = new Integer((String) attrs.get("customer_gender"));
                customer.setGender(gender.equals(new Integer(1)) ? Gender.MALE : Gender.FEMALE);
            }
            if (attrs.get("taxvat") != null) {
                customer.setTaxvat((String) attrs.get("taxvat"));
            }

            return customer;
        }

        return null;
    }


    @Override
    public Object serializeToApi() {
        Map<String, Object> props = getAllProperties();
        props.remove("customer_id");
        if (gender != null) {
            props.put("gender", gender.getValue());
        }
        if (password != null) {
            props.put("password_hash", MagjaStringUtils.getMd5Hash(password));
        }
        if (taxvat != null) {
            props.put("taxvat", taxvat);
        }
        if (id != null) {
            props.put("id", id);
        }
        return props;
    }

    /**
     * @return the prefix
     */
    public String getPrefix() {
        return prefix;
    }

    /**
     * @param prefix
     *          the prefix to set
     */
    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    /**
     * @return the firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName
     *          the firstName to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return the middleName
     */
    public String getMiddleName() {
        return middleName;
    }

    /**
     * @param middleName
     *          the middleName to set
     */
    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    /**
     * @return the lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @param lastName
     *          the lastName to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * @return the suffix
     */
    public String getSuffix() {
        return suffix;
    }

    /**
     * @param suffix
     *          the suffix to set
     */
    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email
     *          the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the passwordHash
     */
    public String getPasswordHash() {
        return passwordHash;
    }

    /**
     * @param passwordHash
     *          the passwordHash to set
     */
    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    /**
     * @return the storeId
     */
    public Integer getStoreId() {
        return storeId;
    }

    /**
     * @param storeId
     *          the storeId to set
     */
    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    /**
     * @return the websiteId
     */
    public Integer getWebsiteId() {
        return websiteId;
    }

    /**
     * @param websiteId
     *          the websiteId to set
     */
    public void setWebsiteId(Integer websiteId) {
        this.websiteId = websiteId;
    }

    /**
     * @return the groupId
     */
    public Integer getGroupId() {
        return groupId;
    }

    /**
     * @param groupId
     *          the groupId to set
     */
    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password
     *          the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the gender
     */
    public Gender getGender() {
        return gender;
    }

    /**
     * @param gender
     *          the gender to set
     */
    public void setGender(Gender gender) {
        this.gender = gender;
    }

    /**
     * @return the createdAt
     */
    public String getCreatedAt() {
        return createdAt;
    }

    /**
     * @param createdAt
     *          the createdAt to set
     */
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }


    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .appendSuper(super.hashCode())
                .append(prefix)
                .append(firstName)
                .append(middleName)
                .append(lastName)
                .append(suffix)
                .append(email)
                .append(password)
                .append(passwordHash)
                .append(storeId)
                .append(websiteId)
                .append(groupId)
                .append(gender)
                .append(createdAt)
                .append(taxvat)
                .toHashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Customer customer = (Customer) o;

        return new EqualsBuilder()
                .appendSuper(super.equals(o))
                .append(prefix, customer.prefix)
                .append(firstName, customer.firstName)
                .append(middleName, customer.middleName)
                .append(lastName, customer.lastName)
                .append(suffix, customer.suffix)
                .append(email, customer.email)
                .append(password, customer.password)
                .append(passwordHash, customer.passwordHash)
                .append(storeId, customer.storeId)
                .append(websiteId, customer.websiteId)
                .append(groupId, customer.groupId)
                .append(gender, customer.gender)
                .append(createdAt, customer.createdAt)
                .append(taxvat, customer.taxvat)
                .isEquals();
    }

    public String getTaxvat() {
        return taxvat;
    }

    public void setTaxvat(String taxvat) {
        this.taxvat = taxvat;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "prefix='" + prefix + '\'' +
                ", firstName='" + firstName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", suffix='" + suffix + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", passwordHash='" + passwordHash + '\'' +
                ", storeId=" + storeId +
                ", websiteId=" + websiteId +
                ", groupId=" + groupId +
                ", gender=" + gender +
                ", createdAt='" + createdAt + '\'' +
                ", taxvat='" + taxvat + '\'' +
                '}';
    }
}
