package ua.aleks4ay.manufplan.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ua.aleks4ay.manufplan.domain.model.BigTmc;
import ua.aleks4ay.manufplan.domain.services.BigTmcReader;
import ua.aleks4ay.manufplan.domain.tools.DateConverter;
import ua.aleks4ay.manufplan.domain.web.Period;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ReportController {

    private String beginDay = "20.04.2020";
    private String endDay = DateConverter.getNowDateString(); //"20.10.2020";

    public ReportController() {
    }

    @GetMapping("/")
    public String getStart() {
        return "redirect:/tmc";
//        return "index";
    }

    @GetMapping("/tmc")
    public ModelAndView mainView() {
        Timestamp start = DateConverter.getTimestampFromString(beginDay);
        Timestamp end = DateConverter.getTimestampFromString(endDay);
        BigTmcReader bigTmcReader = new BigTmcReader();
        List<BigTmc> allSortedTmc = bigTmcReader.getbigTmcListAfterSorting(start, end);
        int numberOfReports = bigTmcReader.getSizeOfDescription(allSortedTmc);

        ModelAndView modelAndView = new ModelAndView("all_tmc");

        modelAndView.addObject("testItems", allSortedTmc);
        modelAndView.addObject("numberOfReports", numberOfReports);
        modelAndView.addObject("beginStringDay", beginDay); //beginDay.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")
        modelAndView.addObject("endStringDay", endDay);
        modelAndView.addObject("period", new Period(beginDay, endDay));

        return modelAndView;
    }

    @PostMapping("/tmcChange")
    public String changeDate(@ModelAttribute Period period, Model model) {
        beginDay = period.getBeginDay();
        endDay = period.getEndDay();

        return "redirect:/tmc";
    }


    @GetMapping("/tmc/one/{id}")
    public ModelAndView viewOne(@PathVariable("id") String id) {
        Timestamp start = DateConverter.getTimestampFromString(beginDay);
        Timestamp end = DateConverter.getTimestampFromString(endDay);
        BigTmcReader bigTmcReader = new BigTmcReader();
        List<BigTmc> allSortedTmc = bigTmcReader.getbigTmcListAfterSorting(start, end);
        int numberOfReports = bigTmcReader.getSizeOfDescription(allSortedTmc);


        BigTmc bigTmc = bigTmcReader.getOne(allSortedTmc, id);
//        List<BigTmc> oneTmc = new ArrayList<>();
//        oneTmc.add(bigTmc);

        ModelAndView modelAndView = new ModelAndView("one_tmc");

        modelAndView.addObject("oneTmc", bigTmc);
        modelAndView.addObject("numberOfReports", numberOfReports);
        modelAndView.addObject("beginStringDay", beginDay); //beginDay.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")
        modelAndView.addObject("endStringDay", endDay);
        modelAndView.addObject("period", new Period(beginDay, endDay));

        return modelAndView;
    }
//
//    @GetMapping("/tmc/one/change/{id}")
//    public ModelAndView getBooking(@PathVariable("id") String id) {
//        Timestamp start = DateConverter.getTimestampFromString(beginDay);
//        Timestamp end = DateConverter.getTimestampFromString(endDay);
//        BigTmcReader bigTmcReader = new BigTmcReader();
//        List<BigTmc> allSortedTmc = bigTmcReader.getbigTmcListAfterSorting(start, end);
//        int numberOfReports = bigTmcReader.getSizeOfDescription(allSortedTmc);
//
//
//        BigTmc bigTmc = bigTmcReader.getOne(allSortedTmc, id);
//        ModelAndView modelAndView = new ModelAndView("one_tmc");
//
//        modelAndView.addObject("oneTmc", bigTmc);
//        modelAndView.addObject("numberOfReports", numberOfReports);
//        modelAndView.addObject("beginStringDay", beginDay); //beginDay.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")
//        modelAndView.addObject("endStringDay", endDay);
//        modelAndView.addObject("period", new Period(beginDay, endDay));
//
//        return modelAndView;
//    }
}
