package com.example.grpc;

import com.example.grpc.gencode.HelloRequest;
import com.example.grpc.gencode.HelloResponse;
import com.example.grpc.gencode.HelloServiceGrpc.HelloServiceImplBase;
import io.grpc.stub.StreamObserver;

public class HelloServiceImpl extends HelloServiceImplBase {

  @Override
  public void hello(HelloRequest helloRequest, StreamObserver<HelloResponse> responseStreamObserver) {
    String greeting = new StringBuilder()
        .append("Hello ")
        .append(helloRequest.getFirstName())
        .append(helloRequest.getLastName())
        .toString();
    HelloResponse helloResponse = HelloResponse.newBuilder().setGreeting(greeting)
        .build();
    responseStreamObserver.onNext(helloResponse);
    responseStreamObserver.onCompleted();
  }

}
