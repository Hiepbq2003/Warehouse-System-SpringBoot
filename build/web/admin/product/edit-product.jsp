<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>Chỉnh Sửa Sản Phẩm - Quản Lý Kho Hàng</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" />
  <link href="https://fonts.googleapis.com/css2?family=JetBrains+Mono&display=swap" rel="stylesheet" />
  <link href="${pageContext.request.contextPath}/css/index.css" rel="stylesheet"/>
</head>
<body>
<div class="container-fluid">
  <div class="row">
    <!-- Sidebar -->
    <jsp:include page="../../common/sidebar.jsp"></jsp:include>

    <!-- Main Content -->
    <main class="col-md-10 ms-sm-auto col-lg-10 px-md-4 py-4">
      <h3>Chỉnh Sửa Sản Phẩm</h3>
      <form action="${pageContext.request.contextPath}/admin/manage-product" method="POST">
        <input type="hidden" name="action" value="edit">
        <input type="hidden" name="productId" value="${product.productId}">

        <div class="row mb-3">
          <div class="col-md-6">
            <label for="productCode" class="form-label">Mã Sản Phẩm</label>
            <input type="text" class="form-control" id="productCode" name="productCode" value="${product.productCode}" required>
          </div>
          <div class="col-md-6">
            <label for="productName" class="form-label">Tên Sản Phẩm</label>
            <input type="text" class="form-control" id="productName" name="productName" value="${product.productName}" required>
          </div>
        </div>

        <div class="mb-3">
          <label for="description" class="form-label">Mô Tả</label>
          <textarea class="form-control" id="description" name="description" rows="3">${product.description}</textarea>
        </div>

        <div class="row mb-3">
          <div class="col-md-6">
            <label for="unit" class="form-label">Đơn Vị Tính</label>
            <select class="form-select" id="unit" name="unit">
              <option value="chiếc" ${product.unit == 'chiếc' ? 'selected' : ''}>Chiếc</option>
              <option value="thanh" ${product.unit == 'thanh' ? 'selected' : ''}>Thanh</option>
              <option value="bộ" ${product.unit == 'bộ' ? 'selected' : ''}>Bộ</option>
              <option value="sợi" ${product.unit == 'sợi' ? 'selected' : ''}>Sợi</option>
            </select>
          </div>
          <div class="col-md-6">
            <label for="supplierId" class="form-label">Nhà Cung Cấp</label>
            <select class="form-select" id="supplierId" name="supplierId">
              <option value="">-- Chọn nhà cung cấp --</option>
              <c:forEach var="supplier" items="${suppliers}">
                <option value="${supplier.supplierId}" ${supplier.supplierId == product.supplierId ? 'selected' : ''}>${supplier.supplierName}</option>
              </c:forEach>
            </select>
          </div>
        </div>

        <div class="row mb-3">
          <div class="col-md-4">
            <label for="purchasePrice" class="form-label">Giá Mua</label>
            <input type="number" class="form-control" id="purchasePrice" name="purchasePrice" step="any" value="${product.purchasePrice}">
          </div>
          <div class="col-md-4">
            <label for="salePrice" class="form-label">Giá Bán</label>
            <input type="number" class="form-control" id="salePrice" name="salePrice" step="any" value="${product.salePrice}">
          </div>
          <div class="col-md-4">
            <label for="lowStockThreshold" class="form-label">Ngưỡng Tồn Kho Thấp</label>
            <input type="number" class="form-control" id="lowStockThreshold" name="lowStockThreshold" value="${product.lowStockThreshold}">
          </div>
        </div>
        
        <div class="mb-3 form-check">
          <input type="checkbox" class="form-check-input" id="isActive" name="isActive" value="true" ${product.isActive ? 'checked' : ''}>
          <label class="form-check-label" for="isActive">Hoạt động</label>
        </div>

        <button type="submit" class="btn btn-success">Cập Nhật Sản Phẩm</button>
        <a href="${pageContext.request.contextPath}/admin/manage-product?action=list" class="btn btn-secondary">Hủy</a>
      </form>
    </main>
  </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html> 