package org.openmrs.web.controller.policy;

import java.io.ByteArrayInputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.openmrs.Role;
import org.openmrs.User;
import org.openmrs.api.PolicyService;
import org.openmrs.api.UserService;
import org.openmrs.api.context.Context;
import org.openmrs.util.PolicyGenerator;
import org.openmrs.util.UserInformation;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.w3c.dom.Document;

@Controller
public class PolicyMigrationController {
	
	@RequestMapping(value = "/admin/users/policyMig.list", method = RequestMethod.GET)
	public String showUserInfoList(Model model) {
		Map<String, UserInformation> usersMap = getUserInformation();
		
		model.addAttribute("usersMap", usersMap);
		return "admin/users/policyMigrationList";
	}
	
	@RequestMapping(value = "/admin/users/policyMig.list", method = RequestMethod.POST)
	public void generatePolicy(HttpServletResponse response, @RequestParam("method") String methodology) throws Exception {
		
		Map<String, UserInformation> usersMapObject = getUserInformation();
		
		String fileName = "openmrs-role-policy.xml";
		boolean isUserBased = false;
		
		if (usersMapObject != null) {
			
			if (methodology.equals("user")) {
				fileName = "openmrs-user-policy.xml";
				isUserBased = true;
			}
			
			Map<String, UserInformation> usersMap = (Map<String, UserInformation>) usersMapObject;
			PolicyGenerator policyGenerator = PolicyGenerator.getInstance();
			String policyContent = policyGenerator.getPolicyContent(usersMap, isUserBased);
			
			response.setContentType("text/xml");
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
			response.setHeader("Pragma", "public");
			response.setHeader("Cache-Control", "max-age=0");
			
			PrintWriter writer = response.getWriter();
			
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			
			DocumentBuilderFactory docBuildFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docBuildFactory.newDocumentBuilder();
			Document document = docBuilder.parse(new ByteArrayInputStream(policyContent.getBytes()));
			
			DOMSource domSource = new DOMSource(document);
			StreamResult streamResult = new StreamResult(writer);
			transformer.transform(domSource, streamResult);
		}
		
	}
	
	private Map<String, UserInformation> getUserInformation() {
		Map<String, UserInformation> usersMap = new HashMap<String, UserInformation>();
		
		PolicyService policyService = Context.getPolicyService();
		List<String> exceptedUsers = policyService.getAuthExceptedUserList();
		UserService userService = Context.getUserService();
		
		List<User> userList = userService.getAllUsers();
		
		for (User user : userList) {
			if (user != null && !exceptedUsers.contains(user.getUsername())) {
				UserInformation userInfo = new UserInformation();
				
				Set<Role> roles = user.getAllRoles();
				
				for (Role role : roles) {
					userInfo.getAssignedRoles().add(role.getRole());
				}
				
				userInfo.setVirtualRole(policyService.getVirtualRole(user));
				usersMap.put(user.getUsername(), userInfo);
			}
			
		}
		
		return usersMap;
	}
	
}
