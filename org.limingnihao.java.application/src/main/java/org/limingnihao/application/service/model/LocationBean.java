package org.limingnihao.application.service.model;

public class LocationBean {

    private String address;
    private Content content;
    private Integer status;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }



    public class Content{
        private String address;
        private AddressDetail address_detail;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public AddressDetail getAddress_detail() {
            return address_detail;
        }

        public void setAddress_detail(AddressDetail address_detail) {
            this.address_detail = address_detail;
        }
    }



    public class AddressDetail{
        private String city;
        private Integer city_code;
        private String district;
        private String province;

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public Integer getCity_code() {
            return city_code;
        }

        public void setCity_code(Integer city_code) {
            this.city_code = city_code;
        }

        public String getDistrict() {
            return district;
        }

        public void setDistrict(String district) {
            this.district = district;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }
    }
}
