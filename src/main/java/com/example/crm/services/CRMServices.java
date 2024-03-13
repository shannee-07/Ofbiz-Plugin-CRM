package com.example.crm.services;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

    public static Map<String, Object> xyz(DispatchContext dctx, Map<String, ? extends Object> context) {
        Map<String, Object> result = ServiceUtil.returnSuccess();
        Delegator delegator = dctx.getDelegator();
        try {
            GenericValue ofbizDemo = delegator.makeValue("OfbizDemo");
            // Auto generating next sequence of ofbizDemoId primary key
            ofbizDemo.setNextSeqId();
            // Setting up all non primary key field values from context map
            ofbizDemo.setNonPKFields(context);
            // Creating record in database for OfbizDemo entity for prepared value
            ofbizDemo = delegator.create(ofbizDemo);
            result.put("ofbizDemoId", ofbizDemo.getString("ofbizDemoId"));
            Debug.log("==========This is my first Java Service implementation in Apache OFBiz. OfbizDemo record created successfully with ofbizDemoId:"+ofbizDemo.getString("ofbizDemoId"));
        } catch (GenericEntityException e) {
            Debug.logError(e, module);
            return ServiceUtil.returnError("Error in creating record in OfbizDemo entity ........" +module);
        }
        return result;
    }

    public static Map<String, Object> getSummary(DispatchContext ctx, Map<String, Object> context) {
        Delegator delegator = ctx.getDelegator();

        try {
            List<GenericValue> list = EntityQuery.use(delegator)
                    .select("orderId","entryDate","grandTotal", "orderTypeId")
                    .from("OrderHeader")
                    .orderBy("grandTotal DESC")
                    .where(EntityCondition.makeCondition("grandTotal", EntityOperator.GREATER_THAN,  BigDecimal.valueOf(100)))
                    .queryList();
            Map<String,Object> res = ServiceUtil.returnSuccess();
            res.put("response",list);
            return res;

        }catch (Exception e){
            Map<String,Object> res = ServiceUtil.returnSuccess();
            res.put("response",e);
            System.out.println("\nERROR IN QUERY\n=======================================\n");
            System.out.println(res);
            return res;
//            return e;
        }


//        String idToFind = (String) context.get("idToFind");
////        String goodIdentificationTypeId = (String) context.get("goodIdentificationTypeId");
//        String searchProductFirstContext = (String) context.get("searchProductFirst");
//        String searchAllIdContext = (String) context.get("searchAllId");
//
//        boolean searchProductFirst = UtilValidate.isNotEmpty(searchProductFirstContext) && "N".equals(searchProductFirstContext) ? false : true;
//        boolean searchAllId = UtilValidate.isNotEmpty(searchAllIdContext) && "Y".equals(searchAllIdContext) ? true : false;
//
//        GenericValue product = null;
//        List<GenericValue> productsFound = null;
//        try {
//            productsFound = ProductWorker.findProductsById(delegator, idToFind, searchProductFirst, searchAllId);
//        } catch (GenericEntityException e) {
////            Debug.logError(e, MODULE);
//            return ServiceUtil.returnError(e.getMessage());
//        }
//
//        if (UtilValidate.isNotEmpty(productsFound)) {
//            // gets the first productId of the List
//            product = EntityUtil.getFirst(productsFound);
//            // remove this productId
//            productsFound.remove(0);
//        }
//
//        Map<String, Object> result = ServiceUtil.returnSuccess();
//        result.put("product", product);
//        result.put("productsList", productsFound);
//
//        return result;
    }
}
