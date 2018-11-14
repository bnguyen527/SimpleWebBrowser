package edu.temple.simplewebbrowser;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageButton;

import java.util.LinkedList;
import java.util.ListIterator;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BrowserListener} interface
 * to handle interaction events.
 */
public class TabFragment extends Fragment {

    private LinkedList<String> browsingHistory;
    private ListIterator<String> historyIterator;
    private WebView webView;
    private ImageButton backButton;
    private ImageButton forwardButton;
    private BrowserListener browserListener;

    public TabFragment() {
        // Required empty public constructor
    }

    public static TabFragment newInstance() {
        TabFragment fragment = new TabFragment();
        fragment.browsingHistory = new LinkedList<>();
        fragment.historyIterator = fragment.browsingHistory.listIterator();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        browsingHistory = new LinkedList<>();
        historyIterator = browsingHistory.listIterator();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tab, container, false);
        webView = view.findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        backButton = view.findViewById(R.id.backButton);
        forwardButton = view.findViewById(R.id.forwardButton);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!browsingHistory.isEmpty()) {
                    historyIterator.previous();
                    if (historyIterator.hasPrevious()) {
                        String previousUrl = historyIterator.previous();
                        historyIterator.next();
                        browserListener.setAddressBar(previousUrl);
                        webView.loadUrl(previousUrl);
                    } else {
                        historyIterator.next();
                    }
                }
            }
        });
        forwardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (historyIterator.hasNext()) {
                    String nextUrl = historyIterator.next();
                    browserListener.setAddressBar(nextUrl);
                    webView.loadUrl(nextUrl);
                }
            }
        });
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof BrowserListener) {
            browserListener = (BrowserListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement BrowserListener");
        }
    }

    void loadUrl(String webAddress) {
        historyIterator.add(webAddress);
        webView.loadUrl(webAddress);
    }

    String getCurrentAddress() {
        if (!browsingHistory.isEmpty()) {
            String currentAddress = historyIterator.previous();
            historyIterator.next();
            return currentAddress;
        }
        return "";
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    interface BrowserListener {
        void setAddressBar(String webAddress);
    }

}
