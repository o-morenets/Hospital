package model.dao;

import model.entities.DiagnosisType;
import model.entities.Patient;

/**
 * interface PatientDao
 * Created by alexey.morenets@gmail.com on 21.01.2017.
 */
public interface PatientDao extends GenericDao<Patient> {
    DiagnosisType getDiagnosisType(int patientId);
}
