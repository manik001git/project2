<!DOCTYPE html>
<%@page import="java.util.List"%>
<%@page import="com.manik.general.Database.Prepopulate"%>
<%@page import="com.manik.general.javalite.FlowList"%>
<%@page import="com.manik.project.Util.Attributes"%>
<%@page import="com.manik.general.mysql.query.Column"%>
<%@page import="com.manik.general.Instrumentation.Main"%>
<%@page import="com.manik.general.javalite.Configuration"%>
<%@page import="com.manik.project.models.Password"%>
<%@page import="java.sql.Connection"%>
<%@page import="com.manik.general.Database.Database"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
	<meta charset="UTF-8">
	<title>Welcome</title>
	<link href="/project/styles/styles.css" rel="stylesheet" type="text/css">
	<script src="/project/js/main.js" type="text/javascript"></script>
</head>


<body>
	<div id="mainContent" class="w100per h100per overflowhide">
		<table width="100%" height="100%" cellspacing="0" cellpadding="0" border="0">
			<tbody>
				<tr id="freezelayer" style="display: none;">
   					<td class="feezescreen opt-anim" id="freezelayercontent" style="z-index:6001;"></td>
   				</tr>
   				
   				<tr id="topbanner">
					<td class="top_banner">
						<div class="main_menu_box">
							<a class="main_menu_icons">Home</a>
						</div>
					</td>
				</tr>
				<tr id="contentbanner"><td>
					<%
						Password.getPassword();
					%>
				</td></tr>
   			</tbody>
		</table>
	</div>
</body>
</html>