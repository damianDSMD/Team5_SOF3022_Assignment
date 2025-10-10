create database Nhom5_Java5;
USE Nhom5_Java5;

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

-- Create DiaChi table
CREATE TABLE DiaChi (
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
    ChucVu VARCHAR(100),
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

INSERT INTO SanPham (MaSP, TenSP, DonGia, SoLuongTonKho, MaLoai) VALUES
('SP001', 'Laptop Dell XPS 13', 25000000, 15, 'LAPTOP'),
('SP002', 'iPhone 15 Pro Max', 32000000, 20, 'PHONE'),
('SP003', 'Samsung Galaxy S24', 22000000, 25, 'PHONE'),
('SP004', 'MacBook Air M2', 28000000, 10, 'LAPTOP'),
('SP005', 'iPad Pro 12.9', 30000000, 12, 'TABLET'),
('SP006', 'AirPods Pro 2', 6500000, 30, 'AUDIO'),
('SP007', 'Sony WH-1000XM5', 8500000, 18, 'AUDIO'),
('SP008', 'Apple Watch Series 9', 11000000, 22, 'WATCH'),
('SP009', 'Gaming PC RTX 4090', 55000000, 5, 'PC'),
('SP010', 'LG OLED TV 55"', 35000000, 8, 'TV');

-- Insert sample data for HinhAnh (Images)
INSERT INTO HinhAnh (MaHA, MaSP, TenHA, FilePath) VALUES
('HA001', 'SP001', 'Dell XPS 13', 'https://images.unsplash.com/photo-1593642632823-8f785ba67e45?w=400'),
('HA002', 'SP002', 'iPhone 15 Pro Max', 'https://images.unsplash.com/photo-1592286927505-f4d08efffb6e?w=400'),
('HA003', 'SP003', 'Samsung S24', 'https://images.unsplash.com/photo-1610945415295-d9bbf067e59c?w=400'),
('HA004', 'SP004', 'MacBook Air', 'https://images.unsplash.com/photo-1517336714731-489689fd1ca8?w=400'),
('HA005', 'SP005', 'iPad Pro', 'https://images.unsplash.com/photo-1544244015-0df4b3ffc6b0?w=400'),
('HA006', 'SP006', 'AirPods Pro', 'https://images.unsplash.com/photo-1606841837239-c5a1a4a07af7?w=400'),
('HA007', 'SP007', 'Sony Headphones', 'https://images.unsplash.com/photo-1545127398-14699f92334b?w=400'),
('HA008', 'SP008', 'Apple Watch', 'https://images.unsplash.com/photo-1579586337278-3befd40fd17a?w=400'),
('HA009', 'SP009', 'Gaming PC', 'https://images.unsplash.com/photo-1587202372634-32705e3bf49c?w=400'),
('HA010', 'SP010', 'LG OLED TV', 'https://images.unsplash.com/photo-1593359677879-a4bb92f829d1?w=400');