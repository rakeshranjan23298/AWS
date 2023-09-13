package awss3;

import java.io.File;
import java.nio.file.Paths;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

public class PutObject {

	public static void main(String[] args) 
	{
	
		final String USAGE = "\n" +
                "To run this example, supply the name of an S3 bucket and a file to\n" +
                "upload to it.\n" +
                "\n" +
                "Ex: PutObject <bucketname> <filename>\n";

        if (args.length < 2) 
        {
            System.out.println(USAGE);
            System.exit(1);
        }
        
        
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
		
		
		
        
        Region region = Region.US_WEST_2;
        S3Client client = S3Client.builder()
        		.region(region)			
        		.credentialsProvider(crd)
        		.build();
        
        
        

        String bucket_name = "testbuckettoss";
        String file_path = "D:\\Img\\img1.jpg";
        String key_name = Paths.get(file_path).getFileName().toString();

        System.out.format("Uploading %s to S3 bucket %s...\n", file_path, bucket_name);
        final AmazonS3 s3 = AmazonS3ClientBuilder.standard().withRegion(Regions.DEFAULT_REGION).build();
        try {
            s3.putObject(bucket_name, key_name, new File(file_path));
        } catch (AmazonServiceException e) {
            System.err.println(e.getErrorMessage());
            System.exit(1);
        }
        System.out.println("Done!");
	}

}
