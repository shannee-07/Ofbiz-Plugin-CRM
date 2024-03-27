package com.example.crm.services;

import org.apache.ofbiz.service.ModelService;
import org.apache.ofbiz.common.email.EmailServices;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.HashMap;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.sql.Timestamp;

import java.util.Locale;


import org.apache.ofbiz.service.LocalDispatcher;
import org.apache.ofbiz.base.util.Debug;
import org.apache.ofbiz.base.util.UtilMisc;
import org.apache.ofbiz.base.util.UtilValidate;
import org.apache.ofbiz.entity.Delegator;
import org.apache.ofbiz.entity.GenericEntityException;
import org.apache.ofbiz.entity.GenericValue;
import org.apache.ofbiz.entity.condition.EntityCondition;
import org.apache.ofbiz.entity.condition.EntityOperator;
import org.apache.ofbiz.entity.util.EntityQuery;
import org.apache.ofbiz.entity.util.EntityUtil;
import org.apache.ofbiz.product.product.ProductWorker;
import org.apache.ofbiz.service.DispatchContext;
import org.apache.ofbiz.service.ServiceUtil;


public class CRMServices {

    public static final String module = CRMServices.class.getName();

    public static Map<String, Object> createCoupon(DispatchContext dctx, Map<String, ? extends Object> context) {
        Map<String, Object> result = ServiceUtil.returnSuccess();
        Delegator delegator = dctx.getDelegator();
        try {
            GenericValue coupon = delegator.makeValue("Coupon");
            coupon.setNextSeqId();
            coupon.setNonPKFields(context);

//            result.put("couponId", coupon.getString("couponId"));


            System.out.println("context is \n\n\n\n\n");
            System.out.println(context);
            System.out.println("\n\n\n\n\n");



            //coupon = delegator.create(coupon);
            //result.put("couponId", coupon.getString("couponId"));
        } catch (Exception e) {
            Debug.logError(e, module);
            return ServiceUtil.returnError("Error in creating record in OfbizDemo entity ........" + module);
        }
        return result;
    }

    public static Map<String, Object> getSummary(DispatchContext ctx, Map<String, Object> context) {
        Delegator delegator = ctx.getDelegator();

        try {

            // [1] Get month wise orders:

            Map<String, Object> summaryByMonth = new HashMap<>();

            List<GenericValue> orderHeaders = EntityQuery.use(delegator).from("OrderHeader").orderBy("entryDate ASC").queryList();

            // Process the data
            List<Map<String, Object>> monthWiseOrders = new ArrayList<>();
            for (GenericValue orderHeader : orderHeaders) {
                Timestamp entryDateTimestamp = orderHeader.getTimestamp("entryDate");
                if (entryDateTimestamp != null) {
                    LocalDate entryDate = entryDateTimestamp.toLocalDateTime().toLocalDate();
                    String formattedDate = entryDate.format(DateTimeFormatter.ofPattern("yyyy-MM"));
                    boolean found = false;
                    // Check if the formattedDate already exists in monthWiseOrders
                    for (Map<String, Object> map : monthWiseOrders) {
                        if (map.containsKey(formattedDate)) {
                            // If it exists, increment the count by 1
                            map.put(formattedDate, (Integer) map.get(formattedDate) + 1);
                            found = true;
                            break;
                        }
                    }
                    if (!found) {
                        // If it doesn't exist, add a new map with the formattedDate and a count of 1
                        Map<String, Object> monthOrders = new HashMap<>();
                        monthOrders.put(formattedDate, 1);
                        monthWiseOrders.add(monthOrders);
                    }
                }
            }


            // Prepare the result
//            summaryByMonth.put("monthWiseOrders", monthWiseOrders);

            // completed

            // [2] Completed Items Count
            long completedItemsCount = EntityQuery.use(delegator).from("OrderItem").where("statusId", "ITEM_COMPLETED").queryCount();

            List<GenericValue> completedOrders = EntityQuery.use(delegator).from("OrderHeader").where("statusId", "ORDER_COMPLETED").queryList();

            // [3] Completed Orders Count
            long completedOrdersCount = completedOrders.size();

            // [4] Total revenue:
            BigDecimal totalRevenue = BigDecimal.ZERO;
            for (GenericValue order : completedOrders) {
                BigDecimal grandTotal = order.getBigDecimal("grandTotal");
                if (grandTotal != null) {
                    totalRevenue = totalRevenue.add(grandTotal);
                }
            }

            // [5] Total Customers:
            long totalCustomers = EntityQuery.use(delegator).from("PartyPersonAndRole").where("roleTypeId", "CUSTOMER").queryCount();

            // [6] Top Customers based on spent amount:
            List<GenericValue> topCustomers = EntityQuery.use(delegator).select("partyId", "firstName", "lastName", "totalSpending", "totalOrders").from("TopCustomers").orderBy("totalSpending DESC").queryList();

            // [7] Latest Orders
            List<GenericValue> latestOrders = EntityQuery.use(delegator).from("LatestOrders").orderBy("entryDate DESC").queryList();

            // long totalRevenue = EntityQuery.use(delegator).from("")

            Map<String, Object> res = ServiceUtil.returnSuccess();
            res.put("completedItemsCount", completedItemsCount);
            res.put("completedOrdersCount", completedOrdersCount);
            res.put("monthWiseOrders", monthWiseOrders);
            res.put("totalRevenue", totalRevenue);
            res.put("totalCustomers", totalCustomers);
            res.put("topCustomers", topCustomers);
            res.put("latestOrders", latestOrders);


            return res;

        } catch (Exception e) {
            Map<String, Object> res = ServiceUtil.returnSuccess();
            res.put("response", e);
            System.out.println("\nERROR IN QUERY\n=======================================\n");
            System.out.println(res);
            return res;
        }
    }

    public static Map<String, Object> ordersAnalysis(DispatchContext ctx, Map<String, Object> context) {
        Delegator delegator = ctx.getDelegator();
        Map<String, Object> res = ServiceUtil.returnSuccess();

        try {
            List<GenericValue> cityWiseOrders = EntityQuery.use(delegator).from("CityWiseOrders").orderBy("totalOrders DESC").queryList();
            res.put("cityWiseOrders", cityWiseOrders);
        } catch (Exception e) {
            res.put("responseMessage", "Error: " + e.getMessage());
            e.printStackTrace();
        }


        return res;
    }


}

//        try {
//            context.put("communicationEventId", "1");
//            context.put("locale", Locale.US);
//            context.put("subject", "Test Email");
//            context.put("body", "This is a test email.");
//            context.put("sendTo", "shanneeahirwar@gmail.com");
//            context.put("sendFrom", "shanneeahirwar20174@acropolis.in");
//            context.put("sendType", "mail.smtp.host");
//            context.put("sendVia", "smtp.example.com");
//
//            // Call the sendMail method
//            Map<String, Object> result = EmailServices.sendMail(ctx, context);
//
//            // Check the result
//            if (ServiceUtil.isSuccess(result)) {
//                System.out.println("Email sent successfully.");
//            } else {
//                System.out.println("Failed to send email: " + ServiceUtil.getErrorMessage(result));
//            }
//        } catch (Exception e) {
//
//        }