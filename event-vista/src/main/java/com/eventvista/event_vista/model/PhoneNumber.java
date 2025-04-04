//package com.eventvista.event_vista.model;
//
//import jakarta.persistence.Embeddable;
//
//import java.io.Serializable;
//
//@Embeddable
//public class PhoneNumber implements Serializable {
//
//    private String phoneNumber;
//    private boolean isValid;
//
//    // Empty Constructor and Constructor that takes a phone number as input
//    public PhoneNumber() {
//    }
//
//    public PhoneNumber(String phoneNumber) {
//        this.phoneNumber = phoneNumber;
//        this.isValid = validatePhoneNumber(phoneNumber);
//    }
//
//    private static boolean validatePhoneNumber(String phoneNumber) {
//        // Validate phone numbers of format "1234567890" or "+11234567890"
//        if (phoneNumber.matches("\\+\\d(-\\d{3}){2}-\\d{4}"))
//            return true;
//            // Validating phone number with -, . or spaces
//        else if (phoneNumber.matches("\\d{3}[-\\.\\s]\\d{3}[-\\.\\s]\\d{4}"))
//            return true;
//            // Validating phone number with extension length from 3 to 5
//        else if (phoneNumber.matches("\\d{3}-\\d{3}-\\d{4}\\s(x|(ext))\\d{3,5}"))
//            return true;
//            // Validating phone number where area code is in braces ()
//        else if (phoneNumber.matches("\\(\\d{3}\\)-\\d{3}-\\d{4}"))
//            return true;
//            // Validation for India numbers
//        else if (phoneNumber.matches("\\d{4}[-\\.\\s]\\d{3}[-\\.\\s]\\d{3}"))
//            return true;
//        else if (phoneNumber.matches("\\(\\d{5}\\)-\\d{3}-\\d{3}"))
//            return true;
//        else if (phoneNumber.matches("\\(\\d{4}\\)-\\d{3}-\\d{3}"))
//            return true;
//            // Return false if nothing matches the input
//        else
//            return false;
//    }
//
//    // Getters
//    public boolean isValid() {
//        return isValid;
//    }
//
//    public String getPhoneNumber() {
//        return phoneNumber;
//    }
//
//    // Override the toString method for easy output
//    @Override
//    public String toString() {
//        if (isValid) {
//            return "Phone Number " + this.phoneNumber + " is valid.";
//        } else {
//            return "Phone Number " + this.phoneNumber + " is invalid.";
//        }
//    }
//
//}

package com.eventvista.event_vista.model;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.io.Serializable;

@Embeddable
public class PhoneNumber implements Serializable {

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^(\\(\\d{3}\\)\\s?|\\d{3}[-.]?)\\d{3}[-.]?\\d{4}$",
            message = "Please enter a valid phone number (e.g., (123) 456-7890, 123-456-7890, or 1234567890)")
    private String phoneNumber;

    public PhoneNumber() {
    }

    public PhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return phoneNumber;
    }
}
