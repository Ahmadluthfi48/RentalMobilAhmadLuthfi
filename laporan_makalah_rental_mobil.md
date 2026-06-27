# Laporan Makalah Aplikasi Rental Mobil

## 1. Judul
Aplikasi Manajemen Rental Mobil Berbasis Java Swing dan MySQL

## 2. Latar Belakang
Perkembangan teknologi informasi telah mendorong banyak usaha, termasuk usaha rental mobil, untuk mengadopsi sistem digital dalam proses pengelolaan data. Pengelolaan data secara manual sering menimbulkan berbagai masalah, seperti kesalahan pencatatan, keterlambatan pencarian data, serta sulitnya memantau status mobil dan pelanggan. Oleh karena itu, diperlukan sebuah sistem yang mampu membantu pengelola rental mobil dalam mengelola data secara lebih cepat, terstruktur, dan akurat.

Program yang dikembangkan ini merupakan aplikasi desktop berbasis Java Swing yang terhubung dengan database MySQL. Aplikasi ini dirancang untuk membantu admin dan staff dalam mengelola data mobil, data penyewa, transaksi penyewaan, serta menghasilkan laporan yang diperlukan oleh pihak usaha.

## 3. Tujuan Penelitian
Tujuan dari pembuatan aplikasi ini adalah sebagai berikut:
- Membuat sistem informasi rental mobil yang sederhana, terstruktur, dan mudah digunakan.
- Mengotomatisasi proses pencatatan data mobil, penyewa, dan transaksi penyewaan.
- Memudahkan pengelola dalam memantau status mobil yang tersedia, disewa, atau sedang servis.
- Menyediakan fitur login untuk membatasi akses pengguna berdasarkan level akun.
- Menyediakan laporan penyewaan yang dapat digunakan sebagai bahan evaluasi dan pengambilan keputusan.

## 4. Rumusan Masalah
Berdasarkan latar belakang tersebut, dapat dirumuskan beberapa masalah sebagai berikut:
- Bagaimana merancang aplikasi rental mobil yang dapat mengelola data secara terorganisir?
- Bagaimana membangun sistem yang mampu mengelola data mobil, penyewa, dan transaksi penyewaan?
- Bagaimana membuat aplikasi yang memiliki fitur login dan hak akses pengguna?
- Bagaimana menyediakan laporan penyewaan yang dapat membantu pengelola usaha?

## 5. Batasan Masalah
Aplikasi ini dibatasi pada beberapa fungsi utama, yaitu:
- Pengelolaan data mobil.
- Pengelolaan data penyewa.
- Pengelolaan transaksi penyewaan.
- Sistem login dengan level admin dan staff.
- Pembuatan laporan data penyewaan.

## 6. Landasan Teori
Aplikasi ini dikembangkan dengan menggunakan beberapa konsep pemrograman yang umum digunakan dalam pengembangan sistem informasi, yaitu:
- Java Swing sebagai teknologi antarmuka pengguna desktop.
- MySQL sebagai sistem manajemen basis data.
- JDBC sebagai penghubung antara aplikasi Java dengan database.
- Pola desain MVC (Model-View-Controller) untuk memisahkan logika, tampilan, dan data.

## 7. Analisis dan Perancangan Sistem
### 7.1 Kebutuhan Fungsional
Sistem ini memiliki beberapa kebutuhan fungsional sebagai berikut:
- Pengguna dapat melakukan login ke sistem.
- Admin dapat mengelola data user.
- Staff/admin dapat mengelola data mobil.
- Staff/admin dapat mengelola data penyewa.
- Staff/admin dapat mencatat transaksi penyewaan.
- Sistem dapat menampilkan status mobil dalam keadaan tersedia, disewa, atau servis.
- Sistem dapat menampilkan laporan penyewaan.

### 7.2 Kebutuhan Non-Fungsional
- Antarmuka aplikasi sederhana dan mudah dipahami.
- Database tersimpan secara lokal dengan MySQL.
- Aplikasi dapat berjalan pada sistem operasi Windows.
- Sistem menyediakan pesan error jika koneksi database gagal.

## 8. Struktur Program
Program ini disusun dengan struktur yang terorganisir menggunakan paket-paket berikut:
- package rental.mobil: berisi kelas utama aplikasi.
- package rental.view: berisi form dan tampilan antarmuka pengguna seperti LoginForm, MenuUtama, FormMobil, FormPenyewa, FormPenyewaan, FormUser, dan FormLaporan.
- package rental.controller: berisi kelas controller untuk menangani logika aplikasi.
- package rental.dao: berisi kelas akses data ke database.
- package rental.model: berisi kelas model untuk entitas data seperti Mobil, Penyewa, Penyewaan, dan User.
- package rental.util: berisi kelas KoneksiDatabase untuk menghubungkan aplikasi ke MySQL.

## 9. Implementasi Sistem
### 9.1 Form Login
Aplikasi dimulai dengan tampilan login. Pengguna memasukkan username dan password untuk masuk ke sistem. Jika kredensial benar, pengguna akan diarahkan ke dashboard utama.

### 9.2 Dashboard Utama
Dashboard utama menampilkan menu navigasi untuk mengelola data mobil, data penyewa, transaksi sewa, pengelolaan user (khusus admin), dan laporan.

### 9.3 Pengelolaan Data Mobil
Sistem memungkinkan pengguna untuk mengelola data mobil, seperti plat nomor, merk, model, tahun, warna, tarif harian, dan status mobil.

### 9.4 Pengelolaan Data Penyewa
Sistem juga menyediakan fitur untuk mengelola data pelanggan yang akan menyewa mobil, mencakup nama, nomor KTP, nomor HP, dan alamat.

### 9.5 Transaksi Penyewaan
Fitur transaksi digunakan untuk mencatat kegiatan penyewaan mobil. Data yang dicatat meliputi mobil yang disewa, penyewa, tanggal sewa, tanggal kembali, durasi sewa, total biaya, dan status transaksi.

### 9.6 Laporan
Sistem menyediakan fitur laporan yang memuat data penyewaan yang telah dicatat untuk kebutuhan evaluasi dan pencatatan administrasi.

## 10. Desain Database
Database yang digunakan dalam aplikasi ini bernama db_rentalmobil_231011401058. Tabel-tabel yang dibuat antara lain:
- user: untuk menyimpan akun pengguna aplikasi.
- mobil: untuk menyimpan data kendaraan yang tersedia untuk disewakan.
- penyewa: untuk menyimpan data pelanggan.
- penyewaan: untuk menyimpan transaksi sewa.
- view v_laporan_penyewaan: untuk menampilkan data laporan penyewaan yang lebih siap pakai.

## 11. Keunggulan Aplikasi
Aplikasi ini memiliki beberapa keunggulan, antara lain:
- Mempermudah pengelolaan data rental mobil secara terstruktur.
- Menyediakan akses yang terbatas berdasarkan peran pengguna.
- Mengurangi kesalahan pencatatan manual.
- Memudahkan pencarian dan pemantauan data.
- Menyediakan informasi yang lebih cepat untuk kebutuhan laporan.

## 12. Kelemahan dan Pengembangan Selanjutnya
Beberapa kekurangan yang masih dapat dikembangkan antara lain:
- Antarmuka aplikasi masih sederhana dan dapat ditingkatkan secara visual.
- Fitur laporan dapat dikembangkan menjadi lebih lengkap, misalnya export PDF atau Excel.
- Sistem dapat ditambahkan fitur pembayaran, denda keterlambatan, dan riwayat transaksi yang lebih detail.

## 13. Kesimpulan
Aplikasi rental mobil berbasis Java Swing dan MySQL ini berhasil dibangun sebagai sistem informasi sederhana yang mampu membantu proses pengelolaan data rental mobil. Aplikasi ini tidak hanya memudahkan pencatatan data, tetapi juga mendukung kegiatan operasional seperti pengelolaan mobil, penyewa, transaksi, dan laporan. Dengan adanya sistem ini, proses administrasi rental mobil menjadi lebih terstruktur, cepat, dan efisien.

## 14. Saran
Agar aplikasi ini menjadi lebih optimal, disarankan untuk terus dikembangkan dengan menambahkan fitur-fitur tambahan yang lebih kompleks, seperti integrasi pembayaran online, pencetakan laporan otomatis, dan notifikasi status penyewaan.

## 15. Penutup
Demikian laporan makalah ini disusun sebagai bentuk dokumentasi dari program aplikasi rental mobil yang telah dibuat. Semoga laporan ini dapat menjadi referensi dalam memahami proses pembuatan aplikasi berbasis desktop dengan Java Swing dan MySQL.
