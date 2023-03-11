package com.pottersfieldap.biblememoryhangman;
import com.gargoylesoftware.htmlunit.*;
import com.gargoylesoftware.htmlunit.html.*;
import com.gargoylesoftware.htmlunit.javascript.host.dom.Node;

import java.util.List;

import java.io.IOException;


public class WebScraper {
    public static String getRawVerseText(String bookName, int chapterNum, int startVerse, int endVerse, String versionName) {
        // Configure the web scraper.
        WebClient wc = new WebClient(BrowserVersion.CHROME);
        // Options silence a lot of compiler messages
        wc.getOptions().setCssEnabled(false);
        wc.getOptions().setThrowExceptionOnFailingStatusCode(false);
        wc.getOptions().setThrowExceptionOnScriptError(false);
        wc.getOptions().setPrintContentOnFailingStatusCode(false);
        StringBuilder sb = new StringBuilder("");

        try {
            // Navigate to the web page. Uses the function parameters to get the right URL
            HtmlPage page  = wc.getPage("https://www.biblegateway.com/passage/?search=" + bookName + chapterNum + "%3A" + startVerse + "-" + endVerse + "&version=" + versionName);
            // xpath puts us as the root which holds the passage text
            String xpath = "//div[@class='version-ESV result-text-style-normal text-html']";
            HtmlDivision passageDiv = page.getFirstByXPath(xpath);
            // The only <p> tag in the div is the one that holds it all
            DomNodeList<HtmlElement> paragraphList = passageDiv.getElementsByTagName("p");
            HtmlElement passageParagraph = paragraphList.get(0);

            /* Inside the <p> tags are a number of spans which have the text pieces out throughout them. We need to
            iterate through and return anything that is just text
             */
            // For each span inside the <p> tag
            for (DomElement e : passageParagraph.getChildElements()) {
                if (e.getTagName().equals("span")) {
                    // For every node inside the span
                    for (DomNode n : e.getChildNodes()) {
                        // If the node is text, append it to the sb
                        if (n.getNodeType() == Node.TEXT_NODE) {
                            sb.append(n.getTextContent());
                            sb.append(" ");
                        }
                    }
                }
            }

            // Close out the scraper
            wc.getCurrentWindow().getJobManager().removeAllJobs();
            wc.close();
        } catch (IOException e) {
            System.out.println("Something went wrong. Check the Web Scraper");
        }
        return sb.toString();
    }
}
