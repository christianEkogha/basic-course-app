package fr.cekogha.coursemanager.util;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;

public class SizeAndDuplicateValidator implements ConstraintValidator<SizeAndDuplicate, List<Long>> {

    private int minSize;
    private boolean duplicate;

    @Override
    public void initialize(SizeAndDuplicate constraintAnnotation) {
        this.minSize = constraintAnnotation.minSize();
        this.duplicate = constraintAnnotation.duplicate();
    }

    public boolean isValid(List<Long> values, ConstraintValidatorContext context){
        if(values == null || values.size() < minSize)
            return false;

        if(!duplicate && values.size() > values.stream().distinct().toList().size())
            return false;

        return true;
    }
}
