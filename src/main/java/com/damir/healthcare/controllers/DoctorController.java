package com.damir.healthcare.controllers;

import com.damir.healthcare.auth.AuGroupRepository;
import com.damir.healthcare.entities.*;
import com.damir.healthcare.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelExtensionsKt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Controller
public class DoctorController {
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
    EntityManager entityManager;
    @Autowired
    DiseaseTypeRepository diseaseTypeRepository;

    @GetMapping("/diseases")
    public String getDisease(Model model) {
        List<Disease> Diseases = diseaseRepository.findAllByOrderByDiseaseCode();
        List<Discover> Discoveries = discoveryRepository.findAll();
        List<Diseasetype> Types = diseaseTypeRepository.findAll();
        List<DiseaseOutput> result = new ArrayList<>();
        for (Disease d : Diseases) {
            result.add(new DiseaseOutput(
                    d,
                    new Discover(),
                    Types.stream().filter(x -> Objects.equals(x.getId(), d.getId())).findFirst().get()
            ));
        }
        model.addAttribute("Disease", result);
        return "diseases";
    }

    @GetMapping("/add/disease")
    public String addDisease(Model model) {
        model.addAttribute("helper", new Dishelper());
        model.addAttribute("types", diseaseTypeRepository.findAll());
        return "addDisease";
    }

    @GetMapping("/modify/disease/{id}")
    public String updateDisease(@PathVariable("id") String disCode, Model model) {
        Disease disease = diseaseRepository.findById(disCode).get();
        model.addAttribute("helper", new Dishelper(disease,
                diseaseTypeRepository.findById(disease.getId()).get().getDescription()));
        model.addAttribute("types", diseaseTypeRepository.findAll());
        return "modifyDisease";
    }

    @PostMapping("/save/disease")
    @Transactional
    public String saveDisease(
            @ModelAttribute("helper") Dishelper dishelper
    ) {
        dishelper.dis.setId(diseaseTypeRepository.findByDescription(dishelper.type).getId());
        diseaseRepository.save(dishelper.dis);
        return "redirect:/diseases";
    }

    @PostMapping("/delete/disease/{id}")
    public String deleteDisease(@PathVariable("id") String diseaseCode, Model model) {
        if (diseaseRepository.findById(diseaseCode).isPresent()) diseaseRepository.deleteById(diseaseCode);
        return "redirect:/diseases";
    }

    //    @PostMapping("/modify/disease/{id}")
//    public String modify(@PathVariable("id") String diseaseCode,
//                         @ModelAttribute("helper")Dishelper dishelper) {
//        if (diseaseRepository.findById(diseaseCode).isPresent()) diseaseRepository.save(dishelper.dis);
//        return "redirect:/see/countries";
//    }
    @GetMapping("/add/discovered")
    public String addDiscovered(Model model) {
        model.addAttribute("Discover", new Discover());
        model.addAttribute("Disease", diseaseRepository.findAll());
        model.addAttribute("Countries", countryRepository.findAll());
        return "adddiscovered";
    }

    @PostMapping("/save/discovered")
    public String saveDiscovered(@ModelAttribute Discover discover) {
        discover.setFirstEncDate(LocalDate.parse(discover.date));
        discoveryRepository.save(discover);
        return "redirect:/see/discovered";
    }
    @GetMapping("/modify/discovered/{cname}/{discode}")
    public String saveDiscovered(@PathVariable("cname") String cname,
                                 @PathVariable("discode") String disCode,
                                 Model model) {
        Discover discover = discoveryRepository.findById(new DiscoverID(cname, disCode)).get();
        System.out.println(discover);
        model.addAttribute("Discover",discover);
        model.addAttribute("Disease", diseaseRepository.findAll());
        model.addAttribute("Countries", countryRepository.findAll());
        return "modifydiscovered";
    }
    @PostMapping("/delete/discovered/{cname}/{discode}")
    public String deleteDisc(@PathVariable("cname") String cname,
                                 @PathVariable("discode") String disCode,
                                 Model model) {
        discoveryRepository.deleteById(new DiscoverID(cname,disCode));
        return "redirect:/see/discovered";
    }
    @PostMapping("/delete/discovered/{id}")
    public String deleteType(@PathVariable("id") Integer id) {
        diseaseTypeRepository.deleteById(id);
        return "redirect:/see/discovered";
    }

}
