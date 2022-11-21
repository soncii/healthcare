package com.damir.healthcare.controllers;

import com.damir.healthcare.auth.AuGroup;
import com.damir.healthcare.auth.AuGroupRepository;
import com.damir.healthcare.entities.*;
import com.damir.healthcare.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Controller
public class AuthController {
    @Autowired
    DiseaseRepository diseaseRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    DoctorRepository doctorRepository;
    @Autowired
    PublicServantRepository publicServantRepository;
    @Autowired
    AuGroupRepository auGroupRepository;
    @Autowired
    CountryRepository countryRepository;
    @Autowired
    SpecializeRepository specializeRepository;
    @Autowired
    DiscoveryRepository discoveryRepository;
    @Autowired
    RecordRepository recordRepository;
    @Autowired
    DiseaseTypeRepository diseaseTypeRepository;
    @GetMapping("/login")
    public String login(){
        return "login";
    }
    @GetMapping("/logoutsuccess")
    public String logout(){
        return "redirect:login";
    }
    @GetMapping("/")
    public String getIndex(Model model){
        String curUser = SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println("I'm cur user: " + curUser);
        String role="Admin";
        if (doctorRepository.findById(curUser).isPresent())
        {
            role="Doctor";
        }
        if (publicServantRepository.findById(curUser).isPresent())
        {
            role="Public Servant";
        }
        model.addAttribute("role",role);
        model.addAttribute("email",curUser);
        return "index";
    }

    @GetMapping("/records")
    public String getRecord(Model model){
        model.addAttribute("records",recordRepository.findAll());
        model.addAttribute("id",SecurityContextHolder.getContext().getAuthentication().getName());
        return "ps/publicservant_main_page";
    }

    @GetMapping("/register/doctor")
    @PreAuthorize("permitAll()")
    public String addDoctor(Model model){
        User user = new User();
        model.addAttribute("User",user);
        model.addAttribute("Doctor", new Doctor());
        model.addAttribute("Diseases", diseaseRepository.findAll());
        model.addAttribute("Countries", countryRepository.findAll());
        model.addAttribute("returnDis", new Dishelper());
        return "admin/addnewdoctor";
    }
    @GetMapping("/register/publicservant")
    @PreAuthorize("permitAll()")
    public String addUser(Model model){
        model.addAttribute("User",new User());
        model.addAttribute("PublicServant", new Publicservant());
        model.addAttribute("Countries", countryRepository.findAll());
        return "admin/addnewpublicservant";
    }

    @PostMapping("/save/doctor")
    @PreAuthorize("permitAll()")
    @Transactional
    public String saveDoctor(@ModelAttribute("User") User user,
                             @ModelAttribute("Doctor") Doctor doctor,
                             @ModelAttribute("returnDis") Dishelper dis
    ){
        System.out.println(user.getId()+" "+user.getName()+" "+user.getSurname());
        doctor.setEmail(user.getId());
        userRepository.save(user);
        doctorRepository.save(doctor);
        System.out.println(dis.type);
        if (!specializeRepository.findByEmail(user.getId()).isPresent()){
            specializeRepository.save(new Specialize(diseaseRepository.findByDiseaseCode(dis.type).getId(), doctor.getEmail()));
        } else
            specializeRepository.updateId(diseaseRepository.findByDiseaseCode(dis.type).getId(), doctor.getEmail());

        auGroupRepository.save(new AuGroup(user.getId(),"DOCTOR"));
        return "redirect:/";
    }
    @PostMapping("/save/publicservant")
    @PreAuthorize("permitAll()")
    public String saveUser12(@ModelAttribute("User") User user,
                             @ModelAttribute("PublicServant") Publicservant ps){
        System.out.println(user.getId()+" "+user.getName()+" "+user.getSurname());
        ps.setEmail(user.getId());
        userRepository.save(user);
        AuGroup augroup = new AuGroup(user.getId(), "PUBLICSERVANT");
        if (!auGroupRepository.findById(user.getId()).isPresent())        auGroupRepository.save(augroup);
        publicServantRepository.save(ps);
        return "redirect:/";
    }
}
