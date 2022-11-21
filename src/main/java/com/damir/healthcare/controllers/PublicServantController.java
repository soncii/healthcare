package com.damir.healthcare.controllers;

import com.damir.healthcare.entities.Record;
import com.damir.healthcare.entities.RecordID;
import com.damir.healthcare.repositories.CountryRepository;
import com.damir.healthcare.repositories.DiseaseRepository;
import com.damir.healthcare.repositories.RecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@PreAuthorize("hasAnyRole('ROLE_PUBLICSERVANT','ROLE_ADMIN')")
public class  PublicServantController {
    @Autowired
    RecordRepository recordRepository;
    @Autowired
    DiseaseRepository diseaseRepository;
    @Autowired
    CountryRepository countryRepository;
    @GetMapping("/add/record")
    public String addRecord(Model model){
        Record record = new Record();
        model.addAttribute("Record", record );
        model.addAttribute("diseases",diseaseRepository.findAll());
        model.addAttribute("countries",countryRepository.findAllByOrderByIdAsc());
        return "ps/addnewrecord";
    }
    @PostMapping("/save/record")
    public String saveRecord(@ModelAttribute("Record") Record record) {
        record.setEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        recordRepository.save(record);
        return "redirect:/";
    }
    @GetMapping("/modify/record/{email}/{cname}/{diseaseCode}")
    public String modifyRecord(@PathVariable("email") String email,
    @PathVariable("cname") String cname,
                               @PathVariable("diseaseCode") String diseaseCode,
                               Model model) {
        RecordID ID = new RecordID();
        ID.setCname(cname);
        ID.setEmail(email);
        ID.setDiseaseCode(diseaseCode);
        Optional<Record> byId = recordRepository.findById(ID);
        model.addAttribute("Record",byId.get());
        model.addAttribute("diseases",diseaseRepository.findAll());
        model.addAttribute("countries",countryRepository.findAllByOrderByIdAsc());
        return "ps/modifyrecord";
    }
    @PostMapping("/delete/record/{email}/{cname}/{diseaseCode}")
    @PreAuthorize("permitAll()")
    public String deleteRecord(@PathVariable("email") String email,
                               @PathVariable("cname") String cname,
                               @PathVariable("diseaseCode") String diseaseCode,
                               Model model) {
        RecordID ID = new RecordID();
        ID.setCname(cname);
        ID.setEmail(email);
        ID.setDiseaseCode(diseaseCode);
        recordRepository.deleteById(ID);
        return "redirect:/records";
    }
    @GetMapping("/delete/record/{email}/{cname}/{diseaseCode}")
    @PreAuthorize("permitAll()")
    public String deleteRecord1(@PathVariable("email") String email,
                               @PathVariable("cname") String cname,
                               @PathVariable("diseaseCode") String diseaseCode,
                               Model model) {
        RecordID ID = new RecordID();
        ID.setCname(cname);
        ID.setEmail(email);
        ID.setDiseaseCode(diseaseCode);
        recordRepository.deleteById(ID);
        return "redirect:/records";
    }
}