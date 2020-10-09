package ua.aleks4ay.manufplan.domain.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.aleks4ay.manufplan.domain.dao.TmcDao;
import ua.aleks4ay.manufplan.domain.dao.UtilDao;
import ua.aleks4ay.manufplan.domain.model.*;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

import static ua.aleks4ay.manufplan.log.ClassNameUtil.getCurrentClassName;

public class BigTmcReader {

    private static final Logger log = LoggerFactory.getLogger(getCurrentClassName());
    private final UtilDao utilDao;

    public BigTmcReader() {
        utilDao = new UtilDao();
    }

    public List<BigTmc> removeEpicenter(List<BigTmc> listBefore) {
        List<BigTmc> listAfter = new ArrayList<>();
        for (BigTmc bigTmc : listBefore) {
            List<Description> descriptions = bigTmc.getDescriptions();
            List<Description> newDescriptionList = new ArrayList<>();
            for (Description descr : descriptions) {
                if (! descr.getClient().contains("Епіцентр")) {
                    newDescriptionList.add(descr);
                }
            }
            if (newDescriptionList.size() > 0) {
                bigTmc.setDescriptions(newDescriptionList);
                listAfter.add(bigTmc);
            }
        }
        return listAfter;
    }

    public List<BigTmc> getbigTmcListAfterSorting(Timestamp dateStart, Timestamp dateEnd) {
        OrderReader orderReader = new OrderReader();
        List<Order> orders = orderReader.getOrdersBetweenDates(dateStart, dateEnd);
        Map<String, Order> orderMap = orderReader.getAllAsMap(orders);

        InvoiceReader invoiceReader = new InvoiceReader();
        Map<String, List<Invoice>> invoiceMap = invoiceReader.getAllAsMapOfList();


        DescriptionReader descriptionReader = new DescriptionReader();
        List<Description> descriptions = descriptionReader.getOpenDescription();
        descriptions = descriptionReader.setOrderAllAndDeleteUnnecessary(descriptions, orderMap);
        descriptions = descriptionReader.setInvoiceAll(descriptions, invoiceMap);
        descriptions = descriptionReader.removeClosedPosition(descriptions);

        descriptionReader.sortByDateFactory(descriptions);

        Map<String, BigTmc> bigTmcMap = getBigTmcAsMap();
        bigTmcMap = setDescriptionAllAndDeleteEmptyTmc(bigTmcMap, descriptions);
        List<BigTmc> bigTmcListAfterSorting = sortByTmcDescription(convertMapToList(bigTmcMap));

        fillDemandTmc(bigTmcListAfterSorting);
        return bigTmcListAfterSorting;
    }


    private List<BigTmc> sortByTmcDescription(List<BigTmc> bigTmcList) {
        Comparator<BigTmc> comparatorBigTmcDescription = (tmc1, tmc2) ->
                tmc1.getTmcDescription().compareTo(tmc2.getTmcDescription());
        Collections.sort(bigTmcList, comparatorBigTmcDescription);
        return bigTmcList;
    }

    private List<BigTmc> convertMapToList(Map<String, BigTmc> bigTmcMap) {
        List<BigTmc> result = new ArrayList<>();
        result.addAll(bigTmcMap.values());
        return result;
    }

    private Map<String, BigTmc> setDescriptionAllAndDeleteEmptyTmc(Map<String, BigTmc> bigTmcMap, List<Description> descriptions) {
        Map<String, BigTmc> result = new HashMap<>();
        for (Description descr : descriptions) {
            BigTmc tempBigTmc = bigTmcMap.get(descr.getIdTmc());
            if (tempBigTmc != null) {
                tempBigTmc.addDescription(descr);
            }
        }
        for (BigTmc tmc : bigTmcMap.values()) {
            if (tmc.getDescriptions() != null) {
                result.put(tmc.getId(), tmc);
            }
        }
        return result;
    }


    public List<BigTmc> getBigTmcList() {
        Connection connPostgres = utilDao.getConnPostgres();
        TmcDao tmcDao = new TmcDao(connPostgres);

        List<BigTmc> result = new ArrayList<>();
        List<Tmc> tmcList = tmcDao.getAll();
        for (Tmc tmc : tmcList) {
            BigTmc newBigTmc = new BigTmc(tmc.getId(), tmc.getTmcDescription(), tmc.getBalance());
            result.add(newBigTmc);
        }
        utilDao.closeConnection(connPostgres);
        log.info("Was read {} TMC.", tmcList.size());
        return result;
    }

    public Map<String, BigTmc> getBigTmcAsMap() {
        List<BigTmc> result = getBigTmcList();
        return result
                .stream()
                .collect(Collectors.toMap(BigTmc::getId, BigTmc::getBigTmc));
    }

    public int getSizeOfDescription(List<BigTmc> bigTmcList) {
        int result = 0;
        for (BigTmc bigTmc : bigTmcList) {
            if (bigTmc.getDescriptions() != null) {
                result += bigTmc.getDescriptions().size();
            }
        }
        return result;
    }

    public void fillDemandTmc(List<BigTmc> bigTmcList) {
        for (BigTmc bigTmc : bigTmcList) {
            int demand = 0;
            for (Description d : bigTmc.getDescriptions()) {
                int tempDemand = d.getQuantity() - d.getQuantityShipped();
                if (tempDemand > 0) {
                    demand += tempDemand;
                }
            }
            bigTmc.setDemand(demand);
        }
    }

    public BigTmc getOne(List<BigTmc> allSortedTmc, String id) {
        for (BigTmc bigTmc : allSortedTmc) {
            if (bigTmc.getId().equals(id)) {
                return bigTmc;
            }
        }
        return null;
    }
}
