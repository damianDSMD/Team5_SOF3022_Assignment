package org.example.nhom5_assignment_java5.dto;

public class CartItem {
    private Integer sanPhamId;
    private Integer soLuong;

    public CartItem() {}
    public CartItem(Integer sanPhamId, Integer soLuong) {
        this.sanPhamId = sanPhamId;
        this.soLuong = soLuong;
    }

    public Integer getSanPhamId() {
        return sanPhamId;
    }

    public void setSanPhamId(Integer sanPhamId) {
        this.sanPhamId = sanPhamId;
    }

    public Integer getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(Integer soLuong) {
        this.soLuong = soLuong;
    }
}
