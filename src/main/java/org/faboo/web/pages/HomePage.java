package org.faboo.web.pages;

import org.apache.wicket.ResourceReference;
import org.apache.wicket.markup.html.CSSPackageResource;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.link.Link;
import org.faboo.web.components.HomePanel;
import org.faboo.web.components.TwitterSearchPanel;

/**
 * Homepage and only Page of the Application.
 * Contains links to construct and display the panels.
 *
 * @author Bert Radke
 */
public class HomePage extends WebPage {

    private static final long serialVersionUID = 1L;

    public HomePage() {

        add(CSSPackageResource.getHeaderContribution(
                new ResourceReference(HomePage.class, "../resources/css/960.css")));

        add(CSSPackageResource.getHeaderContribution(
                new ResourceReference(HomePage.class, "../resources/css/main.css")));


        add(new Link("homePage" ) {
            @Override
            public void onClick() {
                setResponsePage(HomePage.class);
            }
        });

        add(new HomePanel("mainPlaceholder"));

        add(new Link<TwitterSearchPanel>("searchTwitter") {
            @Override
            public void onClick() {
                HomePage.this.addOrReplace(new TwitterSearchPanel("mainPlaceholder"));
            }
        });

        add(new Link<TwitterSearchPanel>("displayRSS") {
            @Override
            public void onClick() {
                HomePage.this.addOrReplace(new TwitterSearchPanel("mainPlaceholder"));
            }
        });
    }

}
