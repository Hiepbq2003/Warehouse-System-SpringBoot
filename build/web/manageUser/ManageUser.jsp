<%@page import="model.User"%>
<%@page import="model.Role"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" import="java.util.List" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <title>Quản Lý Kho Hàng</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"/>
    <link href="https://fonts.googleapis.com/css2?family=JetBrains+Mono&display=swap" rel="stylesheet"/>
    <style>
        .inactive {
            color: gray;
            background-color: #f8f9fa; /* nền sáng nhạt */
        }
    </style>
</head>
<body>
<div class="container-fluid">
    <div class="row">
        <jsp:include page="../common/sidebar.jsp" />
        <main class="col-md-10 ms-sm-auto col-lg-10 px-md-4 py-4">
            <div class="d-flex justify-content-between align-items-center mb-4">
                <h3>Quản lý Tài khoản</h3>
                <button class="btn btn-success" data-bs-toggle="modal" data-bs-target="#addUserModal">+ Thêm người dùng</button>
            </div>

            <table id="userTable" class="table table-bordered table-hover">
                <thead class="table-light">
                    <tr>
                        <th>ID</th>
                        <th>Username</th>
                        <th>Họ tên</th>
                        <th>Email</th>
                        <th>Vai trò</th>
                        <th>Trạng thái</th>
                        <th>Ngày tạo</th>
                        <th>Ngày cập nhật</th>
                        <th>Hành động</th>
                    </tr>
                </thead>
                <tbody>
                <%
                    List<User> userList = (List<User>) request.getAttribute("userList");
                    List<Role> roleList = (List<Role>) request.getAttribute("roleList");
                    if (userList == null) {
                        userList = new java.util.ArrayList<>();
                    }
                    if (roleList == null) {
                        roleList = new java.util.ArrayList<>();
                    }

                    for (User u : userList) {
                        String roleName = "Không xác định";
                        for (Role r : roleList) {
                            if (r.getRoleId() == u.getRoleId()) {
                                roleName = r.getRoleName();
                                break;
                            }
                        }
                %>
                <tr class="<%= u.isActive() ? "" : "inactive" %>">
                    <td><%= u.getUserId()%></td>
                    <td><%= u.getUsername()%></td>
                    <td><%= u.getFullName()%></td>
                    <td><%= u.getEmail()%></td>
                    <td><%= roleName%></td>
                    <td><%= u.isActive() ? "Hoạt động" : "Không hoạt động"%></td>
                    <td><%= u.getCreatedAt()%></td>
                    <td><%= u.getUpdatedAt()%></td>
                    <td>
                        <button class="btn btn-sm btn-info"
                                onclick="openEditModal(
                                                '<%= u.getUserId()%>',
                                                '<%= u.getFullName().replace("'", "\\'")%>',
                                                '<%= u.getEmail()%>',
                                                '<%= u.getRoleId()%>',
                                                '<%= u.isActive()%>')">Sửa
                        </button>
                        <button class="btn btn-sm btn-danger" onclick="openInactiveModal(this)">Vô Hiệu Hóa</button>
                    </td>
                </tr>
                <% } %>
                </tbody>
            </table>
        </main>
    </div>
</div>

<!-- Modal Thêm người dùng -->
<div class="modal fade" id="addUserModal" tabindex="-1" aria-hidden="true">
    <div class="modal-dialog">
        <form class="modal-content" method="post" action="AddUserServlet">
            <div class="modal-header bg-success text-white">
                <h5 class="modal-title">Thêm người dùng</h5>
                <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal"></button>
            </div>
            <div class="modal-body">
                <label class="form-label">Username</label>
                <input name="username" class="form-control mb-2" required/>

                <label class="form-label">Mật khẩu</label>
                <input type="password" name="password" class="form-control mb-2" required/>

                <label class="form-label">Họ tên</label>
                <input name="fullName" class="form-control mb-2" required/>

                <label class="form-label">Email</label>
                <input type="email" name="email" class="form-control mb-2" required/>

                <label class="form-label">Vai trò</label>
                <select name="roleId" class="form-select mb-2" required>
                    <% for (Role r : roleList) { %>
                        <option value="<%= r.getRoleId() %>"><%= r.getRoleName() %></option>
                    <% } %>
                </select>

                <label class="form-label">Trạng thái</label>
                <select name="isActive" class="form-select" required>
                    <option value="true">Hoạt động</option>
                    <option value="false">Không hoạt động</option>
                </select>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Hủy</button>
                <button type="submit" class="btn btn-success">Lưu</button>
            </div>
        </form>
    </div>
</div>

<!-- Modal Sửa người dùng -->
<div class="modal fade" id="editUserModal" tabindex="-1" aria-hidden="true">
    <div class="modal-dialog">
        <form class="modal-content" method="post" action="EditUserServlet">
            <div class="modal-header bg-info text-white">
                <h5 class="modal-title">Sửa người dùng</h5>
                <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal"></button>
            </div>
            <div class="modal-body">
                <input type="hidden" id="editUserId" name="userId"/>

                <label class="form-label">Họ tên</label>
                <input type="text" id="editName" name="fullName" class="form-control mb-2" required/>

                <label class="form-label">Email</label>
                <input type="email" id="editEmail" name="email" class="form-control mb-2" required/>

                <label class="form-label">Vai trò</label>
                <select id="editRole" name="role" class="form-select mb-2" required>
                    <% for (Role r : roleList) { %>
                        <option value="<%= r.getRoleId() %>"><%= r.getRoleName() %></option>
                    <% } %>
                </select>

                <label class="form-label">Trạng thái</label>
                <select id="editStatus" name="isActive" class="form-select" required>
                    <option value="true">Hoạt động</option>
                    <option value="false">Không hoạt động</option>
                </select>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Hủy</button>
                <button type="submit" class="btn btn-info">Cập nhật</button>
            </div>
        </form>
    </div>
</div>

<div class="modal fade" id="inactiveConfirmModal" tabindex="-1" aria-hidden="true">
    <div class="modal-dialog">
        <form class="modal-content" method="post" action="InactiveUserServlet">
            <div class="modal-header bg-warning text-dark">
                <h5 class="modal-title">Đặt trạng thái Không hoạt động</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
            </div>
            <div class="modal-body">
                Bạn có chắc muốn đặt trạng thái <strong id="inactiveUserName">người dùng này</strong> thành Không hoạt động?
                <input type="hidden" name="userId" id="inactiveUserId"/>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Hủy</button>
                <button type="submit" class="btn btn-warning">Xác nhận</button>
            </div>
        </form>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
<script>
    function openEditModal(userId, fullName, email, roleId, isActive) {
        document.getElementById('editUserId').value = userId;
        document.getElementById('editName').value = fullName;
        document.getElementById('editEmail').value = email;
        document.getElementById('editRole').value = roleId;
        document.getElementById('editStatus').value = (isActive === 'true' || isActive === true) ? 'true' : 'false';

        let modal = new bootstrap.Modal(document.getElementById('editUserModal'));
        modal.show();
    }

    function openInactiveModal(btn) {
        let row = btn.closest("tr");
        document.getElementById("inactiveUserId").value = row.cells[0].innerText;
        document.getElementById("inactiveUserName").innerText = row.cells[1].innerText;

        let inactiveModal = new bootstrap.Modal(document.getElementById("inactiveConfirmModal"));
        inactiveModal.show();
    }
</script>
</body>
</html>
