# HTTP to Amazon S3 Direct Uploader 

This is a Java library that reads from HTTP and directly writes to Amazon S3 using Java Piped streams.
 
## QuickStart

Let's start with a sample code below:
        
        String url = "http://www.sample-videos.com/video/mp4/360/big_buck_bunny_360p_1mb.mp4";
        String bucketName = "mybucket";
        String savePath = "test/bunny.mp4";
        
        HttpToS3.upload(url, bucketName, savePath);


If you would like to be more verbose, see the samples folder and HttptoS3 class for details. 