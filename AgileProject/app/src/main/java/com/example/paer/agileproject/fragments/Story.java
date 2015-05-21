package com.example.paer.agileproject.fragments;

import java.util.ArrayList;

/**
 * Created by Qi on 2015/5/18.
 */
public class Story {
    private String kind;
    private String id;
    private String name;
    private ArrayList<String> ownerIds;
    private String ownedById;
    private String createAt;
    private String updateAt;
    private String acceptedAt;
    private String estimate;
    private String storyType;
    private String currentState;
    private String requestedById;
    private String url;
    private String description;
    private ArrayList<String> lable;

    public String getKind(){
        return kind;
    }

    public String getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public ArrayList<String> getOwnerIds(){
        return ownerIds;
    }

    public String getOwnedById(){ return ownedById; }

    public String getCreateAt(){
        return createAt;
    }

    public String getUpdateAt(){
        return updateAt;
    }

    public String getAcceptedAt(){
        return acceptedAt;
    }

    public String getEstimate(){
        return estimate;
    }

    public String getStoryType(){
        return storyType;
    }

    public String getCurrentState(){
        return currentState;
    }

    public String getRequestedById(){
        return requestedById;
    }

    public String getUrl(){
        return url;
    }

    public String getDescription(){
        return  description;
    }


    public ArrayList<String> getLable(){
        return lable;
    }

    public void setKind(String Kind){
        this.kind = Kind;
    }

    public void setId(String Id){
        this.id = Id;
    }

    public void setName(String Name){
        this.name = Name;
    }

    public void setOwnerIds(ArrayList<String> OwenerIds){
        this.ownerIds = OwenerIds;
    }

    public void setOwnedById(String OwnedById){
        this.ownedById = OwnedById;
    }

    public void setCreateAt(String CreateAt){
        this.createAt = CreateAt;
    }

    public void setUpdateAt(String UpdateAt){
        this.updateAt = UpdateAt;
    }

    public void setAcceptedAt(String AcceptedAt){
        this.acceptedAt = AcceptedAt;
    }

    public void setEstimate(String Estimate){
        this.estimate = Estimate;
    }

    public void setStoryType(String StoryType){
        this.storyType = StoryType;
    }

    public void setCurrentState(String CurrentState){
        this.currentState = CurrentState;
    }

    public void setRequestedById(String RequestedById){
        this.requestedById = RequestedById;
    }

    public void setUrl(String Url){
        this.url = Url;
    }

    public void setDescriptionescription(String Description){
        this.description = Description;
    }

    public void setLable(ArrayList<String> Lable){
        this.lable = Lable;
    }

    public String toString (){
        String ret = " Id: " + this.id + "\n Name: " +this.name+ "\n Create at: " + this.createAt +
                "\n Update at: " + this.updateAt + "\n Estimate: " + this.estimate + "\n Story type: "
                + this.storyType+ "\n Current state: " + this.currentState + "\n Kind: " + this.kind +
                "\n Description: " + this.description;
        return ret;
    }
}
