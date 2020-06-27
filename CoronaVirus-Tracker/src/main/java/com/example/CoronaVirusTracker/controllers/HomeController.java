package com.example.CoronaVirusTracker.controllers;
import com.example.CoronaVirusTracker.models.*;
import com.example.CoronaVirusTracker.service.CoronaVirusDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.*;

@Controller
public class HomeController {


    @Autowired
    CoronaVirusDataService coronaVirusDataService;

    @GetMapping("/")
    public String home(Model model){
        List<LocationStats> allstats = coronaVirusDataService.getAllstats();
        List<DeathStat> allDeaths=coronaVirusDataService.getDeathstatarr();
        List<RecoveryStat> allRecovery =coronaVirusDataService.getRecoverystat();
        List<CountryWise> allCountryWise=coronaVirusDataService.getCountryWisesList();


        int totalReportedCases=allstats.stream().mapToInt(stat->stat.getLatestTotalCases()).sum();
        int totalNewCases = allstats.stream().mapToInt(stat->stat.getDiffFromPrevDay()).sum();
        int totalDeath=allDeaths.stream().mapToInt(stat->stat.getLatestDeath()).sum();
        int totalRecovery=allRecovery.stream().mapToInt(stat->stat.getLatestRecoveryCases()).sum();
        model.addAttribute("CountryStat",allCountryWise);
        model.addAttribute("locationstats",allstats);
        model.addAttribute("totalReportedCases",totalReportedCases);
        model.addAttribute("totalNewCases",totalNewCases);
        model.addAttribute("totalDeathCases",totalDeath);
        model.addAttribute("totalRecoveryCases",totalRecovery);
        return "page";
    }

    @GetMapping("/user/{s}")
    public String Graph(Model model,@PathVariable String s)
    {
        List<DataState> datacountry =coronaVirusDataService.newData(s);
        List<String> statelist=new ArrayList<>();
        List<Integer> caseslist=new ArrayList<>();
        List<Integer> deathlist=new ArrayList<>();
        List<Integer> recoverylist=new ArrayList<>();
        for(DataState i:datacountry)
        {
            statelist.add(i.state);
            caseslist.add(i.cases);
            deathlist.add(i.death);
            recoverylist.add(i.recovery);
        }
        model.addAttribute("countrycdr",datacountry);
        model.addAttribute("statewiseList",statelist);
        model.addAttribute("deathwiseList",deathlist);
        model.addAttribute("caseswiseList",caseslist);
        model.addAttribute("recoverywiseList",recoverylist);
        return "test";
    }

}
