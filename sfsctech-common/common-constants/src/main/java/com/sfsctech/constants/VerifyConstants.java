package com.sfsctech.constants;

/**
 * Hibernate Validator
 *
 * @author 张麒 2017/3/20.
 * @version Description:
 */
public class VerifyConstants {

    public static final String Null = "{javax.validation.constraints.Null.message}";
    public static final String NotNull = "{javax.validation.constraints.NotNull.message}";
    public static final String NotBlank = "{org.hibernate.validator.constraints.NotBlank.message}";
    public static final String NotEmpty = "{org.hibernate.validator.constraints.NotEmpty.message}";

    public static final String AssertTrue = "{javax.validation.constraints.AssertTrue.message}";
    public static final String AssertFalse = "{javax.validation.constraints.AssertFalse.message}";

    public static final String Min = "{javax.validation.constraints.Min.message}";
    public static final String Max = "{javax.validation.constraints.Max.message}";
    public static final String DecimalMin = "{javax.validation.constraints.DecimalMin.message}";
    public static final String DecimalMax = "{javax.validation.constraints.DecimalMax.message}";
    public static final String Digits = "{javax.validation.constraints.Digits.message}";

    public static final String Size = "{javax.validation.constraints.Size.message}";
    public static final String Length = "{org.hibernate.validator.constraints.Length.message}";
    public static final String Range = "{org.hibernate.validator.constraints.Range.message}";

    public static final String Past = "{javax.validation.constraints.Past.message}";
    public static final String Future = "{javax.validation.constraints.Future.message}";

}
