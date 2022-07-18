package com.skylightapp.Transactions;

public class WebhookTransferPayload {
    private String id;
    private String account_number;
    private String bank_code;
    private String fullname;
    private String date_created;
    private String currency;
    private String amount;
    private String fee;
    private String status;
    private String reference;
    private String narration;
    private String approver;
    private String complete_message;
    private String requires_approval;
    private String is_approved;
    private String bank_name;
    public String getId() {
        return id;
    }


    public void setId(String id) {
        this.id = id;
    }


    public String getAccount_number() {
        return account_number;
    }


    public void setAccount_number(String account_number) {
        this.account_number = account_number;
    }


    public String getBank_code() {
        return bank_code;
    }


    public void setBank_code(String bank_code) {
        this.bank_code = bank_code;
    }


    public String getFullname() {
        return fullname;
    }


    public void setFullname(String fullname) {
        this.fullname = fullname;
    }


    public String getDate_created() {
        return date_created;
    }


    public void setDate_created(String date_created) {
        this.date_created = date_created;
    }

    public String getCurrency() {
        return currency;
    }


    public void setCurrency(String currency) {
        this.currency = currency;
    }


    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }


    public String getFee() {
        return fee;
    }


    public void setFee(String fee) {
        this.fee = fee;
    }


    public String getStatus() {
        return status;
    }


    public void setStatus(String status) {
        this.status = status;
    }

    public String getReference() {
        return reference;
    }


    public void setReference(String reference) {
        this.reference = reference;
    }


    public String getNarration() {
        return narration;
    }


    public void setNarration(String narration) {
        this.narration = narration;
    }

    public String getApprover() {
        return approver;
    }


    public void setApprover(String approver) {
        this.approver = approver;
    }

    public String getComplete_message() {
        return complete_message;
    }


    public void setComplete_message(String complete_message) {
        this.complete_message = complete_message;
    }


    public String getRequires_approval() {
        return requires_approval;
    }


    public void setRequires_approval(String requires_approval) {
        this.requires_approval = requires_approval;
    }


    public String getIs_approved() {
        return is_approved;
    }


    public void setIs_approved(String is_approved) {
        this.is_approved = is_approved;
    }


    public String getBank_name() {
        return bank_name;
    }

    public void setBank_name(String bank_name) {
        this.bank_name = bank_name;
    }



}
