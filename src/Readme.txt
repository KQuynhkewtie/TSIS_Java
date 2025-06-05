Bước 1: Tải File QuanLyNhaThuoc.rar về máy sau đó giải nén thư mục.
Bước 2: Tải file QLNT_con.sql. Vào CMD hoặc SQL*Plus tạo user “TSISPharmacy” mật khẩu “tsispharmacy” và cấp quyền cho user.
CREATE USER c##QLNT IDENTIFIED BY qlnt;
GRANT DBA to c##QLNT;
Bước 3:  Vào oracle tạo Connect với user vừa tạo. Sau đó tiến hành execute file sql trên user vừa tạo.
Lưu ý: Có tất cả 14 bảng dữ liệu, phải import theo thứ tự: VAITRO, QUYEN, VAITRO_QUYEN, NHANVIEN, TAIKHOAN, KHACHHANG, NHACUNGUNG, HANGSANXUAT, LOAISANPHAM, SANPHAM, PHIEUNHAPHANG, CHITIETPHIEUNHAPHANG, HOADON, CHITIETHOADON.
Bước 4: Mở project trong IntelliJ: Chọn File -> Open -> Chọn thư mục đã giải nén ở bước 1.
Bước 5: File -> Project Structure -> chọn tab Project -> chọn Project SDK -> Add SDK -> trỏ đến thư mục JDK đã cài.Trong cửa sổ project, click chuột phải vào thư mục src -> Mark Directory as -> Sources Root.
Bước 6: File -> Project Structure -> chọn tab Libraries -> + -> Java -> Chọn các file .jar trong thư mục lib của dự án -> OK để thêm thư viện.
Bước 7: Mở file MainFram.java bấn run để chạy chương trình. (Có thể dùng tài khoản có sẵn từ dữ liệu hoặc chọn quên mật khẩu để tạo mật khẩu mới hoặc tạo tài khoản mới để sử dụng).
Tài khoản admin
Username: admin@gmail.com
Password: 123456789
