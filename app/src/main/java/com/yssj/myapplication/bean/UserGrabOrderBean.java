package com.yssj.myapplication.bean;

import java.io.Serializable;
import java.util.List;


public class UserGrabOrderBean implements Serializable {


    private PagerDTO pager;
    private String message;
    private String status;
    private List<GrabOrderListDTO> grabOrderList;

    public PagerDTO getPager() {
        return pager;
    }

    public void setPager(PagerDTO pager) {
        this.pager = pager;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<GrabOrderListDTO> getGrabOrderList() {
        return grabOrderList;
    }

    public void setGrabOrderList(List<GrabOrderListDTO> grabOrderList) {
        this.grabOrderList = grabOrderList;
    }

    public static class PagerDTO implements Serializable {
        private int pageCount;
        private int curPage;
        private int pageSize;
        private int rowCount;

        public int getPageCount() {
            return pageCount;
        }

        public void setPageCount(int pageCount) {
            this.pageCount = pageCount;
        }

        public int getCurPage() {
            return curPage;
        }

        public void setCurPage(int curPage) {
            this.curPage = curPage;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public int getRowCount() {
            return rowCount;
        }

        public void setRowCount(int rowCount) {
            this.rowCount = rowCount;
        }
    }


    public static class GrabOrderListDTO implements Serializable {
        private int id;
        private int user_id;
        private String name;
        private String shop_code;
        private String color;
        private String batch_num;
        private int quality_requirement;
        private double money;
        private int receive_address_id;
        private int restore_address_id;
        private int status;
        private int qc_status;
        private String create_date;
        private ReceiveAddressDTO receive_address;
        private RestoreAddressDTO restore_address;
        private List<StockTypesDTO> stock_types;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getShop_code() {
            return shop_code;
        }

        public void setShop_code(String shop_code) {
            this.shop_code = shop_code;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public String getBatch_num() {
            return batch_num;
        }

        public void setBatch_num(String batch_num) {
            this.batch_num = batch_num;
        }

        public int getQuality_requirement() {
            return quality_requirement;
        }

        public void setQuality_requirement(int quality_requirement) {
            this.quality_requirement = quality_requirement;
        }

        public double getMoney() {
            return money;
        }

        public void setMoney(double money) {
            this.money = money;
        }

        public int getReceive_address_id() {
            return receive_address_id;
        }

        public void setReceive_address_id(int receive_address_id) {
            this.receive_address_id = receive_address_id;
        }

        public int getRestore_address_id() {
            return restore_address_id;
        }

        public void setRestore_address_id(int restore_address_id) {
            this.restore_address_id = restore_address_id;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getQc_status() {
            return qc_status;
        }

        public void setQc_status(int qc_status) {
            this.qc_status = qc_status;
        }

        public String getCreate_date() {
            return create_date;
        }

        public void setCreate_date(String create_date) {
            this.create_date = create_date;
        }

        public ReceiveAddressDTO getReceive_address() {
            return receive_address;
        }

        public void setReceive_address(ReceiveAddressDTO receive_address) {
            this.receive_address = receive_address;
        }

        public RestoreAddressDTO getRestore_address() {
            return restore_address;
        }

        public void setRestore_address(RestoreAddressDTO restore_address) {
            this.restore_address = restore_address;
        }

        public List<StockTypesDTO> getStock_types() {
            return stock_types;
        }

        public void setStock_types(List<StockTypesDTO> stock_types) {
            this.stock_types = stock_types;
        }

        public static class ReceiveAddressDTO implements Serializable {
            private int id;
            private int province;
            private int city;
            private int area;
            private int street;
            private String address;
            private String consignee;
            private String postcode;
            private String phone;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getProvince() {
                return province;
            }

            public void setProvince(int province) {
                this.province = province;
            }

            public int getCity() {
                return city;
            }

            public void setCity(int city) {
                this.city = city;
            }

            public int getArea() {
                return area;
            }

            public void setArea(int area) {
                this.area = area;
            }

            public int getStreet() {
                return street;
            }

            public void setStreet(int street) {
                this.street = street;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getConsignee() {
                return consignee;
            }

            public void setConsignee(String consignee) {
                this.consignee = consignee;
            }

            public String getPostcode() {
                return postcode;
            }

            public void setPostcode(String postcode) {
                this.postcode = postcode;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }
        }


        public static class RestoreAddressDTO implements Serializable {
            private int id;
            private int province;
            private int city;
            private int area;
            private int street;
            private String address;
            private String consignee;
            private String postcode;
            private String phone;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getProvince() {
                return province;
            }

            public void setProvince(int province) {
                this.province = province;
            }

            public int getCity() {
                return city;
            }

            public void setCity(int city) {
                this.city = city;
            }

            public int getArea() {
                return area;
            }

            public void setArea(int area) {
                this.area = area;
            }

            public int getStreet() {
                return street;
            }

            public void setStreet(int street) {
                this.street = street;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getConsignee() {
                return consignee;
            }

            public void setConsignee(String consignee) {
                this.consignee = consignee;
            }

            public String getPostcode() {
                return postcode;
            }

            public void setPostcode(String postcode) {
                this.postcode = postcode;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }
        }


        public static class StockTypesDTO implements Serializable {
            private int id;
            private String user_grab_order_code;
            private String name;
            private String shop_code;
            private int num;
            private String create_date;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getUser_grab_order_code() {
                return user_grab_order_code;
            }

            public void setUser_grab_order_code(String user_grab_order_code) {
                this.user_grab_order_code = user_grab_order_code;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getShop_code() {
                return shop_code;
            }

            public void setShop_code(String shop_code) {
                this.shop_code = shop_code;
            }

            public int getNum() {
                return num;
            }

            public void setNum(int num) {
                this.num = num;
            }

            public String getCreate_date() {
                return create_date;
            }

            public void setCreate_date(String create_date) {
                this.create_date = create_date;
            }
        }
    }
}
