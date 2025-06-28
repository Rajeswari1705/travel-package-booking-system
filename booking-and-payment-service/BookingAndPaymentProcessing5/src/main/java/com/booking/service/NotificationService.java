package com.booking.service;

import com.booking.entity.Booking;
import com.booking.entity.Payment;
import com.booking.dto.UserDTO;
import com.booking.client.TravelPackageClient;
import com.booking.client.UserClient;
import com.booking.dto.TravelPackageDTO;
import com.booking.exception.CustomBusinessException;
import feign.FeignException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Service class for handling notification-related operations.
 * This class provides methods for sending booking details to customers and travel agents via email.
 */
@Service
public class NotificationService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private UserClient userClient;

    @Autowired
    private TravelPackageClient travelPackageClient;

    private static final Logger log = LoggerFactory.getLogger(NotificationService.class);

    private final String senderEmail = "indhaanman@gmail.com";

    /**
     * Send booking details to the customer via email.
     * 
     * @param booking The booking entity containing the booking details.
     * @param payment The payment entity containing the payment details.
     */
    public void notifyCustomer(Booking booking, Payment payment) {
        UserDTO user = userClient.getCustomerById(booking.getUserId());
        String customerEmail = user.getEmail();

        String subject = "Your Travel Booking is Confirmed – Booking ID: " + booking.getBookingId();
        String body = "Dear Customer,\n\n"
                + "Thank you for booking your travel with us!\n\n"
                + "Your booking has been successfully confirmed. Here are the details:\n\n"
                + "- Booking ID: " + booking.getBookingId() + "\n"
                + "- Package ID: " + booking.getPackageId() + "\n"
                + "- Travel Dates: " + booking.getTripStartDate() + " to " + booking.getTripEndDate() + "\n"
                + "- Payment Amount: " + payment.getAmount() + " " + payment.getCurrency() + "\n"
                + "- Payment Status: " + payment.getStatus() + "\n\n"
                + "We look forward to providing you with a wonderful travel experience.\n\n"
                + "Warm regards,\nTravel Booking Team";

        sendEmail(customerEmail, subject, body);
        System.out.println("Email is sent to " + customerEmail);
    }

    /**
     * Send booking details to the travel agent via email.
     * 
     * @param booking The booking entity containing the booking details.
     * @param payment The payment entity containing the payment details.
     */
    public void notifyTravelAgent(Booking booking, Payment payment) {
        TravelPackageDTO pkg = travelPackageClient.getPackageById(booking.getPackageId());

        try {
            String agentEmail = userClient.getCustomerById(pkg.getAgentId()).getEmail();
        } catch (FeignException.Forbidden e) {
            log.error("Forbidden from UserService: {}", e.getMessage());
            throw new CustomBusinessException("User is not a CUSTOMER");
        }
        String agentEmail = userClient.getCustomerById(pkg.getAgentId()).getEmail();

        String subject = "New Booking Received – Booking ID: " + booking.getBookingId();
        String body = "Dear Travel Agent,\n\n"
                + "A new booking has been successfully made. Please find the details below:\n\n"
                + "- Booking ID: " + booking.getBookingId() + "\n"
                + "- Customer ID: " + booking.getUserId() + "\n"
                + "- Package ID: " + booking.getPackageId() + "\n"
                + "- Travel Dates: " + booking.getTripStartDate() + " to " + booking.getTripEndDate() + "\n"
                + "- Payment Amount: " + payment.getAmount() + " " + payment.getCurrency() + "\n"
                + "- Payment Status: " + payment.getStatus() + "\n\n"
                + "Please ensure all arrangements are in place for the customer's travel.\n\n"
                + "Best regards,\nTravel Booking System";

        sendEmail(agentEmail, subject, body);
        System.out.println("Email is sent to " + agentEmail);
    }

    /**
     * Send an email with the specified subject and body to the specified recipient.
     * 
     * @param to The recipient's email address.
     * @param subject The subject of the email.
     * @param body The body of the email.
     */
    private void sendEmail(String to, String subject, String body) {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setFrom(senderEmail);
        mail.setTo(to);
        mail.setSubject(subject);
        mail.setText(body);
        mailSender.send(mail);
    }
}
