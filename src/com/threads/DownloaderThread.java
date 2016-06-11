/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.threads;

import titlecrawler.WebPage;
import com.helper.CommonUtils;
import java.net.UnknownHostException;
import java.sql.Timestamp;
import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 *
 * @author italo
 * Class responsible for download and populate the Web Page
 */
public class DownloaderThread implements Callable<WebPage> {
    private final WebPage webPage;
    
    public DownloaderThread(String originalUrl) {
        webPage = new WebPage(originalUrl);
    }
    
    //Check the status of the page and download the document and populate the Web Page
    @Override
    public WebPage call() {
        try {
            Connection connection = Jsoup.connect(webPage.getUrl());
            Connection.Response response = connection.execute();
            int statusCode = response.statusCode();
            
            if (statusCode == 200) {
                Timestamp startTime = CommonUtils.getTimestamp();
                Document document = connection.get();
                Timestamp endTime = CommonUtils.getTimestamp();
                long downloadTime = endTime.getTime() - startTime.getTime();
                webPage.setDocument(document);
                webPage.setTitle(document.title());
                webPage.setDownloadTime(downloadTime+" Milliseconds");
            }
        } catch(UnknownHostException ue){
            // invalid or 404 url
        } catch (Exception ex) {
            Logger.getLogger(DownloaderThread.class.getName()).log(Level.SEVERE, null, ex);         
        }
        
        return webPage;
    }
}
