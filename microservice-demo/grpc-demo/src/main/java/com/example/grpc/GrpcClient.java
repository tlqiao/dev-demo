package com.example.grpc;

import com.example.grpc.gencode.HelloRequest;
import com.example.grpc.gencode.HelloResponse;
import com.example.grpc.gencode.HelloServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class GrpcClient {

  public static void main(String args[]) {
    ManagedChannel managedChannel = null;
    try {
      managedChannel = ManagedChannelBuilder.forAddress("localhost", 5051).usePlaintext(true).build();
      HelloServiceGrpc.HelloServiceBlockingStub stub = HelloServiceGrpc.newBlockingStub(managedChannel);
      HelloResponse helloResponse = stub.hello(
          HelloRequest.newBuilder().setFirstName("taoli").setLastName("qiao").build());
      System.out.println(helloResponse.getGreeting());
    } catch (Exception e) {
      System.out.println(e.toString());
    }
  }

}
