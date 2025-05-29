<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.Category" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html lang="vi">
<head>
  <meta charset="UTF-8" />
  <title>Quản Lý Kho Hàng - Danh Mục</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"/>
  <link href="https://fonts.googleapis.com/css2?family=JetBrains+Mono&display=swap" rel="stylesheet"/>
  <link href="./styles/index.css" rel="stylesheet"/>
</head>
<body>
<jsp:include page="../common/sidebar.jsp" />

<div class="container-fluid">
  <div class="row">
    <main class="col-md-10 ms-sm-auto col-lg-10 px-md-4 py-4">
      <div class="d-flex justify-content-between align-items-center mb-4">
        <h3>Danh sách Danh Mục</h3>
        <button class="btn btn-success" data-bs-toggle="modal" data-bs-target="#addCategoryModal">+ Thêm Danh Mục</button>
      </div>

      <!-- Hiển thị thông báo lỗi nếu có -->
      <% if (request.getAttribute("error") != null) { %>
        <div class="alert alert-danger"><%= request.getAttribute("error") %></div>
      <% } %>

      <div class="table-responsive">
        <table class="table table-bordered table-hover" id="categoryTable">
          <thead class="table-light">
            <tr>
              <th>Mã</th>
              <th>Tên danh mục</th>
              <th>Hành động</th>
            </tr>
          </thead>
          <tbody>
            <%
              List<Category> list = (List<Category>) request.getAttribute("categoryList");
              if (list != null) {
                for (Category c : list) {
            %>
            <tr>
              <td><%= c.getCategoryId() %></td>
              <td><%= c.getCategoryName() %></td>
              <td>
                <button class="btn btn-sm btn-info"
                        onclick="openEditCategoryModal('<%= c.getCategoryId() %>', '<%= c.getCategoryName() %>')">Sửa</button>
                <button class="btn btn-sm btn-danger"
                        onclick="openDeleteCategoryModal('<%= c.getCategoryId() %>', '<%= c.getCategoryName() %>')">Xóa</button>
              </td>
            </tr>
            <%
                }
              }
            %>
          </tbody>
        </table>
      </div>
    </main>
  </div>
</div>

<!-- Modal Thêm Danh Mục -->
<div class="modal fade" id="addCategoryModal" tabindex="-1" aria-hidden="true">
  <div class="modal-dialog">
    <form class="modal-content" method="post" action="AddCategoryServlet">
      <div class="modal-header bg-success text-white">
        <h5 class="modal-title">Thêm Danh Mục</h5>
        <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal"></button>
      </div>
      <div class="modal-body">
        <label class="form-label">Tên danh mục</label>
        <input name="categoryName" class="form-control" required />
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Hủy</button>
        <button type="submit" class="btn btn-success">Lưu</button>
      </div>
    </form>
  </div>
</div>

<!-- Modal Sửa Danh Mục -->
<div class="modal fade" id="editCategoryModal" tabindex="-1" aria-hidden="true">
  <div class="modal-dialog">
    <form class="modal-content" method="post" action="EditCategoryServlet">
      <div class="modal-header bg-info text-white">
        <h5 class="modal-title">Chỉnh sửa Danh Mục</h5>
        <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal"></button>
      </div>
      <div class="modal-body">
        <input type="hidden" name="categoryId" id="editCategoryId" />
        <label class="form-label">Tên danh mục</label>
        <input name="categoryName" id="editCategoryName" class="form-control" required />
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Hủy</button>
        <button type="submit" class="btn btn-info">Cập nhật</button>
      </div>
    </form>
  </div>
</div>

<!-- Modal Xóa -->
<div class="modal fade" id="deleteCategoryModal" tabindex="-1" aria-hidden="true">
  <div class="modal-dialog">
    <form class="modal-content" method="post" action="DeleteCategoryServlet">
      <div class="modal-header bg-danger text-white">
        <h5 class="modal-title">Xác nhận xóa Danh Mục</h5>
        <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal"></button>
      </div>
      <div class="modal-body">
        <input type="hidden" name="categoryId" id="deleteCategoryId"/>
        <p>Bạn có chắc muốn xóa danh mục <b id="deleteCategoryName"></b> không?</p>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Hủy</button>
        <button type="submit" class="btn btn-danger">Xóa</button>
      </div>
    </form>
  </div>
</div>

<script>
  function openEditCategoryModal(id, name) {
    document.getElementById('editCategoryId').value = id;
    document.getElementById('editCategoryName').value = name;
    new bootstrap.Modal(document.getElementById('editCategoryModal')).show();
  }

  function openDeleteCategoryModal(id, name) {
    document.getElementById('deleteCategoryId').value = id;
    document.getElementById('deleteCategoryName').textContent = name;
    new bootstrap.Modal(document.getElementById('deleteCategoryModal')).show();
  }
</script>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
