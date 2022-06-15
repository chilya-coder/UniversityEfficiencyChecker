package com.chimyrys.universityefficiencychecker.controllers;

import com.chimyrys.universityefficiencychecker.db.RequirementTypeRepository;
import com.chimyrys.universityefficiencychecker.db.SpecialtyRepository;
import com.chimyrys.universityefficiencychecker.services.api.GenerateWordDocumentService;
import com.chimyrys.universityefficiencychecker.services.api.RequirementTypeService;
import com.chimyrys.universityefficiencychecker.services.api.SpecialtyService;
import com.chimyrys.universityefficiencychecker.services.api.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final RequirementTypeRepository requirementTypeRepository;
    private final RequirementTypeService requirementTypeService;
    private final UserService userService;
    private final SpecialtyRepository specialtyRepository;
    private final SpecialtyService specialtyService;
    private final GenerateWordDocumentService generateWordDocumentService;

    public AdminController(RequirementTypeRepository requirementTypeRepository, RequirementTypeService requirementTypeService, UserService userService, SpecialtyRepository specialtyRepository, SpecialtyService specialtyService, GenerateWordDocumentService generateWordDocumentService) {
        this.requirementTypeRepository = requirementTypeRepository;
        this.requirementTypeService = requirementTypeService;
        this.userService = userService;
        this.specialtyRepository = specialtyRepository;
        this.specialtyService = specialtyService;
        this.generateWordDocumentService = generateWordDocumentService;
    }

    @GetMapping(path = "/admin_tools")
    public String adminTools(HttpServletRequest request) {
        request.setAttribute("requirementTypeRepository", requirementTypeRepository);
        request.setAttribute("userService", userService);
        return "admin_tools";
    }
    @PostMapping(path = "/requirement_type")
    @ResponseBody
    public String addNewRequirementType(@RequestBody Map<String, String> body) {
        requirementTypeService.addNewRequirementType(body);
        return "admin_tools";
    }
    @GetMapping(path = "/specialties")
    public String specialtyPage(HttpServletRequest request) {
        request.setAttribute("specialtyRepository", specialtyRepository);
        request.setAttribute("userService", userService);
        return "specialties";
    }
    @PostMapping(path = "/specialties")
    @ResponseBody
    public String addNewSpecialty(@RequestBody Map<String, String> body) {
        specialtyService.addNewSpecialty(body);
        return "specialties";
    }
    @GetMapping(path = "/activity_report", produces = {"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"})
    @ResponseBody
    public ResponseEntity<byte[]> generateActivityReport() {
        try {
            byte[] content = generateWordDocumentService.generateActivityReport();
            return new ResponseEntity<>(content, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
