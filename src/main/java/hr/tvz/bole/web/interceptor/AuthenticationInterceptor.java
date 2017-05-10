package hr.tvz.bole.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import hr.tvz.bole.model.CurrentUser;
import hr.tvz.bole.server.repository.CurrentUserRepository;
import hr.tvz.bole.server.service.RoleService;
import hr.tvz.bole.server.service.UserService;

@ControllerAdvice
public class AuthenticationInterceptor extends HandlerInterceptorAdapter {

	private static Logger logger = LoggerFactory.getLogger(AuthenticationInterceptor.class);

	@Autowired
	CurrentUserRepository currentUserRepository;
	@Autowired
	UserService userService;
	@Autowired
	RoleService roleService;

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
			Object handler, Exception exception) throws Exception {
		// TODO Auto-generated method stub
		// System.out.println("after");
		if (exception != null) {
			System.out.println("exception:");
			System.out.println(exception);
		}

	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		// System.out.println("post");

	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object handler) throws Exception {
		if (request.getUserPrincipal() == null) {
			logger.info("INTERCEPTOR - not logged in");
			return true;
		}

		CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
		if (currentUser == null) {
			currentUser = userService.getCurrentUser(request.getUserPrincipal().getName());
			request.getSession().setAttribute("currentUser", currentUser);
			logger.info("INTERCEPTOR - logged in as: " + currentUser.getUsername());
		}
		
		return true;
	}

}