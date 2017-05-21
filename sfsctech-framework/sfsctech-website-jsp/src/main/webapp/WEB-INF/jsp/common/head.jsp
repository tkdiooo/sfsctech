<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.sfsctech.common.util.SpringContextUtil" %>
<%@ page import="com.sfsctech.common.spring.properties.AppConfig" %>
<%
    // 系统配置信息
    AppConfig property = (AppConfig)SpringContextUtil.getBean("application");
%>
<%-- 静态资源服务路径 --%>
<c:set var="static_resource" scope="application" value="<%= property.getStaticResources() %>"/>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="renderer" content="webkit">
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no, minimal-ui">
    <meta name="author" content="IKKI">
    <meta name="copyright" content="外服信">
    <title>会议支持系统</title>
    <%-- jquery --%>
    <script type="text/javascript" src="${static_resource}/webjars/jquery/3.2.1/dist/jquery.min.js"></script>
    <%-- bootstrap --%>
    <link rel="stylesheet" href="${static_resource}/webjars/bootstrap/3.3.7/dist/css/bootstrap.min.css">
    <script type="text/javascript" src="${static_resource}/webjars/bootstrap/3.3.7/dist/js/bootstrap.min.js"></script>
    <%-- bootstrapValidator --%>
    <link rel="stylesheet" href="${static_resource}/webjars/bootstrapValidator/0.5.2/dist/css/bootstrapValidator.min.css">
    <script type="text/javascript" src="${static_resource}/webjars/bootstrapValidator/0.5.2/dist/js/bootstrapValidator.min.js"></script>
    <%-- other javascript --%>
    <script type="text/javascript" src="${static_resource}/static/js/jquery-form/jquery.form.min.js"></script>
    <script type="text/javascript" src="${static_resource}/static/js/layer/layer.js"></script>
    <%-- jquery support --%>
    <%--<script type="text/javascript" src="${static_resource}/webjars/jquery-ui/1.12.1/jquery-ui.min.js"></script>--%>
    <%--<script type="text/javascript" src="${static_resource}/webjars/jquery.cookie/1.4.1/jquery.cookie.js"></script>--%>
    <%--<script type="text/javascript" src="${static_resource}/webjars/jQuery.Hotkeys/0.2.0/jquery.hotkeys.js"></script>--%>
    <%-- vue --%>
    <%--<script type="text/javascript" src="${static_resource}/webjars/vue/2.3.0/dist/vue.min.js"></script>--%>
    <%--<script type="text/javascript" src="${static_resource}/webjars/vue-resource/1.2.1/dist/vue-resource.min.js"></script>--%>
    <%-- ztree --%>
    <%--<link rel="stylesheet" href="${static_resource}/webjars/ztree/3.5.0/css/zTreeStyle/zTreeStyle.css">--%>
    <%--<script type="text/javascript" src="${static_resource}/webjars/ztree/3.5.0/js/jquery.ztree.all-3.5.min.js"></script>--%>
    <%-- baidu echarts --%>
    <%--<script type="text/javascript" src="${static_resource}/webjars/echarts/3.3.1/dist/echarts.min.js"></script>--%>
</head>