package com.pottersfieldap.biblememoryhangman;
import com.gargoylesoftware.htmlunit.*;
import com.gargoylesoftware.htmlunit.html.*;
import com.gargoylesoftware.htmlunit.javascript.host.dom.Node;

import java.util.List;

import java.io.IOException;


public class WebScraper {
    public static String getRawVerseText(String bookName, int chapterNum, int startVerse, int endVerse, String versionName) {
        WebClient wc = new WebClient(BrowserVersion.CHROME);
        wc.getOptions().setCssEnabled(false);
        wc.getOptions().setThrowExceptionOnFailingStatusCode(false);
        wc.getOptions().setThrowExceptionOnScriptError(false);
        wc.getOptions().setPrintContentOnFailingStatusCode(false);
        StringBuilder sb = new StringBuilder("");
        try {
            HtmlPage page  = wc.getPage("https://www.biblegateway.com/passage/?search=" + bookName + chapterNum + "%3A" + startVerse + "-" + endVerse + "&version=" + versionName);
            System.out.println(page.getTitleText());
            String xpath = "//div[@class='version-ESV result-text-style-normal text-html']";
            HtmlDivision passageDiv = page.getFirstByXPath(xpath);
            DomNodeList<HtmlElement> paragraphList = passageDiv.getElementsByTagName("p");
            HtmlElement passageParagraph = paragraphList.get(0);

            for (DomElement e : passageParagraph.getChildElements()) {
                if (e.getTagName().equals("span")) {
                    for (DomNode n : e.getChildNodes()) {
                        if (n.getNodeType() == Node.TEXT_NODE) {
                            sb.append(n.getTextContent());
                            sb.append(" ");
                        }
                    }
                }
            }

            System.out.println(sb.toString());
            wc.getCurrentWindow().getJobManager().removeAllJobs();
            wc.close();
        } catch (IOException e) {
            System.out.println("Something went wrong. Check the Web Scraper");
        }
        return sb.toString();
    }
}
