package com.example.botma.service;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import com.example.botma.dto.VacancyDto;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import java.util.List;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

@Service
public class VacancyReaderService {
    
    public List<VacancyDto> getVacanciesFromFile(String fileName){
        Resource resource = new ClassPathResource(fileName);

        try(InputStreamReader inputStreamReader = new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8)){
            CsvToBean<VacancyDto> csvToBin = new CsvToBeanBuilder<VacancyDto>(inputStreamReader)
            .withType(VacancyDto.class)
            .withIgnoreLeadingWhiteSpace(true)
            .build();
            return csvToBin.parse();

        } catch(Exception e ){
            throw new RuntimeException("Cant read file", e);
        }
    }
}
