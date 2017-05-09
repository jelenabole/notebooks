package hr.tvz.bole.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

//TODO - default poruka = null
@Service
public class MessageService {

	@Autowired
	MessageSource messages;

	public String getMessage(String code) {
		return messages.getMessage(code, null, LocaleContextHolder.getLocale());
	}

	public String getMessage(String code, Object... arguments) {
		return messages.getMessage(code, arguments, null, LocaleContextHolder.getLocale());
	}
}
