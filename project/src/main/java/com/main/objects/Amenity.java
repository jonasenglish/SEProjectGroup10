package com.main.objects;

import org.bson.Document;

public class Amenity {
    
    public Amenity(String name, String description, AmenityType amenityType){
        this.name = name;
        this.description = description;
        this.setAmenityType(amenityType);
    }

    public Amenity(String name, String description, AmenityType amenityType, String amenityTypeString){
        this.name = name;
        this.description = description;
        this.setAmenityType(amenityType);
        this.amenityTypeString = amenityTypeString;
    }

    public String getAmenityTypeString() {
        return amenityTypeString;
    }

    public void setAmenityTypeString(String amenityTypeString) {
        this.amenityTypeString = amenityTypeString;
    }

    public Amenity(){
    }
    
    public enum AmenityType {STANDARD, QUEEN, KING}
    
    private String name;
    private String description;
    private AmenityType amenityType;
    private String amenityTypeString;
    
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
        document.append("Amenity Type String", this.amenityTypeString);
        return document;
    }

    public static Amenity FromDocument(Document document){
        Amenity amenity = new Amenity();
        amenity.setName((String)document.get("Name"));
        amenity.setDescription((String)document.get("Description"));
        Object obj = document.get("Amenity Type String");
        if(obj != null){
            amenity.setAmenityTypeString((String) obj);
            switch(amenity.getAmenityTypeString()){
                case "Standard":
                    amenity.setAmenityType(AmenityType.STANDARD);
                    break;
                case "Queen":
                    amenity.setAmenityType(AmenityType.QUEEN);
                    break;
                case "King":
                    amenity.setAmenityType(AmenityType.KING);
                    break;
            }
        }
        return amenity;
    }
}
