package com.fikrimuhal.httptos3.writers;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.fikrimuhal.httptos3.downloaders.HttpDownloader;
import org.apache.commons.io.IOUtils;

import java.io.FileNotFoundException;
import java.io.PipedInputStream;
import java.util.logging.Logger;

/**
 * Writer for writing the input data to a S3.
 *
 */
public class S3Writer extends AbstractWriter {
    private static final Logger logger = Logger.getLogger(HttpDownloader.class.getName());
    private final AmazonS3Client amazonS3Client;
    private final int contentLength;
    private final String bucketName;
    private final String uploadKey;

    /**
     * Constructor for S3Writer.
     * @param in PipedInputStream to read the bytes from.
     * @param contentLength Total content length of the file to be written
     * @param bucketName S3 Bucket Name to write on.
     * @param uploadKey S3 Bucket path (key) to write the file at. Example: test/file1.mp4
     * @throws FileNotFoundException
     */
    public S3Writer(PipedInputStream in, int contentLength, String bucketName, String uploadKey)
            throws FileNotFoundException {
        this.in = in;
        this.contentLength = contentLength;
        this.bucketName = bucketName;
        this.uploadKey = uploadKey;
        this.amazonS3Client = new AmazonS3Client(new ProfileCredentialsProvider());
    }

    public void run() {
        try {
            ObjectMetadata metaData = new ObjectMetadata();
            if (contentLength != -1) {
                metaData.setContentLength(contentLength);
            }
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, uploadKey, in, metaData);
            PutObjectResult putObjectResult = amazonS3Client.putObject(putObjectRequest);
            logger.info("File saved to bucket " + bucketName + " on path " + uploadKey);
            logger.info("MD5 = " + putObjectResult.getContentMd5());
        } catch (AmazonServiceException ase) {
            logger.severe("Error Message:    " + ase.getMessage());
            logger.severe("HTTP Status Code: " + ase.getStatusCode());
            logger.severe("AWS Error Code:   " + ase.getErrorCode());
            logger.severe("Error Type:       " + ase.getErrorType());
            logger.severe("Request ID:       " + ase.getRequestId());
        } catch (AmazonClientException ace) {
            logger.severe("Error Message: " + ace.getMessage());
        } finally {
            IOUtils.closeQuietly(in);
        }

    }
}
