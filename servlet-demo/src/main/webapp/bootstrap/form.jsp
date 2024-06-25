<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<html>
<head>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<!-- jQuery library -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<!-- Latest compiled JavaScript -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
</head>
<body>
<form action="<%=request.getContextPath()%>/form" method="post">
    <div class="form-group">
        <lable for="email">Email</lable>
        <input type="email" class="form-control" name="email">
    </div>
    <div class="form-group">
        <lable for="pwd">Password</lable>
        <input type="password" class="form-control" name="password">
    </div>
    <div class="checkbox">
        <lable><input type="checkbox"> Remember me</lable>
    </div>
    <button type="submit" class="btn btn-default">Submit</button>
</form>
</body>
</html>
