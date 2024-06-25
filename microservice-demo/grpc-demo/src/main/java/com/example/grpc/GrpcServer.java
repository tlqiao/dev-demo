package com.example.grpc;

import io.grpc.Server;
import io.grpc.ServerBuilder;

public class GrpcServer {

  public static void main(String[] args) {
    try {
      int port = 5051;
      final Server server = ServerBuilder.forPort(port)
          .addService(new HelloServiceImpl())
          .build()
          .start();
      System.out.println("server started,listen on port: " + port);
      server.awaitTermination();
    } catch (Exception e) {
      System.out.println(e.toString());
    }
  }

}
