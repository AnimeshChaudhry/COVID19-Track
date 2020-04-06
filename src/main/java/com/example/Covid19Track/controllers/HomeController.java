package com.example.Covid19Track.controllers;

import com.example.Covid19Track.models.LocationStatistics;
import com.example.Covid19Track.services.Covid19DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.text.NumberFormat;

@Controller
public class HomeController {

    @Autowired
    Covid19DataService covid19DataService;

    @GetMapping("/")
    public String home(Model model){
        List<LocationStatistics> allLocations = covid19DataService.getAllLocations();
        int totalReportedCases = allLocations.stream().mapToInt(stat -> stat.getLatestCases()).sum();
        int totalNewCases = allLocations.stream().mapToInt(stat -> stat.getCasesDiff()).sum();

        NumberFormat nf = NumberFormat.getInstance();

        model.addAttribute("locationStatistics", allLocations);
        model.addAttribute("totalReportedCases", nf.format(totalReportedCases));
        model.addAttribute("totalNewCases", nf.format(totalNewCases));


        return "home";
    }
}
