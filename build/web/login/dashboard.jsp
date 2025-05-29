<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="vi">
<head>
  <meta charset="UTF-8"/>
  <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
  <title>Dashboard - Quản Lý Kho Hàng</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"/>
  <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" rel="stylesheet"/>
  <link href="https://fonts.googleapis.com/css2?family=JetBrains+Mono&display=swap" rel="stylesheet"/>
  <link href="${pageContext.request.contextPath}/styles/index.css" rel="stylesheet"/>

  <style>
    .sidebar {
      height: 100vh;
      background-color: #343a40 !important;
      background: #343a40 !important;
      box-shadow: 2px 0 5px rgba(0,0,0,0.1);
      position: fixed;
      top: 0;
      left: 0;
      z-index: 1000;
      overflow-y: auto;
      width: 250px;
    }

    .sidebar-sticky {
      position: sticky;
      top: 0;
      height: 100vh;
      padding: 0;
    }
    .sidebar-brand {
      background: rgba(0,0,0,0.2);
      border-bottom: 1px solid rgba(255,255,255,0.1);
      padding: 1.5rem 1rem;
    }

    .sidebar-brand h4 {
      color: #ffffff !important;
      margin-bottom: 0;
      font-size: 1.1rem;
      font-weight: 500;
    }
    .sidebar-nav {
      padding: 0.5rem 0;
    }

    .sidebar-nav .nav-item {
      margin-bottom: 0;
    }
    .sidebar-heading {
      color: rgba(255,255,255,0.5);
      font-size: 0.65rem;
      font-weight: 600;
      letter-spacing: 1px;
      margin: 1rem 1rem 0.5rem 1rem;
      padding-bottom: 0.5rem;
      text-transform: uppercase;
      display: none;
    }
    .sidebar .nav-link {
      color: rgba(255,255,255,0.9) !important;
      text-decoration: none;
      padding: 0.875rem 1.5rem;
      margin: 0;
      border-radius: 0;
      transition: all 0.2s ease;
      display: block;
      font-size: 0.9rem;
      font-weight: 400;
      border-left: 3px solid transparent;
      border-bottom: 1px solid rgba(255,255,255,0.05);
    }

    .sidebar .nav-link:hover {
      background-color: rgba(255,255,255,0.08) !important;
      color: #ffffff !important;
      border-left: 3px solid #28a745;
    }

    .sidebar .nav-link.active {
      background-color: #28a745 !important;
      color: #ffffff !important;
      border-left: 3px solid #28a745;
      font-weight: 500;
    }

    .main-content {
      margin-left: 250px !important;
      width: calc(100% - 250px) !important;
      min-height: 100vh;
    }

    @media (max-width: 767.98px) {
      .sidebar {
        position: fixed;
        top: 0;
        left: -250px;
        width: 250px;
        height: 100vh;
        z-index: 1050;
        transition: left 0.3s ease;
      }

      .sidebar.show {
        left: 0;
      }

      .main-content {
        margin-left: 0 !important;
        width: 100% !important;
      }
    }
    .sidebar::-webkit-scrollbar {
      width: 6px;
    }

    .sidebar::-webkit-scrollbar-track {
      background: rgba(255,255,255,0.1);
    }

    .sidebar::-webkit-scrollbar-thumb {
      background: rgba(255,255,255,0.3);
      border-radius: 3px;
    }

    .sidebar::-webkit-scrollbar-thumb:hover {
      background: rgba(255,255,255,0.5);
    }
  </style>
</head>
<body>
<div class="container-fluid">
  <div class="row">
    <!-- Sidebar -->
    <nav class="sidebar d-md-block">
      <div class="sidebar-sticky">
        <!-- Logo/Brand -->
        <div class="sidebar-brand text-center">
          <h4>📦 Kho Hàng</h4>
        </div>

        <!-- Navigation Menu -->
        <ul class="nav flex-column sidebar-nav">
          <li class="nav-item">
            <a class="nav-link active" href="${pageContext.request.contextPath}/dashboard">
              <span>Dashboard</span>
            </a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="${pageContext.request.contextPath}/product">
              <span>Sản phẩm</span>
            </a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="${pageContext.request.contextPath}/supplier">
              <span>Nhà Cung Cấp</span>
            </a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="${pageContext.request.contextPath}/stockin">
              <span>Nhập kho</span>
            </a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="${pageContext.request.contextPath}/stockout">
              <span>Xuất kho</span>
            </a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="${pageContext.request.contextPath}/stocktake">
              <span>Kiểm kê</span>
            </a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="${pageContext.request.contextPath}/purchase-request">
              <span>Yêu cầu mua</span>
            </a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="${pageContext.request.contextPath}/user-management">
              <span>Người dùng</span>
            </a>
          </li>
        </ul>
      </div>
    </nav>

    <main class="main-content px-4 py-4">
      <div class="d-flex justify-content-between align-items-center pb-3 mb-3 border-bottom">
        <div class="d-flex align-items-center">
          <button class="btn btn-outline-secondary d-md-none me-3" type="button" id="sidebarToggle">
            <i class="fas fa-bars"></i>
          </button>
          <h2 class="mb-0">Dashboard</h2>
        </div>
        <div class="dropdown">
          <button class="btn btn-outline-primary dropdown-toggle" type="button" data-bs-toggle="dropdown">
            👤 ${user.fullName}
          </button>
          <ul class="dropdown-menu dropdown-menu-end">
            <li><a class="dropdown-item" href="${pageContext.request.contextPath}/profile">👤 Xem Profile</a></li>
            <li><hr class="dropdown-divider"></li>
            <li><a class="dropdown-item text-danger" href="${pageContext.request.contextPath}/logout">🚪 Đăng xuất</a></li>
          </ul>
        </div>
      </div>

      <c:if test="${not empty sessionScope.errorMessage}">
        <div class="alert alert-danger alert-dismissible fade show" role="alert">
          ${sessionScope.errorMessage}
          <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
        </div>
        <c:remove var="errorMessage" scope="session"/>
      </c:if>

      <c:if test="${not empty sessionScope.successMessage}">
        <div class="alert alert-success alert-dismissible fade show" role="alert">
          ${sessionScope.successMessage}
          <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
        </div>
        <c:remove var="successMessage" scope="session"/>
      </c:if>

      <div class="row">
        <!-- Statistics Cards -->
        <div class="col-md-3 mb-3">
          <div class="card text-bg-primary">
            <div class="card-body">
              <h5 class="card-title">Tổng Sản Phẩm</h5>
              <p class="card-text fs-4">128</p>
            </div>
          </div>
        </div>
        <div class="col-md-3 mb-3">
          <div class="card text-bg-success">
            <div class="card-body">
              <h5 class="card-title">Đã Nhập Hôm Nay</h5>
              <p class="card-text fs-4">32</p>
            </div>
          </div>
        </div>
        <div class="col-md-3 mb-3">
          <div class="card text-bg-warning">
            <div class="card-body">
              <h5 class="card-title">Sắp Hết Hàng</h5>
              <p class="card-text fs-4">6</p>
            </div>
          </div>
        </div>
        <div class="col-md-3 mb-3">
          <div class="card text-bg-danger">
            <div class="card-body">
              <h5 class="card-title">Hết Hàng</h5>
              <p class="card-text fs-4">2</p>
            </div>
          </div>
        </div>
      </div>

      <div class="row mb-4">
        <div class="col-md-6">
          <div class="card">
            <div class="card-header bg-light">
              <h5>👤 Thông Tin Cá Nhân</h5>
            </div>
            <div class="card-body">
              <div class="row">
                <div class="col-sm-4"><strong>Tên đăng nhập:</strong></div>
                <div class="col-sm-8">${user.username}</div>
              </div>
              <hr>
              <div class="row">
                <div class="col-sm-4"><strong>Họ và tên:</strong></div>
                <div class="col-sm-8">${user.fullName}</div>
              </div>
              <hr>
              <div class="row">
                <div class="col-sm-4"><strong>Vai trò:</strong></div>
                <div class="col-sm-8">
                  <c:choose>
                    <c:when test="${user.role == 'ADMIN'}">
                      <span class="badge bg-danger">👑 Quản trị viên</span>
                    </c:when>
                    <c:when test="${user.role == 'WAREHOUSE_STAFF'}">
                      <span class="badge bg-primary">📦 Nhân viên kho</span>
                    </c:when>
                    <c:when test="${user.role == 'SALES_STAFF'}">
                      <span class="badge bg-success">💰 Nhân viên bán hàng</span>
                    </c:when>
                    <c:when test="${user.role == 'PURCHASING_STAFF'}">
                      <span class="badge bg-info">🛒 Nhân viên mua hàng</span>
                    </c:when>
                    <c:otherwise>
                      <span class="badge bg-secondary">${user.role}</span>
                    </c:otherwise>
                  </c:choose>
                </div>
              </div>
              <hr>
              <div class="row">
                <div class="col-sm-4"><strong>Ngày tạo:</strong></div>
                <div class="col-sm-8">
                  <c:if test="${not empty user.createdAtAsDate}">
                    <fmt:formatDate value="${user.createdAtAsDate}" pattern="dd/MM/yyyy HH:mm"/>
                  </c:if>
                  <c:if test="${empty user.createdAtAsDate}">
                    Không có thông tin
                  </c:if>
                </div>
              </div>
              <div class="mt-3">
                <a href="${pageContext.request.contextPath}/profile" class="btn btn-primary btn-sm">
                  ✏️ Chỉnh sửa Profile
                </a>
              </div>
            </div>
          </div>
        </div>

        <div class="col-md-6">
          <div class="card">
            <div class="card-header bg-light">
              <h5>🚀 Thao Tác Nhanh</h5>
            </div>
            <div class="card-body">
              <div class="row g-2">
                <div class="col-6">
                  <a href="${pageContext.request.contextPath}/product" class="btn btn-outline-primary w-100">
                    📦 Sản Phẩm
                  </a>
                </div>
                <div class="col-6">
                  <a href="${pageContext.request.contextPath}/supplier" class="btn btn-outline-success w-100">
                    🏢 Nhà Cung Cấp
                  </a>
                </div>
                <div class="col-6">
                  <a href="${pageContext.request.contextPath}/stockin" class="btn btn-outline-info w-100">
                    ⬇️ Nhập Kho
                  </a>
                </div>
                <div class="col-6">
                  <a href="${pageContext.request.contextPath}/stockout" class="btn btn-outline-warning w-100">
                    ⬆️ Xuất Kho
                  </a>
                </div>
                <div class="col-6">
                  <a href="${pageContext.request.contextPath}/stocktake" class="btn btn-outline-secondary w-100">
                    📋 Kiểm Kê
                  </a>
                </div>
                <div class="col-6">
                  <a href="${pageContext.request.contextPath}/user-management" class="btn btn-outline-dark w-100">
                    👥 Người Dùng
                  </a>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>

      <div class="card mt-4">
        <div class="card-header bg-light">
          <h5>📋 Danh sách sản phẩm gần đây</h5>
        </div>
        <div class="card-body">
          <table class="table table-hover table-bordered">
            <thead class="table-light">
            <tr>
              <th>#</th>
              <th>Mã SP</th>
              <th>Tên sản phẩm</th>
              <th>Tồn kho</th>
              <th>Giá bán</th>
              <th>Trạng thái</th>
            </tr>
            </thead>
            <tbody>
            <tr>
              <td>1</td>
              <td>SP001</td>
              <td>Laptop Dell Inspiron 15</td>
              <td>45</td>
              <td>15,000,000đ</td>
              <td><span class="badge bg-success">Còn hàng</span></td>
            </tr>
            <tr>
              <td>2</td>
              <td>SP002</td>
              <td>Chuột không dây Logitech</td>
              <td>8</td>
              <td>800,000đ</td>
              <td><span class="badge bg-warning">Sắp hết</span></td>
            </tr>
            <tr>
              <td>3</td>
              <td>SP003</td>
              <td>Bàn phím cơ gaming</td>
              <td>0</td>
              <td>1,800,000đ</td>
              <td><span class="badge bg-danger">Hết hàng</span></td>
            </tr>
            </tbody>
          </table>
          <div class="text-center mt-3">
            <a href="${pageContext.request.contextPath}/product" class="btn btn-primary">
              📦 Xem tất cả sản phẩm
            </a>
          </div>
        </div>
      </div>
    </main>
  </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
<script>
document.addEventListener('DOMContentLoaded', function() {
    const sidebarToggle = document.getElementById('sidebarToggle');
    const sidebar = document.querySelector('.sidebar');

    if (sidebarToggle && sidebar) {
        sidebarToggle.addEventListener('click', function() {
            sidebar.classList.toggle('show');
        });

        document.addEventListener('click', function(e) {
            if (window.innerWidth < 768) {
                if (!sidebar.contains(e.target) && !sidebarToggle.contains(e.target)) {
                    sidebar.classList.remove('show');
                }
            }
        });
    }
    const sidebarLinks = document.querySelectorAll('.sidebar .nav-link');
    sidebarLinks.forEach(link => {
        link.addEventListener('click', function(e) {
            this.style.opacity = '0.7';
            setTimeout(() => {
                this.style.opacity = '1';
            }, 200);
        });
    });

    const buttons = document.querySelectorAll('.btn');
    buttons.forEach(button => {
        button.addEventListener('click', function(e) {
            const ripple = document.createElement('span');
            const rect = this.getBoundingClientRect();
            const size = Math.max(rect.width, rect.height);
            const x = e.clientX - rect.left - size / 2;
            const y = e.clientY - rect.top - size / 2;

            ripple.style.width = ripple.style.height = size + 'px';
            ripple.style.left = x + 'px';
            ripple.style.top = y + 'px';
            ripple.classList.add('ripple');

            this.appendChild(ripple);

            setTimeout(() => {
                ripple.remove();
            }, 600);
        });
    });
});
</script>
</body>
</html>
