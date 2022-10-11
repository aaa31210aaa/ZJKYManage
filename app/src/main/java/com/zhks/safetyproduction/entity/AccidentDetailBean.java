package com.zhks.safetyproduction.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AccidentDetailBean {

    private String message;
    private DataDTO data;
    private String code;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataDTO getData() {
        return data;
    }

    public void setData(DataDTO data) {
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public static class DataDTO {
        @SerializedName("SGDJ")
        private List<SGDJDTO> sGDJ;
        @SerializedName("SGFL")
        private List<SGFLDTO> sGFL;
        @SerializedName("SGLX")
        private List<SGLXDTO> sGLX;
        @SerializedName("JCLX")
        private List<JCLXDTO> jCLX;
        @SerializedName("JCJB")
        private List<JCJBDTO> jCJB;
        @SerializedName("YHLX")
        private List<YHLXDTO> yHLX;
        @SerializedName("SLPT")
        private List<SLPTDTO> sLPT;
        @SerializedName("YHLY")
        private List<YHLYDTO> yHLY;
        @SerializedName("YHJB")
        private List<YHJBDTO> yHJB;
        @SerializedName("YHLB")
        private List<YHLBDTO> yHLB;
        @SerializedName("ZGLX")
        private List<ZGLXDTO> zGLX;


        public List<SGDJDTO> getSGDJ() {
            return sGDJ;
        }

        public void setSGDJ(List<SGDJDTO> sGDJ) {
            this.sGDJ = sGDJ;
        }

        public List<SGFLDTO> getSGFL() {
            return sGFL;
        }

        public void setSGFL(List<SGFLDTO> sGFL) {
            this.sGFL = sGFL;
        }

        public List<SGLXDTO> getSGLX() {
            return sGLX;
        }

        public void setSGLX(List<SGLXDTO> sGLX) {
            this.sGLX = sGLX;
        }

        public List<JCLXDTO> getJCLX() {
            return jCLX;
        }

        public void setJCLX(List<JCLXDTO> JCLX) {
            this.jCLX = JCLX;
        }

        public List<JCJBDTO> getJCJB() {
            return jCJB;
        }

        public void setJCJB(List<JCJBDTO> jCJB) {
            this.jCJB = jCJB;
        }

        public List<YHLXDTO> getyHLX() {
            return yHLX;
        }

        public void setyHLX(List<YHLXDTO> yHLX) {
            this.yHLX = yHLX;
        }

        public List<SLPTDTO> getsLPT() {
            return sLPT;
        }

        public void setsLPT(List<SLPTDTO> sLPT) {
            this.sLPT = sLPT;
        }

        public List<YHLYDTO> getyHLY() {
            return yHLY;
        }

        public void setyHLY(List<YHLYDTO> yHLY) {
            this.yHLY = yHLY;
        }

        public List<YHJBDTO> getyHJB() {
            return yHJB;
        }

        public void setyHJB(List<YHJBDTO> yHJB) {
            this.yHJB = yHJB;
        }

        public List<YHLBDTO> getyHLB() {
            return yHLB;
        }

        public void setyHLB(List<YHLBDTO> yHLB) {
            this.yHLB = yHLB;
        }

        public List<ZGLXDTO> getzGLX() {
            return zGLX;
        }

        public void setzGLX(List<ZGLXDTO> zGLX) {
            this.zGLX = zGLX;
        }

        public static class SGDJDTO {
            private String canmodify;
            private String code;
            private String dicttypeid;
            private String errorMessage;
            private int orderno;
            private String param;
            private String paramname;
            private String paramother;
            private String paramremark;
            private String parentid;
            private String query;
            private String stagetype;
            private boolean success;
            private String typeid;

            public String getCanmodify() {
                return canmodify;
            }

            public void setCanmodify(String canmodify) {
                this.canmodify = canmodify;
            }

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public String getDicttypeid() {
                return dicttypeid;
            }

            public void setDicttypeid(String dicttypeid) {
                this.dicttypeid = dicttypeid;
            }

            public String getErrorMessage() {
                return errorMessage;
            }

            public void setErrorMessage(String errorMessage) {
                this.errorMessage = errorMessage;
            }

            public int getOrderno() {
                return orderno;
            }

            public void setOrderno(int orderno) {
                this.orderno = orderno;
            }

            public String getParam() {
                return param;
            }

            public void setParam(String param) {
                this.param = param;
            }

            public String getParamname() {
                return paramname;
            }

            public void setParamname(String paramname) {
                this.paramname = paramname;
            }

            public String getParamother() {
                return paramother;
            }

            public void setParamother(String paramother) {
                this.paramother = paramother;
            }

            public String getParamremark() {
                return paramremark;
            }

            public void setParamremark(String paramremark) {
                this.paramremark = paramremark;
            }

            public String getParentid() {
                return parentid;
            }

            public void setParentid(String parentid) {
                this.parentid = parentid;
            }

            public String getQuery() {
                return query;
            }

            public void setQuery(String query) {
                this.query = query;
            }

            public String getStagetype() {
                return stagetype;
            }

            public void setStagetype(String stagetype) {
                this.stagetype = stagetype;
            }

            public boolean isSuccess() {
                return success;
            }

            public void setSuccess(boolean success) {
                this.success = success;
            }

            public String getTypeid() {
                return typeid;
            }

            public void setTypeid(String typeid) {
                this.typeid = typeid;
            }
        }

        public static class SGFLDTO {
            private String canmodify;
            private String code;
            private String dicttypeid;
            private String errorMessage;
            private int orderno;
            private String param;
            private String paramname;
            private String paramother;
            private String paramremark;
            private String parentid;
            private String query;
            private String stagetype;
            private boolean success;
            private String typeid;

            public String getCanmodify() {
                return canmodify;
            }

            public void setCanmodify(String canmodify) {
                this.canmodify = canmodify;
            }

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public String getDicttypeid() {
                return dicttypeid;
            }

            public void setDicttypeid(String dicttypeid) {
                this.dicttypeid = dicttypeid;
            }

            public String getErrorMessage() {
                return errorMessage;
            }

            public void setErrorMessage(String errorMessage) {
                this.errorMessage = errorMessage;
            }

            public int getOrderno() {
                return orderno;
            }

            public void setOrderno(int orderno) {
                this.orderno = orderno;
            }

            public String getParam() {
                return param;
            }

            public void setParam(String param) {
                this.param = param;
            }

            public String getParamname() {
                return paramname;
            }

            public void setParamname(String paramname) {
                this.paramname = paramname;
            }

            public String getParamother() {
                return paramother;
            }

            public void setParamother(String paramother) {
                this.paramother = paramother;
            }

            public String getParamremark() {
                return paramremark;
            }

            public void setParamremark(String paramremark) {
                this.paramremark = paramremark;
            }

            public String getParentid() {
                return parentid;
            }

            public void setParentid(String parentid) {
                this.parentid = parentid;
            }

            public String getQuery() {
                return query;
            }

            public void setQuery(String query) {
                this.query = query;
            }

            public String getStagetype() {
                return stagetype;
            }

            public void setStagetype(String stagetype) {
                this.stagetype = stagetype;
            }

            public boolean isSuccess() {
                return success;
            }

            public void setSuccess(boolean success) {
                this.success = success;
            }

            public String getTypeid() {
                return typeid;
            }

            public void setTypeid(String typeid) {
                this.typeid = typeid;
            }
        }

        public static class SGLXDTO {
            private String canmodify;
            private String code;
            private String dicttypeid;
            private String errorMessage;
            private int orderno;
            private String param;
            private String paramname;
            private String paramother;
            private String paramremark;
            private String parentid;
            private String query;
            private String stagetype;
            private boolean success;
            private String typeid;

            public String getCanmodify() {
                return canmodify;
            }

            public void setCanmodify(String canmodify) {
                this.canmodify = canmodify;
            }

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public String getDicttypeid() {
                return dicttypeid;
            }

            public void setDicttypeid(String dicttypeid) {
                this.dicttypeid = dicttypeid;
            }

            public String getErrorMessage() {
                return errorMessage;
            }

            public void setErrorMessage(String errorMessage) {
                this.errorMessage = errorMessage;
            }

            public int getOrderno() {
                return orderno;
            }

            public void setOrderno(int orderno) {
                this.orderno = orderno;
            }

            public String getParam() {
                return param;
            }

            public void setParam(String param) {
                this.param = param;
            }

            public String getParamname() {
                return paramname;
            }

            public void setParamname(String paramname) {
                this.paramname = paramname;
            }

            public String getParamother() {
                return paramother;
            }

            public void setParamother(String paramother) {
                this.paramother = paramother;
            }

            public String getParamremark() {
                return paramremark;
            }

            public void setParamremark(String paramremark) {
                this.paramremark = paramremark;
            }

            public String getParentid() {
                return parentid;
            }

            public void setParentid(String parentid) {
                this.parentid = parentid;
            }

            public String getQuery() {
                return query;
            }

            public void setQuery(String query) {
                this.query = query;
            }

            public String getStagetype() {
                return stagetype;
            }

            public void setStagetype(String stagetype) {
                this.stagetype = stagetype;
            }

            public boolean isSuccess() {
                return success;
            }

            public void setSuccess(boolean success) {
                this.success = success;
            }

            public String getTypeid() {
                return typeid;
            }

            public void setTypeid(String typeid) {
                this.typeid = typeid;
            }
        }

        public static class JCLXDTO {
            private String canmodify;
            private String code;
            private String dicttypeid;
            private String errorMessage;
            private String orderno;
            private String param;
            private String paramname;
            private String paramother;
            private String paramremark;
            private String parentid;
            private String query;
            private String stagetype;
            private String success;
            private String typeid;

            public String getCanmodify() {
                return canmodify;
            }

            public void setCanmodify(String canmodify) {
                this.canmodify = canmodify;
            }

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public String getDicttypeid() {
                return dicttypeid;
            }

            public void setDicttypeid(String dicttypeid) {
                this.dicttypeid = dicttypeid;
            }

            public String getErrorMessage() {
                return errorMessage;
            }

            public void setErrorMessage(String errorMessage) {
                this.errorMessage = errorMessage;
            }

            public String getOrderno() {
                return orderno;
            }

            public void setOrderno(String orderno) {
                this.orderno = orderno;
            }

            public String getParam() {
                return param;
            }

            public void setParam(String param) {
                this.param = param;
            }

            public String getParamname() {
                return paramname;
            }

            public void setParamname(String paramname) {
                this.paramname = paramname;
            }

            public String getParamother() {
                return paramother;
            }

            public void setParamother(String paramother) {
                this.paramother = paramother;
            }

            public String getParamremark() {
                return paramremark;
            }

            public void setParamremark(String paramremark) {
                this.paramremark = paramremark;
            }

            public String getParentid() {
                return parentid;
            }

            public void setParentid(String parentid) {
                this.parentid = parentid;
            }

            public String getQuery() {
                return query;
            }

            public void setQuery(String query) {
                this.query = query;
            }

            public String getStagetype() {
                return stagetype;
            }

            public void setStagetype(String stagetype) {
                this.stagetype = stagetype;
            }

            public String getSuccess() {
                return success;
            }

            public void setSuccess(String success) {
                this.success = success;
            }

            public String getTypeid() {
                return typeid;
            }

            public void setTypeid(String typeid) {
                this.typeid = typeid;
            }
        }

        public static class JCJBDTO {
            private String canmodify;
            private String code;
            private String dicttypeid;
            private String errorMessage;
            private String param;
            private String paramname;
            private String paramother;
            private String paramremark;
            private String parentid;
            private String query;
            private String stagetype;
            private boolean success;
            private String typeid;

            public String getCanmodify() {
                return canmodify;
            }

            public void setCanmodify(String canmodify) {
                this.canmodify = canmodify;
            }

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public String getDicttypeid() {
                return dicttypeid;
            }

            public void setDicttypeid(String dicttypeid) {
                this.dicttypeid = dicttypeid;
            }

            public String getErrorMessage() {
                return errorMessage;
            }

            public void setErrorMessage(String errorMessage) {
                this.errorMessage = errorMessage;
            }

            public String getParam() {
                return param;
            }

            public void setParam(String param) {
                this.param = param;
            }

            public String getParamname() {
                return paramname;
            }

            public void setParamname(String paramname) {
                this.paramname = paramname;
            }

            public String getParamother() {
                return paramother;
            }

            public void setParamother(String paramother) {
                this.paramother = paramother;
            }

            public String getParamremark() {
                return paramremark;
            }

            public void setParamremark(String paramremark) {
                this.paramremark = paramremark;
            }

            public String getParentid() {
                return parentid;
            }

            public void setParentid(String parentid) {
                this.parentid = parentid;
            }

            public String getQuery() {
                return query;
            }

            public void setQuery(String query) {
                this.query = query;
            }

            public String getStagetype() {
                return stagetype;
            }

            public void setStagetype(String stagetype) {
                this.stagetype = stagetype;
            }

            public boolean isSuccess() {
                return success;
            }

            public void setSuccess(boolean success) {
                this.success = success;
            }

            public String getTypeid() {
                return typeid;
            }

            public void setTypeid(String typeid) {
                this.typeid = typeid;
            }
        }

        public static class YHLXDTO {
            private String canmodify;
            private String code;
            private String dicttypeid;
            private String errorMessage;
            private String orderno;
            private String param;
            private String paramname;
            private String paramother;
            private String paramremark;
            private String parentid;
            private String query;
            private String stagetype;
            private String success;
            private String typeid;

            public String getCanmodify() {
                return canmodify;
            }

            public void setCanmodify(String canmodify) {
                this.canmodify = canmodify;
            }

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public String getDicttypeid() {
                return dicttypeid;
            }

            public void setDicttypeid(String dicttypeid) {
                this.dicttypeid = dicttypeid;
            }

            public String getErrorMessage() {
                return errorMessage;
            }

            public void setErrorMessage(String errorMessage) {
                this.errorMessage = errorMessage;
            }

            public String getOrderno() {
                return orderno;
            }

            public void setOrderno(String orderno) {
                this.orderno = orderno;
            }

            public String getParam() {
                return param;
            }

            public void setParam(String param) {
                this.param = param;
            }

            public String getParamname() {
                return paramname;
            }

            public void setParamname(String paramname) {
                this.paramname = paramname;
            }

            public String getParamother() {
                return paramother;
            }

            public void setParamother(String paramother) {
                this.paramother = paramother;
            }

            public String getParamremark() {
                return paramremark;
            }

            public void setParamremark(String paramremark) {
                this.paramremark = paramremark;
            }

            public String getParentid() {
                return parentid;
            }

            public void setParentid(String parentid) {
                this.parentid = parentid;
            }

            public String getQuery() {
                return query;
            }

            public void setQuery(String query) {
                this.query = query;
            }

            public String getStagetype() {
                return stagetype;
            }

            public void setStagetype(String stagetype) {
                this.stagetype = stagetype;
            }

            public String isSuccess() {
                return success;
            }

            public void setSuccess(String success) {
                this.success = success;
            }

            public String getTypeid() {
                return typeid;
            }

            public void setTypeid(String typeid) {
                this.typeid = typeid;
            }
        }

        public static class SLPTDTO {
            private String canmodify;
            private String code;
            private String dicttypeid;
            private String errorMessage;
            private String orderno;
            private String param;
            private String paramname;
            private String paramother;
            private String paramremark;
            private String parentid;
            private String query;
            private String stagetype;
            private String success;
            private String typeid;

            public String getCanmodify() {
                return canmodify;
            }

            public void setCanmodify(String canmodify) {
                this.canmodify = canmodify;
            }

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public String getDicttypeid() {
                return dicttypeid;
            }

            public void setDicttypeid(String dicttypeid) {
                this.dicttypeid = dicttypeid;
            }

            public String getErrorMessage() {
                return errorMessage;
            }

            public void setErrorMessage(String errorMessage) {
                this.errorMessage = errorMessage;
            }

            public String getOrderno() {
                return orderno;
            }

            public void setOrderno(String orderno) {
                this.orderno = orderno;
            }

            public String getParam() {
                return param;
            }

            public void setParam(String param) {
                this.param = param;
            }

            public String getParamname() {
                return paramname;
            }

            public void setParamname(String paramname) {
                this.paramname = paramname;
            }

            public String getParamother() {
                return paramother;
            }

            public void setParamother(String paramother) {
                this.paramother = paramother;
            }

            public String getParamremark() {
                return paramremark;
            }

            public void setParamremark(String paramremark) {
                this.paramremark = paramremark;
            }

            public String getParentid() {
                return parentid;
            }

            public void setParentid(String parentid) {
                this.parentid = parentid;
            }

            public String getQuery() {
                return query;
            }

            public void setQuery(String query) {
                this.query = query;
            }

            public String getStagetype() {
                return stagetype;
            }

            public void setStagetype(String stagetype) {
                this.stagetype = stagetype;
            }

            public String isSuccess() {
                return success;
            }

            public void setSuccess(String success) {
                this.success = success;
            }

            public String getTypeid() {
                return typeid;
            }

            public void setTypeid(String typeid) {
                this.typeid = typeid;
            }
        }

        public static class YHLYDTO {
            private String canmodify;
            private String code;
            private String dicttypeid;
            private String errorMessage;
            private String orderno;
            private String param;
            private String paramname;
            private String paramother;
            private String paramremark;
            private String parentid;
            private String query;
            private String stagetype;
            private String success;
            private String typeid;

            public String getCanmodify() {
                return canmodify;
            }

            public void setCanmodify(String canmodify) {
                this.canmodify = canmodify;
            }

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public String getDicttypeid() {
                return dicttypeid;
            }

            public void setDicttypeid(String dicttypeid) {
                this.dicttypeid = dicttypeid;
            }

            public String getErrorMessage() {
                return errorMessage;
            }

            public void setErrorMessage(String errorMessage) {
                this.errorMessage = errorMessage;
            }

            public String getOrderno() {
                return orderno;
            }

            public void setOrderno(String orderno) {
                this.orderno = orderno;
            }

            public String getParam() {
                return param;
            }

            public void setParam(String param) {
                this.param = param;
            }

            public String getParamname() {
                return paramname;
            }

            public void setParamname(String paramname) {
                this.paramname = paramname;
            }

            public String getParamother() {
                return paramother;
            }

            public void setParamother(String paramother) {
                this.paramother = paramother;
            }

            public String getParamremark() {
                return paramremark;
            }

            public void setParamremark(String paramremark) {
                this.paramremark = paramremark;
            }

            public String getParentid() {
                return parentid;
            }

            public void setParentid(String parentid) {
                this.parentid = parentid;
            }

            public String getQuery() {
                return query;
            }

            public void setQuery(String query) {
                this.query = query;
            }

            public String getStagetype() {
                return stagetype;
            }

            public void setStagetype(String stagetype) {
                this.stagetype = stagetype;
            }

            public String isSuccess() {
                return success;
            }

            public void setSuccess(String success) {
                this.success = success;
            }

            public String getTypeid() {
                return typeid;
            }

            public void setTypeid(String typeid) {
                this.typeid = typeid;
            }
        }

        public static class YHJBDTO {
            private String canmodify;
            private String code;
            private String dicttypeid;
            private String errorMessage;
            private String orderno;
            private String param;
            private String paramname;
            private String paramother;
            private String paramremark;
            private String parentid;
            private String query;
            private String stagetype;
            private String success;
            private String typeid;

            public String getCanmodify() {
                return canmodify;
            }

            public void setCanmodify(String canmodify) {
                this.canmodify = canmodify;
            }

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public String getDicttypeid() {
                return dicttypeid;
            }

            public void setDicttypeid(String dicttypeid) {
                this.dicttypeid = dicttypeid;
            }

            public String getErrorMessage() {
                return errorMessage;
            }

            public void setErrorMessage(String errorMessage) {
                this.errorMessage = errorMessage;
            }

            public String getOrderno() {
                return orderno;
            }

            public void setOrderno(String orderno) {
                this.orderno = orderno;
            }

            public String getParam() {
                return param;
            }

            public void setParam(String param) {
                this.param = param;
            }

            public String getParamname() {
                return paramname;
            }

            public void setParamname(String paramname) {
                this.paramname = paramname;
            }

            public String getParamother() {
                return paramother;
            }

            public void setParamother(String paramother) {
                this.paramother = paramother;
            }

            public String getParamremark() {
                return paramremark;
            }

            public void setParamremark(String paramremark) {
                this.paramremark = paramremark;
            }

            public String getParentid() {
                return parentid;
            }

            public void setParentid(String parentid) {
                this.parentid = parentid;
            }

            public String getQuery() {
                return query;
            }

            public void setQuery(String query) {
                this.query = query;
            }

            public String getStagetype() {
                return stagetype;
            }

            public void setStagetype(String stagetype) {
                this.stagetype = stagetype;
            }

            public String isSuccess() {
                return success;
            }

            public void setSuccess(String success) {
                this.success = success;
            }

            public String getTypeid() {
                return typeid;
            }

            public void setTypeid(String typeid) {
                this.typeid = typeid;
            }
        }

        public static class YHLBDTO {
            private String canmodify;
            private String code;
            private String dicttypeid;
            private String errorMessage;
            private String orderno;
            private String param;
            private String paramname;
            private String paramother;
            private String paramremark;
            private String parentid;
            private String query;
            private String stagetype;
            private String success;
            private String typeid;

            public String getCanmodify() {
                return canmodify;
            }

            public void setCanmodify(String canmodify) {
                this.canmodify = canmodify;
            }

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public String getDicttypeid() {
                return dicttypeid;
            }

            public void setDicttypeid(String dicttypeid) {
                this.dicttypeid = dicttypeid;
            }

            public String getErrorMessage() {
                return errorMessage;
            }

            public void setErrorMessage(String errorMessage) {
                this.errorMessage = errorMessage;
            }

            public String getOrderno() {
                return orderno;
            }

            public void setOrderno(String orderno) {
                this.orderno = orderno;
            }

            public String getParam() {
                return param;
            }

            public void setParam(String param) {
                this.param = param;
            }

            public String getParamname() {
                return paramname;
            }

            public void setParamname(String paramname) {
                this.paramname = paramname;
            }

            public String getParamother() {
                return paramother;
            }

            public void setParamother(String paramother) {
                this.paramother = paramother;
            }

            public String getParamremark() {
                return paramremark;
            }

            public void setParamremark(String paramremark) {
                this.paramremark = paramremark;
            }

            public String getParentid() {
                return parentid;
            }

            public void setParentid(String parentid) {
                this.parentid = parentid;
            }

            public String getQuery() {
                return query;
            }

            public void setQuery(String query) {
                this.query = query;
            }

            public String getStagetype() {
                return stagetype;
            }

            public void setStagetype(String stagetype) {
                this.stagetype = stagetype;
            }

            public String isSuccess() {
                return success;
            }

            public void setSuccess(String success) {
                this.success = success;
            }

            public String getTypeid() {
                return typeid;
            }

            public void setTypeid(String typeid) {
                this.typeid = typeid;
            }
        }

        public static class ZGLXDTO {
            private String canmodify;
            private String code;
            private String dicttypeid;
            private String errorMessage;
            private String orderno;
            private String param;
            private String paramname;
            private String paramother;
            private String paramremark;
            private String parentid;
            private String query;
            private String stagetype;
            private String success;
            private String typeid;

            public String getCanmodify() {
                return canmodify;
            }

            public void setCanmodify(String canmodify) {
                this.canmodify = canmodify;
            }

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public String getDicttypeid() {
                return dicttypeid;
            }

            public void setDicttypeid(String dicttypeid) {
                this.dicttypeid = dicttypeid;
            }

            public String getErrorMessage() {
                return errorMessage;
            }

            public void setErrorMessage(String errorMessage) {
                this.errorMessage = errorMessage;
            }

            public String getOrderno() {
                return orderno;
            }

            public void setOrderno(String orderno) {
                this.orderno = orderno;
            }

            public String getParam() {
                return param;
            }

            public void setParam(String param) {
                this.param = param;
            }

            public String getParamname() {
                return paramname;
            }

            public void setParamname(String paramname) {
                this.paramname = paramname;
            }

            public String getParamother() {
                return paramother;
            }

            public void setParamother(String paramother) {
                this.paramother = paramother;
            }

            public String getParamremark() {
                return paramremark;
            }

            public void setParamremark(String paramremark) {
                this.paramremark = paramremark;
            }

            public String getParentid() {
                return parentid;
            }

            public void setParentid(String parentid) {
                this.parentid = parentid;
            }

            public String getQuery() {
                return query;
            }

            public void setQuery(String query) {
                this.query = query;
            }

            public String getStagetype() {
                return stagetype;
            }

            public void setStagetype(String stagetype) {
                this.stagetype = stagetype;
            }

            public String isSuccess() {
                return success;
            }

            public void setSuccess(String success) {
                this.success = success;
            }

            public String getTypeid() {
                return typeid;
            }

            public void setTypeid(String typeid) {
                this.typeid = typeid;
            }
        }
    }
}
