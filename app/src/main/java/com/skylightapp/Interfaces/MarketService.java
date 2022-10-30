package com.skylightapp.Interfaces;

import com.skylightapp.MapAndLoc.EmergReportNext;
import com.skylightapp.MapAndLoc.EmergencyReport;
import com.skylightapp.MarketClasses.BizLoan;
import com.skylightapp.MarketClasses.Market;
import com.skylightapp.MarketClasses.MarketAdmin;
import com.skylightapp.MarketClasses.MarketAnnouncement;
import com.skylightapp.MarketClasses.MarketBizRegulator;
import com.skylightapp.MarketClasses.MarketBizSubScription;
import com.skylightapp.MarketClasses.MarketBusiness;
import com.skylightapp.MarketClasses.MarketCommodity;
import com.skylightapp.MarketClasses.MarketCustomer;
import com.skylightapp.MarketClasses.MarketDays;
import com.skylightapp.MarketClasses.MarketInventory;
import com.skylightapp.MarketClasses.MarketStock;
import com.skylightapp.MarketClasses.MarketTranx;

import java.util.ArrayList;

public interface MarketService {
    ArrayList<MarketAdmin> findCurrentAdminList(int marketID);
    ArrayList<MarketAnnouncement> findCurMAnnounmtList(int marketID);
    ArrayList<MarketBusiness> findCurMBizList(int marketID);
    ArrayList<MarketCommodity> findCurMCommodityList(int marketID);
    ArrayList<MarketCustomer> findCurMCusList(int marketID);
    ArrayList<MarketDays> findCurMDaysList(int marketID);
    ArrayList<MarketBizSubScription> findCurMSubList(int marketID);
    ArrayList<MarketBizRegulator> findCurMRegList(int marketID);
    ArrayList<MarketTranx> findCurMTranxList(int marketID);
    ArrayList<MarketInventory> findCurMInvList(int marketID);
    ArrayList<MarketStock> findCurMStocksList(int marketID);
    ArrayList<EmergencyReport> findCurMEmergencyList(int marketID);
    ArrayList<EmergReportNext> findCurMEmergNxtList(int marketID);
    ArrayList<Market> findCurMMarketList(int marketID);
    ArrayList<BizLoan> findCurMBizLoanList(int marketID);
}
