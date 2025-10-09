-- Create KhachHang table
CREATE TABLE KhachHang (
    MaKH VARCHAR(50) PRIMARY KEY,
    TenKH VARCHAR(255) NOT NULL,
    SDT VARCHAR(20),
    Email VARCHAR(255),
    Password VARCHAR(255),
    HangTV VARCHAR(50)
);

-- Create GioHang table
CREATE TABLE GioHang (
    MaGH VARCHAR(50) PRIMARY KEY,
    MaKH VARCHAR(50),
    SoLuong INT,
    FOREIGN KEY (MaKH) REFERENCES KhachHang(MaKH)
);

-- Create DaiChi table
CREATE TABLE DaiChi (
    MaDC VARCHAR(50) PRIMARY KEY,
    MaKH VARCHAR(50),
    DiaChi VARCHAR(500),
    MacDinh VARCHAR(10),
    FOREIGN KEY (MaKH) REFERENCES KhachHang(MaKH)
);

-- Create HoaDon table
CREATE TABLE HoaDon (
    MaHD VARCHAR(50) PRIMARY KEY,
    MaKH VARCHAR(50),
    MaNV VARCHAR(50),
    MaDC VARCHAR(50),
    NgayTaoHD DATE,
    NgayUpHD DATE,
    MaGG VARCHAR(50),
    TongTien DECIMAL(15, 2),
    TrangThai VARCHAR(100),
    FOREIGN KEY (MaKH) REFERENCES KhachHang(MaKH)
);

-- Create SanPham table
CREATE TABLE SanPham (
    MaSP VARCHAR(50) PRIMARY KEY,
    TenSP VARCHAR(255) NOT NULL,
    DonGia DECIMAL(15, 2),
    SoLuongTonKho INT,
    MaLoai VARCHAR(50)
);

-- Create HinhAnh table
CREATE TABLE HinhAnh (
    MaHA VARCHAR(50) PRIMARY KEY,
    MaSP VARCHAR(50),
    TenHA VARCHAR(255),
    FilePath VARCHAR(500),  -- Path to image file
    FOREIGN KEY (MaSP) REFERENCES SanPham(MaSP)
);


-- Create NhapKho table
CREATE TABLE NhapKho (
    MaNK VARCHAR(50) PRIMARY KEY,
    MaSP VARCHAR(50),
    SoLuong INT,
    MaNV VARCHAR(50),
    NgayNhap DATE,
    FOREIGN KEY (MaSP) REFERENCES SanPham(MaSP)
);

-- Create GiamGia table
CREATE TABLE GiamGia (
    MaGG VARCHAR(50) PRIMARY KEY,
    MaNV VARCHAR(50),
    TenGG VARCHAR(255),
    TienGiam DECIMAL(15, 2),
    HangTV VARCHAR(50),
    SoLuong INT,
    NgayBatDau DATE,
    NgayKetThuc DATE
);

-- Create HoaDonChiTiet table
CREATE TABLE HoaDonChiTiet (
    MaHD VARCHAR(50),
    MaSP VARCHAR(50),
    SL INT,
    TienSP DECIMAL(15, 2),
    PRIMARY KEY (MaHD, MaSP),
    FOREIGN KEY (MaHD) REFERENCES HoaDon(MaHD),
    FOREIGN KEY (MaSP) REFERENCES SanPham(MaSP)
);

-- Create NhanVien table
CREATE TABLE NhanVien (
    MaNV VARCHAR(50) PRIMARY KEY,
    TenNV VARCHAR(255) NOT NULL,
    SDT VARCHAR(20),
    Email VARCHAR(255),
    ChuoVu VARCHAR(100),
    Password VARCHAR(255)
);

-- Add foreign key constraints for tables referencing NhanVien
ALTER TABLE HoaDon
ADD CONSTRAINT FK_HoaDon_NhanVien
FOREIGN KEY (MaNV) REFERENCES NhanVien(MaNV);

ALTER TABLE GiamGia
ADD CONSTRAINT FK_GiamGia_NhanVien
FOREIGN KEY (MaNV) REFERENCES NhanVien(MaNV);

ALTER TABLE NhapKho
ADD CONSTRAINT FK_NhapKho_NhanVien
FOREIGN KEY (MaNV) REFERENCES NhanVien(MaNV);

-- Add foreign key constraint for HoaDon referencing GiamGia
ALTER TABLE HoaDon
ADD CONSTRAINT FK_HoaDon_GiamGia
FOREIGN KEY (MaGG) REFERENCES GiamGia(MaGG);