CREATE DATABASE FastFood;
GO
USE FastFood;
GO

-- 1. BẢNG VAI TRÒ (Admin, Khách hàng, Shipper)
CREATE TABLE VaiTro (
    MaVaiTro INT IDENTITY(1,1) PRIMARY KEY,
    TenVaiTro NVARCHAR(50) NOT NULL UNIQUE -- 'Admin', 'KhachHang', 'Shipper'
);
GO

-- 2. BẢNG NGƯỜI DÙNG (Quản lý chung tất cả user)
CREATE TABLE NguoiDung (
    MaNguoiDung INT IDENTITY(1,1) PRIMARY KEY,
    MaVaiTro INT NOT NULL,
    TaiKhoan VARCHAR(50) UNIQUE NOT NULL,
    MatKhau VARCHAR(100) NOT NULL,
    HoTen NVARCHAR(100) NOT NULL,
    Email VARCHAR(100),
    SDT VARCHAR(15),
    DiaChimacDinh NVARCHAR(255),
    NgayTao DATETIME DEFAULT GETDATE(),
    
    CONSTRAINT FK_NguoiDung_VaiTro FOREIGN KEY (MaVaiTro) REFERENCES VaiTro(MaVaiTro)
        ON DELETE CASCADE ON UPDATE CASCADE
);
GO

-- 3. BẢNG LOẠI MÓN ĂN (Burger, Gà, Nước...)
CREATE TABLE LoaiMonAn (
    MaLoai INT IDENTITY(1,1) PRIMARY KEY,
    TenLoai NVARCHAR(100) UNIQUE NOT NULL,
    HinhAnhLoai VARCHAR(MAX) -- Icon cho đẹp giao diện
);
GO

-- 4. BẢNG MÓN ĂN (Sản phẩm chính)
CREATE TABLE MonAn (
    MaMon INT IDENTITY(1,1) PRIMARY KEY,
    MaLoai INT NOT NULL,
    TenMon NVARCHAR(150) NOT NULL,
    MoTa NVARCHAR(MAX), -- Mô tả thành phần: Bò, phô mai, xà lách...
    GiaBan DECIMAL(18,0) NOT NULL CHECK (GiaBan >= 0),
    HinhAnhMon VARCHAR(MAX),
    DaBan INT DEFAULT 0, -- Đếm số lượng đã bán (để hiện món Hot)
    TrangThai BIT DEFAULT 1, -- 1: Còn bán, 0: Hết hàng/Ngừng bán
    
    CONSTRAINT FK_MonAn_Loai FOREIGN KEY (MaLoai) REFERENCES LoaiMonAn(MaLoai)
        ON DELETE CASCADE ON UPDATE CASCADE
);
GO

-- 5. BẢNG MÃ GIẢM GIÁ (Voucher - Mới thêm)
CREATE TABLE MaGiamGia (
    MaGiamGia INT IDENTITY(1,1) PRIMARY KEY,
    Code VARCHAR(20) UNIQUE NOT NULL, -- Ví dụ: SALE50, TET2025
    PhanTramGiam INT DEFAULT 0, -- Giảm 10%, 20%
    GiamToiDa DECIMAL(18,0) DEFAULT 0, -- Giảm tối đa 50k
    DonToiThieu DECIMAL(18,0) DEFAULT 0, -- Đơn từ 100k mới được dùng
    NgayHetHan DATETIME NOT NULL,
    SoLuong INT DEFAULT 100 -- Giới hạn số lần dùng
);
GO

-- 6. BẢNG ĐƠN HÀNG (Lưu thông tin đặt món)
CREATE TABLE DonHang (
    MaDonHang INT IDENTITY(1,1) PRIMARY KEY,
    MaNguoiDung INT NOT NULL, -- Khách mua
    MaGiamGia INT NULL, -- Có dùng voucher không (có thể NULL)
    NgayDat DATETIME DEFAULT GETDATE(),
    DiaChiGiaoHang NVARCHAR(255) NOT NULL,
    SDT_NhanHang VARCHAR(15) NOT NULL,
    GhiChu NVARCHAR(255), -- Ví dụ: "Không lấy tương ớt"
    
    TongTienHang DECIMAL(18,0) NOT NULL,
    TienGiam DECIMAL(18,0) DEFAULT 0,
    PhiShip DECIMAL(18,0) DEFAULT 15000, -- Mặc định ship 15k
    TongThanhToan DECIMAL(18,0) NOT NULL, -- = Tiền hàng + Ship - Giảm
    
    TrangThai NVARCHAR(50) DEFAULT N'Chờ xác nhận', -- Chờ xác nhận, Đang làm, Đang giao, Hoàn thành, Hủy
    PhuongThucThanhToan NVARCHAR(50) DEFAULT N'Tiền mặt', -- Tiền mặt, Chuyển khoản
    
    CONSTRAINT FK_DonHang_NguoiDung FOREIGN KEY (MaNguoiDung) REFERENCES NguoiDung(MaNguoiDung)
        ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT FK_DonHang_GiamGia FOREIGN KEY (MaGiamGia) REFERENCES MaGiamGia(MaGiamGia)
);
GO

-- 7. BẢNG CHI TIẾT ĐƠN HÀNG (Mua món gì, số lượng bao nhiêu)
CREATE TABLE ChiTietDonHang (
    MaChiTiet INT IDENTITY(1,1) PRIMARY KEY,
    MaDonHang INT NOT NULL,
    MaMon INT NOT NULL,
    SoLuong INT NOT NULL CHECK (SoLuong > 0),
    GiaLuuTru DECIMAL(18,0) NOT NULL, -- Lưu giá lúc mua để sau này quán tăng giá không bị sai lệch
    
    CONSTRAINT FK_CTDH_DonHang FOREIGN KEY (MaDonHang) REFERENCES DonHang(MaDonHang)
        ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT FK_CTDH_MonAn FOREIGN KEY (MaMon) REFERENCES MonAn(MaMon)
        ON DELETE CASCADE ON UPDATE CASCADE
);
GO

-- 8. BẢNG ĐÁNH GIÁ (REVIEW - Mới thêm)
CREATE TABLE DanhGia (
    MaDanhGia INT IDENTITY(1,1) PRIMARY KEY,
    MaNguoiDung INT NOT NULL,
    MaMon INT NOT NULL, -- Đánh giá món ăn cụ thể
    SoSao INT CHECK (SoSao >= 1 AND SoSao <= 5), -- 1 đến 5 sao
    BinhLuan NVARCHAR(MAX),
    NgayDanhGia DATETIME DEFAULT GETDATE(),
    
    CONSTRAINT FK_DanhGia_NguoiDung FOREIGN KEY (MaNguoiDung) REFERENCES NguoiDung(MaNguoiDung)
        ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT FK_DanhGia_MonAn FOREIGN KEY (MaMon) REFERENCES MonAn(MaMon)
        ON DELETE CASCADE ON UPDATE CASCADE
);
GO

-- 9. BẢNG YÊU THÍCH (FAVORITES - Mới thêm)
-- Để người dùng lưu món ngon lần sau mua tiếp
CREATE TABLE YeuThich (
    MaYeuThich INT IDENTITY(1,1) PRIMARY KEY,
    MaNguoiDung INT NOT NULL,
    MaMon INT NOT NULL,
    NgayThem DATETIME DEFAULT GETDATE(),
    
    CONSTRAINT FK_YeuThich_User FOREIGN KEY (MaNguoiDung) REFERENCES NguoiDung(MaNguoiDung),
    CONSTRAINT FK_YeuThich_Mon FOREIGN KEY (MaMon) REFERENCES MonAn(MaMon)
        ON DELETE CASCADE ON UPDATE CASCADE
);
GO

-- 1. Thêm Vai Trò
INSERT INTO VaiTro (TenVaiTro) VALUES (N'Admin'), (N'KhachHang'), (N'Shipper'), (N'DauBep'), (N'QuanLy');

-- 2. Thêm Người Dùng
INSERT INTO NguoiDung (MaVaiTro, TaiKhoan, MatKhau, HoTen, SDT, DiaChiMacDinh) VALUES 
(1, 'admin', '123', N'Quản Trị Viên', '0901000000', N'Văn phòng Admin'),
(2, 'thong03', '123', N'Trần Quang Thông', '0902000000', N'Đại học Duy Tân, Đà Nẵng'),
(2, 'khachvip', '123', N'Nguyễn Văn Giàu', '0903000000', N'Biệt thự Đảo Xanh'),
(3, 'shipper1', '123', N'Phạm Giao Nhanh', '0904000000', N'Hải Châu, Đà Nẵng'),
(4, 'daubep', '123', N'Gordon Ramsay VN', '0905000000', N'Bếp trung tâm');

-- 3. Thêm Loại Món
INSERT INTO LoaiMonAn (TenLoai, HinhAnhLoai) VALUES 
(N'Burger', 'ic_burger.png'),
(N'Gà Rán', 'ic_chicken.png'),
(N'Đồ Uống', 'ic_drink.png'),
(N'Ăn Kèm', 'ic_fries.png'),
(N'Combo', 'ic_combo.png');

-- 4. Thêm Món Ăn (Fastfood)
INSERT INTO MonAn (MaLoai, TenMon, GiaBan, MoTa) VALUES 
(1, N'Burger Bò Phô Mai', 45000, N'Bò nướng than, 2 lớp phô mai cheddar'),
(1, N'Burger Tôm', 50000, N'Nhân tôm tươi chiên xù sốt mayonaise'),
(2, N'Gà Rán Cay (2 Miếng)', 68000, N'Gà tẩm bột cay giòn rụm'),
(3, N'Pepsi Tươi Lớn', 15000, N'Ly size L nhiều gas'),
(4, N'Khoai Tây Chiên', 25000, N'Khoai tây nhập khẩu Mỹ');

-- 5. Thêm Mã Giảm Giá
INSERT INTO MaGiamGia (Code, PhanTramGiam, GiamToiDa, DonToiThieu, NgayHetHan) VALUES 
('CHAOBANMOI', 50, 20000, 0, '2025-12-31'),
('FREESHIP', 100, 15000, 100000, '2025-12-31'),
('TET2025', 20, 50000, 200000, '2025-02-15'),
('MUAHE', 10, 30000, 50000, '2025-06-30'),
('VIPMEMBER', 25, 100000, 500000, '2025-12-31');

-- 6. Thêm Đơn Hàng
INSERT INTO DonHang (MaNguoiDung, MaGiamGia, DiaChiGiaoHang, SDT_NhanHang, TongTienHang, TongThanhToan, TrangThai) VALUES 
(2, NULL, N'ĐH Duy Tân', '0902000000', 45000, 60000, N'Hoàn thành'), -- Mua 1 Burger + 15k ship
(2, 1, N'Nhà riêng', '0902000000', 93000, 88000, N'Đang giao'), -- Mua Gà + Khoai, giảm 20k
(3, 2, N'Biệt thự Đảo Xanh', '0903000000', 500000, 500000, N'Chờ xác nhận'), -- Đơn lớn
(2, NULL, N'Công ty', '0902000000', 15000, 30000, N'Đã hủy'), -- Mua nước
(3, NULL, N'Nhà bạn gái', '0903000000', 100000, 115000, N'Đang làm');

-- 7. Thêm Chi Tiết Đơn Hàng (Rất quan trọng)
INSERT INTO ChiTietDonHang (MaDonHang, MaMon, SoLuong, GiaLuuTru) VALUES 
(1, 1, 1, 45000), -- Đơn 1 mua Burger Bò
(2, 3, 1, 68000), -- Đơn 2 mua Gà Rán
(2, 5, 1, 25000), -- Đơn 2 mua thêm Khoai Tây
(3, 2, 10, 50000),-- Đơn 3 mua 10 Burger Tôm
(5, 2, 2, 50000); -- Đơn 5 mua 2 Burger Tôm

-- 8. Thêm Đánh Giá (Review)
INSERT INTO DanhGia (MaNguoiDung, MaMon, SoSao, BinhLuan) VALUES 
(2, 1, 5, N'Burger rất ngon, thịt bò mềm, sẽ quay lại!'),
(2, 3, 4, N'Gà hơi cay so với mình nhưng vẫn ngon'),
(3, 2, 5, N'Tôm tươi, sốt béo ngậy, tuyệt vời'),
(2, 5, 3, N'Khoai tây hơi ỉu, chắc do ship lâu'),
(3, 1, 5, N'Đồ ăn nóng hổi, đóng gói cẩn thận');

-- 9. Thêm Yêu Thích
INSERT INTO YeuThich (MaNguoiDung, MaMon) VALUES 
(2, 1), -- User 2 thích Burger Bò
(2, 3), -- User 2 thích Gà Rán
(3, 2), -- User 3 thích Burger Tôm
(3, 1), -- User 3 cũng thích Burger Bò
(2, 4); -- User 2 thích Pepsi