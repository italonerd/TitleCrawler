/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package titlecrawler;

import com.threads.DownloaderThread;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author italo
 */
public class TitleCrawler {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        List<String> urlsFound;
        
        //Check if argument is null, if not tries to extract the urls from the file
        if(args.length == 0){
           System.out.println("This program requires a file path as arguent.");
           urlsFound = urlSamples();
       } else{
           urlsFound = extractUrlsFromFile(args[0]);  
        }

        int numUrlFound = urlsFound.size();
	
        ExecutorService threadPool = Executors.newCachedThreadPool();
        DownloaderThread downloadedUrls[] = new DownloaderThread[numUrlFound];
        Future futureDownloadedUrls[] = new Future[numUrlFound];
        
        //Set up and start the threads
        for (int i = 0; i < numUrlFound; i++) {
            String currentUrl = urlsFound.get(i);
            //Remove the white Spaces
            downloadedUrls[i] = new DownloaderThread(currentUrl);
            futureDownloadedUrls[i] = threadPool.submit(downloadedUrls[i]);
        }
        
	List<WebPage> downloadedWebPages = new ArrayList<>();
        
        //Wait for the threads to download
        for (int i = 0; i < numUrlFound; i++) {
            try {
                downloadedWebPages.add((WebPage) futureDownloadedUrls[i].get());
            } catch (InterruptedException | ExecutionException e) {
                Logger.getLogger(TitleCrawler.class.getName()).log(Level.SEVERE, null, e);
            }
        }
        
        //Iterate the downloadedPages and print the result
        displayWebPageData(downloadedWebPages);
                
        threadPool.shutdown();
    }
      
    //Extract the urls from the File
    public static List<String> extractUrlsFromFile(String path){
        List<String> urlsFound = new ArrayList<>();
        BufferedReader fileBufferReader;
        String nextline = null;
        
        try {
            fileBufferReader = new BufferedReader(new FileReader(path));
            nextline = fileBufferReader.readLine();
            while(nextline != null )
            {
                if(!nextline.trim().isEmpty()){
                   urlsFound.add(nextline);
                }
                nextline = fileBufferReader.readLine();
            }
        } catch(IOException e) { 
            throw new IllegalArgumentException(e); 
        }

        return urlsFound;
    }
    
    //Url Samples
    public static List<String> urlSamples(){
        System.out.println("Using sample urls.");
        List<String> urlSamples = new ArrayList<>();
        
        urlSamples.add("https://www.google.com/");
        urlSamples.add("https://www.wikipedia.org/");
        urlSamples.add("https://www.face book. com/");
        urlSamples.add("http://www.youtube.com/");
        urlSamples.add("http://www.notFoundPage404.com/");
        urlSamples.add("https://mail.google.com/");
        
        return urlSamples;
    }
    
    //Print the data of the downloaded Urls
    public static void displayWebPageData(List<WebPage> webPages){
        for (WebPage webPage : webPages) {
            String titleFound = "FAILED";
            String downloadTime = "FAILED";
            
            if(webPage.getTitle() != null){
                titleFound = webPage.getTitle();
            }
            if(webPage.getDownloadTime() != null){
                downloadTime = webPage.getDownloadTime();
            }
            
            System.out.println(titleFound + " : " + webPage.getOriginalUrl() + " : " + downloadTime);
        }
    }
}
