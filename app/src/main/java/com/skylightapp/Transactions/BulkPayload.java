package com.skylightapp.Transactions;

public class BulkPayload {

    private String seckey;
    private String title;
    private BulkdataPayload bulk_data;
    public String getSeckey() {
        return seckey;
    }


    public void setSeckey(String seckey) {
        this.seckey = seckey;
    }


    public String getTitle() {
        return title;
    }


    public void setTitle(String title) {
        this.title = title;
    }


    public BulkdataPayload getBulk_data() {
        return bulk_data;
    }


    public void setBulk_data(BulkdataPayload bulk_data) {
        this.bulk_data = bulk_data;
    }

//    {
//  "seckey":"FLWSECK-xxxxxxxxxxxxxxxxxxxxx-X",
//  "title":"May Staff Salary",
//  "bulk_data":[
//  	{
//        "Bank":"044",
//        "Account Number": "0690000032",
//        "Amount":500,
//        "Currency":"NGN",
//        "Narration":"Bulk transfer 1",
//        "reference": "mk-82973029"
//    },
//    {
//        "Bank":"044",
//        "Account Number": "0690000034",
//        "Amount":500,
//        "Currency":"NGN",
//        "Narration":"Bulk transfer 2",
//        "reference": "mk-283874750"
//    }
//  ]
//}

}
