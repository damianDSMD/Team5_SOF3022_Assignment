package org.example.nhom5_assignment_java5.service;

import org.example.nhom5_assignment_java5.entity.GioHang;
import org.example.nhom5_assignment_java5.entity.KhachHang;
import org.example.nhom5_assignment_java5.entity.SanPham;
import org.example.nhom5_assignment_java5.repository.GioHangRepository;
import org.example.nhom5_assignment_java5.repository.SanPhamRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GioHangService {

    private final GioHangRepository gioHangRepository;
    private final SanPhamRepository sanPhamRepository;

    public GioHangService(GioHangRepository gioHangRepository, SanPhamRepository sanPhamRepository) {
        this.gioHangRepository = gioHangRepository;
        this.sanPhamRepository = sanPhamRepository;
    }

    // ✅ Lấy danh sách sản phẩm trong giỏ của 1 khách hàng
    public List<GioHang> getGioHangByKhachHang(KhachHang kh) {
        return gioHangRepository.findByKhachHang(kh);
    }

    // ✅ Thêm sản phẩm vào giỏ hàng
    public void addToCart(KhachHang kh, String maSP, int soLuong) {
        GioHang gioHang = gioHangRepository.findByKhachHangAndSanPham_MaSP(kh, maSP);

        if (gioHang == null) { // nếu chưa có sản phẩm này trong giỏ
            gioHang = new GioHang();
            gioHang.setKhachHang(kh);

            SanPham sanPham = sanPhamRepository.findById(maSP)
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm có mã: " + maSP));
            gioHang.setSanPham(sanPham);
            gioHang.setSoLuong(soLuong);
        } else {
            gioHang.setSoLuong(gioHang.getSoLuong() + soLuong);
        }

        gioHangRepository.save(gioHang);
    }

    // ✅ Tính tổng số lượng sản phẩm trong giỏ
    public int getTotalItems(KhachHang kh) {
        List<GioHang> gioHangList = gioHangRepository.findByKhachHang(kh);
        return gioHangList.stream()
                .mapToInt(GioHang::getSoLuong)
                .sum();
    }

    // ✅ Cập nhật số lượng
    public void updateSoLuong(Integer maGH, int soLuong) {
        GioHang gh = gioHangRepository.findById(maGH).orElseThrow();
        gh.setSoLuong(soLuong);
        gioHangRepository.save(gh);
    }

    // ✅ Xóa 1 sản phẩm khỏi giỏ
    public void removeItem(Integer id) {
        gioHangRepository.deleteById(id);
    }

    // ✅ Xóa toàn bộ giỏ của khách hàng
    public void clearCart(KhachHang kh) {
        List<GioHang> gioHangList = gioHangRepository.findByKhachHang(kh);
        gioHangRepository.deleteAll(gioHangList);
    }
}
