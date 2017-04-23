package zadaci;

import com.j256.ormlite.dao.CloseableIterator;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.misc.TransactionManager;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.support.ConnectionSource;
import model.Artikal;
import model.Racun;
import model.Stavka;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by dzoni on 4/22/2017.
 */
public class Zadatak5BrisanjeRacunaStavkiTransakcija {

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
            System.out.println("Svi racuni iz baze");
            for (Racun rn : listaRacuna){
                System.out.println(rn);
            }

            List<Stavka> listaStavki = stavkaDao.queryForAll();
            System.out.println("Sve stavke iz baze");
            for (Stavka st : listaStavki) {
                System.out.println(st);
            }

            PreparedQuery<Racun> racunPreparedQuery =
                    racunDao.queryBuilder().where().eq(Racun.POLJE_OZNAKA,"RacunDrugi").prepare();

            final  Racun racunDrugi = racunDao.queryForFirst(racunPreparedQuery);
            if (racunDrugi != null){
                final List<Stavka> listaStavkiZaBrisanje = new ArrayList<>();
                ForeignCollection<Stavka> stavke = racunDrugi.getStavke();
                CloseableIterator<Stavka> iterator = stavkaDao.closeableIterator();

                try{
                    while (iterator.hasNext()){
                        Stavka st = iterator.next();
                        listaStavkiZaBrisanje.add(st);
                        System.out.println("Stavke za brisanje: " + st);
                    }
                } catch (Exception e){
                    e.printStackTrace();
                } finally {
                    try {
                        iterator.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                TransactionManager.callInTransaction(conn, new Callable<Void>() {

                    @Override
                    public Void call() throws Exception {
                        // delete both objects but make sure that if either one fails,
                        // the transaction is rolled back
                        // and both objects are "restored" to the database
                        stavkaDao.delete(listaStavkiZaBrisanje);
                        racunDao.delete(racunDrugi);
                        return null;
                    }
                });

            } // IF statement


            listaRacuna = racunDao.queryForAll();
            System.out.println("Svi racuni iz baze posle brisanja");
            for (Racun rn : listaRacuna){
                System.out.println(rn);
            }

            listaStavki = stavkaDao.queryForAll();
            System.out.println("Sve stavke iz baze posle brisanja");
            for (Stavka st : listaStavki){
                System.out.println(st);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
