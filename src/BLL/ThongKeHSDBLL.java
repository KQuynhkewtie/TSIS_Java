package BLL;

import DAL.ThongKeHSDDAL;
import DTO.ThongKeHSDDTO;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class ThongKeHSDBLL {
    private ThongKeHSDDAL thongKeHSDDAL;

    public ThongKeHSDBLL(ThongKeHSDDAL thongKeHSDDAL) {
        this.thongKeHSDDAL = thongKeHSDDAL;
    }

    public List<ThongKeHSDDTO> thongKeSanPhamTheoHSD(String tenSP, String filterHSD) {
        List<ThongKeHSDDTO> nhapHang = thongKeHSDDAL.layDanhSachNhapHang();
        Map<String, Integer> soLuongDaBan = thongKeHSDDAL.laySoLuongDaBan();

        Map<String, List<ThongKeHSDDTO>> nhomTheoMaSP = nhapHang.stream()
                .collect(Collectors.groupingBy(ThongKeHSDDTO::getMaSP));

        List<ThongKeHSDDTO> ketQua = new ArrayList<>();
        LocalDate now = LocalDate.now();

        for (Map.Entry<String, List<ThongKeHSDDTO>> entry : nhomTheoMaSP.entrySet()) {
            String maSP = entry.getKey();
            List<ThongKeHSDDTO> cacLo = entry.getValue();
            int tongDaBan = soLuongDaBan.getOrDefault(maSP, 0);
            int conPhaiTru = tongDaBan;

            for (ThongKeHSDDTO lo : cacLo) {
                if (conPhaiTru <= 0) {
                    ketQua.add(new ThongKeHSDDTO(
                            lo.getMaSP(),
                            lo.getTenSP(),
                            lo.getSoLo(),
                            lo.getHanSuDung(),
                            lo.getSoLuongCon(),
                            lo.getNhaCungUng(),
                            lo.getNgayNhapKho()
                    ));
                } else {
                    int tonKho = lo.getSoLuongCon() - conPhaiTru;
                    if (tonKho > 0) {
                        ketQua.add(new ThongKeHSDDTO(
                                lo.getMaSP(),
                                lo.getTenSP(),
                                lo.getSoLo(),
                                lo.getHanSuDung(),
                                tonKho,
                                lo.getNhaCungUng(),
                                lo.getNgayNhapKho()
                        ));
                        conPhaiTru = 0;
                    } else {
                        conPhaiTru -= lo.getSoLuongCon();
                    }
                }
            }
        }

        return ketQua.stream()
                .filter(sp -> tenSP == null || tenSP.isEmpty() ||
                        sp.getTenSP().toLowerCase().contains(tenSP.toLowerCase()))
                .filter(sp -> {
                    switch (filterHSD) {
                        case "Đã hết hạn":
                            return sp.getHanSuDung().isBefore(now);
                        case "Còn hạn":
                            return !sp.getHanSuDung().isBefore(now);
                        case "7 ngày tới":
                            return !sp.getHanSuDung().isBefore(now) &&
                                    !sp.getHanSuDung().isAfter(now.plusDays(7));
                        case "30 ngày tới":
                            return !sp.getHanSuDung().isBefore(now) &&
                                    !sp.getHanSuDung().isAfter(now.plusDays(30));
                        case "90 ngày tới":
                            return !sp.getHanSuDung().isBefore(now) &&
                                    !sp.getHanSuDung().isAfter(now.plusDays(90));
                        case "Tất cả":
                        default:
                            return true;
                    }
                })
                .collect(Collectors.toList());
    }
}
