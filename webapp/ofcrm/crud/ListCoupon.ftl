<table class="table table-bordered table-striped table-hover">
    <thead>
        <tr>
            <th>${uiLabelMap.CouponId}</th>
            <th>${uiLabelMap.CouponDescription}</th>
            <th>${uiLabelMap.CouponTypeId}</th>
            <th>${uiLabelMap.CouponDiscountPercentage}</th>
            <th>${uiLabelMap.CouponExpiryDate}</th>
        </tr>
    </thead>
    <tbody>
        <#if couponList??>
            <#list couponList as coupon>
                <tr>
                    <td>${coupon.couponId?default("NA")}</td>
                    <td>${coupon.description?default("NA")}</td>
                     <td>${ofcrm.getRelatedOne("CouponType").get("couponTypeId", locale)}</td>

                    <td>${coupon.discountPercentage!}</td>
                    <td>${coupon.expiryDate!}</td>
                </tr>
            </#list>
        <#else>
            <tr>
                <td colspan="4">No coupons available</td>
            </tr>
        </#if>
    </tbody>
</table>
