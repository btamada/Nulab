<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    
<!DOCTYPE html>
<html>
	<head>
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/style.css">
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script>
		<script src="<%=request.getContextPath()%>/js/functions.js"></script>
		<title>Main Menu</title>
	</head>
	<body>
		<div>
			<h1>Main Menu</h1>
			<div style="float: right">
                <div id="acctInfoHide">
                    <h3>Account Information: </h3>
                    <img src="<%=request.getAttribute("imageUrl") %>">
                    <p>UID: <%=request.getAttribute("uid") %></p>
                    <p>Name: <%=request.getAttribute("userName") %></p>
                    <p>Account Type: <%=request.getAttribute("type") %></p>
                    </br>
                </div>
				<button id="acctInfoBtn">Hide Account Information</button>
				</br>
				</br>
                <div id="diagStatHide">
                    <h3>Diagram Statistics: </h3>
                    <button onclick="location.href='create'">Create New Diagram</button>
                    <p>Plan Type: <%=request.getAttribute("plan")%></p>
                    <p>Remaing Diagrams: <%=request.getAttribute("remainingSheets")%></p>
                    <p>Remaining Shared Folders: <%=request.getAttribute("remainingSharedFolders")%></p>
                    <p>Max Sharers Per Diagram: <%=request.getAttribute("maxNumberOfSharersPerDiagram")%></p>
                    <p>Max Sharers Per Shared Folder: <%=request.getAttribute("maxNumberOfSharersPerSharedFolder")%></p>
                    <p>Can Create Diagram: <%=request.getAttribute("canCreateSheet")%></p>
                    <p>Can Created Shared Folder: <%=request.getAttribute("canCreateSharedFolder")%></p>
                    <p>Can Export Vector Format: <%=request.getAttribute("canExportVectorFormat")%></p>
                </div>
                <button id="diagStatBtn">Hide Diagram Statistics</button>
			</div>
			<div>
				<%
				List<Map> diagrams = (List<Map>)request.getAttribute("diagrams");
				if(diagrams.isEmpty()){
				%>
					There are no diagrams to show here!
				<% }else{ %>
					<table class="bordered">
						<tr>
							<th></th>
							<th style="width:200px; text-align: center;">Diagram Name</th>
							<th colspan="3" style="text-align: center">Operations</th>
						</tr>
					<% for(Map d : diagrams){ %>
						<tr>
							<td><img height="100" width="100" src=<%=d.get("imageUrl").toString()%>></td>
							<td id="diagName"><a href="detail?diagramId=<%=d.get("diagramId")%>"><%=d.get("title")%></a></td>
							<td>
								<span class="action"><button onclick="location.href='editor?diagramId=<%=d.get("diagramId")%>'">Edit</button></span>
							</td>
							<td>
                                <span class="action"><button onclick="location.href='copyInit?diagramId=<%=d.get("diagramId")%>'">Copy</button></span>
							</td>
							<td>
                                <span class="action"><button onclick="location.href='delete?diagramId=<%=d.get("diagramId")%>'">Delete</button></span>
							</td>
						</tr>		
					<% } %>
				</table>
				<% } %>
			</div>
		</div>
	</body>
</html>