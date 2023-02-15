package com.pottersfieldap.biblememoryhangman;
import com.gargoylesoftware.htmlunit.*;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import java.io.IOException;

public class WebScraper {
    public static void testBrowse() {
        WebClient wc = new WebClient(BrowserVersion.CHROME);
        wc.getOptions().setCssEnabled(false);
        wc.getOptions().setThrowExceptionOnFailingStatusCode(false);
        wc.getOptions().setThrowExceptionOnScriptError(false);
        wc.getOptions().setPrintContentOnFailingStatusCode(false);
        try {
            HtmlPage page  = wc.getPage("https://www.biblegateway.com/passage/?search=Romans%201&version=ESV");
            System.out.println(page.getTitleText());
            wc.getCurrentWindow().getJobManager().removeAllJobs();
            wc.close();
        } catch (IOException e) {
            System.out.println("SIUUUUUU");
        }
    }
}
