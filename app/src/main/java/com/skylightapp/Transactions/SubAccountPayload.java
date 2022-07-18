package com.skylightapp.Transactions;

public class SubAccountPayload {
    public String getSplit_type() {
        return split_type;
    }

    /**
     * @param split_type the split_type to set
     */
    public void setSplit_type(String split_type) {
        this.split_type = split_type;
    }

    /**
     * @return the split_value
     */
    public String getSplit_value() {
        return split_value;
    }

    /**
     * @param split_value the split_value to set
     */
    public void setSplit_value(String split_value) {
        this.split_value = split_value;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the account_bank
     */
    public String getAccount_bank() {
        return account_bank;
    }

    /**
     * @param account_bank the account_bank to set
     */
    public void setAccount_bank(String account_bank) {
        this.account_bank = account_bank;
    }

    /**
     * @return the account_number
     */
    public String getAccount_number() {
        return account_number;
    }

    /**
     * @param account_number the account_number to set
     */
    public void setAccount_number(String account_number) {
        this.account_number = account_number;
    }

    /**
     * @return the business_name
     */
    public String getBusiness_name() {
        return business_name;
    }

    /**
     * @param business_name the business_name to set
     */
    public void setBusiness_name(String business_name) {
        this.business_name = business_name;
    }

    /**
     * @return the business_email
     */
    public String getBusiness_email() {
        return business_email;
    }

    /**
     * @param business_email the business_email to set
     */
    public void setBusiness_email(String business_email) {
        this.business_email = business_email;
    }

    /**
     * @return the business_contact
     */
    public String getBusiness_contact() {
        return business_contact;
    }

    /**
     * @param business_contact the business_contact to set
     */
    public void setBusiness_contact(String business_contact) {
        this.business_contact = business_contact;
    }

    /**
     * @return the business_contact_mobile
     */
    public String getBusiness_contact_mobile() {
        return business_contact_mobile;
    }

    /**
     * @param business_contact_mobile the business_contact_mobile to set
     */
    public void setBusiness_contact_mobile(String business_contact_mobile) {
        this.business_contact_mobile = business_contact_mobile;
    }

    /**
     * @return the business_mobile
     */
    public String getBusiness_mobile() {
        return business_mobile;
    }

    /**
     * @param business_mobile the business_mobile to set
     */
    public void setBusiness_mobile(String business_mobile) {
        this.business_mobile = business_mobile;
    }

    /**
     * @return the seckey
     */
    public String getSeckey() {
        return seckey;
    }

    /**
     * @param seckey the seckey to set
     */
    public void setSeckey(String seckey) {
        this.seckey = seckey;
    }

    /**
     * @return the meta
     */
    public Mpesameta getMeta() {
        return meta;
    }

    /**
     * @param meta the meta to set
     */
    public void setMeta(Mpesameta meta) {
        this.meta = meta;
    }

//    {
//	"account_bank": "044",
//	"account_number": "0690000035",
//	"business_name": "JK Services",
//	"business_email": "jk@services.com",
//	"business_contact": "Seun Alade",
//	"business_contact_mobile": "090890382",
//	"business_mobile": "09087930450",
//	"meta": [{"metaname": "MarketplaceID", "metavalue": "ggs-920900"}],
//	"seckey": "<PASS YOUR SECRET KEY HERE>"
//}

    private String account_bank;
    private String account_number;
    private String business_name;
    private String business_email;
    private String business_contact;
    private String business_contact_mobile;
    private String business_mobile;
    private String seckey;
    private Mpesameta meta;
    private String id;
    private String split_type;
    private String split_value;
}
