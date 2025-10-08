package org.ilyasse.patientservice.controller;

import org.ilyasse.patientservice.dto.PatientRequestDTO;
import org.ilyasse.patientservice.dto.PatientResponseDTO;
import org.ilyasse.patientservice.service.PatientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/patients")
public class PatientController{
    private PatientService patientService;

    public PatientController ( PatientService patientService) {
        this.patientService = patientService;
    }
    // Get all patient methode :
    @GetMapping
    public ResponseEntity<List<PatientResponseDTO>> getPatient(){
        List<PatientResponseDTO> patientResponseDTOList = patientService.getPatients();
        return ResponseEntity.ok().body(patientResponseDTOList);
    }
    // Create a patient
    @PostMapping
    public ResponseEntity<PatientResponseDTO> createPatient(@RequestBody PatientRequestDTO patientRequestDTO){
        PatientResponseDTO patientResponseDTO = patientService.createPatient(patientRequestDTO);
         return ResponseEntity.ok().body(patientResponseDTO);
    }



}
