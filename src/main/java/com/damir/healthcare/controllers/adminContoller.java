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
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
public class adminContoller {
    final
    DiscoveryRepository discoveryRepository;
    final
    DiseaseTypeRepository diseaseTypeRepository;
    final
    UserRepository userRepository;
    final
    DoctorRepository doctorRepository;
    final
    PublicServantRepository publicServantRepository;
    final
    AuGroupRepository auGroupRepository;
    final
    RecordRepository recordRepository;
    final
    DiseaseRepository diseaseRepository;
    final
    CountryRepository countryRepository;
    @Autowired
    SpecializeRepository specializeRepository;

    public adminContoller(DiscoveryRepository discoveryRepository, DiseaseTypeRepository diseaseTypeRepository, UserRepository userRepository, DoctorRepository doctorRepository, PublicServantRepository publicServantRepository, AuGroupRepository auGroupRepository, RecordRepository recordRepository, DiseaseRepository diseaseRepository, CountryRepository countryRepository) {
        this.discoveryRepository = discoveryRepository;
        this.diseaseTypeRepository = diseaseTypeRepository;
        this.userRepository = userRepository;
        this.doctorRepository = doctorRepository;
        this.publicServantRepository = publicServantRepository;
        this.auGroupRepository = auGroupRepository;
        this.recordRepository = recordRepository;
        this.diseaseRepository = diseaseRepository;
        this.countryRepository = countryRepository;
    }

    @GetMapping("/see/countries")
    public String seeCountries(Model model){
        model.addAttribute("Countries", countryRepository.findAllByOrderByIdAsc());
        return "admin/seeCountries";
    }
    @GetMapping("/modify/country/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String modifyCountry(@PathVariable("id")String string, Model model){
        model.addAttribute("Country",countryRepository.findById(string));
        return "admin/modifyCountry";
    }
    @GetMapping("/add/country")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String addCountry(Model model){
        model.addAttribute("Country",new Country());
        return "admin/addCountry";
    }
    @PostMapping("/save/country")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String saveCountry(@ModelAttribute("Country") Country c, Model model) {
        countryRepository.save(c);
        return "redirect:/see/countries";
    }
    @PostMapping("/delete/country/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String deleteCountry(@PathVariable("id") String email, Model model) {
        if(countryRepository.findById(email).isPresent()) countryRepository.deleteById(email);
        return "redirect:/see/countries";
    }

    @GetMapping("/see/discovered")
    public String allDiscovered(Model model){
        model.addAttribute("discovered",discoveryRepository.findAll());
        return "discovered";
    }
    @GetMapping("/see/doctors")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String allDoctors(Model model){
        List<UserDoctor> res = new ArrayList<>();
        List<User> Users = userRepository.findAllByOrderByIdAsc();
        Set<Doctor> Doctors = new HashSet<>(doctorRepository.findAll());
        List<Specialize> specs = specializeRepository.findAll();
        List<Disease> diseases = diseaseRepository.findAll();
        for (User u:Users             ) {
            Optional<Doctor> first = Doctors.stream().filter(x -> x.email.equals(u.getId())).findFirst();
            if (first.isPresent()){
                Optional<Specialize> byEmail = specializeRepository.findByEmail(u.getId());
                if (byEmail.isPresent()) {
                    Specialize specialize = byEmail.get();
                    Disease first1 = diseases.stream().filter(x -> x.getId() == specialize.getId()).findFirst().get();
                    res.add(new UserDoctor(u, first1.getDiseaseCode()));
                } else {
                    res.add(new UserDoctor(u,"no information"));
                }
            }
        }
        model.addAttribute("Users", res);
        return "admin/seedoctors";
    }
    @GetMapping("/see/publicservants")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String allPublicServants(Model model){
        List<UserDoctor> res = new ArrayList<>();
        List<User> Users = userRepository.findAllByOrderByIdAsc();
        Set<Publicservant> PS = new HashSet<>(publicServantRepository.findAll());
        for (User u:Users             ) {
            Optional<Publicservant> first = PS.stream().filter(x -> x.email.equals(u.getId())).findFirst();
            if (first.isPresent()){
                res.add(new UserDoctor(u, first.get().getDepartment()));
            }
        }
        model.addAttribute("Users", res);
        return "admin/seepublicservants";
    }
    @GetMapping("/see/users")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String allUsers(Model model){
        model.addAttribute("Users", userRepository.findAllByOrderByIdAsc());
        return "admin/seeusers";
    }
    @GetMapping("/modify/user/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String modifyUser(@PathVariable("id")String string, Model model){
        model.addAttribute("User",userRepository.findById(string).get());
        model.addAttribute("email", userRepository.findById(string).get().getId());
        Optional<Doctor> DoctorByID = doctorRepository.findById(string);
        if (DoctorByID.isPresent()){
            model.addAttribute("Doctor", DoctorByID.get());
            model.addAttribute("Diseases", diseaseRepository.findAllByOrderByDiseaseCode());
            model.addAttribute("Countries", countryRepository.findAll());
            model.addAttribute("returnDis", new Dishelper(new Disease(), diseaseRepository.native_findByid(DoctorByID.get().getEmail()).get(0)));
        return "admin/modifyDoctor";
        }
        if (publicServantRepository.findById(string).isPresent()) {
            model.addAttribute("PublicServant", publicServantRepository.findById(string).get());
            model.addAttribute("Countries", countryRepository.findAll());
            return "admin/modifyPublicServant";
        }
        return "admin/modifyuser";
    }
    @PostMapping("/delete/user/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String deleteUser(@PathVariable("id") String email, Model model) {
        if (doctorRepository.findById(email).isPresent()) doctorRepository.deleteById(email);
        if (publicServantRepository.findById(email).isPresent()) publicServantRepository.deleteById(email);
        if (auGroupRepository.findById(email).isPresent()) auGroupRepository.deleteById(email);
        if (userRepository.findById(email).isPresent()) userRepository.deleteById(email);
        return "redirect:/see/users";
    }
}
