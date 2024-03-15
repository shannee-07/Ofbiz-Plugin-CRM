import org.apache.ofbiz.entity.GenericEntityException;

def createCoupon() {
    result = [:];
    try {
        coupon = delegator.makeValue("Coupon");
        // Auto generating next sequence of ofbizDemoId primary key
        coupon.setNextSeqId();
        // Setting up all non primary key field values from context map
        coupon.setNonPKFields(context);
        // Creating record in database for OfbizDemo entity for prepared value
        coupon = delegator.create(coupon);
        result.couponId = coupon.couponId;
        logInfo("==========This is my first Groovy Service implementation in Apache OFBiz. Coupon record "
                +"created successfully with couponId: "+coupon.getString("couponId"));
    } catch (GenericEntityException e) {
        logError(e.getMessage());
        return error("Error in creating record in OfbizDemo entity ........");
    }
    return result;
}