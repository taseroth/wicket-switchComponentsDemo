package org.faboo.web.components;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.SubmitLink;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.RefreshingView;
import org.apache.wicket.markup.repeater.util.ModelIteratorAdapter;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import twitter4j.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Panel to let users search on Twitter.
 * Displays the the last found tweets in a list.
 *
 * This is only a demo, in a real application, one would factor the retrieving
 * of the tweets out into a service and implement some caching.
 *
 * @author Bert Radke
 */
public class TwitterSearchPanel extends Panel {

    private static final long serialVersionUID = 1L;

    private String searchString = "#searchTerm";

    private List<Tweet> tweets = new ArrayList<Tweet>();

    private Twitter twitter = new TwitterFactory().getInstance();

    public TwitterSearchPanel(String id) {
        super(id);

        final Form form = new Form("form") {
            @Override
            protected void onSubmit() {
                super.onSubmit();
                performSearch();
            }
        };

        add(form);

        form.add(new TextField<String>("searchString"
                , new PropertyModel<String>(TwitterSearchPanel.this, "searchString")));

        form.add(new SubmitLink("submitLink"));

        add(new Label("searchStringShow"
                , new PropertyModel<String>(TwitterSearchPanel.this, "searchString")));


        add(new Link("searchWicket") {
            @Override
            public void onClick() {
                searchString = "#wicket";
                performSearch();
            }
        });

        add(new Link("searchODT") {
            @Override
            public void onClick() {
                searchString = "#onedaytalk";
                performSearch();
            }
        });

        RefreshingView<Tweet> tweetsView = new RefreshingView<Tweet>("tweets") {
            @Override
            protected Iterator<IModel<Tweet>> getItemModels() {
                return new ModelIteratorAdapter<Tweet>(tweets.iterator()) {
                    @Override
                    protected IModel<Tweet> model(Tweet object) {
                        return new CompoundPropertyModel<Tweet>(object);
                    }
                };
            }

            @Override
            protected void populateItem(Item<Tweet> item) {
                item.add(new Label("text"));
            }
        };

        add(tweetsView);
    }

    /**
     * do the actual search and store the result in the tweets list.
     */
    private void performSearch() {

        Query twQuery = new Query(searchString);
        try {
            QueryResult result = twitter.search(twQuery);
            tweets = result.getTweets();
        } catch (TwitterException e) {
            e.printStackTrace();
        }
    }

    public String getSearchString() {
        return searchString;
    }

    public void setSearchString(String searchString) {
        this.searchString = searchString;
    }
}
