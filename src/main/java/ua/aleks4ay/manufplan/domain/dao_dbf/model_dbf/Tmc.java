package ua.aleks4ay.manufplan.domain.dao_dbf.model_dbf;

public class Tmc {

    private String id;
    private String idParent;
    private String code;
    private String descr;
    private int isFolder;
    private String descrAll;
    private String type;

    public Tmc() {
    }

    public Tmc(String id) {
        this.id = id;
    }



    public Tmc(String id, String idParent, String code, String descr, int isFolder, String descrAll, String type) {
        this.id = id;
        this.idParent = idParent;
        this.code = code;
        this.descr = descr;
        this.isFolder = isFolder;
        this.descrAll = descrAll;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdParent() {
        return idParent;
    }

    public void setIdParent(String idParent) {
        this.idParent = idParent;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public int getIsFolder() {
        return isFolder;
    }

    public void setIsFolder(int isFolder) {
        this.isFolder = isFolder;
    }

    public String getDescrAll() {
        return descrAll;
    }

    public void setDescrAll(String descrAll) {
        this.descrAll = descrAll;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Tmc getTmc() {
        return this;
    }

    @Override
    public String toString() {
        return "Tmc{" +
                "id='" + id + '\'' +
                ", idParent='" + idParent + '\'' +
                ", code='" + code + '\'' +
                ", descr='" + descr + '\'' +
                ", isFolder=" + isFolder +
                ", descrAll='" + descrAll + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
