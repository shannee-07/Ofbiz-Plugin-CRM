package com.example.crm.services;

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
            // Auto generating next sequence of couponId primary key
            coupon.setNextSeqId();
            // Setting up all non primary key field values from context map
            coupon.setNonPKFields(context);
            // Creating record in database for Coupon entity for prepared value
            coupon = delegator.create(coupon);
            result.put("couponId", coupon.getString("couponId"));
            Debug.log("==========This is my first Java Service implementation in Apache OFBiz. OfbizDemo record created successfully with couponId: " + coupon.getString("couponId"));
        } catch (GenericEntityException e) {
            Debug.logError(e, module);
            return ServiceUtil.returnError("Error in creating record in OfbizDemo entity ........" + module);
        }
        return result;
    }

    public static Map<String, Object> getSummary(DispatchContext ctx, Map<String, Object> context) {
        Delegator delegator = ctx.getDelegator();

        try {
//                    .queryList();

            // [1] Get month vise orders:

            Map<String, Object> summaryByMonth = new HashMap<>();

            List<GenericValue> orderHeaders = EntityQuery.use(delegator).from("OrderHeader").orderBy("entryDate ASC").queryList();

            // Process the data
            List<Map<String, Object>> monthViseOrders = new ArrayList<>();
            for (GenericValue orderHeader : orderHeaders) {
                Timestamp entryDateTimestamp = orderHeader.getTimestamp("entryDate");
                if (entryDateTimestamp != null) {
                    LocalDate entryDate = entryDateTimestamp.toLocalDateTime().toLocalDate();
                    String formattedDate = entryDate.format(DateTimeFormatter.ofPattern("yyyy-MM"));
                    boolean found = false;
                    // Check if the formattedDate already exists in monthViseOrders
                    for (Map<String, Object> map : monthViseOrders) {
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
                        monthViseOrders.add(monthOrders);
                    }
                }
            }


            // Prepare the result
//            summaryByMonth.put("monthViseOrders", monthViseOrders);

            // completed

            // [2] Completed Items Count
            long completedItemsCount = EntityQuery.use(delegator).from("OrderItem").where("statusId", "ITEM_COMPLETED").queryCount();

            List<GenericValue>  completedOrders = EntityQuery.use(delegator).from("OrderHeader").where("statusId", "ORDER_COMPLETED").queryList();

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
            List<GenericValue> topCustomers = EntityQuery.use(delegator).select("partyId","firstName","lastName","totalSpending","totalOrders").from("TopCustomers").orderBy("totalSpending DESC").queryList();

            // [7] Latest Orders
            List<GenericValue> latestOrders = EntityQuery.use(delegator).from("LatestOrders").orderBy("entryDate DESC").queryList();


//            long totalRevenue = EntityQuery.use(delegator).from("")

            Map<String, Object> res = ServiceUtil.returnSuccess();
            res.put("completedItemsCount", completedItemsCount);
            res.put("completedOrdersCount", completedOrdersCount);
            res.put("monthViseOrders", monthViseOrders);
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

//    public static Map<String, Object> getSummary(DispatchContext ctx, Map<String, Object> context) {
//        Delegator delegator = ctx.getDelegator();
//
//        try {
//            // Construct the EntityQuery
//
//        } catch (Exception e) {
//            result.put("responseMessage", "Error: " + e.getMessage());
//            e.printStackTrace();
//        }
//
//        return result;
//    }


}
