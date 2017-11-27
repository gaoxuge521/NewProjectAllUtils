package com.gxg.alltils.projectallutils.model.home.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gxg.alltils.projectallutils.R;
import com.gxg.alltils.projectallutils.imageloader.ImageLoader;
import com.gxg.alltils.projectallutils.imageloader.ImageLoaderUtil;
import com.gxg.alltils.projectallutils.model.home.bean.SearchGoodBean;
import com.gxg.alltils.projectallutils.model.home.bean.SearchGoodStoreCreditBean;

import java.util.List;

/**
 * 作者：Administrator on 2017/11/26 10:01
 * 邮箱：android_gaoxuge@163.com
 * 搜索列表的适配器
 */
public class SearchGoodAdapter extends BaseQuickAdapter<SearchGoodBean.DatasBean.GoodsListBean,BaseViewHolder> {
    private SearchGoodStoreCreditBean mSearchGoodStoreCreditBean;
    public SearchGoodAdapter(@Nullable List<SearchGoodBean.DatasBean.GoodsListBean> data) {
        super(R.layout.item_search_goods,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SearchGoodBean.DatasBean.GoodsListBean item) {

        //显示和隐藏商店信息
        if(item.isShowStore()){
            helper.setVisible(R.id.ll_goods,false);
            helper.setVisible(R.id.ll_good_store,true);
            if(mSearchGoodStoreCreditBean!=null){
                if(!TextUtils.isEmpty(item.getStore_name())){
                    helper.setVisible(R.id.tv_store_name,true);
                    helper.setText(R.id.tv_store_name,item.getStore_name());
                }else{
                    helper.setVisible(R.id.tv_store_name,false);
                }


                helper.setText(R.id.tv_store_desccredit,mSearchGoodStoreCreditBean.getDatas().getStore_credit().getStore_desccredit().getCredit());
                helper.setText(R.id.tv_store_servicecredit,mSearchGoodStoreCreditBean.getDatas().getStore_credit().getStore_servicecredit().getCredit());
                helper.setText(R.id.tv_store_deliverycredit,mSearchGoodStoreCreditBean.getDatas().getStore_credit().getStore_deliverycredit().getCredit());
            }
        }else{
            helper.setVisible(R.id.ll_goods,true);
            helper.setVisible(R.id.ll_good_store,false);
        }

        ImageView imageView = helper.getView(R.id.iv_search_good_item);
        new ImageLoaderUtil().loadImage(mContext,new ImageLoader.Builder().url(item.getGoods_image_url()).imgView(imageView).build());
//        Glide.with(mContext).load(item.getGoods_image_url()).into(imageView);
        if(!TextUtils.isEmpty(item.getGoods_name())){
            helper.setVisible(R.id.tv_search_good_name,true);
            helper.setText(R.id.tv_search_good_name,item.getGoods_name());
        }else{
            helper.setVisible(R.id.tv_search_good_name,false);
        }


        if(!TextUtils.isEmpty(item.getGoods_jingle())){
            helper.setVisible(R.id.tv_search_good_jingle,true);
            helper.setText(R.id.tv_search_good_jingle,item.getGoods_jingle());
        }else{
            helper.setVisible(R.id.tv_search_good_jingle,false);
        }

        if(!TextUtils.isEmpty(item.getGoods_price())){
            helper.setVisible(R.id.tv_search_good_price,true);
            helper.setText(R.id.tv_search_good_price,"￥"+item.getGoods_price());
        }else{
            helper.setVisible(R.id.tv_search_good_price,false);
        }

        if(!TextUtils.isEmpty(item.getGoods_salenum())){
            helper.setVisible(R.id.tv_search_good_selasnum,true);
            helper.setText(R.id.tv_search_good_selasnum,"销量："+item.getGoods_salenum());
        }else{
            helper.setVisible(R.id.tv_search_good_selasnum,false);
        }

        if(!TextUtils.isEmpty(item.getStore_name())){
            helper.setVisible(R.id.tv_search_good_store_name,true);
            helper.setText(R.id.tv_search_good_store_name,item.getStore_name()+"···");
        }else{
            helper.setVisible(R.id.tv_search_good_store_name,false);
        }



        //添加点击事件
        helper.addOnClickListener(R.id.tv_search_good_store_name);//点击显示商店信息
        helper.addOnClickListener(R.id.ll_store_credit);//点击显示商品信息


    }

    public void setShowStore(SearchGoodStoreCreditBean searchGoodStoreCreditBean , int position,boolean isShowStore){
        this.mSearchGoodStoreCreditBean = searchGoodStoreCreditBean;
        getData().get(position).setShowStore(isShowStore);
        notifyItemChanged(position);
    }
}
