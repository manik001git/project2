//$Id$
package com.manik.general.activejdbc.validation;

import java.util.ArrayList;
import java.util.List;

public class ValidationBuilder<T extends Validator> {
		
	protected List<T> validators;

    public ValidationBuilder(List<T> validators){
        this.validators = validators;
    }

    public ValidationBuilder(T validator) {
        validators = new ArrayList<>();
        validators.add(validator);
    }

    public void message(String message){
        for(T validator : validators) {
            validator.setMessage(message);
        }
    }
}
