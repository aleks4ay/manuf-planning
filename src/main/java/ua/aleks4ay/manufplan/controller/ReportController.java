package ua.aleks4ay.manufplan.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import ua.aleks4ay.manufplan.domain.model.BigTmc;
import ua.aleks4ay.manufplan.domain.services.BigTmcReader;

import java.time.LocalDate;
import java.util.List;

import static ua.aleks4ay.manufplan.log.ClassNameUtil.getCurrentClassName;

@Controller
public class ReportController {

    private static final LocalDate START_DEY_TO_FACTORY = LocalDate.of(2020, 8, 27);
    private static final LocalDate END_DEY_TO_FACTORY = LocalDate.of(2020, 10, 30);
    private static final Logger log = LoggerFactory.getLogger(getCurrentClassName());

    public ReportController() {
    }

    @GetMapping("/")
    public String getStart() {
//        return "redirect:report";
        return "index";
    }

    @GetMapping("/tmc")
    public ModelAndView mainView() {
        BigTmcReader bigTmcReader = new BigTmcReader();
        List<BigTmc> allSortedTmc = bigTmcReader.getbigTmcListAfterSorting(START_DEY_TO_FACTORY, END_DEY_TO_FACTORY);
        int numberOfReports = bigTmcReader.getSizeOfDescription(allSortedTmc);

        ModelAndView modelAndView = new ModelAndView("report");

        modelAndView.addObject("testItems", allSortedTmc);
        modelAndView.addObject("numberOfReports", numberOfReports);
        modelAndView.addObject("dayStart", START_DEY_TO_FACTORY);
        modelAndView.addObject("dayEnd", END_DEY_TO_FACTORY);

        return modelAndView;
    }
}
