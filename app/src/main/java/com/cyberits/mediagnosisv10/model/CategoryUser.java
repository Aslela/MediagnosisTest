package com.cyberits.mediagnosisv10.model;

/**
 * Created by w7 on 30/11/2015.
 */
public class CategoryUser {

    private String objectId;
    private String categoryName;

    public CategoryUser(String id, String name){
        objectId = id;
        categoryName = name;
    }
    public CategoryUser(){

    }
    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }


}
