package ua.aleks4ay.manufplan.domain.dao_dbf;

import java.io.*;
import com.linuxense.javadbf.*;
import ua.aleks4ay.manufplan.domain.model.Description;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import static ua.aleks4ay.manufplan.log.ClassNameUtil.getCurrentClassName;

public class DescriptionReader  {

    private static final Logger log = LoggerFactory.getLogger(getCurrentClassName());
    private static final String DBF_PATH = DbfTools.getDbfPath();

    public static void main(String[] args) {
        List<Description> descriptionList = new DescriptionReader().getAll();

        descriptionList.forEach(System.out::println);

        System.out.println(descriptionList.size());
    }


    public List<Description> getAll() {

        List<Description> descriptionList = new ArrayList<>();
        DBFReader reader = null;
        try {
            reader = new DBFReader(new FileInputStream(DBF_PATH + "DT1898.DBF"));

            DBFRow row;
            while ((row = reader.nextRow()) != null) {
                String idDoc = row.getString("IDDOC");
                String idTmc = row.getString("SP1902");//Must not be: 'Go designer to size measurement', 'Shipment', 'Fixing', 'DELETED'
                if (idTmc.equalsIgnoreCase("   CBN") || idTmc.equalsIgnoreCase("   7LH") ||
                        idTmc.equalsIgnoreCase("   9VQ") || idTmc.equalsIgnoreCase("     0")) {
                    continue;
                }
                int position = row.getInt("LINENO");
                int quantity = row.getInt("SP1905");
                String descrSecond = new String(row.getString("SP14676").getBytes("ISO-8859-15"), "Windows-1251");
                int sizeA = row.getInt("SP14686");
                int sizeB = row.getInt("SP14687");
                int sizeC = row.getInt("SP14688");

                double price = row.getDouble("SP14684");

                String kod = idDoc + "-" + position;

                descriptionList.add(new Description(
                        kod, idDoc, position, idTmc, quantity, descrSecond, sizeA, sizeB, sizeC, price));
            }
            log.debug("Was read {} Description from 1C 'DT1898'.", descriptionList.size());

            return descriptionList;

        } catch (DBFException | IOException e) {
            log.warn("Exception during reading file 'DT1898.dbf'.", e);
        } catch (Exception e) {
            log.warn("Exception during writing all 'Description'.", e);
        }
        finally {
            DBFUtils.close(reader);
        }

        log.debug("Description not found.");
        return null;
    }
}
