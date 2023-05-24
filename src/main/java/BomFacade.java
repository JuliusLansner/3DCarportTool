import java.sql.SQLException;
import java.util.ArrayList;

public class BomFacade {

    public static ArrayList<Bom> getBoms(ConnectionPool connectionPool) throws SQLException {
        return BomMapper.getBoms(connectionPool);
    }
}
