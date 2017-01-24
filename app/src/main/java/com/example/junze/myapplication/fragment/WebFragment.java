package com.example.junze.myapplication.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.example.junze.myapplication.R;

/**
 * Created by junze on 16-05-10.
 */
public class WebFragment extends Fragment {
    private View layout;
    private WebView wv;

    public static WebFragment newInstance(String url, String title) {
        WebFragment wf = new WebFragment();
        Bundle bundle = new Bundle();
        bundle.putString("url", url);
        bundle.putString("title", title);
        wf.setArguments(bundle);
        return wf;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        layout = inflater.inflate(R.layout.web_fragment, container, false);
        init();
        return layout;
    }
    private void init() {
        wv = (WebView) layout.findViewById(R.id.web_view);
//        Intent intent = getActivity().getIntent();
//        String[] extra = intent.getStringArrayExtra("url, title");

        String url = getArguments().getString("url");
        String title = getArguments().getString("title");

        wv.getSettings().setJavaScriptEnabled(true);
        wv.loadUrl(url);
        wv.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                wv.loadUrl(url);
                return true;
            }
        });

        setHasOptionsMenu(true);
        Toolbar toolbar = (Toolbar) layout.findViewById(R.id.web_toolbar);
        //toolbar.setLogo(R.drawable.nav);
        //toolbar.setNavigationIcon(R.drawable.ab_android);
        //toolbar.setSubtitle("Sub title");
        toolbar.setTitle(title);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity.getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getActivity().finish();
                if (wv.canGoBack()) {
                    wv.goBack();
                } else {
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    fm.popBackStack();
                }
            }
        });

        toolbar.setOnMenuItemClickListener(onMenuItemClick);
    }

    private Toolbar.OnMenuItemClickListener onMenuItemClick = new Toolbar.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            String msg = "";
            switch (menuItem.getItemId()) {
                case R.id.action_edit:
                    msg += "Click edit";
                    break;
                case R.id.action_share:
                    msg += "Click share";
                    break;
                case R.id.action_settings:
                    msg += "Click setting";
                    break;
            }

            if (!msg.equals("")) {
                Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
            }
            return true;
        }
    };

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_web, menu);
    }
}
