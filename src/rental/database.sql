-- =====================================================================
-- TUGAS AKHIR PEMROGRAMAN 2 - APLIKASI RENTAL MOBIL
-- Nama   : Ahmad Luthfi
-- NIM    : 231011401058
-- Objek  : Rental Mobil (No. 8 - Katalog 35 Objek)
-- =====================================================================
-- CARA PAKAI:
-- Buka MySQL Workbench / phpMyAdmin / HeidiSQL, lalu jalankan seluruh file ini
-- =====================================================================

-- ---------------------------------------------------------------------
-- 1. BUAT DATABASE (nama wajib memuat NIM)
-- ---------------------------------------------------------------------
DROP DATABASE IF EXISTS db_rentalmobil_231011401058;
CREATE DATABASE db_rentalmobil_231011401058;
USE db_rentalmobil_231011401058;

-- ---------------------------------------------------------------------
-- 2. TABEL USER (untuk fitur Login)
-- ---------------------------------------------------------------------
CREATE TABLE user (
    id_user      INT AUTO_INCREMENT PRIMARY KEY,
    username     VARCHAR(50) NOT NULL UNIQUE,
    password     VARCHAR(100) NOT NULL,
    nama_lengkap VARCHAR(100) NOT NULL,
    level        ENUM('admin', 'staff') DEFAULT 'staff'
);

-- Akun login default -> username: admin, password: admin123
INSERT INTO user (username, password, nama_lengkap, level) VALUES
('admin', 'admin123', 'Administrator', 'admin'),
('staff1', 'staff123', 'Staff Rental', 'staff');

-- ---------------------------------------------------------------------
-- 3. TABEL MOBIL (data unit mobil yang disewakan)
-- Atribut ditambah dari katalog minimal (plat, merk) -> nilai plus
-- ---------------------------------------------------------------------
CREATE TABLE mobil (
    id_mobil      INT AUTO_INCREMENT PRIMARY KEY,
    plat_nomor    VARCHAR(15) NOT NULL UNIQUE,
    merk          VARCHAR(50) NOT NULL,
    model         VARCHAR(50) NOT NULL,
    tahun         INT NOT NULL,
    warna         VARCHAR(30),
    tarif_harian  DECIMAL(12,2) NOT NULL,
    status_mobil  ENUM('Tersedia', 'Disewa', 'Servis') DEFAULT 'Tersedia'
);

INSERT INTO mobil (plat_nomor, merk, model, tahun, warna, tarif_harian, status_mobil) VALUES
('B 1234 ABC', 'Toyota', 'Avanza', 2022, 'Silver', 350000, 'Tersedia'),
('B 2345 BCD', 'Daihatsu', 'Xenia', 2021, 'Putih', 320000, 'Tersedia'),
('B 3456 CDE', 'Honda', 'Brio', 2023, 'Merah', 280000, 'Disewa'),
('B 4567 DEF', 'Toyota', 'Innova', 2020, 'Hitam', 500000, 'Tersedia'),
('B 5678 EFG', 'Suzuki', 'Ertiga', 2022, 'Putih', 330000, 'Tersedia'),
('B 6789 FGH', 'Mitsubishi', 'Xpander', 2023, 'Abu-abu', 380000, 'Servis'),
('B 7890 GHI', 'Toyota', 'Calya', 2021, 'Merah', 250000, 'Tersedia'),
('B 8901 HIJ', 'Honda', 'Mobilio', 2022, 'Silver', 300000, 'Disewa');

-- ---------------------------------------------------------------------
-- 4. TABEL PENYEWA (data pelanggan)
-- ---------------------------------------------------------------------
CREATE TABLE penyewa (
    id_penyewa   INT AUTO_INCREMENT PRIMARY KEY,
    nama_penyewa VARCHAR(100) NOT NULL,
    no_ktp       VARCHAR(20) NOT NULL,
    no_hp        VARCHAR(20) NOT NULL,
    alamat       VARCHAR(150)
);

INSERT INTO penyewa (nama_penyewa, no_ktp, no_hp, alamat) VALUES
('NAMA_PLACEHOLDER', 'NO_KTP_PLACEHOLDER', '081234567890', 'Jl. Mahasiswa No. 21, Tangerang'),
('Budi Santoso', '3171015501880001', '081298765432', 'Jl. Merdeka No. 10, Tangerang'),
('Siti Aminah', '3171026203900002', '081345678901', 'Jl. Sudirman No. 5, Jakarta'),
('Andi Wijaya', '3171039507850003', '081456789012', 'Jl. Diponegoro No. 8, Depok'),
('Rina Kusuma', '3171041209920004', '081567890123', 'Jl. Ahmad Yani No. 15, Bekasi'),
('Dedi Saputra', '3171052811890005', '081678901234', 'Jl. Pajajaran No. 3, Bogor'),
('Maya Putri', '3171061504950006', '081789012345', 'Jl. Veteran No. 22, Tangerang'),
('Joko Susilo', '3171072209870007', '081890123456', 'Jl. Kartini No. 11, Tangerang'),
('Lestari Wati', '3171083106930008', '081901234567', 'Jl. Imam Bonjol No. 7, Jakarta'),
('Fajar Ramadhan', '3171091807910009', '082012345678', 'Jl. Cendrawasih No. 19, Tangerang');

-- ---------------------------------------------------------------------
-- 5. TABEL PENYEWAAN (transaksi rental - entitas utama)
-- Relasi: mobil (1) -> (N) penyewaan, penyewa (1) -> (N) penyewaan
-- ---------------------------------------------------------------------
CREATE TABLE penyewaan (
    id_penyewaan    INT AUTO_INCREMENT PRIMARY KEY,
    id_mobil        INT NOT NULL,
    id_penyewa      INT NOT NULL,
    tanggal_sewa    DATE NOT NULL,
    tanggal_kembali DATE NOT NULL,
    durasi_hari     INT NOT NULL,
    total_biaya     DECIMAL(12,2) NOT NULL,
    status          ENUM('Berjalan', 'Selesai', 'Batal') DEFAULT 'Berjalan',
    CONSTRAINT fk_penyewaan_mobil FOREIGN KEY (id_mobil) REFERENCES mobil(id_mobil)
        ON UPDATE CASCADE ON DELETE RESTRICT,
    CONSTRAINT fk_penyewaan_penyewa FOREIGN KEY (id_penyewa) REFERENCES penyewa(id_penyewa)
        ON UPDATE CASCADE ON DELETE RESTRICT
);

INSERT INTO penyewaan (id_mobil, id_penyewa, tanggal_sewa, tanggal_kembali, durasi_hari, total_biaya, status) VALUES
(1, 1, '2026-06-01', '2026-06-04', 3, 1050000, 'Selesai'),
(3, 2, '2026-06-10', '2026-06-15', 5, 1400000, 'Berjalan'),
(4, 3, '2026-06-05', '2026-06-07', 2, 1000000, 'Selesai'),
(2, 4, '2026-06-12', '2026-06-14', 2, 640000, 'Selesai'),
(5, 5, '2026-06-15', '2026-06-18', 3, 990000, 'Berjalan'),
(8, 6, '2026-06-08', '2026-06-10', 2, 600000, 'Selesai'),
(7, 7, '2026-06-20', '2026-06-22', 2, 500000, 'Berjalan'),
(1, 8, '2026-06-18', '2026-06-20', 2, 700000, 'Selesai'),
(4, 9, '2026-06-22', '2026-06-25', 3, 1500000, 'Berjalan'),
(2, 10, '2026-06-23', '2026-06-24', 1, 320000, 'Berjalan');

-- ---------------------------------------------------------------------
-- 6. VIEW LAPORAN (untuk fitur Laporan/Cetak - Bagian B)
-- Menggabungkan data penyewaan + mobil + penyewa jadi satu tampilan siap cetak
-- ---------------------------------------------------------------------
CREATE VIEW v_laporan_penyewaan AS
SELECT
    p.id_penyewaan,
    m.plat_nomor,
    m.merk,
    m.model,
    pw.nama_penyewa,
    pw.no_hp,
    p.tanggal_sewa,
    p.tanggal_kembali,
    p.durasi_hari,
    p.total_biaya,
    p.status
FROM penyewaan p
JOIN mobil m   ON p.id_mobil = m.id_mobil
JOIN penyewa pw ON p.id_penyewa = pw.id_penyewa
ORDER BY p.tanggal_sewa DESC;
