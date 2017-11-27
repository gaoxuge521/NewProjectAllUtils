package com.gxg.alltils.projectallutils.model.home.bean;

import com.gxg.alltils.projectallutils.bean.BaseBean;

/**
 * 作者：Administrator on 2017/11/26 10:58
 * 邮箱：android_gaoxuge@163.com
 */
public class SearchGoodStoreCreditBean extends BaseBean {

    /**
     * code : 200
     * datas : {"store_credit":{"store_desccredit":{"text":"描述","credit":"4.5","percent":"4%","percent_class":"low","percent_text":"低于"},"store_servicecredit":{"text":"服务","credit":"4.5","percent":"4%","percent_class":"low","percent_text":"低于"},"store_deliverycredit":{"text":"物流","credit":"4.5","percent":"4%","percent_class":"low","percent_text":"低于"}}}
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
         * store_credit : {"store_desccredit":{"text":"描述","credit":"4.5","percent":"4%","percent_class":"low","percent_text":"低于"},"store_servicecredit":{"text":"服务","credit":"4.5","percent":"4%","percent_class":"low","percent_text":"低于"},"store_deliverycredit":{"text":"物流","credit":"4.5","percent":"4%","percent_class":"low","percent_text":"低于"}}
         */

        private StoreCreditBean store_credit;

        public StoreCreditBean getStore_credit() {
            return store_credit;
        }

        public void setStore_credit(StoreCreditBean store_credit) {
            this.store_credit = store_credit;
        }

        public static class StoreCreditBean {
            /**
             * store_desccredit : {"text":"描述","credit":"4.5","percent":"4%","percent_class":"low","percent_text":"低于"}
             * store_servicecredit : {"text":"服务","credit":"4.5","percent":"4%","percent_class":"low","percent_text":"低于"}
             * store_deliverycredit : {"text":"物流","credit":"4.5","percent":"4%","percent_class":"low","percent_text":"低于"}
             */

            private StoreDesccreditBean store_desccredit;
            private StoreServicecreditBean store_servicecredit;
            private StoreDeliverycreditBean store_deliverycredit;

            public StoreDesccreditBean getStore_desccredit() {
                return store_desccredit;
            }

            public void setStore_desccredit(StoreDesccreditBean store_desccredit) {
                this.store_desccredit = store_desccredit;
            }

            public StoreServicecreditBean getStore_servicecredit() {
                return store_servicecredit;
            }

            public void setStore_servicecredit(StoreServicecreditBean store_servicecredit) {
                this.store_servicecredit = store_servicecredit;
            }

            public StoreDeliverycreditBean getStore_deliverycredit() {
                return store_deliverycredit;
            }

            public void setStore_deliverycredit(StoreDeliverycreditBean store_deliverycredit) {
                this.store_deliverycredit = store_deliverycredit;
            }

            public static class StoreDesccreditBean {
                /**
                 * text : 描述
                 * credit : 4.5
                 * percent : 4%
                 * percent_class : low
                 * percent_text : 低于
                 */

                private String text;
                private String credit;
                private String percent;
                private String percent_class;
                private String percent_text;

                public String getText() {
                    return text;
                }

                public void setText(String text) {
                    this.text = text;
                }

                public String getCredit() {
                    return credit;
                }

                public void setCredit(String credit) {
                    this.credit = credit;
                }

                public String getPercent() {
                    return percent;
                }

                public void setPercent(String percent) {
                    this.percent = percent;
                }

                public String getPercent_class() {
                    return percent_class;
                }

                public void setPercent_class(String percent_class) {
                    this.percent_class = percent_class;
                }

                public String getPercent_text() {
                    return percent_text;
                }

                public void setPercent_text(String percent_text) {
                    this.percent_text = percent_text;
                }
            }

            public static class StoreServicecreditBean {
                /**
                 * text : 服务
                 * credit : 4.5
                 * percent : 4%
                 * percent_class : low
                 * percent_text : 低于
                 */

                private String text;
                private String credit;
                private String percent;
                private String percent_class;
                private String percent_text;

                public String getText() {
                    return text;
                }

                public void setText(String text) {
                    this.text = text;
                }

                public String getCredit() {
                    return credit;
                }

                public void setCredit(String credit) {
                    this.credit = credit;
                }

                public String getPercent() {
                    return percent;
                }

                public void setPercent(String percent) {
                    this.percent = percent;
                }

                public String getPercent_class() {
                    return percent_class;
                }

                public void setPercent_class(String percent_class) {
                    this.percent_class = percent_class;
                }

                public String getPercent_text() {
                    return percent_text;
                }

                public void setPercent_text(String percent_text) {
                    this.percent_text = percent_text;
                }
            }

            public static class StoreDeliverycreditBean {
                /**
                 * text : 物流
                 * credit : 4.5
                 * percent : 4%
                 * percent_class : low
                 * percent_text : 低于
                 */

                private String text;
                private String credit;
                private String percent;
                private String percent_class;
                private String percent_text;

                public String getText() {
                    return text;
                }

                public void setText(String text) {
                    this.text = text;
                }

                public String getCredit() {
                    return credit;
                }

                public void setCredit(String credit) {
                    this.credit = credit;
                }

                public String getPercent() {
                    return percent;
                }

                public void setPercent(String percent) {
                    this.percent = percent;
                }

                public String getPercent_class() {
                    return percent_class;
                }

                public void setPercent_class(String percent_class) {
                    this.percent_class = percent_class;
                }

                public String getPercent_text() {
                    return percent_text;
                }

                public void setPercent_text(String percent_text) {
                    this.percent_text = percent_text;
                }
            }
        }
    }
}
