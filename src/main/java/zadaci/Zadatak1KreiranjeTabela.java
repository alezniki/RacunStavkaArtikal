package zadaci;

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
public class Zadatak1KreiranjeTabela {
    public static void main(String[] args) {
        ConnectionSource conn = null;

        try {
            conn = new JdbcConnectionSource("jdbc:sqlite:baza.db");

            // Brisanje tabela
            TableUtils.dropTable(conn, Stavka.class, true);
            TableUtils.dropTable(conn, Artikal.class, true);
            TableUtils.dropTable(conn, Racun.class, true);

            // Kreiranje tabela
            TableUtils.createTable(conn, Artikal.class);
            TableUtils.createTable(conn, Racun.class);
            TableUtils.createTable(conn, Stavka.class);

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
