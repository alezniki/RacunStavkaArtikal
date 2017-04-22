package zadaci;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.SelectArg;
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
public class Zadatak4PretragaVrednosti {

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

            QueryBuilder<Artikal,Integer> artikalQueryBuilder = artikalDao.queryBuilder();

            // 2.5.1. Select izraz: like() metoda Where objekta
            artikalQueryBuilder.where().like(Artikal.POLJE_NAZIV, "%hleb%");
            List<Artikal> listaArtikala = artikalQueryBuilder.query();
            System.out.println("Svi artikli u bazi koji za vrednost kolone naziv imaju tekst 'hleb' : ");
            for (Artikal art: listaArtikala){
                System.out.println(art);
            }

            // 2.5.2 SELECT Izraz sa SelectArg
            SelectArg selectArg = new SelectArg();
            artikalQueryBuilder.where().like(Artikal.POLJE_OPIS, selectArg);
            PreparedQuery<Artikal> artikalPreparedQuery = artikalQueryBuilder.prepare();

            selectArg.setValue("%1L%");
            listaArtikala = artikalDao.query(artikalPreparedQuery);
            System.out.println("Svi artikli u bazi koji za vrednost kolone opis imaju tekst '1L' :");
            for (Artikal art: listaArtikala){
                System.out.println(art);
            }

            selectArg.setValue("%u%");
            listaArtikala = artikalDao.query(artikalPreparedQuery);
            System.out.println("Svi artikli u bazi koji za vrednost kolone opis imaju tekst 'u' :");
            for (Artikal art: listaArtikala){
                System.out.println(art);
            }

            // 2.5.3. SELECT izraz sa JOIN operatorom
            QueryBuilder<Stavka,Integer> stavkaQueryBuilder = stavkaDao.queryBuilder();
            stavkaQueryBuilder.where().eq(Stavka.POLJE_KOLICINA,2);

            QueryBuilder<Racun,Integer> racunQueryBuilder = racunDao.queryBuilder();

           List<Racun> listaRacuna = racunQueryBuilder.join(stavkaQueryBuilder).query();

            System.out.println("Svi racuni iz baze: ");
            for (Racun rn: listaRacuna){
                System.out.println(rn);
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
