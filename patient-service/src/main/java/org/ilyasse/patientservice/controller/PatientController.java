package org.ilyasse.patientservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.groups.Default;
import org.ilyasse.patientservice.dto.PatientRequestDTO;
import org.ilyasse.patientservice.dto.PatientResponseDTO;
import org.ilyasse.patientservice.dto.validators.CreatePatientValidationGroup;
import org.ilyasse.patientservice.service.PatientService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/patients")
@Tag ( name = "Patient" , description = "API of management of patients")
public class PatientController{
    private PatientService patientService;

    public PatientController ( PatientService patientService) {
        this.patientService = patientService;
    }
    // Get all patient methode :
    @GetMapping
    @Operation(summary = "Get all patients")
    public ResponseEntity<List<PatientResponseDTO>> getPatient(){
        List<PatientResponseDTO> patientResponseDTOList = patientService.getPatients();
        return ResponseEntity.ok().body(patientResponseDTOList);
    }
    // Create a patient
    @PostMapping
    @Operation(summary = "Create a patient")
    public ResponseEntity<PatientResponseDTO> createPatient(
                        @Validated({Default.class , CreatePatientValidationGroup.class}) @RequestBody PatientRequestDTO patientRequestDTO){
        PatientResponseDTO patientResponseDTO = patientService.createPatient(patientRequestDTO);
         return ResponseEntity.ok().body(patientResponseDTO);
    }
    @PutMapping("/{id}")
    @Operation(summary = "Update a patient")
    public ResponseEntity<PatientResponseDTO> updatePatient(@PathVariable UUID id ,
                        @Validated({ Default.class}) @RequestBody PatientRequestDTO patientRequestDTO){
        /* this @validated tell controller to use default validation in DTO class */
        PatientResponseDTO patientResponseDTO = patientService.updatePatient ( id ,patientRequestDTO);
        return ResponseEntity.ok().body(patientResponseDTO);
    }
    // Delete a Patient
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a patient")
    public ResponseEntity<Void> deletePatient(@PathVariable UUID id){
         patientService.deletePatient ( id );
         return ResponseEntity.noContent().build();
    }



}
