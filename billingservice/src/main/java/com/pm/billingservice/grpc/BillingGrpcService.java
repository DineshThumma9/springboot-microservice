package com.pm.billingservice.grpc;


import billing.BillingResponse;
import billing.BillingServiceGrpc.BillingServiceImplBase;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@GrpcService
public class BillingGrpcService extends BillingServiceImplBase {

    private static final Logger log = LoggerFactory.getLogger(BillingGrpcService.class);

    @Override
    public void createBillingAccount(
            billing.BillingRequest request,
            StreamObserver<BillingResponse> responseObserver) {


        log.info("Received request to create billing account: {}", request.toString());

        BillingResponse response = BillingResponse.newBuilder()
                .setPatientId(request.getPatientId())
                .setStatus("Billing account created successfully")
                .build();



        responseObserver.onNext(response);
        responseObserver.onCompleted();





    }


}