package com.skylightapp.Classes;


import com.skylightapp.MarketClasses.MarketBizPackModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@SuppressWarnings("rawtypes")
public class CenterRepository {
    private static CenterRepository centerRepository;

    private ArrayList<AwajimaPackModel> listOfCategory = new ArrayList<AwajimaPackModel>();
    private ConcurrentHashMap<String, ArrayList<MarketBizPackModel>> mapOfProductsInCategory = new ConcurrentHashMap<String, ArrayList<MarketBizPackModel>>();
    private List<MarketBizPackModel> listOfProductsInShoppingList = Collections.synchronizedList(new ArrayList<MarketBizPackModel>());
    private List<Set<String>> listOfItemSetsForDataMining = new ArrayList<>();

    public static CenterRepository getCenterRepository() {

        if (null == centerRepository) {
            centerRepository = new CenterRepository();
        }
        return centerRepository;
    }


    public List<MarketBizPackModel> getListOfProductsInShoppingList() {
        return listOfProductsInShoppingList;
    }

    public void setListOfProductsInShoppingList(ArrayList<MarketBizPackModel> getShoppingList) {
        this.listOfProductsInShoppingList = getShoppingList;
    }

    public Map<String, ArrayList<MarketBizPackModel>> getMapOfProductsInCategory() {

        return mapOfProductsInCategory;
    }

    public void setMapOfProductsInCategory(ConcurrentHashMap<String, ArrayList<MarketBizPackModel>> mapOfProductsInCategory) {
        this.mapOfProductsInCategory = mapOfProductsInCategory;
    }

    public ArrayList<AwajimaPackModel> getListOfCategory() {

        return listOfCategory;
    }

    public void setListOfCategory(ArrayList<AwajimaPackModel> listOfCategory) {
        this.listOfCategory = listOfCategory;
    }

    public List<Set<String>> getItemSetList() {

        return listOfItemSetsForDataMining;
    }

    public void addToItemSetList(HashSet list) {
        listOfItemSetsForDataMining.add(list);
    }
}


