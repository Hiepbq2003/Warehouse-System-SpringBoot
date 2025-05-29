<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <title>Sidebar Fixed Icons</title>

    <!-- Bootstrap & Icons -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet" />
    <link href="./styles/sidebar.css" rel="stylesheet" />
    <style>
        body {
    margin: 0;
    background-color: #f8f9fa;
    font-family: 'Segoe UI', sans-serif;
}

.sidebar {
    position: fixed;
    top: 0;
    left: 0;
    height: 100vh;
    background-color: #343a40;
    overflow-x: hidden;
    padding-top: 20px;
    z-index: 1000;
    width: 250px;
}


.user-section {
    text-align: center;
    margin-bottom: 30px;
    position: relative;
}

.user-img {
    width: 50px;
    height: 50px;
    border-radius: 50%;
    border: 2px solid #0d6efd;
    object-fit: cover;
    cursor: pointer;
}

.user-name {
    color: white;
    font-size: 14px;
    margin-top: 8px;

}


.dropdown-menu-user {
    position: absolute;
    top: 60px;
    left: 50%;
    transform: translateX(-50%);
    background-color: #343a40;
    border: 1px solid #0d6efd;
    border-radius: 6px;
    box-shadow: 0 2px 8px rgba(0,0,0,0.3);
    width: 180px;
    display: none;
    flex-direction: column;
    z-index: 1100;
}

.dropdown-menu-user .dropdown-item {
    color: #ced4da;
    padding: 10px 15px;
    text-decoration: none;
    transition: background-color 0.2s ease;
}

.dropdown-menu-user .dropdown-item:hover {
    background-color: #0d6efd;
    color: white;
}

.dropdown-divider {
    border-top: 1px solid #495057;
    margin: 5px 0;
}

ul#sidebarnav {
    padding-left: 0;
    margin: 0;
    list-style: none;
}

ul#sidebarnav li a.nav-link {
    position: relative;
    display: block;
    color: white;
    padding: 12px 20px 12px 24px;
    text-decoration: none;
    height: 48px;
}

ul#sidebarnav li a.nav-link i {
    position: absolute;
    top: 50%;
    left: 24px;
    transform: translateY(-50%);
    font-size: 1.3rem;
}

ul#sidebarnav li a .link-text {
    margin-left: 60px;

    white-space: nowrap;
}

ul#sidebarnav li a.nav-link:hover,
ul#sidebarnav li a.nav-link.active {
    background-color: #0d6efd;
    color: white !important;
    
    .inactive {
    color: gray;
    background-color: #f0f0f0;
}
}</style>
</head>
<body>
    
    <nav class="sidebar" id="sidebar">
        <div class="user-section" id="userSection">
            <div class="user-avatar-wrapper" style="position: relative; display: inline-block;">
                <img src="https://i.pravatar.cc/100?img=5" alt="Avatar" class="user-img" id="userAvatar" />
                <div class="dropdown-menu-user" id="dropdownUserMenu">
                    <a href="index.jsp" class="dropdown-item">Thông tin cá nhân</a>
                    <a href="index.jsp" class="dropdown-item">Cài đặt</a>
                    <hr class="dropdown-divider" />
                    <a href="index.jsp" class="dropdown-item">Đăng xuất</a>
                </div>
            </div>
            <div class="user-name">Nguyễn Văn A</div>
        </div>

        <ul class="nav flex-column" id="sidebarnav">
            <li class="nav-item">
                <a href="/admin/DashBoard.jsp" class="nav-link"><i class="bi bi-speedometer2"></i><span class="link-text">Dashboard</span></a>
            </li>
            <li class="nav-item">
                <a href="categories" class="nav-link"><i class="bi bi-box-seam"></i><span class="link-text">Phân Loại</span></a>
            </li>
            <li class="nav-item">
                <a href="manageProduct" class="nav-link"><i class="bi bi-box-seam"></i><span class="link-text">Sản phẩm</span></a>
            </li>
            <li class="nav-item">
                <a href="suppliers" class="nav-link"><i class="bi bi-truck"></i><span class="link-text">Nhà cung cấp</span></a>
            </li>
            <li class="nav-item">
                <a href="stockin.jsp" class="nav-link"><i class="bi bi-arrow-down-circle"></i><span class="link-text">Nhập kho</span></a>
            </li>
            <li class="nav-item">
                <a href="stockout.jsp" class="nav-link"><i class="bi bi-arrow-up-circle"></i><span class="link-text">Xuất kho</span></a>
            </li>
            <li class="nav-item">
                <a href="stocktake.jsp" class="nav-link"><i class="bi bi-journal-check"></i><span class="link-text">Kiểm kê</span></a>
            </li>
            <li class="nav-item">
                <a href="purchaserequest.jsp" class="nav-link"><i class="bi bi-bag-plus"></i><span class="link-text">Yêu cầu mua</span></a>
            </li>
            <li class="nav-item">
                <a href="UserServlet" class="nav-link"><i class="bi bi-people"></i><span class="link-text">Người dùng</span></a>
            </li>
        </ul>
    </nav>

    <!-- jQuery -->
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script>
        $(function () {
            // Gán active cho link đang mở
            var url = window.location.href;
            $('#sidebarnav a').each(function () {
                if (this.href === url) {
                    $(this).addClass('active');
                }
            });

            // Hiện dropdown khi hover avatar
            $('#userAvatar').hover(function () {
                $('#dropdownUserMenu').stop(true, true).fadeIn(150);
            }, function () {
                setTimeout(() => {
                    if (!$('#dropdownUserMenu').is(':hover')) {
                        $('#dropdownUserMenu').fadeOut(150);
                    }
                }, 200);
            });

            $('#dropdownUserMenu').mouseleave(function () {
                $(this).fadeOut(150);
            });
        });
    </script>
</body>
</html>
