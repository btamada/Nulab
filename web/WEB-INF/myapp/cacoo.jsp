<%@ page import="java.util.Map" %>
<%@ page import="java.util.List" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="/WEB-INF/css/style.css">
    <title>Cacoo API Call</title>
</head>
<body>

<table>
<%  String api = request.getAttribute("api").toString(); %>


    <% switch(api) {
        case "account":
    %>
            <h1>My Account Information</h1>
            <tr>
                <td>Name: </td>
                <td><%= request.getAttribute("name") %></td>
            </tr>
            <tr>
                <td>Nickname: </td>
                <td><%= request.getAttribute("nickname") %></td>
            </tr>
            <tr>
                <td>User Type: </td>
                <td><%= request.getAttribute("type") %></td>
            </tr>
            <tr>
                <td>Image URL: </td>
                <td><img src=<%= request.getAttribute("imageUrl")%>></td>
            </tr>
    <%
            break;
        case "folders":
    %>
            <h1>Folders Information</h1>
    <%
            List<Map<String,String>> folders = (List<Map<String, String>>) request.getAttribute("folders");
            if(folders != null) {
                for(Map<String,String> map : folders) {
    %>
            <tr>
                <td>Folder ID: </td>
                <td><%= map.get("folderId") %></td>
            </tr>
            <tr>
                <td>Folder Name: </td>
                <td><%= map.get("folderName")%></td>
            </tr>
            <tr>
                <td>Folder Type: </td>
                <td><%= map.get("type")%></td>
            </tr>
            <tr>
                <td>Creation Date: </td>
                <td><%= map.get("created")%></td>
            </tr>
            <tr>
                <td>Updated Date: </td>
                <td><%= map.get("updated")%></td>
            </tr>
    <%
                }
            }
            break;
        case "license":
    %>
            <h1>License Information</h1>
            <tr>
                <td>Plan: </td>
                <td><%=request.getAttribute("plan")%></td>
            </tr>
            <tr>
                <td>Remaining Sheets: </td>
                <td><%=request.getAttribute("remainingSheets")%></td>
            </tr>
            <tr>
                <td>Remaining Shared Folders: </td>
                <td><%=request.getAttribute("remainingSharedFolders")%></td>
            </tr>
            <tr>
                <td>Maximum Number of Sharers Per Diagram: </td>
                <td><%=request.getAttribute("maxNumberOfSharersPerDiagram")%></td>
            </tr>
            <tr>
                <td>Maximum Number of Sharers Per Shared Folder: </td>
                <td><%=request.getAttribute("maxNumberOfSharersPerSharedFolder")%></td>
            </tr>
            <tr>
                <td>Can Create Sheet: </td>
                <td><%=request.getAttribute("canCreateSheet")%></td>
            </tr>
            <tr>
                <td>Can Create Shared Folder: </td>
                <td><%=request.getAttribute("canCreateSharedFolder")%></td>
            </tr>
            <tr>
                <td>Can Export Vector Format: </td>
                <td><%=request.getAttribute("canExportVectorFormat")%></td>
            </tr>
    <%
            break;
        case "diagrams":
    %>
            <h1>Diagram Information</h1>
    <%
            List<Map<String,String>> diagrams = (List<Map<String, String>>) request.getAttribute("diagrams");
                if(diagrams != null) {
                for(Map<String,String> map : diagrams) {
    %>
                    <tr>
                        <td>Title: </td>
                        <td><%= map.get("title") %></td>
                    </tr>
                    <tr>
                        <td>Diagram ID: </td>
                        <td><%= map.get("diagramId") %></td>
                    </tr>
                    <tr>
                        <td class="diagImg">Image URL: </td>
                        <td><a href="https://cacoo.com/diagrams/<%= map.get("diagramId") %>"><img height="200" width="200" src=<%= map.get("imageUrl") %>></a></td>
                    </tr>
    <%
                    }
                }
            break;
        default:
    %>
            <tr>
                <td>Sample Data 1-1</td>
                <td>Sample Data 2-1</td>
                <td>Sample Data 3-1</td>
            </tr>
            <tr>
                <td>Sample Data 1-2</td>
                <td>Sample Data 2-2</td>
                <td>Sample Data 3-2</td>
            </tr>
            <tr>
                <td>Sample Data 1-3</td>
                <td>Sample Data 2-3</td>
                <td>Sample Data 3-3</td>
            </tr>
    <%
            break;
    }
    %>
</table>
<a href="/">Back to Home Page</a>
</body>
</html>
