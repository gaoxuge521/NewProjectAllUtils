package com.gxg.alltils.projectallutils.model.find.bean;

import com.gxg.alltils.projectallutils.bean.BaseBean;

import java.util.List;

/**
 * 作者：Administrator on 2017/11/24 15:50
 * 邮箱：android_gaoxuge@163.com
 */
public class FindOtherBean  extends BaseBean{

    /**
     * code : 200
     * datas : {"class_list":[{"gc_id":"200","gc_name":"家纺","child":[{"gc_id":"206","gc_name":"床品件套"},{"gc_id":"207","gc_name":"被子"},{"gc_id":"208","gc_name":"枕芯枕套"},{"gc_id":"209","gc_name":"床单被罩"},{"gc_id":"210","gc_name":"毛巾被/毯"},{"gc_id":"211","gc_name":"床垫/床褥"},{"gc_id":"212","gc_name":"蚊帐/凉席"},{"gc_id":"213","gc_name":"抱枕坐垫"},{"gc_id":"214","gc_name":"毛巾家纺"},{"gc_id":"215","gc_name":"电热毯"},{"gc_id":"216","gc_name":"窗帘/窗纱"},{"gc_id":"217","gc_name":"酒店用品"}]},{"gc_id":"201","gc_name":"灯具","child":[{"gc_id":"1069","gc_name":"壁灯"},{"gc_id":"218","gc_name":"台灯"},{"gc_id":"219","gc_name":"节能灯"},{"gc_id":"220","gc_name":"装饰灯"},{"gc_id":"221","gc_name":"落地灯"},{"gc_id":"222","gc_name":"应急灯/手电"},{"gc_id":"223","gc_name":"LED灯"},{"gc_id":"224","gc_name":"吸顶灯"},{"gc_id":"225","gc_name":"五金电器"},{"gc_id":"226","gc_name":"吊灯"},{"gc_id":"227","gc_name":"氛围照明"}]},{"gc_id":"202","gc_name":"生活日用","child":[{"gc_id":"228","gc_name":"收纳用品"},{"gc_id":"229","gc_name":"雨伞雨具"},{"gc_id":"230","gc_name":"浴室用品"},{"gc_id":"231","gc_name":"缝纫用品"},{"gc_id":"232","gc_name":"洗晒用品"},{"gc_id":"233","gc_name":"净化除味"}]},{"gc_id":"203","gc_name":"家装软饰","child":[{"gc_id":"234","gc_name":"桌布/罩件"},{"gc_id":"235","gc_name":"地毯地垫"},{"gc_id":"236","gc_name":"沙发垫套"},{"gc_id":"237","gc_name":"相框/相片墙"},{"gc_id":"238","gc_name":"墙画墙贴"},{"gc_id":"239","gc_name":"节庆饰品"},{"gc_id":"240","gc_name":"手工/十字绣"},{"gc_id":"241","gc_name":"工艺摆件"},{"gc_id":"242","gc_name":"其他"}]},{"gc_id":"204","gc_name":"清洁日用","child":[{"gc_id":"243","gc_name":"纸品湿巾"},{"gc_id":"244","gc_name":"衣物清洁"},{"gc_id":"245","gc_name":"清洁工具"},{"gc_id":"246","gc_name":"驱虫用品"},{"gc_id":"247","gc_name":"居室清洁"},{"gc_id":"248","gc_name":"皮具护理"}]},{"gc_id":"205","gc_name":"宠物生活","child":[{"gc_id":"249","gc_name":"宠物主粮"},{"gc_id":"250","gc_name":"宠物零食"},{"gc_id":"251","gc_name":"营养品"},{"gc_id":"252","gc_name":"家居日用"},{"gc_id":"253","gc_name":"玩具服饰"},{"gc_id":"254","gc_name":"出行装备"},{"gc_id":"255","gc_name":"医护美容"}]}]}
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
        private List<ClassListBean> class_list;

        public List<ClassListBean> getClass_list() {
            return class_list;
        }

        public void setClass_list(List<ClassListBean> class_list) {
            this.class_list = class_list;
        }

        public static class ClassListBean {
            /**
             * gc_id : 200
             * gc_name : 家纺
             * child : [{"gc_id":"206","gc_name":"床品件套"},{"gc_id":"207","gc_name":"被子"},{"gc_id":"208","gc_name":"枕芯枕套"},{"gc_id":"209","gc_name":"床单被罩"},{"gc_id":"210","gc_name":"毛巾被/毯"},{"gc_id":"211","gc_name":"床垫/床褥"},{"gc_id":"212","gc_name":"蚊帐/凉席"},{"gc_id":"213","gc_name":"抱枕坐垫"},{"gc_id":"214","gc_name":"毛巾家纺"},{"gc_id":"215","gc_name":"电热毯"},{"gc_id":"216","gc_name":"窗帘/窗纱"},{"gc_id":"217","gc_name":"酒店用品"}]
             */

            private String gc_id;
            private String gc_name;
            private List<ChildBean> child;

            public String getGc_id() {
                return gc_id;
            }

            public void setGc_id(String gc_id) {
                this.gc_id = gc_id;
            }

            public String getGc_name() {
                return gc_name;
            }

            public void setGc_name(String gc_name) {
                this.gc_name = gc_name;
            }

            public List<ChildBean> getChild() {
                return child;
            }

            public void setChild(List<ChildBean> child) {
                this.child = child;
            }

            public static class ChildBean {
                /**
                 * gc_id : 206
                 * gc_name : 床品件套
                 */

                private String gc_id;
                private String gc_name;

                public String getGc_id() {
                    return gc_id;
                }

                public void setGc_id(String gc_id) {
                    this.gc_id = gc_id;
                }

                public String getGc_name() {
                    return gc_name;
                }

                public void setGc_name(String gc_name) {
                    this.gc_name = gc_name;
                }
            }
        }
    }
}
