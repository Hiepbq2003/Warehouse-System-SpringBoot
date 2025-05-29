--  database
CREATE DATABASE clothes_warehouse;
GO

USE clothes_warehouse;
GO

-- Roles
CREATE TABLE roles (
    role_id INT PRIMARY KEY IDENTITY(1,1),
    role_name NVARCHAR(50) NOT NULL
);
GO

-- Users
CREATE TABLE users (
    user_id INT PRIMARY KEY IDENTITY(1,1),
    username NVARCHAR(100) NOT NULL,
    password_hash NVARCHAR(255) NOT NULL,
    full_name NVARCHAR(255),
    email NVARCHAR(255) NOT NULL,
    role_id INT,
    created_at DATETIME DEFAULT GETDATE(),
    updated_at DATETIME DEFAULT GETDATE(),
	isActive BIT DEFAULT 1,
    CONSTRAINT FK_users_roles FOREIGN KEY (role_id) REFERENCES roles(role_id)
);
GO
-- Customers
CREATE TABLE customers (
    customer_id INT PRIMARY KEY IDENTITY(1,1),
    customer_name NVARCHAR(255) NOT NULL,
    phone NVARCHAR(20),
    email NVARCHAR(100),
    address NVARCHAR(255)
);
GO

-- Order Status
CREATE TABLE order_status (
    status_id INT PRIMARY KEY IDENTITY(1,1),
    status_name NVARCHAR(50) NOT NULL
);
GO

-- Adjustment Reasons
CREATE TABLE adjustmentreasons (
    reason_id INT PRIMARY KEY IDENTITY(1,1),
    reason_description NVARCHAR(255),
    created_at DATETIME DEFAULT GETDATE()
);
GO

-- Suppliers
CREATE TABLE suppliers (
    supplier_id INT PRIMARY KEY IDENTITY(1,1),
    supplier_name NVARCHAR(255) NOT NULL,
    contact_person NVARCHAR(255),
    phone_number NVARCHAR(20),
    email NVARCHAR(100),
    address NVARCHAR(255),
    created_at DATETIME DEFAULT GETDATE(),
    updated_at DATETIME DEFAULT GETDATE()
);
GO

-- Categories
CREATE TABLE categories (
    category_id INT PRIMARY KEY IDENTITY(1,1),
    category_name NVARCHAR(255) NOT NULL,
    created_at DATETIME DEFAULT GETDATE(),
    updated_at DATETIME DEFAULT GETDATE()
);
GO

-- Products
CREATE TABLE products (
    product_id INT PRIMARY KEY IDENTITY(1,1),
    product_code NVARCHAR(50) NOT NULL,
    product_name NVARCHAR(255) NOT NULL,
    description NVARCHAR(MAX),
    unit NVARCHAR(50),
    purchase_price DECIMAL(18, 2),
    sale_price DECIMAL(18, 2),
    supplier_id INT,
    category_id INT,
    low_stock_threshold INT DEFAULT 0,
    created_at DATETIME DEFAULT GETDATE(),
    updated_at DATETIME DEFAULT GETDATE(),
    FOREIGN KEY (supplier_id) REFERENCES suppliers(supplier_id),
    FOREIGN KEY (category_id) REFERENCES categories(category_id)
);
GO

-- Inventory
CREATE TABLE inventory (
    inventory_id INT PRIMARY KEY IDENTITY(1,1),
    product_id INT NOT NULL UNIQUE,
    quantity_on_hand INT NOT NULL,
    last_updated DATETIME DEFAULT GETDATE(),
    FOREIGN KEY (product_id) REFERENCES products(pro[clothes_warehouse]duct_id)
);
GO

-- Sales Orders
CREATE TABLE salesorders (
    sales_order_id INT PRIMARY KEY IDENTITY(1,1),
    order_code NVARCHAR(50) NOT NULL,
    customer_id INT,
    user_id INT,
    order_date DATETIME DEFAULT GETDATE(),
    status_id INT,
    notes NVARCHAR(MAX),
    created_at DATETIME DEFAULT GETDATE(),
    updated_at DATETIME DEFAULT GETDATE(),
    FOREIGN KEY (customer_id) REFERENCES customers(customer_id),
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (status_id) REFERENCES order_status(status_id)
);
GO

-- Sales Order Details
CREATE TABLE salesorderdetails (
    order_detail_id INT PRIMARY KEY IDENTITY(1,1),
    sales_order_id INT,
    product_id INT,
    quantity_ordered INT NOT NULL,
    unit_sale_price DECIMAL(18, 2),
    FOREIGN KEY (sales_order_id) REFERENCES salesorders(sales_order_id),
    FOREIGN KEY (product_id) REFERENCES products(product_id)
);
GO

-- Stock Outwards
CREATE TABLE stockoutwards (
    stock_outward_id INT PRIMARY KEY IDENTITY(1,1),
    outward_code NVARCHAR(50) NOT NULL,
    sales_order_id INT,
    user_id INT,
    outward_date DATETIME DEFAULT GETDATE(),
    reason NVARCHAR(255),
    notes NVARCHAR(MAX),
    created_at DATETIME DEFAULT GETDATE(),
    FOREIGN KEY (sales_order_id) REFERENCES salesorders(sales_order_id),
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);
GO

-- Stock Outward Details
CREATE TABLE stockoutwarddetails (
    outward_detail_id INT PRIMARY KEY IDENTITY(1,1),
    stock_outward_id INT,
    product_id INT,
    quantity_shipped INT NOT NULL,
    FOREIGN KEY (stock_outward_id) REFERENCES stockoutwards(stock_outward_id),
    FOREIGN KEY (product_id) REFERENCES products(product_id)
);
GO

-- Purchase Requests
CREATE TABLE purchaserequests (
    request_id INT PRIMARY KEY IDENTITY(1,1),
    request_code NVARCHAR(50) NOT NULL,
    user_id_requester INT,
    request_date DATETIME DEFAULT GETDATE(),
    status NVARCHAR(50),
    notes NVARCHAR(MAX),
    created_at DATETIME DEFAULT GETDATE(),
    FOREIGN KEY (user_id_requester) REFERENCES users(user_id)
);
GO

-- Purchase Request Details
CREATE TABLE purchaserequestdetails (
    request_detail_id INT PRIMARY KEY IDENTITY(1,1),
    request_id INT,
    product_id INT,
    requested_quantity INT NOT NULL,
    supplier_id_suggested INT,
    FOREIGN KEY (request_id) REFERENCES purchaserequests(request_id),
    FOREIGN KEY (product_id) REFERENCES products(product_id),
    FOREIGN KEY (supplier_id_suggested) REFERENCES suppliers(supplier_id)
);
GO

-- Stock Inwards
CREATE TABLE stockinwards (
    stock_inward_id INT PRIMARY KEY IDENTITY(1,1),
    inward_code NVARCHAR(50) NOT NULL,
    supplier_id INT,
    user_id INT,
    inward_date DATETIME DEFAULT GETDATE(),
    notes NVARCHAR(MAX),
    created_at DATETIME DEFAULT GETDATE(),
    FOREIGN KEY (supplier_id) REFERENCES suppliers(supplier_id),
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);
GO

-- Stock Inward Details
CREATE TABLE stockinwarddetails (
    inward_detail_id INT PRIMARY KEY IDENTITY(1,1),
    stock_inward_id INT,
    product_id INT,
    quantity_received INT NOT NULL,
    unit_purchase_price DECIMAL(18, 2),
    FOREIGN KEY (stock_inward_id) REFERENCES stockinwards(stock_inward_id),
    FOREIGN KEY (product_id) REFERENCES products(product_id)
);
GO

-- Stock Takes
CREATE TABLE stocktakes (
    stock_take_id INT PRIMARY KEY IDENTITY(1,1),
    stock_take_code NVARCHAR(50) NOT NULL,
    user_id INT,
    stock_take_date DATETIME DEFAULT GETDATE(),
    notes NVARCHAR(MAX),
    created_at DATETIME DEFAULT GETDATE(),
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);
GO

-- Stock Take Details
CREATE TABLE stocktakedetails (
    stock_take_detail_id INT PRIMARY KEY IDENTITY(1,1),
    stock_take_id INT,
    product_id INT,
    system_quantity INT,
    counted_quantity INT,
    discrepancy AS (counted_quantity - system_quantity) PERSISTED,
    FOREIGN KEY (stock_take_id) REFERENCES stocktakes(stock_take_id),
    FOREIGN KEY (product_id) REFERENCES products(product_id)
);
GO

-- Bills
CREATE TABLE bills (
    bill_id INT PRIMARY KEY IDENTITY(1,1),
    sales_order_id INT,
    bill_date DATETIME DEFAULT GETDATE(),
    total_amount DECIMAL(18, 2),
    created_by INT,
    FOREIGN KEY (sales_order_id) REFERENCES salesorders(sales_order_id),
    FOREIGN KEY (created_by) REFERENCES users(user_id)
);
GO

-- Inventory Adjustments
CREATE TABLE inventory_adjustments (
    adjustment_id INT PRIMARY KEY IDENTITY(1,1),
    product_id INT,
    old_quantity INT,
    new_quantity INT,
    adjusted_by INT,
    reason_id INT,
    adjusted_at DATETIME DEFAULT GETDATE(),
    FOREIGN KEY (product_id) REFERENCES products(product_id),
    FOREIGN KEY (adjusted_by) REFERENCES users(user_id),
    FOREIGN KEY (reason_id) REFERENCES adjustmentreasons(reason_id)
);
GO
ALTER TABLE users ADD isActive BIT DEFAULT 1;
 go 
 SET IDENTITY_INSERT roles ON;
 go
 INSERT INTO roles (role_id, role_name) VALUES
(1, 'customer'),
(2, 'staff'),
(3, 'manager'),
(4, 'admin');
go
ALTER TABLE products ADD isActive BIT DEFAULT 1;