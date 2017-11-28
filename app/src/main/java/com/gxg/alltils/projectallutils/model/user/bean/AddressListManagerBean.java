package com.gxg.alltils.projectallutils.model.user.bean;

import com.gxg.alltils.projectallutils.bean.BaseBean;

import java.util.List;

/**
 * 作者：Administrator on 2017/11/28 15:07
 * 邮箱：android_gaoxuge@163.com
 * 收货地址管理的bean
 */
public class AddressListManagerBean extends BaseBean {

    /**
     * code : 200
     * datas : {"address_list":[{"address_id":"55","member_id":"222","true_name":"的话教室上课","area_id":"37","city_id":"36","area_info":"北京 北京市 东城区","address":"夏季军训基地就","tel_phone":null,"mob_phone":"15810212656","is_default":"0","dlyp_id":"0"},{"address_id":"54","member_id":"222","true_name":"时尚","area_id":"37","city_id":"36","area_info":"北京 北京市 东城区","address":"你休息好就是就是","tel_phone":null,"mob_phone":"15735804834","is_default":"1","dlyp_id":"0"}]}
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
        private List<AddressListBean> address_list;

        public List<AddressListBean> getAddress_list() {
            return address_list;
        }

        public void setAddress_list(List<AddressListBean> address_list) {
            this.address_list = address_list;
        }

        public static class AddressListBean {
            /**
             * address_id : 55
             * member_id : 222
             * true_name : 的话教室上课
             * area_id : 37
             * city_id : 36
             * area_info : 北京 北京市 东城区
             * address : 夏季军训基地就
             * tel_phone : null
             * mob_phone : 15810212656
             * is_default : 0
             * dlyp_id : 0
             */

            private String address_id;
            private String member_id;
            private String true_name;
            private String area_id;
            private String city_id;
            private String area_info;
            private String address;
            private Object tel_phone;
            private String mob_phone;
            private String is_default;
            private String dlyp_id;

            public String getAddress_id() {
                return address_id;
            }

            public void setAddress_id(String address_id) {
                this.address_id = address_id;
            }

            public String getMember_id() {
                return member_id;
            }

            public void setMember_id(String member_id) {
                this.member_id = member_id;
            }

            public String getTrue_name() {
                return true_name;
            }

            public void setTrue_name(String true_name) {
                this.true_name = true_name;
            }

            public String getArea_id() {
                return area_id;
            }

            public void setArea_id(String area_id) {
                this.area_id = area_id;
            }

            public String getCity_id() {
                return city_id;
            }

            public void setCity_id(String city_id) {
                this.city_id = city_id;
            }

            public String getArea_info() {
                return area_info;
            }

            public void setArea_info(String area_info) {
                this.area_info = area_info;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public Object getTel_phone() {
                return tel_phone;
            }

            public void setTel_phone(Object tel_phone) {
                this.tel_phone = tel_phone;
            }

            public String getMob_phone() {
                return mob_phone;
            }

            public void setMob_phone(String mob_phone) {
                this.mob_phone = mob_phone;
            }

            public String getIs_default() {
                return is_default;
            }

            public void setIs_default(String is_default) {
                this.is_default = is_default;
            }

            public String getDlyp_id() {
                return dlyp_id;
            }

            public void setDlyp_id(String dlyp_id) {
                this.dlyp_id = dlyp_id;
            }
        }
    }
}
