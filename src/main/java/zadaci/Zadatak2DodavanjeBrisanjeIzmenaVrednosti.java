package zadaci;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import model.Artikal;
import model.Racun;
import model.Stavka;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

/**
 * Created by dzoni on 4/22/2017.
 */
public class Zadatak2DodavanjeBrisanjeIzmenaVrednosti {

    static Dao<Artikal, Integer> artikalDao;
    static Dao<Racun, Integer> racunDao;
    static Dao<Stavka, Integer> stavkaDao;

    public static void main(String[] args) {
        ConnectionSource conn = null;

        try {
            conn = new JdbcConnectionSource("jdbc:sqlite:baza.db");

            // Instanciranje Dao objekata
            artikalDao = DaoManager.createDao(conn,Artikal.class);
            racunDao = DaoManager.createDao(conn, Racun.class);
            stavkaDao = DaoManager.createDao(conn, Stavka.class);

            // Brisanje vrednosti iz tabela
            TableUtils.clearTable(conn, Stavka.class);
            TableUtils.clearTable(conn, Artikal.class);
            TableUtils.clearTable(conn, Racun.class);


            // 2.3.1 Dodavanje vrednosti

            Artikal art1 = new Artikal("Mleko", "Mleko u flasi 1L", 40.99);
            Artikal art2 = new Artikal("Beli hleb", "400g", 50.00);
            Artikal art3 = new Artikal("Crni hleb", "Crni razeni hleb", 60.00);
            Artikal art4 = new Artikal("Jogurt", "Jogurt u tetrapaku 1L", 90.99);

            artikalDao.create(art1);
            artikalDao.create(art2);
            artikalDao.create(art3);
            artikalDao.create(art4);

            Racun rn1 = new Racun("Racun1", new Date());
            Racun rn2 = new Racun("Racun2", new Date());

            racunDao.create(rn1);
            racunDao.create(rn2);

            Stavka st1 = new Stavka(1, art1, rn1);
            Stavka st2 = new Stavka(1, art2, rn1);
            Stavka st3 = new Stavka(1, art3, rn2);

            stavkaDao.create(st1);
            stavkaDao.create(st2);
            stavkaDao.create(st3);

            // Prikaz svih vrednosti iz svih tabela

            List<Artikal> listaArtikala = artikalDao.queryForAll();
            System.out.println("Svi Artikli iz baze: ");
            for (Artikal art : listaArtikala ) {
                System.out.println(art);
            }

            List<Racun> listaRacuna = racunDao.queryForAll();
            System.out.println("Svi Racuni iz baze: ");
            for (Racun rn : listaRacuna ) {
                System.out.println(rn);
            }


            List<Stavka> listaStavki = stavkaDao.queryForAll();
            System.out.println("Sve Stavke iz baze: ");
            for (Stavka st: listaStavki) {
                System.out.println(st);
            }

            //  2.3.2. Izmena vrednosti

            Racun racunZaIzmenu = racunDao.queryForId(rn1.getId());
            racunZaIzmenu.setOznaka("RacunPrvi");
            racunDao.update(racunZaIzmenu);

            listaRacuna = racunDao.queryForEq(Racun.POLJE_OZNAKA, "Racun2");
            Racun racunZaIzmenuDva = listaRacuna.get(0);
            racunZaIzmenuDva.setOznaka("RacunDrugi");
            racunDao.update(racunZaIzmenuDva);

            listaRacuna = racunDao.queryForAll();
            System.out.println("Svi Racuni iz baze posle izmene: ");
            for (Racun rn : listaRacuna ) {
                System.out.println(rn);
            }



            // 2.3.3 Brisanje vrednosti

            Artikal art5 = new Artikal("Voda", "Flasa vode od 1.5L", 70.00);
            artikalDao.create(art5);

            listaArtikala = artikalDao.queryForAll();
            System.out.println("Svi Artikli iz baze posle dodatog novog artikla: ");
            for (Artikal art : listaArtikala) {
                System.out.println(art);
            }

            Artikal artikalZaBrisanje = artikalDao.queryForId(art5.getId());
            artikalDao.delete(artikalZaBrisanje);

            listaArtikala = artikalDao.queryForAll();
            System.out.println("Svi Artikli iz baze posle brisanja novog artikla: ");
            for (Artikal art : listaArtikala) {
                System.out.println(art);
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
