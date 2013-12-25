package Helpers;

import java.math.BigDecimal;

public class MathHelper
{
    public static float roundNumber(Double inputNum,int minFractionDigits)
    {
    

    float value = (new BigDecimal(inputNum).setScale(
    minFractionDigits, BigDecimal.ROUND_HALF_UP)).floatValue();
 
    return value;
    } 
}
