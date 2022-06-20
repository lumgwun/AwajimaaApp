package com.skylightapp.Transactions;

public class Fetchcardpayload {
    public String getTest() {
        return test;
    }


    public void setTest(String test) {
        this.test = test;
    }


    public String getFromDate() {
        return FromDate;
    }


    public void setFromDate(String FromDate) {
        this.FromDate = FromDate;
    }


    public String getToDate() {
        return ToDate;
    }


    public void setToDate(String ToDate) {
        this.ToDate = ToDate;
    }


    public String getPageIndex() {
        return PageIndex;
    }

    /**
     * @param PageIndex the PageIndex to set
     */
    public void setPageIndex(String PageIndex) {
        this.PageIndex = PageIndex;
    }

    /**
     * @return the PageSize
     */
    public String getPageSize() {
        return PageSize;
    }

    /**
     * @param PageSize the PageSize to set
     */
    public void setPageSize(String PageSize) {
        this.PageSize = PageSize;
    }

    /**
     * @return the CardId
     */
    public String getCardId() {
        return CardId;
    }

    /**
     * @param CardId the CardId to set
     */
    public void setCardId(String CardId) {
        this.CardId = CardId;
    }

//    		{
//      "FromDate": "2018-02-13",
//      "ToDate": "2019-12-21",
//      "PageIndex": 0,
//      "PageSize": 20,
//      "CardId": "105c55f1-b69f-4915-b8e1-d2f645cd9955"
//		}

    private String FromDate;
    private String ToDate;
    private String PageIndex;
    private String PageSize;
    private String CardId;
    private String test;

}
