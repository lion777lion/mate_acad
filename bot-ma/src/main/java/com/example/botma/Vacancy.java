package com.example.botma;

import java.util.ArrayList;
import java.util.List;

public class Vacancy {

    public String id; 
    public String name;
    public String level;
    public String description;

    public Vacancy() {}

    public Vacancy(String id, String name, String level, String description) {
        this.id = id;
        this.name = name;
        this.level = level;
        this.description = description;
    }

    public List<Vacancy> generateData() {
        List<Vacancy> vacancyList = new ArrayList<>();
        vacancyList.add(new Vacancy("1", "Junior at Ma", "junior", "Junior Dev at MA"));
        vacancyList.add(new Vacancy("2", "Junior at Google", "junior", "Junior Dev at Google"));
        vacancyList.add(new Vacancy("3", "Middle at Ma", "middle", "Middle Dev at MA"));
        vacancyList.add(new Vacancy("4", "Middle at Google", "middle", "Middle Dev at Google"));
        vacancyList.add(new Vacancy("5", "Senior at Ma", "senior", "Senior Dev at MA"));
        vacancyList.add(new Vacancy("6", "Senior at Google", "senior", "Senior Dev at Google"));
        return vacancyList;
    }
    
    @Override
    public String toString(){
        return name;
    }
}
