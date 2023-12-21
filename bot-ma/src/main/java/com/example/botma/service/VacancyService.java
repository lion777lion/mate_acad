package com.example.botma.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.botma.dto.VacancyDto;
import java.util.List;
import java.util.ArrayList;
import jakarta.annotation.PostConstruct;


@Service
public class VacancyService {

    @Autowired
    private VacancyReaderService vacancyReaderService;

    private final List<VacancyDto> vacancies = new ArrayList<>();

    @PostConstruct
    public void init() {
        List<VacancyDto> vacanciesFromFile = vacancyReaderService.getVacanciesFromFile("vacancies.csv");
        for (VacancyDto vacancyDto : vacanciesFromFile) {
            vacancies.add(vacancyDto);
        }
    }

    public List<VacancyDto> getVacancyByLvl(String level) {
        
        List<VacancyDto> sortedVacancy = new ArrayList<>();
        
        sortedVacancy = vacancies.stream()
        .filter(item -> item.getName().toLowerCase().contains(level))
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

    public String getVacancyView(String id) {
        VacancyDto vacancy = getVacancyById(id);
        String vacancyView = """
                *Title:* %s
                *Company:* %s
                *Short description:* %s
                *Full description* %s
                *Salary* %s
                """.formatted(escapeMarkdownReversedChars(vacancy.getName()), 
                escapeMarkdownReversedChars(vacancy.getCompany()),
                escapeMarkdownReversedChars(vacancy.getDescription()),
                escapeMarkdownReversedChars(vacancy.getFullDescription()),
                vacancy.getSalary().isBlank() ? "Not specified" : escapeMarkdownReversedChars(vacancy.getSalary()));
        return vacancyView;
    }

    private String escapeMarkdownReversedChars(String text){
        return text.replace("-", "\\-")
        .replace("_", "\\_")
        .replace("*", "\\*")
        .replace("(", "\\(")
        .replace(")", "\\)")
        .replace("[", "\\[")
        .replace("]", "\\]")
        .replace("`", "\\`")
        .replace("~", "\\~")
        .replace("<", "\\>")
        .replace("#", "\\#")
        .replace("+", "\\+")
        .replace(".", "\\.")
        .replace("!", "\\!");
    }
}