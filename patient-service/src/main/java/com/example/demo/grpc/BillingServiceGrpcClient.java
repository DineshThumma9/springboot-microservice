package com.example.demo.grpc;


import billing.BillingRequest;
import billing.BillingResponse;
import billing.BillingServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class BillingServiceGrpcClient {

    private static final Logger log = LoggerFactory.getLogger(BillingServiceGrpcClient.class);
    private final BillingServiceGrpc.BillingServiceBlockingStub blockingStub;


    public BillingServiceGrpcClient(
            @Value("${billing.service.address:localhost}") String serviceAddress,
            @Value("${billing.service.grpc.port:9001}") int servicePort) {

        log.info("Creating gRPC client for Billing Service at {}:{}", serviceAddress, servicePort);
        ManagedChannel channel = ManagedChannelBuilder
                .forAddress(serviceAddress, servicePort)
                .usePlaintext()
                .build();

        blockingStub = BillingServiceGrpc.newBlockingStub(channel);
    }


    public BillingResponse createBillingAccount(String name , String patientId, String email) {
        log.info("Creating billing account for patient ID: {}", patientId);
        BillingRequest request = BillingRequest.newBuilder()
                .setPatientId(patientId)
                .setName(name)
                .setEmail(email)
                .build();

        BillingResponse response = blockingStub.createBillingAccount(request);
        log.info("Received response: {}", response);

        if (response.getStatus().isEmpty()) {
            log.error("Failed to create billing account for patient ID: {}", patientId);
            throw new RuntimeException("Failed to create billing account");
        }
        return response;
    }


}
