package org.example.nhom5_assignment_java5.dto;

import java.util.List;

public class CheckoutForm {
    private String diaChiId;    // nếu chọn địa chỉ
    private String diaChiText;   // nếu nhập mới
    private String ghiChu;
    private List<CartItem> items;

    public String getDiaChiId() {
        return diaChiId;
    }

    public void setDiaChiId(String diaChiId) {
        this.diaChiId = diaChiId;
    }

    public String getDiaChiText() {
        return diaChiText;
    }

    public void setDiaChiText(String diaChiText) {
        this.diaChiText = diaChiText;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public List<CartItem> getItems() {
        return items;
    }

    public void setItems(List<CartItem> items) {
        this.items = items;
    }
}
