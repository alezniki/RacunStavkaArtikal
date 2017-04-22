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
