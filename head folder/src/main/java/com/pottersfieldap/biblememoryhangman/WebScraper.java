package com.pottersfieldap.biblememoryhangman;
import com.gargoylesoftware.htmlunit.*;
import com.gargoylesoftware.htmlunit.html.*;

import java.util.List;

import java.io.IOException;


public class WebScraper {
    public static void getRawVerseText(String bookName, int chapterNum, int startVerse, int endVerse, String versionName) {
        WebClient wc = new WebClient(BrowserVersion.CHROME);
        wc.getOptions().setCssEnabled(false);
        wc.getOptions().setThrowExceptionOnFailingStatusCode(false);
        wc.getOptions().setThrowExceptionOnScriptError(false);
        wc.getOptions().setPrintContentOnFailingStatusCode(false);
        try {
            HtmlPage page  = wc.getPage("https://www.biblegateway.com/passage/?search=" + bookName + chapterNum + "%3A" + startVerse + "-" + endVerse + "&version=" + versionName);
            System.out.println(page.getTitleText());
            String xpath = "//div[@class='version-ESV result-text-style-normal text-html']";
            List<HtmlDivision> divisionList = page.getByXPath(xpath);
            HtmlDivision passageDiv = divisionList.get(0);
            DomNodeList<HtmlElement> paragraphList = passageDiv.getElementsByTagName("p");
            for (HtmlElement p : paragraphList) {
                System.out.println(p.getTextContent());
            }
            wc.getCurrentWindow().getJobManager().removeAllJobs();
            wc.close();
        } catch (IOException e) {
            System.out.println("Something went wrong. Check the Web Scraper");
        }
    }
}
