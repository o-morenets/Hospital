package model.dao.jdbc;

import model.dao.AssignationsProceduresDao;
import model.entities.AssignationsProcedures;
import model.entities.Procedure;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by alexey.morenets@gmail.com on 25.01.2017.
 */
public class JdbcAssignationsProceduresDao implements AssignationsProceduresDao {

    private static final Logger LOGGER = Logger.getLogger(JdbcAssignationsProceduresDao.class);

    /* SELECT */
    private static final String SELECT_FROM_ASSIGNATIONS_PROCEDURES =
            "SELECT ap.id, diagnosis_history_id, procedure_id, num_days, p.id id_procedure, name\n" +
                    "FROM assignations_procedures ap JOIN procedures p\n" +
                    "ON ap.procedure_id = p.id\n" +
                    "WHERE ap.diagnosis_history_id = ?";
    private static final String INSERT_INTO_ASSIGNATIONS_PROCEDURES =
            "INSERT INTO assignations_procedures\n" +
                    "(diagnosis_history_id, procedure_id, num_days)\n" +
                    "VALUES(?, ?, ?)";

    /* Fields for assignations_procedures */
    private static final String ID = "id";
    private static final String DIAGNOSIS_HISTORY_ID = "diagnosis_history_id";
    public static final String NUM_DAYS = "num_days";

    /* Fields for procedures */
    public static final String ID_PROCEDURE = "id_procedure";
    public static final String NAME = "name";

    private Connection connection;

    JdbcAssignationsProceduresDao(Connection connection) {
        this.connection = connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public List<AssignationsProcedures> findByDiagnosisHistoryId(int diagnosisHistoryId) {
        List<AssignationsProcedures> result = new ArrayList<>();
        try (PreparedStatement query = connection.prepareStatement(SELECT_FROM_ASSIGNATIONS_PROCEDURES)) {
            query.setString(1, String.valueOf(diagnosisHistoryId));
            ResultSet resultSet = query.executeQuery();
            while (resultSet.next()) {
                result.add(getAssignationProcedureFromResultSet(resultSet));
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return result;
    }

    private AssignationsProcedures getAssignationProcedureFromResultSet(ResultSet resultSet) throws SQLException {
        Procedure procedure = new Procedure();
        procedure.setId(resultSet.getInt(ID_PROCEDURE));
        procedure.setName(resultSet.getString(NAME));
        return new AssignationsProcedures.Builder()
                .setId(resultSet.getInt(ID))
                .setDiagnosisHistoryId(resultSet.getInt(DIAGNOSIS_HISTORY_ID))
                .setProcedure(procedure)
                .setNumDays(resultSet.getInt(NUM_DAYS))
                .build();
    }

    @Override
    public AssignationsProcedures find(int id) {
        return null;
    }

    @Override
    public List<AssignationsProcedures> findAll() {
        return null;
    }

    @Override
    public void create(AssignationsProcedures assignationsProcedures) {
        try (PreparedStatement query =
                     connection.prepareStatement(INSERT_INTO_ASSIGNATIONS_PROCEDURES, Statement.RETURN_GENERATED_KEYS)) {

            query.setString(1, String.valueOf(assignationsProcedures.getDiagnosisHistoryId()));
            query.setString(2, String.valueOf(assignationsProcedures.getProcedure().getId()));
            query.setString(3, String.valueOf(assignationsProcedures.getNumDays()));

            query.executeUpdate();
            ResultSet keys = query.getGeneratedKeys();

            if (keys.next()) {
                assignationsProcedures.setId(keys.getInt(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(AssignationsProcedures assignationsProcedures) {

    }

    @Override
    public void delete(int id) {

    }
}