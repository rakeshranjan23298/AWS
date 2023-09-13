package awss3;

import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.ListObjectsRequest;
import software.amazon.awssdk.services.s3.model.ListObjectsResponse;
import software.amazon.awssdk.services.s3.model.S3Exception;
import software.amazon.awssdk.services.s3.model.S3Object;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import com.amazonaws.s3.model.CreateBucketRequest;


public class ListBucket {

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
			
	        String bucketName = "abcbucketdemo"; // bucket name
	        
	        Region region = Region.US_WEST_2;
	        S3Client s3 = S3Client.builder()
	        		.region(region)			
	        		.credentialsProvider(crd)
	        		.build();
	        
	        
			
	        dataRead();			//to Read from LOCAL SERVER	
//	        listBucketObjects(s3, bucketName);
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
     
    
     
     
     
     
     
     // data Fetch From sql
     public static void dataRead() 
 	{
    	 LinkedList<String> li=new LinkedList<String>();     
    	 
 	            try {                                                          
 	                    Class.forName("com.mysql.jdbc.Driver");   
 	                    Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/asterisk", "root", "123456");                                 
 	                    Statement st = con.createStatement();
 	                    ResultSet rs = st.executeQuery("select * from products_master");
 	                    
 	                   
 	                    int ii=1, i, count=3;
 	                    
 	                    System.out.println("data in Product Master");
 	                    
 	                    while(rs.next())       
 	                    {
 	                        int id = rs.getInt("id");  
 	                        i=id;
 	                        String p = rs.getString("product");
 	                        String pe = rs.getString("product_enabled");
 	                        Integer ss = rs.getInt("sku");
 	                        double cs = rs.getDouble("cost");
 	                        double w = rs.getDouble("weight");
 	                        double pl = rs.getDouble("p_length");
 	                        double pw = rs.getDouble("p_width");
 	                        double ph = rs.getDouble("p_height");
 	                        double t = rs.getDouble("tax");   
 	                        
 	                        li.add(p);
 	                        
// 	                        System.out.println(p);  //productname
// 	                        System.out.println(pe); //isEnable
// 	                        System.out.println(ss);	//sku
// 	                        System.out.println(cs);	//cost
// 	                        System.out.println(w);	//weight
// 	                        System.out.println(pl);	//length
// 	                        System.out.println(pw);	//weidth	
// 	                        System.out.println(ph);	//height	
// 	                        System.out.println(t);	//tax

 	                    }           
 	                   
 	                    con.close();   
 	                }              

 	                catch (Exception e) 
 	                {
 	                   System.out.println("Exception caught"); 
 	                }
 	            
 	            System.out.println(" object in list");
 	            
 	           Iterator<String> itr=li.iterator();  
 	          while(itr.hasNext())
 	          {  
 	        	  System.out.println("inside lis=="+itr.next());  
 	          }  
   
 	    }
 	}
     


