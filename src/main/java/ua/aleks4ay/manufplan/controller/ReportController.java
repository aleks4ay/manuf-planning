package ua.aleks4ay.manufplan.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import ua.aleks4ay.manufplan.domain.model.Description;
import ua.aleks4ay.manufplan.domain.model.DescriptionModelAtribute;
import ua.aleks4ay.manufplan.domain.services.MainData;
import ua.aleks4ay.manufplan.domain.view.ViewData;

import java.util.List;

import static ua.aleks4ay.manufplan.log.ClassNameUtil.getCurrentClassName;

@Controller
public class ReportController {

    private static final String MIN_DEY_TO_FACTORY = "28-03-2020";
    private static final String MAX_DEY_TO_FACTORY = "31-12-2020";
    private String FILE_NAME;
    private static final Logger log = LoggerFactory.getLogger(getCurrentClassName());
    MainData mainData = new MainData();

    public ReportController(String FILE_NAME) {
        this.FILE_NAME = FILE_NAME;
    }

    public ReportController() {
    }

    //    public static void main(String[] args) {
//        new ReportController("print/report.html").createAndPrintReport(MIN_DEY_TO_FACTORY, MAX_DEY_TO_FACTORY);
//    }

    public void createAndPrintReport (String start, String end) {
        mainData = new MainData();

        List<Description> allDescription = mainData.getAllDescription();

        List<Description> openDescriptions = mainData.filterOpenWithDate(
                allDescription, start, end );

        mainData.sortByDescriptionTmc(openDescriptions);

        ViewData viewData = new ViewData(FILE_NAME, start, end);

        List<List<Description>> tmcListOfList = mainData.getTmcAsListOfList(openDescriptions);

        tmcListOfList = mainData.sortByListDescriptionTmc(tmcListOfList);
        viewData.printTmcMapToHtml(tmcListOfList);
    }

    @GetMapping("/")
    public String getStart() {
//        return "redirect:report";
        return "index";
    }

    @GetMapping("/tmc")
    public ModelAndView mainView() {
//        new ReportController("print/report.html").createAndPrintReport(MIN_DEY_TO_FACTORY, MAX_DEY_TO_FACTORY);
        List<Description> allDescription = mainData.getAllDescription();
        List<Description> openDescriptions = mainData.filterOpenWithDate(
                allDescription, MIN_DEY_TO_FACTORY, MAX_DEY_TO_FACTORY );

        mainData.sortByDescriptionTmc(openDescriptions);

        DescriptionModelAtribute descriptionModelAtribute = new DescriptionModelAtribute();
        descriptionModelAtribute.setItemList(openDescriptions);

        ModelAndView modelAndView = new ModelAndView("report");

        modelAndView.addObject("testItems", descriptionModelAtribute);
        modelAndView.addObject("numberOfReports", openDescriptions.size());
        modelAndView.addObject("dayStart", MIN_DEY_TO_FACTORY.replace("-", "."));
        modelAndView.addObject("dayEnd", MAX_DEY_TO_FACTORY.replace("-", "."));

        return modelAndView; //new ModelAndView("report", "testItems", descriptionModelAtribute);
    }

    @PostMapping("/tmc")
    public ModelAndView submit(@ModelAttribute("testItems") DescriptionModelAtribute descriptionModelAtribute) {
        return new ModelAndView("report", "testItems", descriptionModelAtribute);
    }

}
