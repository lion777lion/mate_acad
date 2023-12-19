package com.example.botma.dto;

public class VacancyDto {

    private String id; 
    private String name;
    private String level;
    private String description;

    public VacancyDto() {}


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    
    

    
    
    @Override
    public String toString(){
        return name;
    }
}
