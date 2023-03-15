package com.pottersfieldap.biblememoryhangman;
import com.gargoylesoftware.htmlunit.*;
import com.gargoylesoftware.htmlunit.html.*;
import com.gargoylesoftware.htmlunit.javascript.host.dom.Node;

import java.util.ArrayList;
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
            HtmlElement rootContainer = page.getFirstByXPath(xpath);
            // We want all the spans which have "text" in their name. We are going to put them in spanList
            List<HtmlElement> spanList = new ArrayList<>();
            for (DomElement container : rootContainer.getChildElements()) {
                // Exclude the h3 which contains headings
                if (container.getTagName().equals("h3")) {
                    continue;
                }
                // Exclude the footnotes
                else if (container.getAttribute("class").equals("footnotes")) {
                    continue;
                }
                // Exclude the cross references
                else if (container.getAttribute("class").equals("crossrefs hidden")) {
                    continue;
                }
                // Search the children of the rest of these nodes and find all the spans
                else {
                    spanList.addAll(container.getElementsByTagName("span"));
                }
            }
            // Iterating through the list of spans
            for (HtmlElement span : spanList) {
                // if the span's class contains "text", then it has verse text in it
                if (span.getAttribute("class").contains("text")) {
                    // search through all the nodes of these 'text spans'
                    for (DomNode subSpanNode : span.getChildNodes()) {
                        // The chapter number is contained in a span, so we need to skip it
                        if (subSpanNode.getNodeName().equals("span")) {
                            if (((HtmlElement) subSpanNode).getAttribute("class").equals("chapternum")) {
                                continue;
                            }
                        }
                        // Skip over all the <sup> tags. These contain verse numbers and cross references
                        if (!subSpanNode.getNodeName().equals("sup")) {
                            sb.append(subSpanNode.getTextContent().trim());
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
