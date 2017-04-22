package zadaci;

import com.j256.ormlite.dao.CloseableIterator;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import model.Artikal;
import model.Racun;
import model.Stavka;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by dzoni on 4/22/2017.
 */
public class Zadatak3BidirekcionaVeza {

    static Dao<Artikal, Integer> artikalDao;
    static Dao<Racun, Integer> racunDao;
    static Dao<Stavka, Integer> stavkaDao;

    public static void main(String[] args) {
        ConnectionSource conn = null;

        try {
            conn = new JdbcConnectionSource("jdbc:sqlite:baza.db");

            artikalDao = DaoManager.createDao(conn, Artikal.class);
            racunDao = DaoManager.createDao(conn, Racun.class);
            stavkaDao = DaoManager.createDao(conn, Stavka.class);

            List<Racun> listaRacuna = racunDao.queryForAll();
            System.out.println("Svi Racuni iz baze: ");
            for (Racun rn : listaRacuna){
                System.out.println(rn);
            }

            listaRacuna = racunDao.queryForEq(Racun.POLJE_OZNAKA,"RacunDrugi");
            System.out.println("Svi racuni koji za vrednost atributa oznaka imaju vrednost 'Racun2': ");
            for (Racun rn : listaRacuna){
                System.out.println(rn);
            }

            //CloseableIterator<Stavka>
            CloseableIterator<Stavka> iterator = stavkaDao.closeableIterator();
            try {
                while (iterator.hasNext()) {
                    Stavka stavka = iterator.next();
                    System.out.println("Stavka: " + stavka);
                    System.out.println("Artikal Stavke: " + stavka.getArtikal());
                }
            }catch (Exception e){
                e.printStackTrace();
            } finally {
                // close it at the end to close underlying SQL statement
                try {
                    iterator.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (conn != null){
                try {
                    conn.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
