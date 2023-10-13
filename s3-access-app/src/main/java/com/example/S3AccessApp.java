import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.ListObjectsRequest;
import software.amazon.awssdk.services.s3.model.ListObjectsResponse;

@SpringBootApplication(scanBasePackages = "com.example")
public class S3AccessApp {

    public static void main(String[] args) {
        SpringApplication.run(S3AccessApp.class, args);

        // S3 클라이언트 생성
        S3Client s3Client = S3Client.builder().build();

        // S3 버킷의 객체 나열
        ListObjectsRequest listObjectsRequest = ListObjectsRequest.builder()
                .bucket("bucket-name")
                .build();

        ListObjectsResponse listObjectsResponse = s3Client.listObjects(listObjectsRequest);

        listObjectsResponse.contents().forEach(object -> {
            System.out.println("S3 Object: " + object.key());
        });

        // S3 클라이언트 종료
        s3Client.close();
    }
}

