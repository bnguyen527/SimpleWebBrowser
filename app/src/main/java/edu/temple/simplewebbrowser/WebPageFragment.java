package edu.temple.simplewebbrowser;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BrowserListener} interface
 * to handle interaction events.
 * Use the {@link WebPageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WebPageFragment extends Fragment {

    private static final String WEB_ADDRESS_KEY = "web_address";

    private String webAddress;
    private WebView webView;
    private BrowserListener browserListener;

    public WebPageFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param webAddress Web address.
     * @return A new instance of fragment WebPageFragment.
     */
    public static WebPageFragment newInstance(String webAddress) {
        WebPageFragment fragment = new WebPageFragment();
        Bundle args = new Bundle();
        args.putString(WEB_ADDRESS_KEY, webAddress);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
            webAddress = getArguments().getString(WEB_ADDRESS_KEY);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_webpage, container, false);
        webView = view.findViewById(R.id.webView);
        webView.loadUrl(webAddress);
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    interface BrowserListener {
    }
}
