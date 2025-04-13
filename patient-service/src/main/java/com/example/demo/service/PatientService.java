package com.example.demo.service;


import com.example.demo.dtos.PatientRequestDTO;
import com.example.demo.dtos.PatientResponseDTO;
import com.example.demo.exception.EmailAlreadyExistsException;
import com.example.demo.exception.PatientNotFoundException;
import com.example.demo.grpc.BillingServiceGrpcClient;
import com.example.demo.kafka.KafkaProducer;
import com.example.demo.mapper.PatientMapper;
import com.example.demo.model.Patient;
import com.example.demo.repositary.PatientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class PatientService {


    private static final Logger log = LoggerFactory.getLogger(PatientService.class);
    private final PatientRepository patientRepository;
    private final BillingServiceGrpcClient billingServiceGrpcClient;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final KafkaProducer kafkaProducer;


    public PatientService(PatientRepository patientRepository , BillingServiceGrpcClient billingServiceGrpcClient, NamedParameterJdbcTemplate namedParameterJdbcTemplate, KafkaProducer kafkaProducer) {

        this.patientRepository = patientRepository;
        this.billingServiceGrpcClient = billingServiceGrpcClient;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.kafkaProducer = kafkaProducer;
    }


    public List<PatientResponseDTO> getPatients(){
       List<Patient> patients = patientRepository.findAll();
       return patients.stream().map(PatientMapper::toDTO).toList();
    }


    public PatientResponseDTO addPatient(PatientRequestDTO patientRequestDTO){
        if (patientRepository.existsByEmail(patientRequestDTO.getEmail())){
            throw new EmailAlreadyExistsException("Email already exists"
            + " with email: " + patientRequestDTO.getEmail());
        }
        Patient newPatient = patientRepository.save(PatientMapper.toEntity(patientRequestDTO));


        log.info("Creating billing account for new patient: {}", newPatient.getName());


        billingServiceGrpcClient.createBillingAccount(
                newPatient.getName(),
                newPatient.getId().toString(),
                newPatient.getEmail()
        );

        kafkaProducer.sendEvent(newPatient);




        return PatientMapper.toDTO(newPatient);
    }

    public PatientResponseDTO  updatePatient(UUID uuid,
                                             PatientRequestDTO patientRequestDTO) {

         Patient patient = patientRepository.findById(uuid).orElseThrow(() ->
                new PatientNotFoundException("Patient not found with id: " + uuid));

        if (patientRepository.existsByEmailAndIdNot(patientRequestDTO.getEmail(),uuid)) {
            throw new EmailAlreadyExistsException("Email already exists"
                    + " with email: " + patientRequestDTO.getEmail());
        }



         patient.setName(patientRequestDTO.getName());
         patient.setEmail(patientRequestDTO.getEmail());
         patient.setAddress(patientRequestDTO.getAddress());
         patient.setDateOfBirth(LocalDate.parse(patientRequestDTO.getDateOfBirth()));


         Patient updatedPatient = patientRepository.save(patient);

            return PatientMapper.toDTO(updatedPatient);



    }



    public void deletePatient(UUID uuid){

        if(!patientRepository.existsById(uuid)){
            throw new PatientNotFoundException("Patient not found with id: " + uuid);
        }

        patientRepository.deleteById(uuid);


    }




}
