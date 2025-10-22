package org.example.nhom5_assignment_java5.dto;

import java.util.List;

public class CheckoutForm {
    private Integer diaChiId;     // chọn địa chỉ có sẵn
    private String diaChiText;    // nhập địa chỉ mới
    private String ghiChu;
    private List<CartItem> items;

    public Integer getDiaChiId() { return diaChiId; }
    public void setDiaChiId(Integer diaChiId) { this.diaChiId = diaChiId; }

    public String getDiaChiText() { return diaChiText; }
    public void setDiaChiText(String diaChiText) { this.diaChiText = diaChiText; }

    public String getGhiChu() { return ghiChu; }
    public void setGhiChu(String ghiChu) { this.ghiChu = ghiChu; }

    public List<CartItem> getItems() { return items; }
    public void setItems(List<CartItem> items) { this.items = items; }
}
