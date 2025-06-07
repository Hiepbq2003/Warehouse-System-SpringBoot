<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Đăng ký - Quản Lý Kho Hàng</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
  <link href="https://fonts.googleapis.com/css2?family=JetBrains+Mono&display=swap" rel="stylesheet">
  <style>
    body {
      font-family: 'JetBrains Mono', monospace;
      background-color: #f1f3f5;
    }
    .register-box {
      max-width: 500px;
      margin: 50px auto;
      padding: 30px;
      background: #fff;
      border-radius: 10px;
      box-shadow: 0 0 10px rgba(0,0,0,0.1);
    }
    .alert {
      margin-bottom: 20px;
    }
  </style>
</head>
<body>

<div class="register-box">
  <h3 class="text-center mb-4">📦 Đăng Ký Tài Khoản</h3>

  <c:if test="${not empty sessionScope.errorMessage}">
    <div class="alert alert-danger alert-dismissible fade show" role="alert">
      ${sessionScope.errorMessage}
      <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
    </div>
    <c:remove var="errorMessage" scope="session"/>
  </c:if>

  <form action="${pageContext.request.contextPath}/register" method="post">
    <div class="mb-3">
      <label class="form-label">Tên đăng nhập</label>
      <input type="text" name="username" class="form-control" placeholder="Nhập tên đăng nhập" required>
      <div class="form-text">Từ 3-50 ký tự, không có khoảng trắng</div>
    </div>

    <div class="mb-3">
      <label class="form-label">Họ và tên</label>
      <input type="text" name="fullName" class="form-control" placeholder="Nhập họ và tên đầy đủ" required>
    </div>

    <div class="mb-3">
      <label class="form-label">Email</label>
      <input type="email" name="email" class="form-control" placeholder="Nhập địa chỉ email" required>
      <div class="form-text">Email sẽ được sử dụng để khôi phục mật khẩu</div>
    </div>
    
    <div class="mb-3">
      <label class="form-label">Phone</label>
      <input type="tel" name="phone" class="form-control"  placeholder="Nhập số điện thoại"
             maxlength="10" pattern="\d{10}" inputmode="numeric" required
             oninput="this.value = this.value.replace(/[^0-9]/g, '').slice(0, 10);" >
    </div>

    <div class="mb-3">
      <label class="form-label">Vai trò</label>
      <select name="role" class="form-select" required>
        <option value="">-- Chọn vai trò --</option>
        <option value="warehouse_staff">Nhân viên kho</option>
        <option value="sales_staff">Nhân viên bán hàng</option>
        <option value="purchasing_staff">Nhân viên mua hàng</option>
        <option value="admin">Quản trị viên</option>
      </select>
    </div>

    <div class="mb-3">
      <label class="form-label">Mật khẩu</label>
      <input type="password" name="password" class="form-control" placeholder="Nhập mật khẩu" required>
      <div class="form-text">Ít nhất 6 ký tự, bao gồm chữ và số</div>
    </div>

    <div class="mb-3">
      <label class="form-label">Xác nhận mật khẩu</label>
      <input type="password" name="confirmPassword" class="form-control" placeholder="Nhập lại mật khẩu" required>
    </div>

    <button type="submit" class="btn btn-success w-100" id="submitBtn">Đăng ký</button>
    <div class="text-center mt-3">
      <a href="${pageContext.request.contextPath}/login">Đã có tài khoản? Đăng nhập</a>
    </div>
  </form>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
<script>
document.addEventListener('DOMContentLoaded', function() {
    const form = document.querySelector('form');
    const emailInput = document.querySelector('input[name="email"]');
    const passwordInput = document.querySelector('input[name="password"]');
    const confirmPasswordInput = document.querySelector('input[name="confirmPassword"]');
    const submitBtn = document.getElementById('submitBtn');

    // Email validation
    emailInput.addEventListener('blur', function() {
        const email = this.value.trim();
        const emailRegex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;

        if (email && !emailRegex.test(email)) {
            this.classList.add('is-invalid');
            if (!this.nextElementSibling || !this.nextElementSibling.classList.contains('invalid-feedback')) {
                const feedback = document.createElement('div');
                feedback.className = 'invalid-feedback';
                feedback.textContent = 'Định dạng email không hợp lệ';
                this.parentNode.appendChild(feedback);
            }
        } else {
            this.classList.remove('is-invalid');
            const feedback = this.parentNode.querySelector('.invalid-feedback');
            if (feedback) feedback.remove();
        }
    });

    // Password confirmation validation
    function validatePasswordMatch() {
        const password = passwordInput.value;
        const confirmPassword = confirmPasswordInput.value;

        if (confirmPassword && password !== confirmPassword) {
            confirmPasswordInput.classList.add('is-invalid');
            if (!confirmPasswordInput.nextElementSibling || !confirmPasswordInput.nextElementSibling.classList.contains('invalid-feedback')) {
                const feedback = document.createElement('div');
                feedback.className = 'invalid-feedback';
                feedback.textContent = 'Mật khẩu xác nhận không khớp';
                confirmPasswordInput.parentNode.appendChild(feedback);
            }
        } else {
            confirmPasswordInput.classList.remove('is-invalid');
            const feedback = confirmPasswordInput.parentNode.querySelector('.invalid-feedback');
            if (feedback) feedback.remove();
        }
    }

    passwordInput.addEventListener('input', validatePasswordMatch);
    confirmPasswordInput.addEventListener('input', validatePasswordMatch);

    // Form submission validation
    form.addEventListener('submit', function(e) {
        const email = emailInput.value.trim();
        const emailRegex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;

        if (!emailRegex.test(email)) {
            e.preventDefault();
            emailInput.focus();
            alert('Vui lòng nhập email hợp lệ');
            return;
        }

        if (passwordInput.value !== confirmPasswordInput.value) {
            e.preventDefault();
            confirmPasswordInput.focus();
            alert('Mật khẩu xác nhận không khớp');
            return;
        }
    });
});
</script>
</body>
</html>
