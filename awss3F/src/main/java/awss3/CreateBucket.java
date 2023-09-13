package awss3;

import software.amazon.awssdk.core.waiters.WaiterResponse;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.CreateBucketRequest;
import software.amazon.awssdk.services.s3.model.HeadBucketRequest;
import software.amazon.awssdk.services.s3.model.HeadBucketResponse;
import software.amazon.awssdk.services.s3.model.S3Exception;
import software.amazon.awssdk.services.s3.waiters.S3Waiter;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;

import java.net.URISyntaxException;
import java.net.URISyntaxException;

public class CreateBucket {

	public static void main(String[] args) throws URISyntaxException
	{
		// TODO Auto-generated method stub
		
		AwsCredentials c=new AwsCredentials() 
     	{
			
			@Override
			public String secretAccessKey() 
			{
				// TODO Auto-generated method stub
				return "b8Htq45amA/q0lAaYnL+fpS7VjiFV0zA6Dr30RY2";
			}
			@Override
			public String accessKeyId() 
			{
				// TODO Auto-generated method stub
				return "AKIA4S5LBAFXAJHMJDG5";
			}
		};
		
		AwsCredentialsProvider crd = new AwsCredentialsProvider() 
		{				
			@Override
			public AwsCredentials resolveCredentials() 
			{
				// TODO Auto-generated method stub
				return c;
			}
		};
		
//		  final String USAGE = "\n" +
//	                "Usage:\n" +
//	                "    <bucketName> \n\n" +
//	                "Where:\n" +
//	                "    bucketName - the name of the bucket to create. The bucket name must be unique, or an error occurs.\n\n" ;
		 final String USAGE = "\n" +"Usage:\n" +"<bucketName> \n\n" +"Where:\n" +"bucketName - the Amazon S3 bucket from which objects are read. \n\n" ;


	        if (args.length != 1) {
	            System.out.println(USAGE);
	            System.exit(1);
	       }

	        String bucketName = args[0];
	        System.out.format("Creating a bucket named %s\n",
	                bucketName);

	        Region region = Region.US_WEST_2;
	        S3Client s3 = S3Client.builder()
	                .region(region)
	                .build();

	        createBucket (s3, bucketName);
	        s3.close();
	}
	
	public static void createBucket( S3Client s3Client, String bucketName) 
	{

        try {
            S3Waiter s3Waiter = s3Client.waiter();
            CreateBucketRequest bucketRequest = CreateBucketRequest.builder()
                    .bucket(bucketName)
                    .build();

            s3Client.createBucket(bucketRequest);
            HeadBucketRequest bucketRequestWait = HeadBucketRequest.builder()
                    .bucket(bucketName)
                    .build();

            // Wait until the bucket is created and print out the response.
            WaiterResponse<HeadBucketResponse> waiterResponse = s3Waiter.waitUntilBucketExists(bucketRequestWait);
            waiterResponse.matched().response().ifPresent(System.out::println);
            System.out.println(bucketName +" is ready");

        } catch (S3Exception e) {
            System.err.println(e.awsErrorDetails().errorMessage());
            System.exit(1);
        }
    }

}
