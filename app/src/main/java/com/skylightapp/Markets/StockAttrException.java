package com.skylightapp.Markets;

public class StockAttrException extends Exception {
    private static final long serialVersionUID = 8443349472733105703L;
    private final int detailMessageId;

    public StockAttrException(int detailMessageId) {
        super();
        this.detailMessageId = detailMessageId;
    }

    public int getDetailMessageId() {
        return detailMessageId;
    }
}
