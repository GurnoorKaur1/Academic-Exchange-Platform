<%--
  Created by IntelliJ IDEA.
  User: cbc
  Date: 2024-10-23
  Time: 2:44 p.m.
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${param.title} - AEP Dashboard</title>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/dashboard.css" rel="stylesheet">


    <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.3/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</head>
<body>
    <div class="wrapper">
        <nav id="sidebar" class="col-md-3 col-lg-2 d-md-block bg-light sidebar">
            <div class="sidebar-sticky pt-3">
                <h6 class="sidebar-heading d-flex justify-content-between align-items-center pt-2 pb-3 px-3 mb-1 text-muted border-bottom" style="font-size: 0.9rem;">
                    AEP Dashboard
                </h6>
                <ul class="nav flex-column">
                    <li class="nav-item">
                        <a class="nav-link" href="#profileCreation">
                            Profile
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="#courseManagement">
                            Course Management
                        </a>
                        <ul class="nav flex-column ml-3">
                            <li class="nav-item">
                                <a class="nav-link" href="#createCourse">Create Course</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="#editCourses">Edit Courses</a>
                            </li>
                        </ul>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="#courseRequests">
                            Course Requests
                        </a>
                    </li>
                </ul>
            </div>
        </nav>

        <main role="main" class="col-md-9 ml-sm-auto col-lg-10 px-md-4">
            <div class="d-flex justify-content-end flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
                <div class="btn-toolbar mb-2 mb-md-0">
                    <div class="btn-group mr-2 d-flex align-items-center">
                        <span class="mr-2">${sessionScope.name}</span>
                        <a href="logout" class="btn btn-sm btn-outline-danger">
                            <i class="fas fa-sign-out-alt"></i> Logout
                        </a>
                    </div>
                </div>
            </div>
            <jsp:include page="${param.content}" />
        </main>
    </div>

    <script src="${pageContext.request.contextPath}/js/dashboard.js"></script>
</body>
</html>
