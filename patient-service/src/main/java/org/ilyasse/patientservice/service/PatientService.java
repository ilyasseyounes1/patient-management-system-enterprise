package org.ilyasse.patientservice.service;

import org.ilyasse.patientservice.dto.PatientRequestDTO;
import org.ilyasse.patientservice.dto.PatientResponseDTO;
import org.ilyasse.patientservice.exception.EmailAlreadyExistsException;
import org.ilyasse.patientservice.mapper.PatientMapper;
import org.ilyasse.patientservice.model.Patient;
import org.ilyasse.patientservice.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientService{
    private PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }
    public List<PatientResponseDTO> getPatients() {
        List<Patient> patients = patientRepository.findAll();

        List<PatientResponseDTO> patientResponseDTOs =
                patients.stream ().map( PatientMapper::toDTO ).toList();
        return patientResponseDTOs;
    }

    // method for patient creation
    public PatientResponseDTO createPatient(PatientRequestDTO patientRequestDTO) {
        if (patientRepository.existsByEmail ( patientRequestDTO.getEmail () )){
            throw new EmailAlreadyExistsException ("A patient with this email");
        }
        Patient newPatient = patientRepository.save(
                PatientMapper.toModel (patientRequestDTO));
        return PatientMapper.toDTO (newPatient);
    }
}