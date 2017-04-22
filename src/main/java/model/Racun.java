package model;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

/**
 * Created by dzoni on 4/22/2017.
 */
@DatabaseTable(tableName = "racun")
public class Racun {

    public static final String POLJE_OZNAKA = "oznaka";
    public static final String POLJE_DATUM = "datum";

    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField(columnName = POLJE_OZNAKA, canBeNull = false)
    private String oznaka;
    @DatabaseField(columnName = POLJE_DATUM, canBeNull = false)
    private Date datum;

    @ForeignCollectionField(foreignFieldName = "racun")
    ForeignCollection<Stavka> stavke;

    public Racun(){

    }

    public Racun(String oznaka, Date datum) {
        this.oznaka = oznaka;
        this.datum = datum;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOznaka() {
        return oznaka;
    }

    public void setOznaka(String oznaka) {
        this.oznaka = oznaka;
    }

    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }

    public ForeignCollection<Stavka> getStavke() {
        return stavke;
    }

    public void setStavke(ForeignCollection<Stavka> stavke) {
        this.stavke = stavke;
    }

    @Override
    public String toString() {
        return "RACUN: " + "ID: " + id + ", OZNAKA: " + oznaka + ", DATUM: " + datum;
    }
}
