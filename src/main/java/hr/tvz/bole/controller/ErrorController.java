package hr.tvz.bole.controller;

import java.nio.file.AccessDeniedException;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

//TODO - lovi i @Valid errors
@ControllerAdvice
public class ErrorController {

	private static Logger logger = LoggerFactory.getLogger(ErrorController.class);

	// @ExceptionHandler(Throwable.class)
	// @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	// public String exceptionInternal(final Throwable throwable, final Model
	// model) {
	// logger.error("Exception during execution of SpringSecurity application",
	// throwable);
	// String errorMessage = (throwable != null ? throwable.getMessage() :
	// "Unknown error");
	// model.addAttribute("errorMessage", errorMessage);
	// return "error";
	// }

	//TODO - ne radi
//	@ExceptionHandler(value = Exception.class)
//	public String defaultErrorHandler(HttpServletRequest req, Exception e, Model model) throws Exception {
//		// If the exception is annotated with @ResponseStatus rethrow it and let
//		// the framework handle it - like the OrderNotFoundException example
//		// at the start of this post.
//		// AnnotationUtils is a Spring Framework utility class.
//		System.out.println("error");
//		if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null)
//			throw e;
//
//		// Otherwise setup and send the user to a default error-view.
//		model.addAttribute("exception", e);
//		model.addAttribute("url", req.getRequestURL());
//		return "error";
//	}
	
	

}