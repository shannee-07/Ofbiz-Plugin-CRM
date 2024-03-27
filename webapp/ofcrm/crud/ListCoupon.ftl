<table class="table table-bordered table-striped table-hover">

    <thead>
        <tr>
            <th>${uiLabelMap.CouponId}</th>
            <th>${uiLabelMap.CouponDescription}</th>
            <th>${uiLabelMap.CouponTypeId}</th>
            <th>${uiLabelMap.CouponDiscountPercentage}</th>
            <th>${uiLabelMap.CouponExpiryDate}</th>

            <th>${uiLabelMap.productCategoryId}</th>
            <th>${uiLabelMap.productId}</th>
            <th>${uiLabelMap.upto}</th>
            <th>${uiLabelMap.minPurchase}</th>

        </tr>
    </thead>
    <tbody>
        <#if couponList??>
            <#list couponList as coupon>
                <tr>
                    <td>${coupon.couponId?default("NA")}</td>
                    <td>${coupon.description?default("NA")}</td>
                    <td>${coupon.couponTypeId}</td>
                    <td>${coupon.discountPercentage!}</td>
                    <td>${coupon.expiryDate!}</td>

                    <td>${coupon.productCategoryId!}</td>
                    <td>${coupon.productId!}</td>
                    <td>${coupon.upto!}</td>
                    <td>${coupon.minPurchase!}</td>

                </tr>
            </#list>
        <#else>
            <tr>
                <td colspan="4">No coupons available</td>
            </tr>
        </#if>
    </tbody>
</table>
