import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "APIServlet")
public class APIServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            if(request != null && response != null && response.getStatus() == 200) {
                String apiOption = request.getParameter("api");
                String apiCall = new String();
                JsonObject root = CacooSettings.callCacooAPI(request, response, "account", "JSON");
                if(root == null) throw new Exception();
                switch(apiOption) {
                    case "Account":
                        apiCall = "account";
                        root = CacooSettings.callCacooAPI(request, response, apiCall, "JSON");
                        if(root == null) throw new Exception();
                        else {
                            request.setAttribute("api", "account");
                            request.setAttribute("name", root.get("name"));
                            request.setAttribute("nickname", root.get("nickname"));
                            request.setAttribute("type", root.get("type"));
                            request.setAttribute("imageUrl", root.get("imageUrl"));
                        }
                        break;
                    case "Diagrams":
                        apiCall = "diagrams";
                        root = CacooSettings.callCacooAPI(request, response, apiCall, "JSON");
                        if(root == null) throw new Exception();
                        else {
                            request.setAttribute("api", "diagrams");
                            JsonArray arr1 = root.get("result").getAsJsonArray();
                            List<Map<String, String>> diagrams = new ArrayList<>();
                            request.setAttribute("diagrams", diagrams);
                            for (int i = 0; i < arr1.size(); i++) {
                                Map<String, String> map = new HashMap<>();
                                diagrams.add(map);
                                JsonObject obj = arr1.get(i).getAsJsonObject();
                                map.put("imageUrl", obj.get("imageUrl").getAsString());
                                map.put("diagramId", obj.get("diagramId").getAsString());
                                map.put("title", obj.get("title").getAsString());
                            }
                        }
                        break;
                    case "Folders":
                        apiCall = "folders";
                        root = CacooSettings.callCacooAPI(request, response, apiCall, "JSON");
                        if(root == null) throw new Exception();
                        else {
                            request.setAttribute("api", "folders");
                            JsonArray arr2 = root.get("result").getAsJsonArray();
                            List<Map<String, String>> folders = new ArrayList<>();
                            request.setAttribute("folders", folders);
                            for (int i = 0; i < arr2.size(); i++) {
                                Map<String, String> map = new HashMap<>();
                                folders.add(map);
                                JsonObject obj = arr2.get(i).getAsJsonObject();
                                map.put("folderId", obj.get("folderId").getAsString());
                                map.put("folderName", obj.get("folderName").getAsString());
                                map.put("type", obj.get("type").getAsString());
                                map.put("created", obj.get("created").getAsString());
                                map.put("updated", obj.get("updated").getAsString());
                            }
                        }
                        break;
                    case "License":
                        apiCall = "account/license";
                        root = CacooSettings.callCacooAPI(request, response, apiCall, "JSON");
                        if(root == null) throw new Exception();
                        else {
                            request.setAttribute("api", "license");
                            request.setAttribute("plan", root.get("plan"));
                            request.setAttribute("remainingSheets", root.get("remainingSheets"));
                            request.setAttribute("remainingSharedFolders", root.get("remainingSharedFolders"));
                            request.setAttribute("maxNumberOfSharersPerDiagram", root.get("maxNumberOfSharersPerDiagram"));
                            request.setAttribute("maxNumberOfSharersPerSharedFolder", root.get("maxNumberOfSharersPerSharedFolder"));
                            request.setAttribute("canCreateSheet", root.get("canCreateSheet"));
                            request.setAttribute("canCreateSheet", root.get("canCreateSheet"));
                            request.setAttribute("canCreateSharedFolder", root.get("canCreateSharedFolder"));
                            request.setAttribute("canExportVectorFormat", root.get("canExportVectorFormat"));
                        }
                        break;
                }

                // redirect to the cacoo.jsp page
                request.getRequestDispatcher("/WEB-INF/myapp/cacoo.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("message", "A problem was found in the API Servlet.");
            request.getRequestDispatcher("/WEB-INF/myapp/error.jsp").forward(request, response);
        }

    }
}
