package com.example.crm.events;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import org.apache.ofbiz.base.util.Debug;
import org.apache.ofbiz.base.util.UtilMisc;
import org.apache.ofbiz.base.util.UtilValidate;
import org.apache.ofbiz.entity.Delegator;
import org.apache.ofbiz.entity.GenericValue;
import org.apache.ofbiz.service.GenericServiceException;
import org.apache.ofbiz.service.LocalDispatcher;

public class CRMEvents {

    public static final String module = CRMEvents.class.getName();

    public static String createCouponEvent(HttpServletRequest request, HttpServletResponse response) {
        Delegator delegator = (Delegator) request.getAttribute("delegator");
        LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");
        GenericValue userLogin = (GenericValue) request.getSession().getAttribute("coupon");

//        String couponId = request.getParameter("couponId");
//        String description = request.getParameter("description");
//        String discountPercentage = request.getParameter("discountPercentage");
//        String expiryDate = request.getParameter("expiryDate");


//        if (UtilValidate.isEmpty(description) || UtilValidate.isEmpty(discountPercentage) || UtilValidate.isEmpty(expiryDate)) {
//            String errMsg = "All fields are required";
//            request.setAttribute("_ERROR_MESSAGE_", errMsg);
//            return "error";
//        }
//        String comments = request.getParameter("comments");

//        Map<String, String> map = new HashMap<>();
//        map.put("couponId", couponId);
//        map.put("description", description);
//        map.put("discountPercentage", discountPercentage);
//        map.put("expiryDate", expiryDate);

        Map<String, String> map = new HashMap<>();
        Enumeration<String> parameterNames = request.getParameterNames();

        while (parameterNames.hasMoreElements()) {
            String paramName = parameterNames.nextElement();
            String paramValue = request.getParameter(paramName);
            map.put(paramName, paramValue);
        }


        try {
            Debug.logInfo("=======Creating Coupon record in event using service createCouponByGroovyService=========", module);
            dispatcher.runSync("createCouponByGroovyService", map);
        } catch (GenericServiceException e) {
            String errMsg = "Unable to create new records in Coupon entity: " + e.toString();
            request.setAttribute("_ERROR_MESSAGE_", errMsg);
            return "error";
        }
        request.setAttribute("_EVENT_MESSAGE_", "Coupon created succesfully.");
        return "success";
    }
}