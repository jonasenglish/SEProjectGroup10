package com.main.objects;

import org.bson.Document;

public class Amenity {
    
    public Amenity(String name, String description, AmenityType amenityType){
        this.name = name;
        this.description = description;
        this.setAmenityType(amenityType);
    }

    public Amenity(){
    }
    
    public enum AmenityType {STANDARD, QUEEN, KING}
    
    private String name;
    private String description;
    private AmenityType amenityType;
    
    public AmenityType getAmenityType() {
        return amenityType;
    }

    public void setAmenityType(AmenityType amenityType) {
        this.amenityType = amenityType;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Document ToDocument(){
        Document document = new Document();
        document.append("Name", this.name);
        document.append("Description", this.description);
        return document;
    }

    public static Amenity FromDocument(Document document){
        Amenity amenity = new Amenity((String)document.get("Name"), (String)document.get("Description"), (AmenityType)document.get("Amenity Type"));
        return amenity;
    }
}
