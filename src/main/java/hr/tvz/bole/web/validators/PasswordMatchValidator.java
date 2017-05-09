package hr.tvz.bole.web.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import hr.tvz.bole.web.form.UserForm;

public class PasswordMatchValidator implements ConstraintValidator<PasswordMatch, Object> {

	@Override
	public void initialize(PasswordMatch constraintAnnotation) {
	}

	@Override
	public boolean isValid(Object object, ConstraintValidatorContext context) {
		UserForm user = (UserForm) object;
		//ako je register, onda se provjerava matchPass, inace ne!
		if (user.getId() == null) {
			return user.getPassword().equals(user.getMatchPassword());
		}
		return true;
	}
}