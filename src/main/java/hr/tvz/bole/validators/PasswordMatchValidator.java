package hr.tvz.bole.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import hr.tvz.bole.form.UserForm;

public class PasswordMatchValidator implements ConstraintValidator<PasswordMatch, Object> {

	@Override
	public void initialize(PasswordMatch constraintAnnotation) {
	}

	@Override
	public boolean isValid(Object object, ConstraintValidatorContext context) {
		UserForm user = (UserForm) object;
		return user.getPassword().equals(user.getMatchPassword());
	}
}
