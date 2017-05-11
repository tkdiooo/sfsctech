<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.sfsctech.common.util.SpringContextUtil" %>
<%@ page import="com.sfsctech.common.spring.properties.Application" %>
<%
    // 系统配置信息
    Application appConfig = (Application)SpringContextUtil.getBean("application");
%>
<%-- 静态资源服务路径 --%>
<c:set var="static_resource" scope="application" value="<%= appConfig.getStaticResources() %>"/>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="renderer" content="webkit">
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no, minimal-ui">
    <meta name="author" content="IKKI">
    <meta name="copyright" content="外服信">
    <title>会议支持系统</title>
    <%-- css --%>
    <link rel="stylesheet" href="${static_resource}/static/css/admin_base.css}">
    <link rel="stylesheet" href="@{/static/css/admin.css}">
    <%-- javascript --%>
    <script type="text/javascript" src="${static_resource}/webjars/jquery/3.2.1/dist/jquery.min.js"></script>
    <script type="text/javascript" src="${static_resource}/webjars/jquery-form/3.51/jquery.form.js"></script>
    <script type="text/javascript" src="${static_resource}/webjars/bootstrap/3.3.7/dist/js/bootstrap.min.js"></script>
</head>