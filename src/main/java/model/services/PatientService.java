package model.services;

import model.dao.DaoFactory;
import model.dao.PatientDao;
import model.entities.Patient;

import java.util.List;

/**
 * Created by alexey.morenets@gmail.com on 21.01.2017.
 */
public class PatientService {

    private PatientDao patientDao = DaoFactory.getInstance().createPatientDao();

    private static class Holder{
        static final PatientService INSTANCE = new PatientService();
    }

    public static PatientService getInstance(){
        return Holder.INSTANCE;
    }

    /* Service methods */

    public List<Patient> getAllPatients(){
        return patientDao.findAll();
    }

    public void createPatient(Patient patient) {
        patientDao.create(patient);
    }

    public Patient getPatientById(int id) {
        return patientDao.find(id);
    }
}