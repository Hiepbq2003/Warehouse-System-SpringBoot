<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <title>Dashboard</title>
    <link href="../styles/dashboard.css" rel="stylesheet" />
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet" />
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>

</head>
<body>
    <%-- Sidebar --%>
    <jsp:include page="../common/sidebar.jsp" />

    <main class="content">
        <header class="page-header">
            Dashboard Tổng Quan
        </header>

        <div class="dashboard-row">
            <div class="chart-container">
                <h5>Sales Overview</h5>
                <canvas id="salesChart" height="200"></canvas>
            </div>
            <div class="weekly-stat">
                <h5>Weekly Stat</h5>
                <ul>
                    <li>Tuần 1: 1500 đơn hàng</li>
                    <li>Tuần 2: 1750 đơn hàng</li>
                    <li>Tuần 3: 1600 đơn hàng</li>
                    <li>Tuần 4: 1900 đơn hàng</li>
                </ul>
            </div>
        </div>
    </main>

    <script>
        const ctx = document.getElementById('salesChart').getContext('2d');
        const salesChart = new Chart(ctx, {
            type: 'line',
            data: {
                labels: ['Tháng 1', 'Tháng 2', 'Tháng 3', 'Tháng 4', 'Tháng 5', 'Tháng 6'],
                datasets: [{
                    label: 'Doanh thu',
                    data: [1200, 1900, 3000, 2500, 3200, 4000],
                    backgroundColor: 'rgba(13, 110, 253, 0.2)',
                    borderColor: 'rgba(13, 110, 253, 1)',
                    borderWidth: 2,
                    fill: true,
                    tension: 0.3
                }]
            },
            options: {
                responsive: true,
                maintainAspectRatio: false,
                plugins: { legend: { display: true } },
                scales: { y: { beginAtZero: true } }
            }
        });
    </script>
</body>
</html>
