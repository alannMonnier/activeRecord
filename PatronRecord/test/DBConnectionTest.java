import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class DBConnectionTest {




    /**
     * Test que la connection est unique
     */
    @Test
    void getConnection() {
        Connection db = DBConnection.getConnection();

        assertEquals(db,DBConnection.getConnection(), "La connexion devrait être unique");

    }

    @Test
    void setNomDB() throws SQLException {
        Connection db = DBConnection.getConnection();
        DBConnection.setNomDB("test");
        assertFalse(db == DBConnection.getConnection(), "Le nom de la base devrait être différent");
    }
}