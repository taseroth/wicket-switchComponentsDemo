package org.faboo.web;

import org.apache.wicket.protocol.http.HttpSessionStore;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.session.ISessionStore;
import org.faboo.web.pages.HomePage;

/**
 * Entrypoint for the wicket application.
 * Only do some adjustments, so that it can run on google
 * app engine.
 */
public class SampleApplication extends WebApplication
{
    public Class<HomePage> getHomePage()
    {
        return HomePage.class;
    }

    @Override
    protected void init()
    {
        super.init();

        // for Google App Engine
        getResourceSettings().setResourcePollFrequency(null);

    }

    @Override
    protected ISessionStore newSessionStore()
    {
        // for Google App Engine
        return new HttpSessionStore(this);
    }
}
