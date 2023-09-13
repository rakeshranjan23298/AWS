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

import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

//import com.amazonaws.AmazonServiceException;
//import com.amazonaws.services.s3.model.ObjectMetadata;


public class s3Edit {

	public static void main(String[] args) throws ClassNotFoundException, IOException {
		

	     final String USAGE = "\n" +"Usage:\n" +"<bucketName> \n\n" +"Where:\n" +"bucketName - the Amazon S3 bucket from which objects are read. \n\n" ;

	     	AwsCredentials c=new AwsCredentials() 
	     	{
				
				@Override
				public String secretAccessKey() 
				{
				
					return "b8Htq45amA/q0lAaYnL+fpS7VjiFV0zA6Dr30RY2";
				}
				@Override
				public String accessKeyId() 
				{
					return "AKIA4S5LBAFXAJHMJDG5";
				}
			};
			
			AwsCredentialsProvider crd = new AwsCredentialsProvider() 
			{				
				@Override
				public AwsCredentials resolveCredentials() 
				{
					
					return c;
				}
			};
			
			final String directory = LocalDate.now().toString();	//return Date
	        String bucketName = "testbuckettoss";
	        String folderName=directory;
	        String fileName="filename.txt";
	        String filePath="D:\\Img\\"+fileName;
	        Region region = Region.US_WEST_2;
	        
	        dataRead();
	        String key=folderName +"/" +fileName;
 
	        S3Client s3 = S3Client.builder()
	        		.region(region)
	        		.credentialsProvider(crd)
	        		.build();
	        		
	       
	       
	        

//        	putObject(por, RequestBody.fromFile(new File(filePath)));
	        
	        PutObjectRequest por = PutObjectRequest.builder()
            .bucket(bucketName)
            .key(key)
            .build();
	        
	        

//CreateFolder on Date 
//	        final String directory = LocalDate.now().toString();

//	        String path ="D:'\'";  
//	        path = path+directory;  
//	        File f1 = new File(path);  
//	        boolean bool = f1.mkdir();  
	      
//Create Folder
//	        
//	         
//	        String key1 = folderName;
//	        
//	        
//	        PutObjectRequest putRequest = PutObjectRequest.builder()
//	                .bucket(bucketName)
//	                .key(key1)
//	                .acl("public-read")
//	                .build();
//	        s3.putObject(putRequest, RequestBody.empty());
	        
	        
	        
//		***	        
	        
	        
	        
	        File f = new File(filePath);
	        try {
	        	s3.putObject(por, RequestBody.fromFile(f));
	        	

	        } catch (Exception e) {
	            System.err.println(e.getLocalizedMessage());
	            System.exit(1);
	        }
	        
	        s3.close();		

	}
	

	
	
	
// DatabSe Operation
	public static void dataRead() throws IOException, ClassNotFoundException 
 	{
    	 LinkedList<String> li=new LinkedList<String>();     
    	 
 	            try {                                                          
 	                    Class.forName("com.mysql.jdbc.Driver");   
 	                    Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/asterisk", "root", "123456");                                 
 	                    Statement st = con.createStatement();
 	                   
 	                    
 	                    
// WRITE Data Into File
	                        
	                       File file = new File("D:\\Img\\filename.txt");

	                      // if file doesnt exists, then create it
	                      if (!file.exists()) 
	                      {
	                          file.createNewFile();
	                      }
	                        
	                      FileWriter fw=new FileWriter(file.getAbsoluteFile());
	                      BufferedWriter bw = new BufferedWriter(fw);
	                      ResultSet rs = st.executeQuery("select * from products_master");
	                      
// 	                    
 	                   
 	                    int ii=1, ie, count=3,j=0;
 	                    
 	                    System.out.println("data in Product Master");
 	                    
 	                    while(rs.next())       
 	                    {
 	                        int id = rs.getInt("id");  
 	                        ie=id;
 	                        String p = rs.getString("product");
 	                        String pe = rs.getString("product_enabled");
 	                        String ss = rs.getString("sku");
 	                        double cs = rs.getDouble("cost");
 	                        double w = rs.getDouble("weight");
 	                        double pl = rs.getDouble("p_length");
 	                        double pw = rs.getDouble("p_width");
 	                        double ph = rs.getDouble("p_height");
 	                        double t = rs.getDouble("tax");   
 	                        
 	                        bw.append("\n product name="+p+" \n IsEnabled or not ="+pe+" \n SKU= "+ss+" \n cost= "+cs+" \n weight ="+w+" \n product Length "+pl+"	\n product Weight= "+pw+"\n  product Height= "+ph+" \n Tax="+t+" \n \n");
 	                        
 	                        li.add(p);

 	                    }           
 	                   bw.close();
 	                   rs.close();
 	                   con.close();   
 	                }              
 	                catch (SQLException e) 
 	                {
 	                   System.out.println("Exception caught"); 
 	                }
 	            
 	            System.out.println(" object in list");
 	            
 	           
 	          
   
 	    }
     
	
	
	
	
	
	
	
	
	
	
	
	
	


}
