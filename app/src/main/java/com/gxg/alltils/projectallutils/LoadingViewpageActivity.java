package com.gxg.alltils.projectallutils;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LoadingViewpageActivity extends AppCompatActivity {

    @Bind(R.id.banner)
    ConvenientBanner banner;
    List<Integer> imgs = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_viewpage);
        ButterKnife.bind(this);

        initBanner();
    }

    private void initBanner() {
        imgs.add(R.drawable.banner1);
        imgs.add(R.drawable.banner2);
        imgs.add(R.drawable.banner3);

        banner.setPages(new CBViewHolderCreator<LocalImageHolderView>() {
            @Override
            public LocalImageHolderView createHolder() {
                return new LocalImageHolderView();
            }
        }, imgs);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    public class LocalImageHolderView implements Holder<Integer>{

        private ImageView iv;

        @Override
        public View createView(Context context) {
            iv = new ImageView(context);
            return iv;
        }

        @Override
        public void UpdateUI(Context context, int position, Integer data) {
                iv.setImageResource(data);
        }
    }


}
