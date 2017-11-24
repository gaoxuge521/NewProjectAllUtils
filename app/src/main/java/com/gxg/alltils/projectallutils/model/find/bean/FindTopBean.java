package com.gxg.alltils.projectallutils.model.find.bean;

import java.util.List;

/**
 * 作者：Administrator on 2017/11/24 15:04
 * 邮箱：android_gaoxuge@163.com
 */
public class FindTopBean {

    /**
     * code : 200
     * datas : {"brand_list":[{"brand_id":"433","brand_name":"karcher凯驰","brand_pic":"http://b2b2c.shopnctest.com/dema/data/upload/shop/brand/04866460197124087_small.jpg"},{"brand_id":"432","brand_name":"伊芙丽","brand_pic":"http://b2b2c.shopnctest.com/dema/data/upload/shop/brand/04865587979220647_sm.png"},{"brand_id":"412","brand_name":"双立人 Zwilling","brand_pic":"http://b2b2c.shopnctest.com/dema/data/upload/shop/brand/04626258492961923_sm.jpg"},{"brand_id":"411","brand_name":"Henckels 亨克斯","brand_pic":"http://b2b2c.shopnctest.com/dema/data/upload/shop/brand/04626257480592227_sm.jpg"},{"brand_id":"364","brand_name":"BH (必艾奇)","brand_pic":"http://b2b2c.shopnctest.com/dema/data/upload/shop/brand/04420633630909218_small.jpg"},{"brand_id":"358","brand_name":"金史密斯（KINGSMITH）","brand_pic":"http://b2b2c.shopnctest.com/dema/data/upload/shop/brand/04420592440315393_small.gif"},{"brand_id":"214","brand_name":"苹果","brand_pic":"http://b2b2c.shopnctest.com/dema/data/upload/shop/brand/04398168923750202_sm.png"},{"brand_id":"208","brand_name":"三星","brand_pic":"http://b2b2c.shopnctest.com/dema/data/upload/shop/brand/04398160720944823_sm.jpg"},{"brand_id":"206","brand_name":"索爱","brand_pic":"http://b2b2c.shopnctest.com/dema/data/upload/shop/brand/04398160348310562_sm.gif"},{"brand_id":"201","brand_name":"松下","brand_pic":"http://b2b2c.shopnctest.com/dema/data/upload/shop/brand/04398159595550035_sm.jpg"},{"brand_id":"196","brand_name":"飞利浦","brand_pic":"http://b2b2c.shopnctest.com/dema/data/upload/shop/brand/04398158808225051_sm.jpg"},{"brand_id":"192","brand_name":"爱普生","brand_pic":"http://b2b2c.shopnctest.com/dema/data/upload/shop/brand/04398158266047493_sm.jpg"},{"brand_id":"187","brand_name":"雷蛇","brand_pic":"http://b2b2c.shopnctest.com/dema/data/upload/shop/brand/04398157472174891_sm.jpg"},{"brand_id":"185","brand_name":"罗技","brand_pic":"http://b2b2c.shopnctest.com/dema/data/upload/shop/brand/04398157235673753_sm.jpg"},{"brand_id":"183","brand_name":"TP-LINK","brand_pic":"http://b2b2c.shopnctest.com/dema/data/upload/shop/brand/04398156873572761_sm.jpg"},{"brand_id":"182","brand_name":"金士顿","brand_pic":"http://b2b2c.shopnctest.com/dema/data/upload/shop/brand/04398156705753579_sm.jpg"},{"brand_id":"179","brand_name":"DELL","brand_pic":"http://b2b2c.shopnctest.com/dema/data/upload/shop/brand/04398156232757027_sm.jpg"},{"brand_id":"170","brand_name":"阿玛尼","brand_pic":"http://b2b2c.shopnctest.com/dema/data/upload/shop/brand/04398121209932680_sm.jpg"},{"brand_id":"169","brand_name":"斯沃琪","brand_pic":"http://b2b2c.shopnctest.com/dema/data/upload/shop/brand/04398121090096799_sm.jpg"},{"brand_id":"165","brand_name":"tissot","brand_pic":"http://b2b2c.shopnctest.com/dema/data/upload/shop/brand/04398120131178815_sm.jpg"},{"brand_id":"164","brand_name":"欧米茄","brand_pic":"http://b2b2c.shopnctest.com/dema/data/upload/shop/brand/04398119996858692_sm.jpg"},{"brand_id":"157","brand_name":"帝舵","brand_pic":"http://b2b2c.shopnctest.com/dema/data/upload/shop/brand/04398118894311290_sm.jpg"},{"brand_id":"155","brand_name":"卡西欧","brand_pic":"http://b2b2c.shopnctest.com/dema/data/upload/shop/brand/04398118617326698_sm.jpg"},{"brand_id":"149","brand_name":"梅花","brand_pic":"http://b2b2c.shopnctest.com/dema/data/upload/shop/brand/04398117504203345_sm.jpg"},{"brand_id":"146","brand_name":"Disney","brand_pic":"http://b2b2c.shopnctest.com/dema/data/upload/shop/brand/04398117134560677_sm.jpg"},{"brand_id":"145","brand_name":"CK","brand_pic":"http://b2b2c.shopnctest.com/dema/data/upload/shop/brand/04398116986166995_sm.jpg"},{"brand_id":"143","brand_name":"施华洛世奇","brand_pic":"http://b2b2c.shopnctest.com/dema/data/upload/shop/brand/04398116735872287_sm.jpg"},{"brand_id":"142","brand_name":"SK-ll","brand_pic":"http://b2b2c.shopnctest.com/dema/data/upload/shop/brand/04398104206717960_sm.jpg"},{"brand_id":"136","brand_name":"资生堂","brand_pic":"http://b2b2c.shopnctest.com/dema/data/upload/shop/brand/04398103163925153_sm.jpg"},{"brand_id":"133","brand_name":"雅诗兰黛","brand_pic":"http://b2b2c.shopnctest.com/dema/data/upload/shop/brand/04398102581821577_sm.jpg"},{"brand_id":"130","brand_name":"雅顿","brand_pic":"http://b2b2c.shopnctest.com/dema/data/upload/shop/brand/04398102086535787_sm.jpg"},{"brand_id":"127","brand_name":"妮维雅","brand_pic":"http://b2b2c.shopnctest.com/dema/data/upload/shop/brand/04398101539246004_sm.jpg"},{"brand_id":"125","brand_name":"娇兰","brand_pic":"http://b2b2c.shopnctest.com/dema/data/upload/shop/brand/04398101035858820_sm.jpg"},{"brand_id":"124","brand_name":"兰蔻","brand_pic":"http://b2b2c.shopnctest.com/dema/data/upload/shop/brand/04398100899214207_sm.jpg"},{"brand_id":"122","brand_name":"纪梵希","brand_pic":"http://b2b2c.shopnctest.com/dema/data/upload/shop/brand/04398100614445814_sm.jpg"},{"brand_id":"116","brand_name":"Dior","brand_pic":"http://b2b2c.shopnctest.com/dema/data/upload/shop/brand/04398099738566948_sm.jpg"},{"brand_id":"115","brand_name":"相宜本草","brand_pic":"http://b2b2c.shopnctest.com/dema/data/upload/shop/brand/04398099611242673_sm.jpg"},{"brand_id":"104","brand_name":"esprit","brand_pic":"http://b2b2c.shopnctest.com/dema/data/upload/shop/brand/04398090828687339_sm.jpg"},{"brand_id":"103","brand_name":"ELLE HOME","brand_pic":"http://b2b2c.shopnctest.com/dema/data/upload/shop/brand/04398090611386532_sm.jpg"},{"brand_id":"100","brand_name":"宝姿","brand_pic":"http://b2b2c.shopnctest.com/dema/data/upload/shop/brand/04398090061006740_sm.jpg"},{"brand_id":"99","brand_name":"梦特娇","brand_pic":"http://b2b2c.shopnctest.com/dema/data/upload/shop/brand/04398089942879365_sm.jpg"},{"brand_id":"96","brand_name":"佐丹奴","brand_pic":"http://b2b2c.shopnctest.com/dema/data/upload/shop/brand/04398089412399747_sm.jpg"},{"brand_id":"94","brand_name":"七匹狼","brand_pic":"http://b2b2c.shopnctest.com/dema/data/upload/shop/brand/04398089136939537_sm.jpg"},{"brand_id":"93","brand_name":"百丽","brand_pic":"http://b2b2c.shopnctest.com/dema/data/upload/shop/brand/04398088925179484_sm.png"},{"brand_id":"92","brand_name":"Newbalance","brand_pic":"http://b2b2c.shopnctest.com/dema/data/upload/shop/brand/04397473633585549_sm.jpg"},{"brand_id":"91","brand_name":"其乐","brand_pic":"http://b2b2c.shopnctest.com/dema/data/upload/shop/brand/04397473331842699_sm.jpg"},{"brand_id":"89","brand_name":"真维斯","brand_pic":"http://b2b2c.shopnctest.com/dema/data/upload/shop/brand/04397472838086984_sm.jpg"},{"brand_id":"84","brand_name":"阿迪达斯","brand_pic":"http://b2b2c.shopnctest.com/dema/data/upload/shop/brand/04397471910652190_sm.jpg"}]}
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
        private List<BrandListBean> brand_list;

        public List<BrandListBean> getBrand_list() {
            return brand_list;
        }

        public void setBrand_list(List<BrandListBean> brand_list) {
            this.brand_list = brand_list;
        }

        public static class BrandListBean {
            /**
             * brand_id : 433
             * brand_name : karcher凯驰
             * brand_pic : http://b2b2c.shopnctest.com/dema/data/upload/shop/brand/04866460197124087_small.jpg
             */

            private String brand_id;
            private String brand_name;
            private String brand_pic;

            public String getBrand_id() {
                return brand_id;
            }

            public void setBrand_id(String brand_id) {
                this.brand_id = brand_id;
            }

            public String getBrand_name() {
                return brand_name;
            }

            public void setBrand_name(String brand_name) {
                this.brand_name = brand_name;
            }

            public String getBrand_pic() {
                return brand_pic;
            }

            public void setBrand_pic(String brand_pic) {
                this.brand_pic = brand_pic;
            }
        }
    }
}
