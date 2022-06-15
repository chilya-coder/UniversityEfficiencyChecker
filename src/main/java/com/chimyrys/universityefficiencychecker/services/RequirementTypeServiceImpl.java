package com.chimyrys.universityefficiencychecker.services;

import com.chimyrys.universityefficiencychecker.db.RequirementTypeRepository;
import com.chimyrys.universityefficiencychecker.model.RequirementType;
import com.chimyrys.universityefficiencychecker.services.api.RequirementTypeService;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class RequirementTypeServiceImpl implements RequirementTypeService {
    private final RequirementTypeRepository requirementTypeRepository;

    public RequirementTypeServiceImpl(RequirementTypeRepository requirementTypeRepository) {
        this.requirementTypeRepository = requirementTypeRepository;
    }

    @Override
    public void addNewRequirementType(Map<String, String> input) {
        RequirementType.RequirementTypeBuilder builder = RequirementType.builder();
        for(Map.Entry<String, String> entry : input.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            if (key.equals("name")) {
                builder.name(value);
            } else if (key.equals("number")) {
                builder.reqNumber(Integer.parseInt(value));
            } else if (key.equals("condition")) {
                builder.condition(value);
            }
        }
        RequirementType requirementType = builder.build();
        requirementTypeRepository.save(requirementType);
    }
}
