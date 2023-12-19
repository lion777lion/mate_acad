package com.example.botma.service;

import org.springframework.stereotype.Service;
import com.example.botma.dto.VacancyDto;
import java.util.List;
import java.util.ArrayList;
import jakarta.annotation.PostConstruct;


@Service
public class VacancyService {

    private final List<VacancyDto> vacancies = new ArrayList<>();

    @PostConstruct
    public void init() {
        VacancyDto junior = new VacancyDto();
        junior.setId("1");
        junior.setLevel("junior");
        junior.setName("Junior at Ma");
        junior.setDescription("Junior developer at MA");
        vacancies.add(junior);

        VacancyDto middle = new VacancyDto();
        middle.setId("2");
        middle.setLevel("middle");
        middle.setName("Middle at Ma");
        middle.setDescription("Middle developer at MA");
        vacancies.add(middle);

        VacancyDto senior = new VacancyDto();
        senior.setId("3");
        senior.setLevel("senior");
        senior.setName("Senior at Ma");
        senior.setDescription("Senior developer at MA");
        vacancies.add(senior);
    }

    public List<VacancyDto> getVacancyByLvl(String level) {
        
        List<VacancyDto> sortedVacancy = new ArrayList<>();
        
        sortedVacancy = vacancies.stream()
        .filter(item -> item.getLevel().equals(level))
        .toList();

        return sortedVacancy;
    }

    public VacancyDto getVacancyById(String id) {
        VacancyDto resultVacancy = new VacancyDto();
        for (VacancyDto vacancy : vacancies) {
            if(vacancy.getId().equals(id)){
                resultVacancy = vacancy;
            }
        }
        return resultVacancy;
    }
}