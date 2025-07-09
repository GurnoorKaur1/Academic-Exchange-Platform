<%--
  Created by IntelliJ IDEA.
  User: cbc
  Date: 2024-10-23
  Time: 11:49 a.m.
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Register as Professional - Academic Exchange Platform</title>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/auth.css" rel="stylesheet">
</head>
<body>
    <div class="container">
        <div class="row justify-content-center align-items-center min-vh-100">
            <div class="col-md-6">
                <div class="card shadow">
                    <div class="card-body">
                        <h2 class="card-title text-center mb-4">Professional Registration</h2>
                        <form action="register" method="post">
                            <input type="hidden" name="userType" value="professional">
                            <div class="form-group">
                                <input type="text" class="form-control" name="name" placeholder="Name" required>
                            </div>
                            <div class="form-group">
                                <input type="email" class="form-control" name="email" placeholder="Email" required>
                            </div>
                            <div class="form-group">
                                <input type="password" class="form-control" name="password" placeholder="Password" required>
                            </div>
                            <div class="form-group">
                                <select class="form-control" name="currentInstitution" required>
                                    <option value="">Select Current Institution</option>
                                    <option value="Algonquin College">Algonquin College</option>
                                    <option value="Carleton University">Carleton University</option>
                                    <option value="McGill University">McGill University</option>
                                    <option value="Queen's University">Queen's University</option>
                                    <option value="Ryerson University">Ryerson University</option>
                                    <option value="University of British Columbia">University of British Columbia</option>
                                    <option value="University of Ottawa">University of Ottawa</option>
                                    <option value="University of Toronto">University of Toronto</option>
                                    <option value="University of Waterloo">University of Waterloo</option>
                                    <option value="Western University">Western University</option>
                                </select>
                            </div>
                            <div class="form-group">
                                <input type="text" class="form-control" name="academicPosition" placeholder="Academic Position">
                            </div>
                            <button type="submit" class="btn btn-primary btn-block">Register</button>
                        </form>
                        <p class="mt-3 text-center">Already have an account? <a href="login.jsp">Login here</a></p>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
