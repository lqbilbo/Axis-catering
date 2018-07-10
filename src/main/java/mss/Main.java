package mss;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.S3ClientOptions;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;

import java.io.*;

public class Main {

    static AmazonS3Client mssClient;

    public static AmazonS3 CreateAmazonS3Conn(String accessKey, String secretKey, String hostname) {
        AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);

        //生成云存储api client
        mssClient = new AmazonS3Client(credentials);

        //配置云存储服务地址
        mssClient.setEndpoint(hostname);

        //设置客户端生成的http请求host格式，目前只支持path type的格式，不支持bucket域名的格式
        S3ClientOptions s3ClientOptions = new S3ClientOptions();
        s3ClientOptions.setPathStyleAccess(true);
        mssClient.setS3ClientOptions(s3ClientOptions);
        return mssClient;
    }

    /**
     * 创建Bucket
     * @param bucketName
     */
    public void createBucketIfNotExistExample(String bucketName) {
        try {
            //判断待创建的bucket是否存在，如果存在不用重复创建，重复创建同名bucket服务器端会返回错误
            if (mssClient.doesBucketExist(bucketName) == false) {
                mssClient.createBucket(bucketName);
            }
        } catch (AmazonServiceException ase) {
            //存储服务端处理异常
            System.out.println("Caught an ServiceException.");
            System.out.println("Error Message:  " + ase.getMessage());
            System.out.println("HTTP Status Code:   " + ase.getStatusCode());
            System.out.println("Error Code: " + ase.getErrorCode());
            System.out.println("Error Type: " + ase.getErrorType());
            System.out.println("Request ID: " + ase.getRequestId());
        } catch (AmazonClientException ace) {
            //客户端处理异常
            System.out.println("Caught an ClientException.");
            System.out.println("Error Message:  " + ace.getMessage());
        }
    }

    /**
     * 上传Object
     * @param bucketName
     * @param objectName
     * @param content
     */
    public void putObjectExample(String bucketName, String objectName, String content) {
        try {
            //bucketName指定上传文件所在的桶名
            //objectName指定上传的文件名
            //content指定上传的文件内容
            mssClient.putObject(bucketName,objectName,new ByteArrayInputStream(content.getBytes()), null);
        } catch (AmazonServiceException ase) {
            //存储服务端处理异常
            System.out.println("Caught an ServiceException.");
            System.out.println("Error Message:    " + ase.getMessage());
            System.out.println("HTTP Status Code: " + ase.getStatusCode());
            System.out.println("Error Code:   " + ase.getErrorCode());
            System.out.println("Error Type:       " + ase.getErrorType());
            System.out.println("Request ID:       " + ase.getRequestId());
        }catch (AmazonClientException ace) {
            //客户端处理异常
            System.out.println("Caught an ClientException.");
            System.out.println("Error Message: " + ace.getMessage());
        }
    }

    public void getObjectExample(String bucketName, String objectName) throws IOException {
        try {
            //bucketName是桶名
            //objectName是文件名
            S3Object s3Object = mssClient.getObject(new GetObjectRequest(bucketName,objectName));
            InputStream content = s3Object.getObjectContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(content));
            if (content != null) {
                while (true) {
                    String line = reader.readLine();
                    if (line == null) break;
                    System.out.println("\n" + line);
                }
                //获取object后需要close()，释放连接
                s3Object.close();
            }
        } catch (AmazonServiceException ase) {
            //存储服务端处理异常
            System.out.println("Caught an ServiceException.");
            System.out.println("Error Message:    " + ase.getMessage());
            System.out.println("HTTP Status Code: " + ase.getStatusCode());
            System.out.println("Error Code:   " + ase.getErrorCode());
            System.out.println("Error Type:       " + ase.getErrorType());
            System.out.println("Request ID:       " + ase.getRequestId());
        }catch (AmazonClientException ace) {
            //客户端处理异常
            System.out.println("Caught an ClientException.");
            System.out.println("Error Message: " + ace.getMessage());
        }
    }
}
