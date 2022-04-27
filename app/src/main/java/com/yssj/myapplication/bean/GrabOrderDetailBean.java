package com.yssj.myapplication.bean;

import java.io.Serializable;
import java.util.List;


public class GrabOrderDetailBean implements Serializable {

    private String pic_list;
    private String message;
    private GrabOrdersDTO grabOrders;
    private String status;

    public String getPic_list() {
        return pic_list;
    }

    public void setPic_list(String pic_list) {
        this.pic_list = pic_list;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public GrabOrdersDTO getGrabOrders() {
        return grabOrders;
    }

    public void setGrabOrders(GrabOrdersDTO grabOrders) {
        this.grabOrders = grabOrders;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public static class GrabOrdersDTO implements Serializable {
        private int id;
        private int status;
        private String name;
        private String shop_code;
        private String color;
        private String batch_num;
        private int quality_requirement;
        private double money;
        private int receive_address_id;
        private int restore_address_id;
        private int delivery_time;
        private String create_date;
        private String update_date;
        private String push_date;
        private ReceiveAddressDTO receive_address;
        private RestoreAddressDTO restore_address;
        private List<StockTypesDTO> stock_types;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
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

        public int getDelivery_time() {
            return delivery_time;
        }

        public void setDelivery_time(int delivery_time) {
            this.delivery_time = delivery_time;
        }

        public String getCreate_date() {
            return create_date;
        }

        public void setCreate_date(String create_date) {
            this.create_date = create_date;
        }

        public String getUpdate_date() {
            return update_date;
        }

        public void setUpdate_date(String update_date) {
            this.update_date = update_date;
        }

        public String getPush_date() {
            return push_date;
        }

        public void setPush_date(String push_date) {
            this.push_date = push_date;
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
            private int grab_orders_id;
            private String name;
            private int surplus_num;
            private int num;
            private int input_num;
            private String create_date;
            private String update_date;

            public int getInput_num() {
                return input_num;
            }

            public void setInput_num(int input_num) {
                this.input_num = input_num;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getGrab_orders_id() {
                return grab_orders_id;
            }

            public void setGrab_orders_id(int grab_orders_id) {
                this.grab_orders_id = grab_orders_id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getSurplus_num() {
                return surplus_num;
            }

            public void setSurplus_num(int surplus_num) {
                this.surplus_num = surplus_num;
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

            public String getUpdate_date() {
                return update_date;
            }

            public void setUpdate_date(String update_date) {
                this.update_date = update_date;
            }
        }
    }
}
