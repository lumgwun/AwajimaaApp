package com.skylightapp.Classes;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.skylightapp.MarketClasses.MarketBizPackModel;

import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class ProductPageModel implements List<ProductPageModel> {
    public static  final int BANNER_SLIDER =0;
    public static final int STRIP_AD_BANNER=1;
    public static final int HORIZONTAL_PRODUCT_VIEW=2;
    public static final int GRID_PRODUCT_VIEW=3;
    private int type;
    private String backgroundColor;
    ///////////////////////// BANNER SLIDER  /////////////////////////////////////////
    private List<SliderModel> sliderModelList;

    public ProductPageModel(int type, List<SliderModel> sliderModelList) {
        this.type = type;
        this.sliderModelList = sliderModelList;
    }


    public int getType() {
        return type;
    }
    public void setType(int type) {
        this.type = type;
    }
    public List<SliderModel> getSliderModelList() {
        return sliderModelList;
    }
    public void setSliderModelList(List<SliderModel> sliderModelList) {
        this.sliderModelList = sliderModelList;
    }
    ///////////////////////// BANNER SLIDER  /////////////////////////////////////////

    ////////////////////////////////STRIP AD  ////////////////////////////////////////////////
    private String resource;


    public ProductPageModel(int type, String resource, String backgroundColor) {
        this.type = type;
        this.resource = resource;
        this.backgroundColor = backgroundColor;
    }

    public String getResource() {
        return resource;
    }
    public void setResource(String resource) {
        this.resource = resource;
    }
    public String getBackgroundColor() {
        return backgroundColor;
    }
    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }


    ////////////////////////////////STRIP AD  ////////////////////////////////////////////////



    /////////////////////////////HORIZONTAL PRODUCT VIEW &&  GRID PRODUCT LAYOUT
    private String title;
    private List<MarketBizPackModel> horizontalProductScrollModelList;
    private List<WishlistModel> viewAllProductList;
    public ProductPageModel(int type, String title, String backgroundColor , List<MarketBizPackModel> horizontalProductScrollModelList, List<WishlistModel> viewAllProductList) {
        this.type = type;
        this.title = title;
        this.backgroundColor = backgroundColor;
        this.horizontalProductScrollModelList = horizontalProductScrollModelList;
        this.viewAllProductList =viewAllProductList;
    }

    public List<WishlistModel> getViewAllProductList() {
        return viewAllProductList;
    }

    public void setViewAllProductList(List<WishlistModel> viewAllProductList) {
        this.viewAllProductList = viewAllProductList;
    }

    public ProductPageModel(int type, String title, String backgroundColor , List<MarketBizPackModel> horizontalProductScrollModelList) {
        this.type = type;
        this.title = title;
        this.backgroundColor = backgroundColor;
        this.horizontalProductScrollModelList = horizontalProductScrollModelList;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<MarketBizPackModel> getHorizontalProductScrollModelList() {
        return horizontalProductScrollModelList;
    }

    public void setHorizontalProductScrollModelList(List<MarketBizPackModel> horizontalProductScrollModelList) {
        this.horizontalProductScrollModelList = horizontalProductScrollModelList;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean contains(@Nullable @org.jetbrains.annotations.Nullable Object o) {
        return false;
    }

    @NonNull
    @NotNull
    @Override
    public Iterator<ProductPageModel> iterator() {
        return null;
    }

    @NonNull
    @NotNull
    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @NonNull
    @NotNull
    @Override
    public <T> T[] toArray(@NonNull @NotNull T[] ts) {
        return null;
    }

    @Override
    public boolean add(ProductPageModel productPageModel) {
        return false;
    }

    @Override
    public boolean remove(@Nullable @org.jetbrains.annotations.Nullable Object o) {
        return false;
    }

    @Override
    public boolean containsAll(@NonNull @NotNull Collection<?> collection) {
        return false;
    }

    @Override
    public boolean addAll(@NonNull @NotNull Collection<? extends ProductPageModel> collection) {
        return false;
    }

    @Override
    public boolean addAll(int i, @NonNull @NotNull Collection<? extends ProductPageModel> collection) {
        return false;
    }

    @Override
    public boolean removeAll(@NonNull @NotNull Collection<?> collection) {
        return false;
    }

    @Override
    public boolean retainAll(@NonNull @NotNull Collection<?> collection) {
        return false;
    }

    @Override
    public void clear() {

    }

    @Override
    public ProductPageModel get(int i) {
        return null;
    }

    @Override
    public ProductPageModel set(int i, ProductPageModel productPageModel) {
        return null;
    }

    @Override
    public void add(int i, ProductPageModel productPageModel) {

    }

    @Override
    public ProductPageModel remove(int i) {
        return null;
    }

    @Override
    public int indexOf(@Nullable @org.jetbrains.annotations.Nullable Object o) {
        return 0;
    }

    @Override
    public int lastIndexOf(@Nullable @org.jetbrains.annotations.Nullable Object o) {
        return 0;
    }

    @NonNull
    @NotNull
    @Override
    public ListIterator<ProductPageModel> listIterator() {
        return null;
    }

    @NonNull
    @NotNull
    @Override
    public ListIterator<ProductPageModel> listIterator(int i) {
        return null;
    }

    @NonNull
    @NotNull
    @Override
    public List<ProductPageModel> subList(int i, int i1) {
        return null;
    }

    public void add(String toUpperCase) {

    }
}
