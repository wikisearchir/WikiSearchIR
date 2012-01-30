package com.uci.ir.wikiSearch;

import info.bliki.wiki.filter.PlainTextConverter;
import info.bliki.wiki.model.WikiModel;

public class TestBliki {
    public static final String TEST = "[[UAB Hospital]] is the only [[trauma center|Level I trauma center]] in Alabama.&lt;ref&gt;{{cite web|url=http://www.facs.org/trauma/verified.html |title=Verified Trauma Centers |date=December 30, 2010 |work=American College of Surgeons, Verified Trauma Centers |accessdate=2011-01-09 }}&lt;/ref&gt;&lt;ref&gt;[https://webspace.utexas.edu/jas5349/Research_Data.htm)]&lt;/ref&gt;  UAB is the largest employer in Alabama, with a workforce of about 20,000.";

    public static void main(String[] args) {
            WikiModel wikiModel = new WikiModel("http://www.mywiki.com/wiki/${image}", "http://www.mywiki.com/wiki/${title}");
            String plainStr = wikiModel.render(new PlainTextConverter(), TEST);
            System.out.print(plainStr);
    }
}
