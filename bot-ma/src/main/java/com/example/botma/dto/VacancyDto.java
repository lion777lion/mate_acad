package com.example.botma.dto;

import com.opencsv.bean.CsvBindByName;

public class VacancyDto {
    //Id,Title,Short description,Long description,Company,Salary,Link
    @CsvBindByName(column = "Id")
    private String id; 
    @CsvBindByName(column = "Title")
    private String name;
    private String level;
    @CsvBindByName(column = "Short description")
    private String description;
    @CsvBindByName(column = "Long description")
    private String fullDescription;
    @CsvBindByName(column = "Company")
    private String company;
    @CsvBindByName(column = "Salary")
    private String salary;
    @CsvBindByName(column = "Link")
    private String link;

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

    public String getFullDescription() {
        return fullDescription;
    }


    public void setFullDescription(String fullDescription) {
        this.fullDescription = fullDescription;
    }


    public String getCompany() {
        return company;
    }


    public void setCompany(String company) {
        this.company = company;
    }


    public String getSalary() {
        return salary;
    }


    public void setSalary(String salary) {
        this.salary = salary;
    }


    public String getLink() {
        return link;
    }


    public void setLink(String link) {
        this.link = link;
    }
}
