package com.chimyrys.universityefficiencychecker.controllers;

import com.chimyrys.universityefficiencychecker.db.*;
import com.chimyrys.universityefficiencychecker.model.*;
import com.chimyrys.universityefficiencychecker.services.api.ContractService;
import com.chimyrys.universityefficiencychecker.services.api.GenerateWordDocumentService;
import com.chimyrys.universityefficiencychecker.services.api.ScienceWorkService;
import com.chimyrys.universityefficiencychecker.services.api.UserService;
import com.chimyrys.universityefficiencychecker.utils.DateUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
public class MainController {
    private final UserRepository userRepository;
    private final UserCredentialRepository userCredentialRepository;
    private final DepartmentRepository departmentRepository;
    private final PositionRepository positionRepository;
    private final UserService userService;
    private final ScienceWorkRepository scienceWorkRepository;
    private final ScienceWorkService scienceWorkService;
    private final GenerateWordDocumentService generateWordDocumentService;
    private final ContractService contractService;
    private final ContractRepository contractRepository;
    private final ScientificTitleRepository scientificTitleRepository;
    private final DegreeRepository degreeRepository;
    private final SpecialtyRepository specialtyRepository;

    public MainController(UserRepository userRepository, UserCredentialRepository userCredentialRepository, DepartmentRepository departmentRepository, PositionRepository positionRepository, UserService userService, ScienceWorkRepository scienceWorkRepository, ScienceWorkService scienceWorkService, GenerateWordDocumentService generateWordDocumentService, ContractService contractService, ContractRepository contractRepository, ScientificTitleRepository scientificTitleRepository, DegreeRepository degreeRepository, SpecialtyRepository specialtyRepository) {
        this.userRepository = userRepository;
        this.userCredentialRepository = userCredentialRepository;
        this.departmentRepository = departmentRepository;
        this.positionRepository = positionRepository;
        this.userService = userService;
        this.scienceWorkRepository = scienceWorkRepository;
        this.scienceWorkService = scienceWorkService;
        this.generateWordDocumentService = generateWordDocumentService;
        this.contractService = contractService;
        this.contractRepository = contractRepository;
        this.scientificTitleRepository = scientificTitleRepository;
        this.degreeRepository = degreeRepository;
        this.specialtyRepository = specialtyRepository;
    }

    @ResponseBody
    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping(path = "/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping(path = "/user_profile")
    public String userProfilePage(@ModelAttribute("user1") User user, HttpServletRequest servletRequest) {
        servletRequest.setAttribute("user", userService.getCurrentUser());
        servletRequest.setAttribute("isUserEnabled", userService.getCurrentUserDetails().isEnabled());
        servletRequest.setAttribute("lastContract", contractRepository.findFirstByUserOrderByDateStartDesc(userService.getCurrentUser()));
        servletRequest.setAttribute("specialtyRepository", specialtyRepository);
        servletRequest.setAttribute("contractRepository", contractRepository);
        return "user_profile";
    }

    @GetMapping(path = "/register")
    public String registerPage(@ModelAttribute("userCredential") UserCredential userCredential, HttpServletRequest servletRequest) {
        servletRequest.setAttribute("departmentRepository", departmentRepository);
        servletRequest.setAttribute("positionRepository", positionRepository);
        servletRequest.setAttribute("scientificTitleRepository", scientificTitleRepository);
        servletRequest.setAttribute("degreeRepository", degreeRepository);
        return "register";
    }

    @PostMapping(path = "/register")
    public String getDataFromRegisterPage(@Valid @ModelAttribute("userCredential") UserCredential userCredential, BindingResult bindingResult, HttpServletRequest servletRequest) {
        servletRequest.setAttribute("departmentRepository", departmentRepository);
        servletRequest.setAttribute("positionRepository", positionRepository);
        servletRequest.setAttribute("contractRepository", contractRepository);
        servletRequest.setAttribute("scientificTitleRepository", scientificTitleRepository);
        servletRequest.setAttribute("degreeRepository", degreeRepository);
        Optional<Department> departmentOptional = departmentRepository.findById(Integer.parseInt(servletRequest.getParameter("departmentId")));
        Optional<Position> positionOptional = positionRepository.findById(Integer.parseInt(servletRequest.getParameter("positionId")));
        Optional<ScientificTitle> scientificTitleOptional = scientificTitleRepository.findById(Integer.parseInt(servletRequest.getParameter("scientificTitleId")));
        Optional<Degree> degreeOptional = degreeRepository.findById(Integer.parseInt(servletRequest.getParameter("degreeId")));
        if (bindingResult.hasErrors()) {
            return "register";
        }
        userService.createDefaultUser(userCredential, departmentOptional, positionOptional, scientificTitleOptional, degreeOptional);

        return "redirect:/login";
    }

    @GetMapping(path = "/science_work")
    public String getUserScienceWorks(HttpServletRequest servletRequest) {
        servletRequest.setAttribute("science_works", scienceWorkRepository.findAllByUsersEqualsOrderByDateOfPublicationAsc(userService.getCurrentUser()));
        servletRequest.setAttribute("userService", userService);
        servletRequest.setAttribute("specialtyRepository", specialtyRepository);
        return "science_works";
    }

    @PostMapping(path = "/science_work")
    @ResponseBody
    public String addScienceWork(@RequestBody Map<String, String> body) {
        scienceWorkService.addScienceWorkByUserInput(body);
        return body.toString();
    }

    @DeleteMapping(path = "/science_work")
    @ResponseBody
    public ResponseEntity<String> deleteScienceWork(@RequestParam(value = "id") int scienceWorkId) {
        if (userService.getCurrentUser().getScienceWorks().contains(scienceWorkRepository.getById(scienceWorkId))) {
            scienceWorkRepository.deleteById(scienceWorkId);
            return new ResponseEntity<>("Science work was deleted", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Bad request", HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping(path = "/filter_science_work")
    public String filterScienceWork(@RequestParam("since") String since,
                                    @RequestParam("to") String to, HttpServletRequest servletRequest) {
        List<Date> dates = DateUtils.getDateRangeForYear(Integer.parseInt(since), Integer.parseInt(to));
        List<ScienceWork> scienceWorkList = scienceWorkRepository
                .findAllByDateOfPublicationBetweenAndUsersEqualsOrderByDateOfPublicationAsc(dates.get(0), dates.get(1), userService.getCurrentUser());
        servletRequest.setAttribute("science_works", scienceWorkList);
        servletRequest.setAttribute("userService", userService);
        servletRequest.setAttribute("specialtyRepository", specialtyRepository);
        return "science_works";
    }

    @PostMapping(path = "/user_profile")
    @ResponseBody
    public String addContractUser(@RequestBody Map<String, String> body) {
        contractService.addContractByUserInput(body);
        return "OK";
    }

    @GetMapping(path = "/generate_report", produces = {"application/vnd.openxmlformats-officedocument.wordprocessingml.document"})
    @ResponseBody
    public ResponseEntity<byte[]> generateReport(@RequestParam(value = "since", defaultValue = "2000") String since,
                                                 @RequestParam(value = "to", defaultValue = "2022") String to) {

        try {
            byte[] content = generateWordDocumentService.generateReport(Integer.parseInt(since), Integer.parseInt(to));
            return new ResponseEntity<>(content, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/generate_info_report", produces = {"application/vnd.openxmlformats-officedocument.wordprocessingml.document"})
    @ResponseBody
    public ResponseEntity<byte[]> generateInfoReport() {
        try {
            byte[] content = generateWordDocumentService.generateInfoReport();
            return new ResponseEntity<>(content, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
