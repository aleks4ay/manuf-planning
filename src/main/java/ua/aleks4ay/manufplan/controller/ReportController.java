package ua.aleks4ay.manufplan.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.servlet.ModelAndView;
import ua.aleks4ay.manufplan.domain.model.BigTmc;
import ua.aleks4ay.manufplan.domain.services.BigTmcReader;
import ua.aleks4ay.manufplan.domain.web.Period;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
public class ReportController {

    private LocalDateTime beginDay = LocalDateTime.of(2020, 4, 27, 0, 0);
    private LocalDateTime endDay = LocalDateTime.of(2020, 10, 30, 23, 59);

    public ReportController() {
    }

    public void setBeginDay(LocalDateTime beginDay) {
        this.beginDay = beginDay;
    }

    public void setEndDay(LocalDateTime endDay) {
        this.endDay = endDay;
    }

    @GetMapping("/")
    public String getStart() {
//        return "redirect:report";
        return "index";
    }

    @GetMapping("/tmc")
    public ModelAndView mainView() {
        BigTmcReader bigTmcReader = new BigTmcReader();
        List<BigTmc> allSortedTmc = bigTmcReader.getbigTmcListAfterSorting(beginDay, endDay);
        int numberOfReports = bigTmcReader.getSizeOfDescription(allSortedTmc);

        ModelAndView modelAndView = new ModelAndView("report");

        modelAndView.addObject("testItems", allSortedTmc);
        modelAndView.addObject("numberOfReports", numberOfReports);
        modelAndView.addObject("beginStringDay", beginDay.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
        modelAndView.addObject("endStringDay", endDay.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
        modelAndView.addObject("period", new Period(beginDay, endDay));

        return modelAndView;
    }

    @PostMapping("/tmcChange")
    public String changeDate(@ModelAttribute Period period, Model model) {
        setBeginDay(period.getBeginDay());
        setEndDay(period.getEndDay());
        System.out.println(period.getBeginDay());
        System.out.println(period.getEndDay());

        return "redirect:/tmc";
    }


//    @PostMapping("/tmc")
//    public String changeDate(@ModelAttribute("minDay")LocalDateTime minDay,
//                             @ModelAttribute("maxDay")LocalDateTime maxDay) {
//        setBeginDay(minDay.toLocalDate());
//        setEndDay(maxDay.toLocalDate());

        /*BigTmcReader bigTmcReader = new BigTmcReader();
        List<BigTmc> allSortedTmc = bigTmcReader.getbigTmcListAfterSorting(beginDay, endDay);
        int numberOfReports = bigTmcReader.getSizeOfDescription(allSortedTmc);

        ModelAndView modelAndView = new ModelAndView("report");

        modelAndView.addObject("testItems", allSortedTmc);
        modelAndView.addObject("numberOfReports", numberOfReports);
        modelAndView.addObject("dayStart", beginDay);
        modelAndView.addObject("dayEnd", endDay);
*/
//        return "/tmc";
//    }
}
