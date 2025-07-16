<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Create Request for Quotation</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <style>
        /* Add margin to account for sidebar */
        .main-content {
            margin-left: 250px;
            padding: 20px;
        }
    </style>
</head>
<body>
<jsp:include page="../../../common/sidebar.jsp"></jsp:include>

<div class="main-content">
    <div class="container-fluid">
        <div class="row">
            <div class="col-md-12">
                <div class="card">
                    <div class="card-header d-flex justify-content-between align-items-center">
                        <h4 class="mb-0">Create Request for Quotation</h4>
                        <a href="purchasing?action=list-rfq" class="btn btn-secondary">
                            <i class="fas fa-arrow-left"></i> Back to List
                        </a>
                    </div>
                    <div class="card-body">
                        <form action="purchasing" method="post" id="rfqForm">
                            <input type="hidden" name="action" value="create-rfq">

                            <div class="row">
                                <div class="col-md-6">
                                    <div class="mb-3">
                                        <label for="providerId" class="form-label">Supplier <span
                                                class="text-danger">*</span></label>
                                        <select class="form-select" id="providerId" name="providerId" required>
                                            <option value="">Select Supplier</option>
                                            <c:forEach var="supplier" items="${suppliers}">
                                                <option value="${supplier.supplierId}">${supplier.supplierName}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="mb-3">
                                        <label for="warehouseId" class="form-label">Warehouse <span class="text-danger">*</span></label>
                                        <select class="form-select" id="warehouseId" name="warehouseId" required>
                                            <option value="">Select Warehouse</option>
                                            <c:forEach var="warehouse" items="${warehouses}">
                                                <option value="${warehouse.warehouseId}">${warehouse.warehouseName}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                            </div>

                            <div class="row">
                                <div class="col-md-6">
                                    <div class="mb-3">
                                        <label for="expectedDeliveryDate" class="form-label">Expected Delivery Date
                                            <span class="text-danger">*</span></label>
                                        <input type="date" class="form-control" id="expectedDeliveryDate"
                                               name="expectedDeliveryDate" required>
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="mb-3">
                                        <label for="note" class="form-label">Note</label>
                                        <textarea class="form-control" id="note" name="note" rows="3"></textarea>
                                    </div>
                                </div>
                            </div>

                            <hr>

                            <div class="d-flex justify-content-between align-items-center mb-3">
                                <h5>Products</h5>
                                <button type="button" class="btn btn-sm btn-success" onclick="addProductRow()">
                                    <i class="fas fa-plus"></i> Add Product
                                </button>
                            </div>

                            <div class="table-responsive">
                                <table class="table table-bordered" id="productTable">
                                    <thead>
                                    <tr>
                                        <th>Product</th>
                                        <th>Quantity</th>
                                        <th>Action</th>
                                    </tr>
                                    </thead>
                                    <tbody id="productTableBody">
                                    <tr>
                                        <td>
                                            <select class="form-select" name="productId" required>
                                                <option value="">Select Product</option>
                                                <c:forEach var="product" items="${products}">
                                                    <option value="${product.productId}">${product.productName}
                                                        (${product.productCode})
                                                    </option>
                                                </c:forEach>
                                            </select>
                                        </td>
                                        <td>
                                            <input type="number" class="form-control" name="quantity" min="1" required>
                                        </td>
                                        <td>
                                            <button type="button" class="btn btn-sm btn-danger"
                                                    onclick="removeProductRow(this)">
                                                <i class="fas fa-trash"></i>
                                            </button>
                                        </td>
                                    </tr>
                                    </tbody>
                                </table>
                            </div>

                            <div class="d-flex justify-content-end gap-2">
                                <a href="purchasing?action=list-rfq" class="btn btn-secondary">Cancel</a>
                                <button type="submit" class="btn btn-primary">Create RFQ</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

<script>
    // Set minimum date to today
    document.getElementById('expectedDeliveryDate').min = new Date().toISOString().split('T')[0];

    function addProductRow() {
        const tbody = document.getElementById('productTableBody');
        const newRow = tbody.rows[0].cloneNode(true);

        // Clear the values
        newRow.querySelectorAll('select').forEach(select => select.selectedIndex = 0);
        newRow.querySelectorAll('input').forEach(input => input.value = '');

        tbody.appendChild(newRow);
    }

    function removeProductRow(button) {
        const tbody = document.getElementById('productTableBody');
        if (tbody.rows.length > 1) {
            button.closest('tr').remove();
        } else {
            alert('At least one product is required');
        }
    }

    // Form validation
    document.getElementById('rfqForm').addEventListener('submit', function (e) {
        const productSelects = document.querySelectorAll('select[name="productId"]');
        const quantityInputs = document.querySelectorAll('input[name="quantity"]');

        let hasProduct = false;
        for (let i = 0; i < productSelects.length; i++) {
            if (productSelects[i].value && quantityInputs[i].value) {
                hasProduct = true;
                break;
            }
        }

        if (!hasProduct) {
            e.preventDefault();
            alert('Please add at least one product with quantity');
        }
    });
</script>
</body>
</html> 