<%--
  Created by IntelliJ IDEA.
  User: cbc
  Date: 2024-10-22
  Time: 9:55 p.m.
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Register - Academic Exchange Platform</title>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/auth.css" rel="stylesheet">
</head>
<body>
    <div class="container">
        <div class="row justify-content-center align-items-center min-vh-100">
            <div class="col-md-8 col-lg-6">
                <div class="card shadow">
                    <div class="card-body">
                        <h2 class="card-title text-center mb-4">Create Your AEP Account</h2>
                        <div class="d-flex flex-column">
                            <a href="registerProfessional.jsp" class="btn btn-primary mb-3">Register as Professional</a>
                            <a href="registerInstitution.jsp" class="btn btn-secondary">Register as Institution</a>
                        </div>
                        <p class="mt-3 text-center">Already have an account? <a href="login.jsp">Login here</a></p>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
