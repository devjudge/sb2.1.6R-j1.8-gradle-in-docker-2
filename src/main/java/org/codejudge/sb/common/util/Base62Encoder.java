package org.codejudge.sb.common.util;

import java.math.BigDecimal;
import java.util.HashMap;

/**
 * The Base62Encoder provides static methods to convert non-negative longs to Base62 Strings and vice versa.
 * The base 62 number system uses a...z to represent values from 10 - 35 and A...Z to represent values from
 * 36 - 61.
 *
 * @author pmullen
 * @version 1.1
 * @copyright 2005 Firm58
 * @since Version x.x (rocketscience)
 */
public class Base62Encoder
{
    private static final char[] base62DigitsArray =
            { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r',
              's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T',
              'U', 'V', 'W', 'X', 'Y', 'Z' };

    //instantiated and used for decodeBase62 ONLY.
    private static HashMap<Character, Integer> base62Digits = null;

    /**
     * Loads the base62Digits Map, used for decodeBase62 ONLY.
     */
    private static synchronized void loadDigits()
    {
        base62Digits = new HashMap<Character, Integer>();

        for (int i = 0; i < base62DigitsArray.length; i++)
        {
            base62Digits.put(base62DigitsArray[i], i);
        }
    }

    /**
     * Decodes a base 62 String into its long value.
     */
    public static long decodeBase62(String base62)
    {
        if (base62Digits == null)
        {
            loadDigits();
        }

        int exp = base62.length() - 1;
        double result = 0;
        double factor = 0;

        for (int i = 0; i < base62.length(); i++)
        {
            factor = Math.pow(62, exp);
            int digitValue = base62Digits.get(new Character(base62.charAt(i)));
            result += factor * digitValue;
            exp--;
        }

        return (long) result;
    }

    /**
     * Decodes a base 62 String into its long value.
     */
    public static BigDecimal decodeBase62ToBigDecimal(String base62)
    {
        if (base62Digits == null)
        {
            loadDigits();
        }

        BigDecimal result = BigDecimal.ZERO;
        final BigDecimal multiplier = new BigDecimal(62);

        for (int i = 0; i < base62.length(); i++)
        {
            result = result.multiply(multiplier);
            int digitValue = base62Digits.get(new Character(base62.charAt(i)));
            result = result.add(new BigDecimal(digitValue));
        }

        return result;
    }

    /**
     * convenience method to call the StringBuilder version
     *
     * @throws IllegalArgumentException If passed a negative number.
     */
    public static String encodeBase62(long number)
    {
        StringBuilder builder = new StringBuilder();
        encodeBase62(builder, number);
        return builder.toString();
    }

    /**
     * Encodes a non negative long value into a base 62 String.
     *
     * @throws IllegalArgumentException If passed a negative number.
     */
    public static void encodeBase62(StringBuilder result, long number)
    {
        if (number == 0)
        {
            result.append("0");
            return;
        }
        else if (number < 0)
        {
            throw new IllegalArgumentException("Negative number passed to encodeBase62 method not supported.");
        }

        int maxExp = 0;
        int exp = 0;
        boolean maxFound = false;
        boolean done = false;
        double factor = 0;
        double remainder = number;

        //find the greatest base62 exponent needed to represent this number
        while (!maxFound)
        {
            factor = Math.pow(62, maxExp);

            if (factor > number)
            {
                maxFound = true;
                maxExp--;
                exp = maxExp;
            }
            else
            {
                maxExp++;
            }
        }

        //for each base62 exponent find the greatest coefficient
        for (int i = 0; i <= maxExp; i++)
        {
            if (!done)
            {
                factor = Math.pow(62, exp);

                for (int j = 0; j < base62DigitsArray.length; j++)
                {
                    //the previous j was the greatest base62 coefficient for this exponent.
                    if (j * factor > remainder)
                    {
                        result.append(base62DigitsArray[j - 1]);
                        remainder = remainder - ((j - 1) * factor);
                        break;
                    }
                    //this j kills the remainder, we are done, tack on 0's for remaining digits
                    else if (j * factor == remainder)
                    {
                        result.append(base62DigitsArray[j]);
                        remainder = 0;
                        done = true;
                        break;
                    }
                    //we're up to base62 'Z' and still less than remainder, tack on 'Z' and continue.
                    else if (j == base62DigitsArray.length - 1)
                    {
                        result.append(base62DigitsArray[j]);
                        remainder = remainder - (j * factor);
                    }
                }
            }
            else
            {
                result.append('0');
            }

            exp--;
        }
    }
}
