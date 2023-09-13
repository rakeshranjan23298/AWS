package awss3;

import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.ListObjectsRequest;
import software.amazon.awssdk.services.s3.model.ListObjectsResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;
import software.amazon.awssdk.services.s3.model.S3Object;
import software.amazon.awssdk.services.s3.waiters.S3Waiter;

import java.io.File;
import java.nio.file.Path;
import java.util.List;
import java.util.ListIterator;

import com.amazonaws.AmazonServiceException;


public class s3 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	     final String USAGE = "\n" +"Usage:\n" +"<bucketName> \n\n" +"Where:\n" +"bucketName - the Amazon S3 bucket from which objects are read. \n\n" ;

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
			
//			
	        String bucketName = "testbuckettoss";
	        String folderName="Programing";
	        String fileName="img7.jpg";
	        String filePath="D:\\Img\\"+fileName;
	        Region region = Region.US_WEST_2;
	        
	        String key=folderName +"/" +fileName;
	        

	        
	        S3Client s3 = S3Client.builder()
	        		.region(region)
	        		.credentialsProvider(crd)
	        		.build();
	        
//	        PutObjectRequest request=PutObjectRequest.builder()
//	        		.bucket(bucketName)
//	        		.key(key)
//	        		.build();
	        	
//	        
//	        
//	        S3Waiter waiter=s3.waiter();
//	        HeadBucketRequest waitRequest = HeadBucketRequest.builder()
//	        		.bucket(bucketName)
//	        		.build();
	        		
	        
//	        listBucketObjects(s3, bucketName);
	        
	        PutObjectRequest por = PutObjectRequest.builder()
            .bucket(bucketName)
            .key(key)
            .build();
	        
	        File f = new File(filePath);
	        try {
	        	s3.putObject(por, RequestBody.fromFile(f));
	        	
//	        	putObject(por, RequestBody.fromFile(new File(filePath)));
	        } catch (AmazonServiceException e) {
	            System.err.println(e.getErrorMessage());
	            System.exit(1);
	        }
	        
	        s3.close();		

	}
	
	
	
	
	

	
	
	
	//List Bucket
    public static void listBucketObjects(S3Client s3, String bucketName ) 
    {

        try {
             ListObjectsRequest listObjects = ListObjectsRequest
                     .builder()
                     .bucket(bucketName)
                     .build();

             ListObjectsResponse res = s3.listObjects(listObjects);
             List<S3Object> objects = res.contents();

             for (ListIterator iterVals = objects.listIterator(); iterVals.hasNext(); ) 
             {
                 S3Object myValue = (S3Object) iterVals.next();
                 System.out.print("\n The name of the key is " + myValue.key());
                 System.out.print("\n The object is " + calKb(myValue.size()) + " KBs");
                 System.out.print("\n The owner is " + myValue.owner());

              }

         } catch (S3Exception e) {
             System.err.println(e.awsErrorDetails().errorMessage());
             System.exit(1);
         }
     }
     //convert bytes to kbs
     private static long calKb(Long val) {
         return val/1024;
     }	

}
