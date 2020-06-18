package com.example.CoronaVirusTracker.controllers;

import com.example.CoronaVirusTracker.models.LocationStats;
import com.example.CoronaVirusTracker.service.CoronaVirusDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    CoronaVirusDataService coronaVirusDataService;

    @GetMapping("/")
    public String home(Model model){
        List<LocationStats> allstats = coronaVirusDataService.getAllstats();
        int totalReportedCases=allstats.stream().mapToInt(stat->stat.getLatestTotalCases()).sum();
        int totalNewCases = allstats.stream().mapToInt(stat->stat.getDiffFromPrevDay()).sum();
        model.addAttribute("locationstats",allstats);
        model.addAttribute("totalReportedCases",totalReportedCases);
        model.addAttribute("totalNewCases",totalNewCases);
        return "home";
    }

}
