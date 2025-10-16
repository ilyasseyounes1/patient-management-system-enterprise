package org.ilyasse.patientservice.service;

import org.ilyasse.patientservice.dto.PatientRequestDTO;
import org.ilyasse.patientservice.dto.PatientResponseDTO;
import org.ilyasse.patientservice.exception.EmailAlreadyExistsException;
import org.ilyasse.patientservice.exception.PatientNotFoundException;
import org.ilyasse.patientservice.grpc.BillingServiceGrpcClient;
import org.ilyasse.patientservice.kafka.KafkaProducer;
import org.ilyasse.patientservice.mapper.PatientMapper;
import org.ilyasse.patientservice.model.Patient;
import org.ilyasse.patientservice.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class PatientService{
    private final PatientRepository patientRepository;
    private final BillingServiceGrpcClient billingServiceGrpcClient;
    private final KafkaProducer kafkaProducer;
    public PatientService( PatientRepository patientRepository ,
                           BillingServiceGrpcClient billingServiceGrpcClient,
                           KafkaProducer kafkaProducer
                           ) {
        this.patientRepository = patientRepository;
        this.billingServiceGrpcClient = billingServiceGrpcClient;
        this.kafkaProducer = kafkaProducer;
    }


    // method to get all patient *************************************************************
    public List<PatientResponseDTO> getPatients() {
        List<Patient> patients = patientRepository.findAll();

        List<PatientResponseDTO> patientResponseDTOs =
                patients.stream ().map( PatientMapper::toDTO ).toList();
        return patientResponseDTOs;
    }

    // method for patient creation***************************************************************
    public PatientResponseDTO createPatient(PatientRequestDTO patientRequestDTO) {
        if (patientRepository.existsByEmail ( patientRequestDTO.getEmail () )){
            throw new EmailAlreadyExistsException ("A patient with this email");
        }
        Patient newPatient = patientRepository.save( PatientMapper.toModel (patientRequestDTO));

        billingServiceGrpcClient.createBillingAccount (newPatient.getId ().toString () ,
                newPatient.getName () , newPatient.getEmail ());

        kafkaProducer.sendEvent(newPatient);


        return PatientMapper.toDTO (newPatient);
    }
    // method for update a patient*******************************************************************
    public PatientResponseDTO updatePatient( UUID id, PatientRequestDTO patientRequestDTO) {
        Patient patient = patientRepository.findById ( id ).orElseThrow (
                ()-> new PatientNotFoundException ("Patient not founed with ID : "+ id));

        if (patientRepository.existsByEmailAndIdNot ( patientRequestDTO.getEmail () , id)){
            throw new EmailAlreadyExistsException ("A patient with this email" + "Already exists"+
                     patientRequestDTO.getEmail ());
        }
        patient.setName ( patientRequestDTO.getName () );
        patient.setAddress ( patientRequestDTO.getAddress () );
        patient.setEmail ( patientRequestDTO.getEmail () );
        patient.setDateOfBirth ( LocalDate.parse(patientRequestDTO.getDateOfBirth () ) );

        return PatientMapper.toDTO (patientRepository.save(patient));
    }
    // method to delete by id : ***********************************************************************
    public void deletePatient(UUID id) {
        patientRepository.deleteById ( id );
    }

}