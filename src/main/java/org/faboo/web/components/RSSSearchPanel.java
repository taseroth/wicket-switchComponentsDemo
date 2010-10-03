package org.faboo.web.components;

import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.SubmitLink;
import org.apache.wicket.markup.html.panel.Panel;

import com.sun.syndication.feed.synd.SyndEntry;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.RefreshingView;
import org.apache.wicket.markup.repeater.util.ModelIteratorAdapter;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Panel to display some RSS feeds.
 * Demonstration oly, for applications, the rss feed should come
 * from some other source, the fetching should be done in an service,
 * caching is missing and so on.
 *
 * 
 *
 * @author Bert Radke
 */
public class RSSSearchPanel extends Panel {

    private List<SyndEntry> entries = new ArrayList<SyndEntry>();

    private String selectedFeed = "http://www.heise.de/developer/rss/news-atom.xml";

    public RSSSearchPanel(String id) {
        super(id);


        final Form form = new Form("form");
        add(form);

        // this should come from some other storage for real usage
        List<String> feeds = new ArrayList<String>();
        feeds.add("http://www.heise.de/developer/rss/news-atom.xml");
        feeds.add("http://chaosradio.ccc.de/chaosradio_express-latest.rss");
        feeds.add("http://blogs.jetbrains.com/idea/feed/");


        form.add(new DropDownChoice<String>("feetsDDC"
                , new PropertyModel<String>(RSSSearchPanel.this, "selectedFeed")
                , feeds));

        form.add(new SubmitLink("submitLink") {
            @Override
            public void onSubmit() {
                super.onSubmit();
                loadFeed();
            }
        });

        RefreshingView<SyndEntry> entriesView = new RefreshingView<SyndEntry>("items") {
            @Override
            protected Iterator<IModel<SyndEntry>> getItemModels() {
                return new ModelIteratorAdapter<SyndEntry>(entries.iterator()) {
                    @Override
                    protected IModel<SyndEntry> model(SyndEntry object) {
                        return new CompoundPropertyModel<SyndEntry>(object);
                    }
                };
            }

            @Override
            protected void populateItem(Item<SyndEntry> item) {
                item.add(new Label("title"));
            }
        };

        add(entriesView);

    }


    @SuppressWarnings("unchecked") // feedreader returns List..
    private void loadFeed() {

        XmlReader reader = null;
        try {
            URL url = new URL(selectedFeed);
            reader = new XmlReader(url);
            SyndFeed feed = new SyndFeedInput().build(reader);
            entries = feed.getEntries();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (FeedException e) {
            e.printStackTrace();
        } finally {
            if(null != reader) {
                try {
                    reader.close();
                } catch (IOException e) {
                    // we are not interested in this..
                }
            }
        }


    }


    public String getSelectedFeed() {
        return selectedFeed;
    }

    public void setSelectedFeed(String selectedFeed) {
        this.selectedFeed = selectedFeed;
    }
}
