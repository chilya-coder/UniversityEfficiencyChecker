package com.chimyrys.universityefficiencychecker.services;

import com.chimyrys.universityefficiencychecker.db.ContractRepository;
import com.chimyrys.universityefficiencychecker.db.SpecialtyRepository;
import com.chimyrys.universityefficiencychecker.model.Contract;
import com.chimyrys.universityefficiencychecker.model.User;
import com.chimyrys.universityefficiencychecker.services.api.ContractService;
import com.chimyrys.universityefficiencychecker.services.api.UserService;
import com.chimyrys.universityefficiencychecker.utils.DateUtils;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Map;

@Service
public class ContractServiceImpl implements ContractService {
    private final ContractRepository contractRepository;
    private final SpecialtyRepository specialtyRepository;
    private final UserService userService;

    public ContractServiceImpl(ContractRepository contractRepository, SpecialtyRepository specialtyRepository, UserService userService) {
        this.contractRepository = contractRepository;
        this.specialtyRepository = specialtyRepository;
        this.userService = userService;
    }

    @Override
    public void addContractByUserInput(Map<String, String> input) {
        Contract.ContractBuilder builder = Contract.builder();
        User currentUser = userService.getCurrentUser();
        for (Map.Entry<String, String> entry : input.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            if (key.equals("start-contract-date")) {
                try {
                    builder.dateStart(DateUtils.getDateFromString(value));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            } else if (key.equals("end-contract-date")) {
                try {
                    builder.dateEnd(DateUtils.getDateFromString(value));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            } else if (key.equals("specialtyId")) {
                builder.specialty(specialtyRepository.findById(Integer.parseInt(value)).get());
            }
        }
        builder.user(userService.getCurrentUser());
        Contract contract = builder.build();
        contractRepository.save(contract);
    }
}
