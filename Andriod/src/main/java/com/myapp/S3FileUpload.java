package com.myapp;

import android.os.AsyncTask;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.PutObjectRequest;

import java.io.File;
import java.util.List;

public class S3FileUpload extends AsyncTask<String,String,String> {
    private final String accessKey="xxxxxx";
    private final String secretKey="xxxxxxx";
    private final String bucketName="MyBucketName";
    @Override
    protected String doInBackground(String... params) {
        String fileName = params[0];
        String dir = params[1];

        System.out.println(fileName);
        System.out.println(dir);

        AmazonS3Client s3 = new AmazonS3Client(new BasicAWSCredentials(accessKey,secretKey));
        s3.setRegion(Region.getRegion(Regions.AP_SOUTH_1));
        /*List<Bucket> buckets=s3.listBuckets();
        for(Bucket bucket:buckets){
            Log.e("Bucket ","Name "+bucket.getName()+" Owner "+bucket.getOwner()+ " Date " + bucket.getCreationDate());
        }
        Log.e("Size ", "" + s3.listBuckets().size());
        */
        PutObjectRequest s3RecognitionImage = new PutObjectRequest(bucketName, fileName,  new File(dir+"/"+fileName));
        s3.putObject(s3RecognitionImage);

        return "Success";
    }
    protected void onPostExecute(String feed) {

        return ;
    }
}