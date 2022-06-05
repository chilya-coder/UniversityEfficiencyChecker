package com.chimyrys.universityefficiencychecker.controllers;

import com.chimyrys.universityefficiencychecker.db.DepartmentRepository;
import com.chimyrys.universityefficiencychecker.db.PositionRepository;
import com.chimyrys.universityefficiencychecker.db.UserCredentialRepository;
import com.chimyrys.universityefficiencychecker.db.UserRepository;
import com.chimyrys.universityefficiencychecker.model.*;
import com.chimyrys.universityefficiencychecker.services.api.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
public class MainController {
    private final UserRepository userRepository;
    private final UserCredentialRepository userCredentialRepository;
    private final DepartmentRepository departmentRepository;
    private final PositionRepository positionRepository;
    private final UserService userService;

    public MainController(UserRepository userRepository, UserCredentialRepository userCredentialRepository, DepartmentRepository departmentRepository, PositionRepository positionRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userCredentialRepository = userCredentialRepository;
        this.departmentRepository = departmentRepository;
        this.positionRepository = positionRepository;
        this.userService = userService;
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
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        servletRequest.setAttribute("user", userCredentialRepository.getUserCredentialByLogin(principal.getUsername()).get().getUser());

        return "user_profile";
    }

    @PostMapping(path = "/user_profile")
    public String getEditedDataFromRegisterPage(@Valid @ModelAttribute("user1") User user, BindingResult bindingResult, HttpServletRequest servletRequest) {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        servletRequest.setAttribute("user", userCredentialRepository.getUserCredentialByLogin(principal.getUsername()).get().getUser());
        user.setUserId(userCredentialRepository.getUserCredentialByLogin(principal.getUsername()).get().getUser().getUserId());
        return "user_profile";
    }

    @GetMapping(path = "/register")
    public String registerPage(@ModelAttribute("userCredential") UserCredential userCredential, HttpServletRequest servletRequest) {
        servletRequest.setAttribute("departmentRepository", departmentRepository);
        servletRequest.setAttribute("positionRepository", positionRepository);
        return "register";
    }

    @PostMapping(path = "/register")
    public String getDataFromRegisterPage(@Valid @ModelAttribute("userCredential") UserCredential userCredential, BindingResult bindingResult, HttpServletRequest servletRequest) {
        servletRequest.setAttribute("departmentRepository", departmentRepository);
        servletRequest.setAttribute("positionRepository", positionRepository);
        servletRequest.getParameter("departmentId");
        servletRequest.getParameter("positionId");
        Optional<Department> departmentOptional = departmentRepository.findById(Integer.parseInt(servletRequest.getParameter("departmentId")));
        Optional<Position> positionOptional = positionRepository.findById(Integer.parseInt(servletRequest.getParameter("positionId")));
        if (bindingResult.hasErrors()) {
            return "register";
        }
        userService.createDefaultUser(userCredential, departmentOptional, positionOptional);
        return "redirect:/login";
    }
}
