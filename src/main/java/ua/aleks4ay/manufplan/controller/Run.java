package ua.aleks4ay.manufplan.controller;

import ua.aleks4ay.manufplan.domain.model.Description;
import ua.aleks4ay.manufplan.domain.services.MainData;
import ua.aleks4ay.manufplan.domain.view.ViewData;

import java.util.List;

public class Run {

    private static final String MIN_DEY_TO_FACTORY = "15-09-2020";
    private static final String MAX_DEY_TO_FACTORY = "31-12-2020";

    public static void main(String[] args) {

        MainData mainData = new MainData();

        List<Description> allDescription = mainData.getAllDescription();
        List<Description> openDescriptions = mainData.filterOpenWithDate(
                allDescription, MIN_DEY_TO_FACTORY, MAX_DEY_TO_FACTORY );

        mainData.sortByDescriptionTmc(openDescriptions);

        ViewData viewData = new ViewData("print/report.html");

        List<List<Description>> tmcListOfList = mainData.getTmcAsListOfList(openDescriptions);
        tmcListOfList = mainData.sortByListDescriptionTmc(tmcListOfList);
        viewData.printTmcMapToHtml(tmcListOfList);




    }

}
