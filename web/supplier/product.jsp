<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="vi">
<head>
  <meta charset="UTF-8"/>
  <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
  <title>Quản Lý Kho Hàng</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"/>
  <link href="https://fonts.googleapis.com/css2?family=JetBrains+Mono&display=swap" rel="stylesheet"/>
  <link href="${pageContext.request.contextPath}/css/index.css" rel="stylesheet"/>
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/izitoast@1.4.0/dist/css/iziToast.min.css">
</head>
<body>
<div class="container-fluid">
  <div class="row">
    <!-- Sidebar -->
    <jsp:include page="../common/sidebar.jsp"></jsp:include>

    <!-- Main Content -->
    <main class="col-md-10 ms-sm-auto col-lg-10 px-md-4 py-4">

      
      <div class="d-flex justify-content-between align-items-center mb-4">
        <h3>Danh sách Sản Phẩm</h3>
        <a href="${pageContext.request.contextPath}/admin/manage-product?action=create" class="btn btn-success">+ Thêm Sản Phẩm</a>
      </div>

      <!-- Filter Form -->
      <form action="${pageContext.request.contextPath}/admin/manage-product" method="GET" class="row g-3 mb-4">
        <input type="hidden" name="action" value="list">
        <div class="col-md-3">
          <input type="text" id="searchInput" name="search" class="form-control" placeholder="Tìm theo mã, tên, NCC..." value="${searchTerm}"/>
        </div>
        <div class="col-md-3">
          <select id="supplierFilter" name="supplierId" class="form-select">
            <option value="">-- Tất cả nhà cung cấp --</option>
            <c:forEach var="supplier" items="${suppliers}">
              <option value="${supplier.supplierId}" ${supplierId == supplier.supplierId ? 'selected' : ''}>${supplier.supplierName}</option>
            </c:forEach>
          </select>
        </div>
        <div class="col-md-2">
          <input type="number" id="minPurchasePrice" name="minPurchasePrice" class="form-control" placeholder="Giá mua >=" value="${minPurchasePrice}"/>
        </div>
        <div class="col-md-2">
          <input type="number" id="minSalePrice" name="minSalePrice" class="form-control" placeholder="Giá bán >=" value="${minSalePrice}"/>
        </div>
        <div class="col-md-2">
          <button type="submit" class="btn btn-primary w-100">Lọc</button>
        </div>
      </form>

      <div class="table-responsive">
        <table class="table table-bordered table-hover">
          <thead class="table-light">
          <tr>
            <th>#</th>
            <th>Mã SP</th>
            <th>Tên sản phẩm</th>
            <th>Giá mua</th>
            <th>Giá bán</th>
            <th>NCC</th>
            <th>Trạng thái</th>
            <th>Hành động</th>
          </tr>
          </thead>
          <tbody>
          <c:choose>
            <c:when test="${not empty products}">
              <c:forEach var="product" items="${products}" varStatus="loop">
                <tr>
                  <td>${(currentPage - 1) * 10 + loop.count}</td>
                  <td><c:out value="${product.productCode}"/></td>
                  <td><c:out value="${product.productName}"/></td>
                  <td><fmt:formatNumber value="${product.purchasePrice}" type="currency" currencySymbol="đ" maxFractionDigits="0"/></td>
                  <td><fmt:formatNumber value="${product.salePrice}" type="currency" currencySymbol="đ" maxFractionDigits="0"/></td>
                  <td>
                    <c:choose>
                      <c:when test="${product.supplierId != null}">
                        <c:set var="supplier" value="${supplierDAO.findById(product.supplierId)}" />
                        <c:choose>
                          <c:when test="${supplier != null}">
                            ${supplier.supplierName}
                          </c:when>
                          <c:otherwise>
                            <span class="text-muted">N/A</span>
                          </c:otherwise>
                        </c:choose>
                      </c:when>
                      <c:otherwise>
                        <span class="text-muted">N/A</span>
                      </c:otherwise>
                    </c:choose>
                  </td>
                  <td>
                    <c:choose>
                      <c:when test="${product.isActive}">
                        <span class="badge bg-success">Hoạt động</span>
                      </c:when>
                      <c:otherwise>
                        <span class="badge bg-secondary">Không HĐ</span>
                      </c:otherwise>
                    </c:choose>
                  </td>
                  <td>
                    <a href="${pageContext.request.contextPath}/admin/manage-product?action=edit&id=${product.productId}" class="btn btn-sm btn-info">Sửa</a>
                    <button class="btn btn-sm btn-danger" onclick="confirmDelete('${product.productId}', '${product.productName}')">Xóa</button>
                  </td>
                </tr>
              </c:forEach>
            </c:when>
            <c:otherwise>
              <tr>
                <td colspan="8" class="text-center">Không tìm thấy sản phẩm nào.</td>
              </tr>
            </c:otherwise>
          </c:choose>
          </tbody>
        </table>
      </div>

      <!-- Pagination -->
      <c:if test="${totalPages > 1}">
        <nav aria-label="Page navigation">
          <ul class="pagination justify-content-center">
            <li class="page-item ${currentPage == 1 ? 'disabled' : ''}">
              <c:url var="prevPageUrl" value="/admin/manage-product">
                <c:param name="action" value="list"/>
                <c:param name="page" value="${currentPage - 1}"/>
                <c:if test="${not empty searchTerm}"><c:param name="search" value="${searchTerm}"/></c:if>
                <c:if test="${not empty supplierId}"><c:param name="supplierId" value="${supplierId}"/></c:if>
                <c:if test="${not empty isActive}"><c:param name="isActive" value="${isActive}"/></c:if>
                <c:if test="${not empty minPurchasePrice}"><c:param name="minPurchasePrice" value="${minPurchasePrice}"/></c:if>
                <c:if test="${not empty minSalePrice}"><c:param name="minSalePrice" value="${minSalePrice}"/></c:if>
              </c:url>
              <a class="page-link" href="${prevPageUrl}">Trước</a>
            </li>
            <c:forEach begin="1" end="${totalPages}" var="i">
              <c:url var="pageUrl" value="/admin/manage-product">
                <c:param name="action" value="list"/>
                <c:param name="page" value="${i}"/>
                <c:if test="${not empty searchTerm}"><c:param name="search" value="${searchTerm}"/></c:if>
                <c:if test="${not empty supplierId}"><c:param name="supplierId" value="${supplierId}"/></c:if>
                <c:if test="${not empty isActive}"><c:param name="isActive" value="${isActive}"/></c:if>
                <c:if test="${not empty minPurchasePrice}"><c:param name="minPurchasePrice" value="${minPurchasePrice}"/></c:if>
                <c:if test="${not empty minSalePrice}"><c:param name="minSalePrice" value="${minSalePrice}"/></c:if>
              </c:url>
              <li class="page-item ${currentPage == i ? 'active' : ''}">
                <a class="page-link" href="${pageUrl}">${i}</a>
              </li>
            </c:forEach>
            <li class="page-item ${currentPage == totalPages ? 'disabled' : ''}">
              <c:url var="nextPageUrl" value="/admin/manage-product">
                <c:param name="action" value="list"/>
                <c:param name="page" value="${currentPage + 1}"/>
                <c:if test="${not empty searchTerm}"><c:param name="search" value="${searchTerm}"/></c:if>
                <c:if test="${not empty supplierId}"><c:param name="supplierId" value="${supplierId}"/></c:if>
                <c:if test="${not empty isActive}"><c:param name="isActive" value="${isActive}"/></c:if>
                <c:if test="${not empty minPurchasePrice}"><c:param name="minPurchasePrice" value="${minPurchasePrice}"/></c:if>
                <c:if test="${not empty minSalePrice}"><c:param name="minSalePrice" value="${minSalePrice}"/></c:if>
              </c:url>
              <a class="page-link" href="${nextPageUrl}">Sau</a>
            </li>
          </ul>
        </nav>
      </c:if>
    </main>
  </div>
</div>

<!-- Modal xác nhận xóa -->
<div class="modal fade" id="deleteConfirmModal" tabindex="-1" aria-labelledby="deleteConfirmLabel" aria-hidden="true">
  <div class="modal-dialog modal-dialog-centered">
    <div class="modal-content">
      <div class="modal-header bg-danger text-white">
        <h5 class="modal-title" id="deleteConfirmLabel">Xác nhận xóa sản phẩm</h5>
        <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
      <div class="modal-body">
        Bạn có chắc chắn muốn xóa <strong id="productNameToDelete"></strong> không?
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Hủy</button>
        <button type="button" class="btn btn-danger" id="confirmDeleteBtn">Xóa</button>
      </div>
    </div>
  </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/izitoast@1.4.0/dist/js/iziToast.min.js"></script>
<script>
  let productIdToDelete = null;
  let productNameToDelete = '';

  function confirmDelete(productId, productName) {
    productIdToDelete = productId;
    productNameToDelete = productName;
    document.getElementById("productNameToDelete").textContent = productName;
    const modal = new bootstrap.Modal(document.getElementById("deleteConfirmModal"));
    modal.show();
  }

  document.addEventListener("DOMContentLoaded", function () {
    document.getElementById("confirmDeleteBtn").addEventListener("click", () => {
      if (productIdToDelete) {
        window.location.href = '<c:out value="${pageContext.request.contextPath}"/>/admin/manage-product?action=delete&id=' + productIdToDelete;
      }
      const modalElement = document.getElementById("deleteConfirmModal");
      const modalInstance = bootstrap.Modal.getInstance(modalElement);
      modalInstance.hide();
    });
    
    // Toast message display
    var toastMessage = "${sessionScope.toastMessage}";
    var toastType = "${sessionScope.toastType}";
    if (toastMessage) {
        iziToast.show({
            title: toastType === 'success' ? 'Success' : 'Error',
            message: toastMessage,
            position: 'topRight',
            color: toastType === 'success' ? 'green' : 'red',
            timeout: 5000,
            onClosing: function () {
                // Remove toast attributes from the session after displaying
                fetch('<c:out value="${pageContext.request.contextPath}"/>/remove-toast', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded',
                    },
                }).then(response => {
                    if (!response.ok) {
                        console.error('Failed to remove toast attributes');
                    }
                }).catch(error => {
                    console.error('Error:', error);
                });
            }
        });
    }
  });
</script>
</body>
</html>
