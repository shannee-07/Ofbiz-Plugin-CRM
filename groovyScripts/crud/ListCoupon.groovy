//ofbizDemoTypes = delegator.findList("OfbizDemoType", null, null, null, null, false);
//context.ofbizDemoTypes = ofbizDemoTypes;
couponList = delegator.findList("Coupon", null, null, null, null, false);

//couponTypes = from("CouponType").select("")

couponTypes = select("couponTypeId","description").from("CouponType").queryList();
context.couponTypes = couponTypes
context.couponList = couponList;