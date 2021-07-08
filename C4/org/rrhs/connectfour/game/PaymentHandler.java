package org.rrhs.connectfour.game;
import com.stripe.*;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.model.Token;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class PaymentHandler {
    public PaymentHandler(PaymentButton b) {
        Stripe.apiKey = "rk_test_N4o8Mm4QmuEwYIdb5c6sFeyO007hwhfgNH";
        Map<String, Object> card = new HashMap<>();
        Scanner s = new Scanner(System.in);
        System.out.print("Enter credit card number> ");
        card.put("number", s.nextLine());
        System.out.print("Enter expiration month> ");
        card.put("exp_month", s.nextLine());
        System.out.print("Enter expiration year> ");
        card.put("exp_year", s.nextLine());
        System.out.print("Enter CVC> ");
        card.put("cvc", s.nextLine());
        Map<String, Object> params = new HashMap<>();
        params.put("card", card);
        Token token = null;
        try {
            token = Token.create(params);
            params.clear();
            params.put("amount", 699);
            params.put("currency", "usd");

            params.put("source", token.getId());
            params.put("description", "Connect4 Instant Win");
        } catch (StripeException deets) {
            System.out.println("We had a problem processing that card. Please try again.");
            System.out.println("details for nerds: " + deets.toString());
            new PaymentHandler(b);
        }
        try {
            Charge charge = Charge.create(params);
            if (charge.getStatus().equals("succeeded")) System.out.println("Success! Payment complete.");
            else System.out.println("Something didn't work quite right... try again later.");
            //System.out.println(charge.toString());
        } catch (StripeException e) {
            System.out.println("Something went wrong: " + e.toString());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new PaymentHandler(null);
    }
}

