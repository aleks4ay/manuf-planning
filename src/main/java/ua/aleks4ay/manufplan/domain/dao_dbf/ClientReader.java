package ua.aleks4ay.manufplan.domain.dao_dbf;

import com.linuxense.javadbf.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static ua.aleks4ay.manufplan.log.ClassNameUtil.getCurrentClassName;

public class ClientReader {
    private static final Logger log = LoggerFactory.getLogger(getCurrentClassName());
    private static final String DBF_PATH = DbfTools.getDbfPath();

    public static void main(String[] args) {
        Map<String, String> clients = new ClientReader().getAll();
    }


    public Map<String, String> getAll() {
        Map<String, String> mapClient = new HashMap<>();

        DBFReader reader = null;
        try {
            reader = new DBFReader(new FileInputStream(DBF_PATH + "SC172.DBF"));

            DBFRow row;
            while ((row = reader.nextRow()) != null) {
                String id = row.getString("ID");
                String name = new String(row.getString("DESCR").getBytes("ISO-8859-1"), "Windows-1251");
                if (name.equals("")) {
                    name = "-";
                }
                mapClient.put(id, name);
            }
            log.debug("Was read {} Clients from 1S.", mapClient.size());

            return mapClient;

        } catch (DBFException | IOException e) {
            log.warn("Exception during reading file 'Client'.", e);
        } catch (Exception e) {
            log.warn("Exception during writing all 'Client'.", e);
        }
        finally {
            DBFUtils.close(reader);
        }

        log.debug("Clients not found.");
        return null;
    }
}
