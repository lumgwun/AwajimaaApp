package com.skylightapp.Classes;


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

    private ArrayList<SkylightPackageModel> listOfCategory = new ArrayList<SkylightPackageModel>();
    private ConcurrentHashMap<String, ArrayList<SkyLightPackModel>> mapOfProductsInCategory = new ConcurrentHashMap<String, ArrayList<SkyLightPackModel>>();
    private List<SkyLightPackModel> listOfProductsInShoppingList = Collections.synchronizedList(new ArrayList<SkyLightPackModel>());
    private List<Set<String>> listOfItemSetsForDataMining = new ArrayList<>();

    public static CenterRepository getCenterRepository() {

        if (null == centerRepository) {
            centerRepository = new CenterRepository();
        }
        return centerRepository;
    }


    public List<SkyLightPackModel> getListOfProductsInShoppingList() {
        return listOfProductsInShoppingList;
    }

    public void setListOfProductsInShoppingList(ArrayList<SkyLightPackModel> getShoppingList) {
        this.listOfProductsInShoppingList = getShoppingList;
    }

    public Map<String, ArrayList<SkyLightPackModel>> getMapOfProductsInCategory() {

        return mapOfProductsInCategory;
    }

    public void setMapOfProductsInCategory(ConcurrentHashMap<String, ArrayList<SkyLightPackModel>> mapOfProductsInCategory) {
        this.mapOfProductsInCategory = mapOfProductsInCategory;
    }

    public ArrayList<SkylightPackageModel> getListOfCategory() {

        return listOfCategory;
    }

    public void setListOfCategory(ArrayList<SkylightPackageModel> listOfCategory) {
        this.listOfCategory = listOfCategory;
    }

    public List<Set<String>> getItemSetList() {

        return listOfItemSetsForDataMining;
    }

    public void addToItemSetList(HashSet list) {
        listOfItemSetsForDataMining.add(list);
    }
}


