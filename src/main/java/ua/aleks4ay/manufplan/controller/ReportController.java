package ua.aleks4ay.manufplan.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ua.aleks4ay.manufplan.domain.model.BigTmc;
import ua.aleks4ay.manufplan.domain.model.ManufactureTechno;
import ua.aleks4ay.manufplan.domain.services.ManufactureReader;
import ua.aleks4ay.manufplan.domain.services.BigTmcReader;
import ua.aleks4ay.manufplan.domain.tools.DateConverter;
import ua.aleks4ay.manufplan.domain.web.Period;

import java.sql.Timestamp;
import java.util.List;

import static ua.aleks4ay.manufplan.domain.tools.DateConverter.getNowDate;
import static ua.aleks4ay.manufplan.domain.tools.DateConverter.offset;

@Controller
public class ReportController {

    private String beginDay = "20.04.2020";
    private String endDay = DateConverter.getNowDateString();

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
        ModelAndView modelAndView = new ModelAndView("one_tmc");

        Timestamp start = DateConverter.getTimestampFromString(beginDay);
        Timestamp end = DateConverter.getTimestampFromString(endDay);
        BigTmcReader bigTmcReader = new BigTmcReader();

        List<BigTmc> allSortedTmc = bigTmcReader.getbigTmcListAfterSorting(start, end);
        BigTmc bigTmc = bigTmcReader.getOne(allSortedTmc, id);
        modelAndView.addObject("oneTmc", bigTmc);

        Timestamp minDateToShipment = bigTmc.getDescriptions().get(0).getOrder().getDateToFactory();

        ManufactureReader manufactureReader = new ManufactureReader();
        List<ManufactureTechno> manufactureTechnoList = manufactureReader.getById(id);
        manufactureReader.sortByDate(manufactureTechnoList);
        manufactureTechnoList = manufactureReader.removeWithOldDate(manufactureTechnoList, minDateToShipment);
        modelAndView.addObject("manufactures", manufactureTechnoList);

        List<ManufactureTechno> manufacturesAll = manufactureReader.getAll();

        Timestamp today = new Timestamp(getNowDate());
        List<ManufactureTechno> manuf1 = manufactureReader.restOnlyThisDate(manufacturesAll, today);
        modelAndView.addObject("manuf1", manuf1);

        Timestamp yesterday = new Timestamp(offset(getNowDate(), -1));
        List<ManufactureTechno> manuf2 = manufactureReader.restOnlyThisDate(manufacturesAll, yesterday);
        modelAndView.addObject("manuf2", manuf2);

        Timestamp yesterdayBefore2 = new Timestamp(offset(getNowDate(), -2));
        List<ManufactureTechno> manuf3 = manufactureReader.restOnlyThisDate(manufacturesAll, yesterdayBefore2);
        modelAndView.addObject("manuf3", manuf3);

        Timestamp yesterdayBefore3 = new Timestamp(offset(getNowDate(), -3));
        List<ManufactureTechno> manuf4 = manufactureReader.restOnlyThisDate(manufacturesAll, yesterdayBefore3);
        modelAndView.addObject("manuf4", manuf4);

        Timestamp yesterdayBefore4 = new Timestamp(offset(getNowDate(), -4));
        List<ManufactureTechno> manuf5 = manufactureReader.restOnlyThisDate(manufacturesAll, yesterdayBefore4);
        modelAndView.addObject("manuf5", manuf5);

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
