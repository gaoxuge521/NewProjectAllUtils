package com.gxg.alltils.projectallutils.model.shop.bean;

import com.gxg.alltils.projectallutils.bean.BaseBean;

import java.util.List;

/**
 * 作者：Administrator on 2017/11/27 09:47
 * 邮箱：android_gaoxuge@163.com
 */
public class ShopCardBean extends BaseBean {

    /**
     * code : 200
     * datas : {"cart_list":[{"store_id":"1","store_name":"平台自营","goods":[{"cart_id":"134","buyer_id":"217","store_id":"1","store_name":"平台自营","goods_id":"100456","goods_name":"2015正品路易威登LOUIS VUITTON手提包 LV男包商务公文包 N41125","goods_price":"8500.00","goods_num":"1","goods_image":"1_04757805524760279.jpg","bl_id":"0","state":true,"storage_state":true,"goods_commonid":"100235","gc_id":"165","transport_id":"0","goods_freight":"0.00","goods_trans_v":"0.00","goods_inv":"1","goods_vat":"0","goods_voucher":"1","goods_storage":"6","goods_storage_alarm":"0","is_fcode":"0","have_gift":"0","groupbuy_info":[],"xianshi_info":[],"promotion_info":{"spike_goods_id":"45","spike_id":"6","spike_name":"调用商品","spike_title":"","spike_explain":"轻奢特惠","goods_id":"100456","store_id":"1","goods_name":"2015正品路易威登LOUIS VUITTON手提包 LV男包商务公文包 N41125","goods_price":"13995.00","spike_price":"8500.00","goods_image":"1_04757805524760279.jpg","start_time":"1495592880","end_time":"1527091200","upper_limit":"0","spike_state":"1","spike_recommend":"0","gc_id_1":"2","spike_amount":"6","had_spiked_count":"0","goods_url":"http://b2b2c.shopnctest.com/dema/shop/item-100456.html","image_url":"http://b2b2c.shopnctest.com/dema/data/upload/shop/store/goods/1/1_04757805524760279_360.jpg","spike_discount":"6.1折","promotion_price":"8500.00","promotion_id":"6","promotion_type":"3","down_price":"5495.00"},"is_chain":"1","is_dis":"0","is_book":"0","book_down_payment":"0.00","book_final_payment":"0.00","book_down_time":"0","sole_info":[],"contractlist":[],"goods_yprice":"13995.00","promotions_id":"6","ifpromotion":true,"goods_image_url":"http://b2b2c.shopnctest.com/dema/data/upload/shop/store/goods/1/1_04757805524760279_240.jpg","goods_total":"8500.00"}]}],"sum":"8500.00","cart_count":1}
     */

    private int code;
    private DatasBean datas;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DatasBean getDatas() {
        return datas;
    }

    public void setDatas(DatasBean datas) {
        this.datas = datas;
    }

    public static class DatasBean {
        /**
         * cart_list : [{"store_id":"1","store_name":"平台自营","goods":[{"cart_id":"134","buyer_id":"217","store_id":"1","store_name":"平台自营","goods_id":"100456","goods_name":"2015正品路易威登LOUIS VUITTON手提包 LV男包商务公文包 N41125","goods_price":"8500.00","goods_num":"1","goods_image":"1_04757805524760279.jpg","bl_id":"0","state":true,"storage_state":true,"goods_commonid":"100235","gc_id":"165","transport_id":"0","goods_freight":"0.00","goods_trans_v":"0.00","goods_inv":"1","goods_vat":"0","goods_voucher":"1","goods_storage":"6","goods_storage_alarm":"0","is_fcode":"0","have_gift":"0","groupbuy_info":[],"xianshi_info":[],"promotion_info":{"spike_goods_id":"45","spike_id":"6","spike_name":"调用商品","spike_title":"","spike_explain":"轻奢特惠","goods_id":"100456","store_id":"1","goods_name":"2015正品路易威登LOUIS VUITTON手提包 LV男包商务公文包 N41125","goods_price":"13995.00","spike_price":"8500.00","goods_image":"1_04757805524760279.jpg","start_time":"1495592880","end_time":"1527091200","upper_limit":"0","spike_state":"1","spike_recommend":"0","gc_id_1":"2","spike_amount":"6","had_spiked_count":"0","goods_url":"http://b2b2c.shopnctest.com/dema/shop/item-100456.html","image_url":"http://b2b2c.shopnctest.com/dema/data/upload/shop/store/goods/1/1_04757805524760279_360.jpg","spike_discount":"6.1折","promotion_price":"8500.00","promotion_id":"6","promotion_type":"3","down_price":"5495.00"},"is_chain":"1","is_dis":"0","is_book":"0","book_down_payment":"0.00","book_final_payment":"0.00","book_down_time":"0","sole_info":[],"contractlist":[],"goods_yprice":"13995.00","promotions_id":"6","ifpromotion":true,"goods_image_url":"http://b2b2c.shopnctest.com/dema/data/upload/shop/store/goods/1/1_04757805524760279_240.jpg","goods_total":"8500.00"}]}]
         * sum : 8500.00
         * cart_count : 1
         */

        private String sum;
        private int cart_count;
        private List<CartListBean> cart_list;

        public String getSum() {
            return sum;
        }

        public void setSum(String sum) {
            this.sum = sum;
        }

        public int getCart_count() {
            return cart_count;
        }

        public void setCart_count(int cart_count) {
            this.cart_count = cart_count;
        }

        public List<CartListBean> getCart_list() {
            return cart_list;
        }

        public void setCart_list(List<CartListBean> cart_list) {
            this.cart_list = cart_list;
        }

        public static class CartListBean {
            /**
             * store_id : 1
             * store_name : 平台自营
             * goods : [{"cart_id":"134","buyer_id":"217","store_id":"1","store_name":"平台自营","goods_id":"100456","goods_name":"2015正品路易威登LOUIS VUITTON手提包 LV男包商务公文包 N41125","goods_price":"8500.00","goods_num":"1","goods_image":"1_04757805524760279.jpg","bl_id":"0","state":true,"storage_state":true,"goods_commonid":"100235","gc_id":"165","transport_id":"0","goods_freight":"0.00","goods_trans_v":"0.00","goods_inv":"1","goods_vat":"0","goods_voucher":"1","goods_storage":"6","goods_storage_alarm":"0","is_fcode":"0","have_gift":"0","groupbuy_info":[],"xianshi_info":[],"promotion_info":{"spike_goods_id":"45","spike_id":"6","spike_name":"调用商品","spike_title":"","spike_explain":"轻奢特惠","goods_id":"100456","store_id":"1","goods_name":"2015正品路易威登LOUIS VUITTON手提包 LV男包商务公文包 N41125","goods_price":"13995.00","spike_price":"8500.00","goods_image":"1_04757805524760279.jpg","start_time":"1495592880","end_time":"1527091200","upper_limit":"0","spike_state":"1","spike_recommend":"0","gc_id_1":"2","spike_amount":"6","had_spiked_count":"0","goods_url":"http://b2b2c.shopnctest.com/dema/shop/item-100456.html","image_url":"http://b2b2c.shopnctest.com/dema/data/upload/shop/store/goods/1/1_04757805524760279_360.jpg","spike_discount":"6.1折","promotion_price":"8500.00","promotion_id":"6","promotion_type":"3","down_price":"5495.00"},"is_chain":"1","is_dis":"0","is_book":"0","book_down_payment":"0.00","book_final_payment":"0.00","book_down_time":"0","sole_info":[],"contractlist":[],"goods_yprice":"13995.00","promotions_id":"6","ifpromotion":true,"goods_image_url":"http://b2b2c.shopnctest.com/dema/data/upload/shop/store/goods/1/1_04757805524760279_240.jpg","goods_total":"8500.00"}]
             */

            private String store_id;
            private String store_name;
            private List<GoodsBean> goods;

            public String getStore_id() {
                return store_id;
            }

            public void setStore_id(String store_id) {
                this.store_id = store_id;
            }

            public String getStore_name() {
                return store_name;
            }

            public void setStore_name(String store_name) {
                this.store_name = store_name;
            }

            public List<GoodsBean> getGoods() {
                return goods;
            }

            public void setGoods(List<GoodsBean> goods) {
                this.goods = goods;
            }

            public static class GoodsBean {
                /**
                 * cart_id : 134
                 * buyer_id : 217
                 * store_id : 1
                 * store_name : 平台自营
                 * goods_id : 100456
                 * goods_name : 2015正品路易威登LOUIS VUITTON手提包 LV男包商务公文包 N41125
                 * goods_price : 8500.00
                 * goods_num : 1
                 * goods_image : 1_04757805524760279.jpg
                 * bl_id : 0
                 * state : true
                 * storage_state : true
                 * goods_commonid : 100235
                 * gc_id : 165
                 * transport_id : 0
                 * goods_freight : 0.00
                 * goods_trans_v : 0.00
                 * goods_inv : 1
                 * goods_vat : 0
                 * goods_voucher : 1
                 * goods_storage : 6
                 * goods_storage_alarm : 0
                 * is_fcode : 0
                 * have_gift : 0
                 * groupbuy_info : []
                 * xianshi_info : []
                 * promotion_info : {"spike_goods_id":"45","spike_id":"6","spike_name":"调用商品","spike_title":"","spike_explain":"轻奢特惠","goods_id":"100456","store_id":"1","goods_name":"2015正品路易威登LOUIS VUITTON手提包 LV男包商务公文包 N41125","goods_price":"13995.00","spike_price":"8500.00","goods_image":"1_04757805524760279.jpg","start_time":"1495592880","end_time":"1527091200","upper_limit":"0","spike_state":"1","spike_recommend":"0","gc_id_1":"2","spike_amount":"6","had_spiked_count":"0","goods_url":"http://b2b2c.shopnctest.com/dema/shop/item-100456.html","image_url":"http://b2b2c.shopnctest.com/dema/data/upload/shop/store/goods/1/1_04757805524760279_360.jpg","spike_discount":"6.1折","promotion_price":"8500.00","promotion_id":"6","promotion_type":"3","down_price":"5495.00"}
                 * is_chain : 1
                 * is_dis : 0
                 * is_book : 0
                 * book_down_payment : 0.00
                 * book_final_payment : 0.00
                 * book_down_time : 0
                 * sole_info : []
                 * contractlist : []
                 * goods_yprice : 13995.00
                 * promotions_id : 6
                 * ifpromotion : true
                 * goods_image_url : http://b2b2c.shopnctest.com/dema/data/upload/shop/store/goods/1/1_04757805524760279_240.jpg
                 * goods_total : 8500.00
                 */

                private String cart_id;
                private String buyer_id;
                private String store_id;
                private String store_name;
                private String goods_id;
                private String goods_name;
                private String goods_price;
                private String goods_num;
                private String goods_image;
                private String bl_id;
                private boolean state;
                private boolean storage_state;
                private String goods_commonid;
                private String gc_id;
                private String transport_id;
                private String goods_freight;
                private String goods_trans_v;
                private String goods_inv;
                private String goods_vat;
                private String goods_voucher;
                private String goods_storage;
                private String goods_storage_alarm;
                private String is_fcode;
                private String have_gift;
                private PromotionInfoBean promotion_info;
                private String is_chain;
                private String is_dis;
                private String is_book;
                private String book_down_payment;
                private String book_final_payment;
                private String book_down_time;
                private String goods_yprice;
                private String promotions_id;
                private boolean ifpromotion;
                private String goods_image_url;
                private String goods_total;
                private List<?> groupbuy_info;
                private List<?> xianshi_info;
                private List<?> sole_info;
                private List<?> contractlist;

                public String getCart_id() {
                    return cart_id;
                }

                public void setCart_id(String cart_id) {
                    this.cart_id = cart_id;
                }

                public String getBuyer_id() {
                    return buyer_id;
                }

                public void setBuyer_id(String buyer_id) {
                    this.buyer_id = buyer_id;
                }

                public String getStore_id() {
                    return store_id;
                }

                public void setStore_id(String store_id) {
                    this.store_id = store_id;
                }

                public String getStore_name() {
                    return store_name;
                }

                public void setStore_name(String store_name) {
                    this.store_name = store_name;
                }

                public String getGoods_id() {
                    return goods_id;
                }

                public void setGoods_id(String goods_id) {
                    this.goods_id = goods_id;
                }

                public String getGoods_name() {
                    return goods_name;
                }

                public void setGoods_name(String goods_name) {
                    this.goods_name = goods_name;
                }

                public String getGoods_price() {
                    return goods_price;
                }

                public void setGoods_price(String goods_price) {
                    this.goods_price = goods_price;
                }

                public String getGoods_num() {
                    return goods_num;
                }

                public void setGoods_num(String goods_num) {
                    this.goods_num = goods_num;
                }

                public String getGoods_image() {
                    return goods_image;
                }

                public void setGoods_image(String goods_image) {
                    this.goods_image = goods_image;
                }

                public String getBl_id() {
                    return bl_id;
                }

                public void setBl_id(String bl_id) {
                    this.bl_id = bl_id;
                }

                public boolean isState() {
                    return state;
                }

                public void setState(boolean state) {
                    this.state = state;
                }

                public boolean isStorage_state() {
                    return storage_state;
                }

                public void setStorage_state(boolean storage_state) {
                    this.storage_state = storage_state;
                }

                public String getGoods_commonid() {
                    return goods_commonid;
                }

                public void setGoods_commonid(String goods_commonid) {
                    this.goods_commonid = goods_commonid;
                }

                public String getGc_id() {
                    return gc_id;
                }

                public void setGc_id(String gc_id) {
                    this.gc_id = gc_id;
                }

                public String getTransport_id() {
                    return transport_id;
                }

                public void setTransport_id(String transport_id) {
                    this.transport_id = transport_id;
                }

                public String getGoods_freight() {
                    return goods_freight;
                }

                public void setGoods_freight(String goods_freight) {
                    this.goods_freight = goods_freight;
                }

                public String getGoods_trans_v() {
                    return goods_trans_v;
                }

                public void setGoods_trans_v(String goods_trans_v) {
                    this.goods_trans_v = goods_trans_v;
                }

                public String getGoods_inv() {
                    return goods_inv;
                }

                public void setGoods_inv(String goods_inv) {
                    this.goods_inv = goods_inv;
                }

                public String getGoods_vat() {
                    return goods_vat;
                }

                public void setGoods_vat(String goods_vat) {
                    this.goods_vat = goods_vat;
                }

                public String getGoods_voucher() {
                    return goods_voucher;
                }

                public void setGoods_voucher(String goods_voucher) {
                    this.goods_voucher = goods_voucher;
                }

                public String getGoods_storage() {
                    return goods_storage;
                }

                public void setGoods_storage(String goods_storage) {
                    this.goods_storage = goods_storage;
                }

                public String getGoods_storage_alarm() {
                    return goods_storage_alarm;
                }

                public void setGoods_storage_alarm(String goods_storage_alarm) {
                    this.goods_storage_alarm = goods_storage_alarm;
                }

                public String getIs_fcode() {
                    return is_fcode;
                }

                public void setIs_fcode(String is_fcode) {
                    this.is_fcode = is_fcode;
                }

                public String getHave_gift() {
                    return have_gift;
                }

                public void setHave_gift(String have_gift) {
                    this.have_gift = have_gift;
                }

                public PromotionInfoBean getPromotion_info() {
                    return promotion_info;
                }

                public void setPromotion_info(PromotionInfoBean promotion_info) {
                    this.promotion_info = promotion_info;
                }

                public String getIs_chain() {
                    return is_chain;
                }

                public void setIs_chain(String is_chain) {
                    this.is_chain = is_chain;
                }

                public String getIs_dis() {
                    return is_dis;
                }

                public void setIs_dis(String is_dis) {
                    this.is_dis = is_dis;
                }

                public String getIs_book() {
                    return is_book;
                }

                public void setIs_book(String is_book) {
                    this.is_book = is_book;
                }

                public String getBook_down_payment() {
                    return book_down_payment;
                }

                public void setBook_down_payment(String book_down_payment) {
                    this.book_down_payment = book_down_payment;
                }

                public String getBook_final_payment() {
                    return book_final_payment;
                }

                public void setBook_final_payment(String book_final_payment) {
                    this.book_final_payment = book_final_payment;
                }

                public String getBook_down_time() {
                    return book_down_time;
                }

                public void setBook_down_time(String book_down_time) {
                    this.book_down_time = book_down_time;
                }

                public String getGoods_yprice() {
                    return goods_yprice;
                }

                public void setGoods_yprice(String goods_yprice) {
                    this.goods_yprice = goods_yprice;
                }

                public String getPromotions_id() {
                    return promotions_id;
                }

                public void setPromotions_id(String promotions_id) {
                    this.promotions_id = promotions_id;
                }

                public boolean isIfpromotion() {
                    return ifpromotion;
                }

                public void setIfpromotion(boolean ifpromotion) {
                    this.ifpromotion = ifpromotion;
                }

                public String getGoods_image_url() {
                    return goods_image_url;
                }

                public void setGoods_image_url(String goods_image_url) {
                    this.goods_image_url = goods_image_url;
                }

                public String getGoods_total() {
                    return goods_total;
                }

                public void setGoods_total(String goods_total) {
                    this.goods_total = goods_total;
                }

                public List<?> getGroupbuy_info() {
                    return groupbuy_info;
                }

                public void setGroupbuy_info(List<?> groupbuy_info) {
                    this.groupbuy_info = groupbuy_info;
                }

                public List<?> getXianshi_info() {
                    return xianshi_info;
                }

                public void setXianshi_info(List<?> xianshi_info) {
                    this.xianshi_info = xianshi_info;
                }

                public List<?> getSole_info() {
                    return sole_info;
                }

                public void setSole_info(List<?> sole_info) {
                    this.sole_info = sole_info;
                }

                public List<?> getContractlist() {
                    return contractlist;
                }

                public void setContractlist(List<?> contractlist) {
                    this.contractlist = contractlist;
                }

                public static class PromotionInfoBean {
                    /**
                     * spike_goods_id : 45
                     * spike_id : 6
                     * spike_name : 调用商品
                     * spike_title :
                     * spike_explain : 轻奢特惠
                     * goods_id : 100456
                     * store_id : 1
                     * goods_name : 2015正品路易威登LOUIS VUITTON手提包 LV男包商务公文包 N41125
                     * goods_price : 13995.00
                     * spike_price : 8500.00
                     * goods_image : 1_04757805524760279.jpg
                     * start_time : 1495592880
                     * end_time : 1527091200
                     * upper_limit : 0
                     * spike_state : 1
                     * spike_recommend : 0
                     * gc_id_1 : 2
                     * spike_amount : 6
                     * had_spiked_count : 0
                     * goods_url : http://b2b2c.shopnctest.com/dema/shop/item-100456.html
                     * image_url : http://b2b2c.shopnctest.com/dema/data/upload/shop/store/goods/1/1_04757805524760279_360.jpg
                     * spike_discount : 6.1折
                     * promotion_price : 8500.00
                     * promotion_id : 6
                     * promotion_type : 3
                     * down_price : 5495.00
                     */

                    private String spike_goods_id;
                    private String spike_id;
                    private String spike_name;
                    private String spike_title;
                    private String spike_explain;
                    private String goods_id;
                    private String store_id;
                    private String goods_name;
                    private String goods_price;
                    private String spike_price;
                    private String goods_image;
                    private String start_time;
                    private String end_time;
                    private String upper_limit;
                    private String spike_state;
                    private String spike_recommend;
                    private String gc_id_1;
                    private String spike_amount;
                    private String had_spiked_count;
                    private String goods_url;
                    private String image_url;
                    private String spike_discount;
                    private String promotion_price;
                    private String promotion_id;
                    private String promotion_type;
                    private String down_price;

                    public String getSpike_goods_id() {
                        return spike_goods_id;
                    }

                    public void setSpike_goods_id(String spike_goods_id) {
                        this.spike_goods_id = spike_goods_id;
                    }

                    public String getSpike_id() {
                        return spike_id;
                    }

                    public void setSpike_id(String spike_id) {
                        this.spike_id = spike_id;
                    }

                    public String getSpike_name() {
                        return spike_name;
                    }

                    public void setSpike_name(String spike_name) {
                        this.spike_name = spike_name;
                    }

                    public String getSpike_title() {
                        return spike_title;
                    }

                    public void setSpike_title(String spike_title) {
                        this.spike_title = spike_title;
                    }

                    public String getSpike_explain() {
                        return spike_explain;
                    }

                    public void setSpike_explain(String spike_explain) {
                        this.spike_explain = spike_explain;
                    }

                    public String getGoods_id() {
                        return goods_id;
                    }

                    public void setGoods_id(String goods_id) {
                        this.goods_id = goods_id;
                    }

                    public String getStore_id() {
                        return store_id;
                    }

                    public void setStore_id(String store_id) {
                        this.store_id = store_id;
                    }

                    public String getGoods_name() {
                        return goods_name;
                    }

                    public void setGoods_name(String goods_name) {
                        this.goods_name = goods_name;
                    }

                    public String getGoods_price() {
                        return goods_price;
                    }

                    public void setGoods_price(String goods_price) {
                        this.goods_price = goods_price;
                    }

                    public String getSpike_price() {
                        return spike_price;
                    }

                    public void setSpike_price(String spike_price) {
                        this.spike_price = spike_price;
                    }

                    public String getGoods_image() {
                        return goods_image;
                    }

                    public void setGoods_image(String goods_image) {
                        this.goods_image = goods_image;
                    }

                    public String getStart_time() {
                        return start_time;
                    }

                    public void setStart_time(String start_time) {
                        this.start_time = start_time;
                    }

                    public String getEnd_time() {
                        return end_time;
                    }

                    public void setEnd_time(String end_time) {
                        this.end_time = end_time;
                    }

                    public String getUpper_limit() {
                        return upper_limit;
                    }

                    public void setUpper_limit(String upper_limit) {
                        this.upper_limit = upper_limit;
                    }

                    public String getSpike_state() {
                        return spike_state;
                    }

                    public void setSpike_state(String spike_state) {
                        this.spike_state = spike_state;
                    }

                    public String getSpike_recommend() {
                        return spike_recommend;
                    }

                    public void setSpike_recommend(String spike_recommend) {
                        this.spike_recommend = spike_recommend;
                    }

                    public String getGc_id_1() {
                        return gc_id_1;
                    }

                    public void setGc_id_1(String gc_id_1) {
                        this.gc_id_1 = gc_id_1;
                    }

                    public String getSpike_amount() {
                        return spike_amount;
                    }

                    public void setSpike_amount(String spike_amount) {
                        this.spike_amount = spike_amount;
                    }

                    public String getHad_spiked_count() {
                        return had_spiked_count;
                    }

                    public void setHad_spiked_count(String had_spiked_count) {
                        this.had_spiked_count = had_spiked_count;
                    }

                    public String getGoods_url() {
                        return goods_url;
                    }

                    public void setGoods_url(String goods_url) {
                        this.goods_url = goods_url;
                    }

                    public String getImage_url() {
                        return image_url;
                    }

                    public void setImage_url(String image_url) {
                        this.image_url = image_url;
                    }

                    public String getSpike_discount() {
                        return spike_discount;
                    }

                    public void setSpike_discount(String spike_discount) {
                        this.spike_discount = spike_discount;
                    }

                    public String getPromotion_price() {
                        return promotion_price;
                    }

                    public void setPromotion_price(String promotion_price) {
                        this.promotion_price = promotion_price;
                    }

                    public String getPromotion_id() {
                        return promotion_id;
                    }

                    public void setPromotion_id(String promotion_id) {
                        this.promotion_id = promotion_id;
                    }

                    public String getPromotion_type() {
                        return promotion_type;
                    }

                    public void setPromotion_type(String promotion_type) {
                        this.promotion_type = promotion_type;
                    }

                    public String getDown_price() {
                        return down_price;
                    }

                    public void setDown_price(String down_price) {
                        this.down_price = down_price;
                    }
                }
            }
        }
    }
}
