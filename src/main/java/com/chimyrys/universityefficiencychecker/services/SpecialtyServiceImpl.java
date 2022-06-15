package com.chimyrys.universityefficiencychecker.services;

import com.chimyrys.universityefficiencychecker.db.SpecialtyRepository;
import com.chimyrys.universityefficiencychecker.model.RequirementType;
import com.chimyrys.universityefficiencychecker.model.Specialty;
import com.chimyrys.universityefficiencychecker.services.api.SpecialtyService;
import org.springframework.stereotype.Service;

import java.util.Map;
@Service
public class SpecialtyServiceImpl implements SpecialtyService {
    private final SpecialtyRepository specialtyRepository;

    public SpecialtyServiceImpl(SpecialtyRepository specialtyRepository) {
        this.specialtyRepository = specialtyRepository;
    }

    @Override
    public void addNewSpecialty(Map<String, String> input) {
        Specialty.SpecialtyBuilder builder = Specialty.builder();
        for(Map.Entry<String, String> entry : input.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            if (key.equals("name")) {
                builder.name(value);
            } else if (key.equals("code")) {
                builder.code(Integer.parseInt(value));
            } else if (key.equals("alias")) {
                builder.specialtyAlias(value);
            }
        }
        Specialty specialty = builder.build();
        specialtyRepository.save(specialty);
    }
}
