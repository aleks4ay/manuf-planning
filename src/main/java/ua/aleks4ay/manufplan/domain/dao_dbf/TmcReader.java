package ua.aleks4ay.manufplan.domain.dao_dbf;

import java.io.*;
import com.linuxense.javadbf.*;
import ua.aleks4ay.manufplan.domain.dao_dbf.model_dbf.Tmc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import static ua.aleks4ay.manufplan.log.ClassNameUtil.getCurrentClassName;

public class TmcReader {
    private static final Logger log = LoggerFactory.getLogger(getCurrentClassName());
    private static final String DBF_PATH = DbfTools.getDbfPath();

    public static void main(String[] args) {
        TmcReader tmcReader = new TmcReader();
        List<Tmc> tmcList = tmcReader.getAll();
        tmcList = tmcReader.doTechnoFilter(tmcList);
        System.out.println(tmcList.size());
        tmcList.forEach(System.out::println);
    }

    public List<Tmc> getAll() {

        List<Tmc> listTmc = new ArrayList<>();

        DBFReader reader = null;
        try {
            reader = new DBFReader(new FileInputStream(DBF_PATH + "SC302.DBF"));

            DBFRow row;

            while ((row = reader.nextRow()) != null) {
                String id = row.getString("ID");
                String parentId = row.getString("PARENTID");
                String code = row.getString("CODE");
                String descr = new String(row.getString("DESCR").getBytes("ISO-8859-1"), "Windows-1251");
                int isFolder = row.getInt("ISFOLDER");
                String descrAll = new String(row.getString("SP276").getBytes("ISO-8859-1"), "Windows-1251");
                String type = row.getString("SP277");

                listTmc.add(new Tmc(id, parentId, code, descr, isFolder, descrAll, type));
            }

            log.debug("Was read {} Tmc from 1S.", listTmc.size());
            return listTmc;
        } catch (DBFException | IOException e) {
            log.warn("Exception during reading file 'Tmc'.", e);
        } catch (Exception e) {
            log.warn("Exception during writing all 'Tmc'.", e);
        }
        finally {
            DBFUtils.close(reader);
        }

        log.debug("Tmc not found.");
        return null;
    }


    public List<Tmc> doTechnoFilter(List<Tmc> allTmcList) {
        Set<String> idFoldersWithTechno = new TreeSet<>();
        idFoldersWithTechno.add("    19");
        return getTechnoChildren(idFoldersWithTechno, new ArrayList<>(), allTmcList);
    }


    private List<Tmc> getTechnoChildren(Set<String> idFoldersWithTechno, List<Tmc> technoItems, List<Tmc> tmcList) {

        Set<String> newFoldersWithTechno = new TreeSet<>();

        if (idFoldersWithTechno.size() == 0) {
            return technoItems;
        }
        else {
            for (String folderName : idFoldersWithTechno) {
                for (Tmc tmc : tmcList) {
                    if (tmc.getIdParent().equals(folderName)) {
                        if (tmc.getIsFolder() == 2) {
                            technoItems.add(tmc);
                        } else {
                            newFoldersWithTechno.add(tmc.getId());
                        }
                    }
                }
            }
            return getTechnoChildren(newFoldersWithTechno, technoItems, tmcList);
        }
    }
}
