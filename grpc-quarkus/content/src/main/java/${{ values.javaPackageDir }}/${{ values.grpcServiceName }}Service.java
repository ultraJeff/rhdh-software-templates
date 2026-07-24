package ${{ values.javaPackage }};

import io.grpc.stub.StreamObserver;
import io.quarkus.grpc.GrpcService;

@GrpcService
public class ${{ values.grpcServiceName }}Service extends ${{ values.grpcServiceName }}Grpc.${{ values.grpcServiceName }}ImplBase {

    @Override
    public void sayHello(HelloRequest request, StreamObserver<HelloReply> responseObserver) {
        String name = request.getName();
        String message = "Hello " + name;
        HelloReply reply = HelloReply.newBuilder()
                .setMessage(message)
                .build();
        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }
}
