package ua.aleks4ay.manufplan.controller;

import ua.aleks4ay.manufplan.domain.model.Description;
import ua.aleks4ay.manufplan.domain.services.MainData;
import ua.aleks4ay.manufplan.domain.view.ViewData;

import java.util.List;

public class ReportController {

    private static final String MIN_DEY_TO_FACTORY = "28-03-2020";
    private static final String MAX_DEY_TO_FACTORY = "31-12-2020";
    private final String FILE_NAME;

    public ReportController(String FILE_NAME) {
        this.FILE_NAME = FILE_NAME;
    }

    public static void main(String[] args) {
        new ReportController("print/report.html").createAndPrintReport(MIN_DEY_TO_FACTORY, MAX_DEY_TO_FACTORY);
    }

    public void createAndPrintReport (String start, String end) {
        MainData mainData = new MainData();

        List<Description> allDescription = mainData.getAllDescription();

        List<Description> openDescriptions = mainData.filterOpenWithDate(
                allDescription, start, end );

        mainData.sortByDescriptionTmc(openDescriptions);

        ViewData viewData = new ViewData(FILE_NAME, start, end);

        List<List<Description>> tmcListOfList = mainData.getTmcAsListOfList(openDescriptions);

        tmcListOfList = mainData.sortByListDescriptionTmc(tmcListOfList);
        viewData.printTmcMapToHtml(tmcListOfList);
    }
}
