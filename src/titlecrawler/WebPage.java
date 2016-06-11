/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package titlecrawler;

import org.jsoup.nodes.Document;

/**
 *
 * @author italo
 * Class containing the desirable data
 */
public class WebPage {
    private Document document;
    private String downloadTime;
    private String title;
    private String url;
    private String originalUrl;

   public WebPage(String originalUrl) {
        this.url = originalUrl.replaceAll("\\s+","");
        this.originalUrl = originalUrl;
    }
   
    //Begin of getters and setters
     public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public String getDownloadTime() {
        return downloadTime;
    }

    public void setDownloadTime(String downloadTime) {
        this.downloadTime = downloadTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    
}
