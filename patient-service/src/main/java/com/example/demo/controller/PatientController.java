package com.example.demo.controller;


import com.example.demo.dtos.validaters.CreatePatientValidationGroup;
import com.example.demo.dtos.PatientRequestDTO;
import com.example.demo.dtos.PatientResponseDTO;
import com.example.demo.service.PatientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.groups.Default;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/patients")
@Tag(name = "Patient API", description = "API for managing Patients")
public class PatientController {


    private final PatientService patientService;


    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }



    @GetMapping
    @Operation(summary = "Get all patients", description = "Retrieve a list of all patients")
    public ResponseEntity<List<PatientResponseDTO>> getAllPatients() {
        List<PatientResponseDTO> patients = patientService.getPatients();
        return ResponseEntity.ok(patients);
    }

    @PostMapping
    @Operation(summary = "Add a new patient", description = "Create a new patient with the provided details")
    public ResponseEntity<PatientResponseDTO> addPatient(@Validated({Default.class, CreatePatientValidationGroup.class}) @RequestBody PatientRequestDTO patientRequestDTO) {
        PatientResponseDTO newPatient = patientService.addPatient(patientRequestDTO);
        return ResponseEntity.ok().body(newPatient);


    }

    @PutMapping("/{uuid}")
    @Operation(summary = "Update an existing patient", description = "Update the details of an existing patient")
    public ResponseEntity<PatientResponseDTO> updatePatient(@PathVariable UUID uuid,
                                                             @Validated({Default.class}) @RequestBody PatientRequestDTO patientRequestDTO) {
        PatientResponseDTO updatedPatient = patientService.updatePatient(uuid, patientRequestDTO);
        return ResponseEntity.ok().body(updatedPatient);
    }

    @DeleteMapping("/{uuid}")
    @Operation(summary = "Delete a patient", description = "Delete a patient by their unique identifier")
    public ResponseEntity<Void> deletePatient(@PathVariable UUID uuid) {
        patientService.deletePatient(uuid);
        return ResponseEntity.noContent().build();
    }






}
